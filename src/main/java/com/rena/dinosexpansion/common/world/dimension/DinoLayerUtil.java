package com.rena.dinosexpansion.common.world.dimension;

import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.layers.*;
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
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.SmoothLayer;
import net.minecraft.world.gen.layer.ZoomLayer;
import sun.security.provider.SHA;

import java.util.Arrays;
import java.util.function.LongFunction;

public class DinoLayerUtil {

    private static Registry<Biome> biomeRegistry;

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

        IAreaFactory<T> oceanLand = OceanLandLayer.INSTANCE.apply(contextFactory.apply(0L));

        IAreaFactory<T> tempreature = TemperatureLayer.INSTANCE.apply(contextFactory.apply(2L));
        tempreature = LayerUtil.repeat(160000L, ZoomLayer.NORMAL, tempreature, 6, contextFactory);
        tempreature = LayerUtil.repeat(170000L, SmoothLayer.INSTANCE, tempreature, 4, contextFactory);


        oceanLand = ZoomLayer.FUZZY.apply(contextFactory.apply(999L), oceanLand);

        oceanLand = DinoAddIslandLayer.INSTANCE.apply(contextFactory.apply(998L), oceanLand);

        oceanLand = LayerUtil.repeat(10000L, ZoomLayer.NORMAL, oceanLand, 1, contextFactory);

        oceanLand = LayerUtil.repeat(1500L, DinoAddIslandLayer.INSTANCE, oceanLand, 4, contextFactory);

        oceanLand = LayerUtil.repeat(11000L, ZoomLayer.NORMAL, oceanLand, 5, contextFactory);

        IAreaFactory<T> oceans = DinoOceanLayer.INSTANCE.apply(contextFactory.apply(5L), tempreature);
        oceans = LayerUtil.repeat(150000L, ZoomLayer.NORMAL, oceans, 4, contextFactory);
        oceanLand = DinoMixOceansLayer.INSTANCE.apply(contextFactory.apply(99L), oceans, oceanLand);


        IAreaFactory<T> biomes = new DinoBiomeLayer().apply(contextFactory.apply(1L), tempreature);

        biomes = ZoomLayer.NORMAL.apply(contextFactory.apply(1000), biomes);
        biomes = ZoomLayer.NORMAL.apply(contextFactory.apply(1001), biomes);
        biomes = DinoSubbiomeLayer.INSTANCE.apply(contextFactory.apply(1006L), biomes);

        biomes = LayerUtil.repeat(2000L, ZoomLayer.NORMAL, biomes, 6, contextFactory);

        oceanLand = DinoMixLand.INSTANCE.apply(contextFactory.apply(76L), oceanLand, biomes);

        IAreaFactory<T> riverLayer = DinoRiverLayer.INSTANCE.apply(contextFactory.apply(1L), oceanLand);
        riverLayer = SmoothLayer.INSTANCE.apply(contextFactory.apply(7000L), riverLayer);
        oceanLand = DinoRiverMixLayer.INSTANCE.apply(contextFactory.apply(100L), oceanLand, riverLayer);
        oceanLand = DeepOceanLayer.INSTANCE.apply(contextFactory.apply(98L), oceanLand);


        return oceanLand;
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

    public static boolean areSubbiomes(int id1, int id2) {
        for (BiomeBase base : BiomeBase.BIOMES) {
            if (id1 == DinoLayerUtil.getBiomeId(base)) {
                if (base.getSubBiomes().stream().map(Pair::getFirst).filter(b -> DinoLayerUtil.getBiomeId(b.get()) == id2).count() > 0)
                    return true;
            } else if (id2 == DinoLayerUtil.getBiomeId(base)) {
                if (base.getSubBiomes().stream().map(Pair::getFirst).filter(b -> DinoLayerUtil.getBiomeId(b.get()) == id1).count() > 0)
                    return true;
            }

        }
        return false;
    }

    public static BiomeBase weightedRandom(INoiseRandom random, BiomeBase[] biomes) {
        int totalWeight = Arrays.stream(biomes).mapToInt(BiomeBase::getWeight).sum();
        for (BiomeBase base : biomes) {
            if (random.random(totalWeight) <= base.getWeight())
                return base;
            else
                totalWeight -= base.getWeight();
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
        IAreaFactory<LazyArea> areaFactory = makeLayers((contextSeed) -> new LazyAreaLayerContext(25, seed, contextSeed), registry);
        return new Layer(areaFactory);
    }

}
