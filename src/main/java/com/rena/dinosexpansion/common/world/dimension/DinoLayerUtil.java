package com.rena.dinosexpansion.common.world.dimension;

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

        IAreaFactory<T> biomes = OceanLandLayer.INSTANCE.apply(contextFactory.apply(0L));

        biomes = new DinoBiomeLayer().apply(contextFactory.apply(1L), biomes);

        biomes = ZoomLayer.FUZZY.apply(contextFactory.apply(999L), biomes);
        biomes = ZoomLayer.NORMAL.apply(contextFactory.apply(1000), biomes);
        biomes = ZoomLayer.NORMAL.apply(contextFactory.apply(1001), biomes);
        biomes = DinoSubbiomeLayer.INSTANCE.apply(contextFactory.apply(1006L), biomes);

        biomes = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, biomes, 6, contextFactory);

        IAreaFactory<T> riverLayer = DinoRiverLayer.INSTANCE.apply(contextFactory.apply(1L), biomes);
        riverLayer = SmoothLayer.INSTANCE.apply(contextFactory.apply(7000L), riverLayer);
        biomes = DinoRiverMixLayer.INSTANCE.apply(contextFactory.apply(100L), biomes, riverLayer);


        return biomes;
    }

    public static Layer makeLayers(long seed, Registry<Biome> registry) {
        biomeRegistry = registry;
        IAreaFactory<LazyArea> areaFactory = makeLayers((contextSeed) -> new LazyAreaLayerContext(25, seed, contextSeed), registry);
        return new Layer(areaFactory);
    }

}
