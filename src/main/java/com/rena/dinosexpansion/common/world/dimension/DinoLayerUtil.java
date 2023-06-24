package com.rena.dinosexpansion.common.world.dimension;

import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.layers.*;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IExtendedNoiseRandom;
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

    public static final RegistryKey<Biome>[] SHALLOW_OCEANS = new RegistryKey[]{
            BiomeInit.LUKEWARM_OCEAN.getKey()
    };

    public static final RegistryKey<Biome>[] DEEP_OCEANS = new RegistryKey[]{
            BiomeInit.LUKEWARM_DEEP_OCEAN.getKey()
    };

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

        IAreaFactory<T> biomes = OceanLandLayer.INSTANCE.apply(contextFactory.apply(0L));

        biomes = ZoomLayer.FUZZY.apply(contextFactory.apply(999L), biomes);

        biomes = DinoAddIslandLayer.INSTANCE.apply(contextFactory.apply(998L), biomes);

        biomes = LayerUtil.repeat(10000L, ZoomLayer.NORMAL, biomes, 1, contextFactory);

        biomes = LayerUtil.repeat(1500L, DinoAddIslandLayer.INSTANCE, biomes, 4, contextFactory);

        biomes = LayerUtil.repeat(11000L, ZoomLayer.NORMAL, biomes, 1, contextFactory);

        IAreaFactory<T> oceans = DinoOceanLayer.INSTANCE.apply(contextFactory.apply(5L));
        oceans = LayerUtil.repeat(150000L, ZoomLayer.NORMAL, oceans, 4, contextFactory);
        oceans = LayerUtil.repeat(160000L, SmoothLayer.INSTANCE, oceans, 1, contextFactory);


        biomes = new DinoBiomeLayer().apply(contextFactory.apply(1L), biomes);

        biomes = ZoomLayer.NORMAL.apply(contextFactory.apply(1000), biomes);
        biomes = ZoomLayer.NORMAL.apply(contextFactory.apply(1001), biomes);
        biomes = DinoSubbiomeLayer.INSTANCE.apply(contextFactory.apply(1006L), biomes);

        biomes = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, biomes, 6, contextFactory);

        IAreaFactory<T> riverLayer = DinoRiverLayer.INSTANCE.apply(contextFactory.apply(1L), biomes);
        riverLayer = SmoothLayer.INSTANCE.apply(contextFactory.apply(7000L), riverLayer);
        biomes = DinoRiverMixLayer.INSTANCE.apply(contextFactory.apply(100L), biomes, riverLayer);

        biomes = DinoMixOceansLayer.INSTANCE.apply(contextFactory.apply(99L), oceans, biomes);
        biomes = DeepOceanLayer.INSTANCE.apply(contextFactory.apply(98L), biomes);


        return biomes;
    }

    public static boolean isShallowDinoOcean(int id) {
        return Arrays.stream(SHALLOW_OCEANS).map(k -> getBiomeId(k)).filter(i -> i == id).count() > 0;
    }

    public static boolean isDeepOcean(int id) {
        return Arrays.stream(DEEP_OCEANS).map(k -> getBiomeId(k)).filter(i -> i == id).count() > 0;
    }

    public static boolean isOcean(int id) {
        return isDeepOcean(id) || isShallowDinoOcean(id);
    }

    public static boolean isVanillaOcean(int biomeIn) {
        return biomeIn == 44 || biomeIn == 45 || biomeIn == 0 || biomeIn == 46 || biomeIn == 10 || biomeIn == 47 || biomeIn == 48 || biomeIn == 24 || biomeIn == 49 || biomeIn == 50;
    }

    public static boolean isVanillaShallowOcean(int biomeIn) {
        return biomeIn == 44 || biomeIn == 45 || biomeIn == 0 || biomeIn == 46 || biomeIn == 10;
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

    public static Layer makeLayers(long seed, Registry<Biome> registry) {
        biomeRegistry = registry;
        IAreaFactory<LazyArea> areaFactory = makeLayers((contextSeed) -> new LazyAreaLayerContext(25, seed, contextSeed), registry);
        return new Layer(areaFactory);
    }

}
