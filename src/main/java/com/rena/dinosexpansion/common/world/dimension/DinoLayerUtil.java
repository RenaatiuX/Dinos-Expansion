package com.rena.dinosexpansion.common.world.dimension;

import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.layers.*;
import com.rena.dinosexpansion.common.world.dimension.noises.SimpleNoiseWithOctaves;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.*;
import sun.security.provider.SHA;

import java.util.Arrays;
import java.util.function.LongFunction;

public class DinoLayerUtil {

    private static Registry<Biome> biomeRegistry;
    private static double minNoise, maxNoise;
    private static SimpleNoiseWithOctaves temperatureNoise;

    public static int getBiomeId(RegistryKey<Biome> define) {
        Biome biome = biomeRegistry.getValueForKey(define);
        return biomeRegistry.getId(biome);
    }

    public static int getBiomeId(BiomeBase define) {
        return getBiomeId(define.getKey());
    }

    public static int getBiomeId(Biome b) {
        return biomeRegistry.getId(b);
    }

    public static Biome getBiome(int id) {
        return biomeRegistry.getByValue(id);
    }

    public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> makeLayers(LongFunction<C> contextFactory, Registry<Biome> registry) {
        biomeRegistry = registry;
        IAreaFactory<T> oceanLand = OceanLandLayer.INSTANCE.apply(contextFactory.apply(1L));
        oceanLand = LayerUtil.repeat(2L, ZoomLayer.NORMAL, oceanLand, 3, contextFactory);
        oceanLand = DinoAddIslandLayer.INSTANCE.apply(contextFactory.apply(10L), oceanLand);
        DinoBiomeLayer biomeLayer = new DinoBiomeLayer();
        oceanLand = biomeLayer.apply(contextFactory.apply(11L), oceanLand);
        oceanLand = LayerUtil.repeat(12L, ZoomLayer.NORMAL, oceanLand, 4, contextFactory);

        //IAreaFactory<T> rivers = DinoRiverLayer.INSTANCE.apply(contextFactory.apply(30L), oceanLand);
        //rivers = LayerUtil.repeat(31L, ZoomLayer.NORMAL, rivers, 1, contextFactory);

        //oceanLand = DinoRiverMixLayer.INSTANCE.apply(contextFactory.apply(40L), oceanLand, rivers);


        return oceanLand;
    }

    public static double getTemperatureNoise(int x, int z) {
        return DinoBiomeProvider.interpolateRange(temperatureNoise.getNoise2D(x, z), minNoise, maxNoise, -1.5, 1.5);
    }

    public static boolean isShallowDinoOcean(int id) {
        return BiomeBase.SHALLOW_OCEANS.stream().map(k -> getBiomeId(k)).filter(i -> i == id).count() > 0;
    }

    public static boolean isDeepOcean(int id) {
        return BiomeBase.DEEP_OCEANS.stream().map(k -> getBiomeId(k)).filter(i -> i == id).count() > 0;
    }

    public static boolean isOcean(int id) {
        return isDeepOcean(id) || isShallowDinoOcean(id);
    }

    public static boolean isRiver(int id){
        return BiomeBase.BIOMES.stream().filter(BiomeBase::isRiver).map(DinoLayerUtil::getBiomeId).filter(i -> i == id).count() > 0;
    }

    public static boolean areSubbiomes(int id1, int id2) {
        for (BiomeBase base : BiomeBase.BIOMES) {
            if (id1 == DinoLayerUtil.getBiomeId(base)) {
                if (base.getSubBiomes().stream().map(BiomeBase.Subbiome::getBiome).filter(b -> DinoLayerUtil.getBiomeId(b.get()) == id2).count() > 0)
                    return true;
            } else if (id2 == DinoLayerUtil.getBiomeId(base)) {
                if (base.getSubBiomes().stream().map(BiomeBase.Subbiome::getBiome).filter(b -> DinoLayerUtil.getBiomeId(b.get()) == id1).count() > 0)
                    return true;
            }

        }
        return false;
    }

    public static BiomeBase weightedRandom(INoiseRandom random, BiomeBase[] biomes) {
        if (biomes.length == 1)
            return biomes[0];
        int totalWeight = Arrays.stream(biomes).mapToInt(BiomeBase::getWeight).sum();
        int randomWeight = random.random(totalWeight);
        for (BiomeBase base : biomes) {
            randomWeight -= base.getWeight();
            if (randomWeight < 0)
                return base;
        }
        throw new IllegalStateException("something went horribly wrong in the weighted random");
    }

    public static <T> boolean contains(T[] arr, T element) {
        for (T item : arr) {
            if (item.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public static Layer makeLayers(long seed, Registry<Biome> registry) {
        biomeRegistry = registry;
        temperatureNoise = new SimpleNoiseWithOctaves(512, .6d, seed);
        testNoise();
        IAreaFactory<LazyArea> areaFactory = makeLayers((contextSeed) -> new LazyAreaLayerContext(25, seed, contextSeed), registry);
        return new Layer(areaFactory);
    }

    protected static void testNoise() {
        double[] noises = new double[4000 * 4000];
        for (int x = 0; x < 4000; x++) {
            for (int z = 0; z < 4000; z++) {
                noises[x * 4000 + z] = temperatureNoise.getNoise2D(x,z);
            }
        }
        minNoise = Arrays.stream(noises).min().getAsDouble();
        maxNoise = Arrays.stream(noises).max().getAsDouble();
        DinosExpansion.LOGGER.debug(String.format("temperature noise in range: [%s, %s]", minNoise, maxNoise));
    }

}
