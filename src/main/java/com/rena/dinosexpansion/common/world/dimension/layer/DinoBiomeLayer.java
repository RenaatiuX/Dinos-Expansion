package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public enum DinoBiomeLayer implements IC0Transformer {

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
        return landIds[context.random(landIds.length)];
    }
}
