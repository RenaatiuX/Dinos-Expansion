package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum DinoIslandLayer implements IAreaTransformer0 {
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, int biome1, int biome2) {
        if (biome1 == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getOrDefault(DinoBiomeProvider.OCEAN)) &&
                biome2 == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.OCEAN))) {
            return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getOrDefault(DinoBiomeProvider.GRASSLAND));
        } else {
            return random.random(10) == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getOrDefault(DinoBiomeProvider.OCEAN)) ? DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getOrDefault(DinoBiomeProvider.GRASSLAND)) : DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getOrDefault(DinoBiomeProvider.OCEAN));
        }
    }
}
