package com.rena.dinosexpansion.common.world.dimension;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.layers.*;
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

import java.util.Arrays;
import java.util.List;
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

    public static boolean canSpawnRiverAdjacent(int id){
        BiomeBase matched = null;
        for (BiomeBase base : BiomeBase.LAND_BIOMES){
            if (getBiomeId(base) == id) {
                matched = base;
                break;
            }

        }
        return matched != null && matched.canGenerateRiver();
    }

    public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> makeLayers(LongFunction<C> contextFactory, Registry<Biome> registry) {
        biomeRegistry = registry;
        IAreaFactory<T> oceanLand = OceanLandLayer.INSTANCE.apply(contextFactory.apply(1L));
        oceanLand = LayerUtil.repeat(2L, ZoomLayer.NORMAL, oceanLand, 3, contextFactory);
        oceanLand = DinoAddIslandLayer.INSTANCE.apply(contextFactory.apply(10L), oceanLand);
        oceanLand = LayerUtil.repeat(11L, DinoMakeBiggerIslands.INSTANCE, oceanLand, 5, contextFactory);

        oceanLand = LayerUtil.repeat(20L, ZoomLayer.FUZZY, oceanLand, 4, contextFactory);
        oceanLand = LayerUtil.repeat(25L, SmoothLayer.INSTANCE, oceanLand, 10, contextFactory);

        IAreaFactory<T> biomes = DinoSimpleBiomeLayer.INSTANCE.apply(contextFactory.apply(26L));
        biomes = LayerUtil.repeat(27L, ZoomLayer.NORMAL, biomes, 2, contextFactory);
        biomes = DinoSubbiomeLayer.INSTANCE.apply(contextFactory.apply(36), biomes);
        biomes = DinoSubbiomeLayer.INSTANCE.apply(contextFactory.apply(37), biomes);
        biomes = DinoSubbiomeLayer.INSTANCE.apply(contextFactory.apply(38), biomes);
        biomes = LayerUtil.repeat(43L, ZoomLayer.NORMAL, biomes, 2, contextFactory);
        biomes = LayerUtil.repeat(40L, ZoomLayer.FUZZY, biomes, 2, contextFactory);
        biomes = DinoRiverLayer.INSTANCE.apply(contextFactory.apply(49L), biomes);

        IAreaFactory<T> oceans = DinoOceanLayer.INSTANCE.apply(contextFactory.apply(1000L));
        oceans = LayerUtil.repeat(1001L, ZoomLayer.NORMAL, oceans, 2, contextFactory);
        oceans = DinoDeepOcean.INSTANCE.apply(contextFactory.apply(2000L), oceans);
        oceans = LayerUtil.repeat(1010L, ZoomLayer.NORMAL, oceans, 3, contextFactory);
        oceans = LayerUtil.repeat(1100L, ZoomLayer.FUZZY, oceans, 2, contextFactory);

        oceans = LayerUtil.repeat(1005L, SmoothLayer.INSTANCE, oceans, 1, contextFactory);


        oceanLand = DinoMixLand.INSTANCE.apply(contextFactory.apply(50L), oceanLand, biomes);
        oceanLand = DinoMixOceansLayer.INSTANCE.apply(contextFactory.apply(1020L), oceans, oceanLand);

        //oceanLand = LayerUtil.repeat(51L, ZoomLayer.NORMAL, oceanLand, 1, contextFactory);
        oceanLand = LayerUtil.repeat(60L, SmoothLayer.INSTANCE, oceanLand, 1, contextFactory);

        return oceanLand;
    }

    public static boolean isShallowDinoOcean(int id) {
        return BiomeBase.SHALLOW_OCEANS.stream().map(DinoLayerUtil::getBiomeId).filter(i -> i == id).count() > 0;
    }

    public static boolean isDeepOcean(int id) {
        return BiomeBase.DEEP_OCEANS.stream().map(DinoLayerUtil::getBiomeId).filter(i -> i == id).count() > 0;
    }

    public static boolean isOcean(int id) {
        return isDeepOcean(id) || isShallowDinoOcean(id);
    }

    public static boolean isRiver(int id){
        return BiomeBase.LAND_BIOMES.stream().filter(BiomeBase::isRiver).map(DinoLayerUtil::getBiomeId).filter(i -> i == id).count() > 0;
    }

    public static boolean areSubbiomes(int id1, int id2) {
        for (BiomeBase base : BiomeBase.LAND_BIOMES) {
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

    public static BiomeBase weightedRandom(INoiseRandom random, List<BiomeBase> biomes) {
        if (biomes.size() == 1)
            return biomes.get(0);
        int totalWeight = biomes.stream().mapToInt(BiomeBase::getWeight).sum();
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

    public static boolean check(int id, int... compareTo) {
        for (int comparable : compareTo) {
            if (id != comparable)
                return false;
        }
        return true;
    }

    public static Layer makeLayers(long seed, Registry<Biome> registry) {
        biomeRegistry = registry;
        IAreaFactory<LazyArea> areaFactory = makeLayers((contextSeed) -> new LazyAreaLayerContext(25, seed, contextSeed), registry);
        return new Layer(areaFactory);
    }

}
