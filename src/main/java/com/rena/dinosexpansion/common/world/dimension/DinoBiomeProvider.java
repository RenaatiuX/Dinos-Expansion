package com.rena.dinosexpansion.common.world.dimension;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.types.Func;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.noises.SimpleNoiseWithOctaves;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.INoiseGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.layer.Layer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class DinoBiomeProvider extends BiomeProvider {


    public static final Codec<DinoBiomeProvider> CODEC = RecordCodecBuilder.create((instance)
            -> instance.group(Codec.LONG.fieldOf("seed").orElse(DinoChunkGenerator.hackSeed)
            .forGetter((obj) -> obj.seed), RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY)
            .forGetter((obj) -> obj.registry)).apply(instance, instance.
            stable(DinoBiomeProvider::new)));

    private final long seed;
    private final Registry<Biome> registry;
    private final Layer genBiomes;

    protected InterpolatedNoise surface;
    private static final List<RegistryKey<Biome>> POSSIBLE_BIOMES = ImmutableList.of(
            BiomeInit.DESERT.getKey(), BiomeInit.RIVER.getKey(), BiomeInit.RED_DESERT.getKey(),
            BiomeInit.DESERT_HILLS.getKey(), BiomeInit.ALPS.getKey(), BiomeInit.OCEAN.getKey(),
            BiomeInit.DEEP_OCEAN.getKey(), BiomeInit.COLD_OCEAN.getKey(), BiomeInit.DEEP_COLD_OCEAN.getKey(),
            BiomeInit.LUKEWARM_DEEP_OCEAN.getKey(), BiomeInit.LUKEWARM_DEEP_OCEAN.getKey(),
            BiomeInit.WARM_OCEAN.getKey(), BiomeInit.DEEP_WARM_OCEAN.getKey(),
            BiomeInit.FROZEN_OCEAN.getKey(), BiomeInit.DEEP_FROZEN_OCEAN.getKey(),
            BiomeInit.DENSE_SWAMP.getKey(), BiomeInit.CHERRY_FOREST.getKey(),
            BiomeInit.FLOODPLAIN.getKey(), BiomeInit.QUAGMIRE.getKey(),
            BiomeInit.LAKE.getKey(), BiomeInit.ETHEREAL_SWAMP.getKey()
    );

    public DinoBiomeProvider(long seed, Registry<Biome> registry) {
        super(POSSIBLE_BIOMES.stream().map(define -> () -> registry.getOrThrow(define)));
        this.seed = seed;
        this.registry = registry;
        this.genBiomes = DinoLayerUtil.makeLayers(seed, registry);
        surface = new InterpolatedNoise(new SimpleNoiseWithOctaves(2048, .2d, seed), new Point(-1,40), new Point2D.Double(-.2d, 63), new Point2D.Double(.2d, 70), new Point2D.Double(.3d, 95), new Point2D.Double(1, 100));
        //surface = new InterpolatedNoise(new SimpleNoiseWithOctaves(2048, .2d, seed), new Point(-1,50), new Point(1, 100));
    }

    public InterpolatedNoise getSurface() {
        return surface;
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    @Override
    public BiomeProvider getBiomeProvider(long l) {
        return new DinoBiomeProvider(l, registry);
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return sample(registry, x,z);
    }

    public Biome sample(Registry<Biome> dynamicBiomeRegistry, int x, int z) {
        int resultBiomeID = this.genBiomes.field_215742_b.getValue(x, z);
        Biome biome = dynamicBiomeRegistry.getByValue(resultBiomeID);
        if (biome == null) {
            if (SharedConstants.developmentMode) {
                throw Util.pauseDevMode(new IllegalStateException("Unknown biome id: " + resultBiomeID));
            } else {
                // Spawn ocean if we can't resolve the biome from the layers.
                RegistryKey<Biome> backupBiomeKey = BiomeRegistry.getKeyFromID(0);
                DinosExpansion.LOGGER.warn("Unknown biome id: ${}. Will spawn ${} instead.", resultBiomeID, backupBiomeKey.getLocation());
                return dynamicBiomeRegistry.getValueForKey(backupBiomeKey);
            }
        } else {
            return biome;
        }
    }

    public static class InterpolatedNoise{
        protected final SimpleNoiseWithOctaves generator;
        protected LinearInterpolator[] interpolators;
        protected double max;
        protected double min;

        public InterpolatedNoise(SimpleNoiseWithOctaves generator, Point2D... points) {
            if (points.length < 2)
                throw new IllegalArgumentException("we must have at least 2 points to interpolate them to a line");
            this.generator = generator;
            interpolators = new LinearInterpolator[points.length - 1];
            for (int i = 1; i < points.length; i++) {
                interpolators[i-1] = new LinearInterpolator(points[i-1], points[i]);
            }
            max = Arrays.stream(this.interpolators).mapToDouble(l -> l.getEnd().getX()).max().getAsDouble();
            min = Arrays.stream(this.interpolators).mapToDouble(l -> l.getStart().getX()).min().getAsDouble();
        }

        public double getMax() {
            return max;
        }

        public double getMin() {
            return min;
        }

        public double noiseAt(int x, int z) {
            double noise = this.generator.getNoise2D((int) x, (int) z);
            noise = interpolateRange(noise, -0.21524397845109344, 0.20785927926271314, min, max);
            for (LinearInterpolator interpolator : this.interpolators){
                if (interpolator.isInRange(noise)) {
                    return interpolator.get(noise);
                }
            }
            throw new IllegalStateException(String.format("noise was not in range od the points range was [%s, %s] and noise was %s", min, max, noise));

        }
    }

    public static class LinearInterpolator {
        protected final Function<Double, Double> linearFunction;
        protected final Point2D start, end;

        public LinearInterpolator(Point2D start, Point2D end) {
            this.linearFunction = interpolate(start, end);
            this.start = start;
            this.end = end;
        }

        public Point2D getStart() {
            return start;
        }

        public Point2D getEnd() {
            return end;
        }

        public double get(double x) {
            return linearFunction.apply(x);
        }

        public boolean isInRange(double x) {
            return start.getX() <= x && end.getX() >= x;
        }

        protected Function<Double, Double> interpolate(Point2D start, Point2D end) {
            double derivative = (start.getY() - end.getY()) / (start.getX() - end.getX());
            double c = start.getY() - derivative * start.getX();
            return (x) -> derivative * x + c;
        }
    }

    public static double interpolateRange(double number, double sourceMin, double sourceMax, double targetMin, double targetMax) {
        // Make sure the source range is valid
        if (sourceMin >= sourceMax) {
            throw new IllegalArgumentException("Invalid source range");
        }

        // Make sure the target range is valid
        if (targetMin >= targetMax) {
            throw new IllegalArgumentException("Invalid target range");
        }

        // Clamp the input number to the source range
        double clampedNumber = Math.max(sourceMin, Math.min(sourceMax, number));

        // Calculate the normalized position of the clamped number within the source range
        double normalizedPosition = (clampedNumber - sourceMin) / (sourceMax - sourceMin);

        // Interpolate the normalized position to the target range
        return targetMin + normalizedPosition * (targetMax - targetMin);
    }
}
