package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum DinoIslandLayer implements IAreaTransformer0 {
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, int x, int z) {
        if (random.random(10) < 4) {
            return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getOrDefault(DinoBiomeProvider.GRASSLAND));
        } else {
            return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getOrDefault(DinoBiomeProvider.OCEAN));
        }
    }
}
