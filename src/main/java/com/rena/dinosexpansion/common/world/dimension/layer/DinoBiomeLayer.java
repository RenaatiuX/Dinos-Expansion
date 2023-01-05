package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public enum DinoBiomeLayer implements IC0Transformer {

    /*private static final int UNCOMMON_BIOME_CHANCE = 8;
    private static final int RARE_BIOME_CHANCE = 16;
    public DinoBiomeLayer() {

    }
    protected int[] commonBiomes = new int[] {
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.LOW_DESERT)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.GRASSLAND)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.MOUNTAIN)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.OCEAN)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.DEEP_OCEAN))

    };
    protected int[] uncommonBiomes = new int[] {
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.BADLANDS)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.SWAMP)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.HIGH_DESERT)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.JUNGLE)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.TUNDRA)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.LUKEWARM_OCEAN)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.DEEP_LUKEWARM_OCEAN)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.WARM_OCEAN)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.DEEP_WARM_OCEAN)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.COLD_OCEAN)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.DEEP_COLD_OCEAN))
    };
    protected int[] rareBiomes = new int[] {
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.ARCTIC)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.CANYON)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.DUNES)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.REDWOOD_FOREST)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.FROZEN_OCEAN)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.DEEP_FROZEN_OCEAN))
    };

    @Override
    public int apply(INoiseRandom random, int p_215735_2_, int p_215735_3_) {
        if (random.random(RARE_BIOME_CHANCE) == 0) {
            return rareBiomes[random.random(rareBiomes.length)];
        } else if (random.random(UNCOMMON_BIOME_CHANCE) == 0) {
            return uncommonBiomes[random.random(uncommonBiomes.length)];
        } else {
            return commonBiomes[random.random(commonBiomes.length)];
        }
    }*/
    INSTANCE;
    private final int[] landIds;

    DinoBiomeLayer() {
        this.landIds = new int[] {
                DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.ARCTIC)),
                DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.BADLANDS)),
                DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.SWAMP)),
                DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.CANYON)),
                DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.DUNES)),
                DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.GRASSLAND)),
                DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.HIGH_DESERT)),
                DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.LOW_DESERT)),
                DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.JUNGLE)),
                DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.MOUNTAIN)),
                DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.REDWOOD_FOREST)),
                DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.TUNDRA))
        };
    }

    @Override
    public int apply(INoiseRandom context, int center) {
        if (DinoBiomeProvider.isLand(center)){
            return landIds[context.random(landIds.length)];
        }
        return center;
    }
}
