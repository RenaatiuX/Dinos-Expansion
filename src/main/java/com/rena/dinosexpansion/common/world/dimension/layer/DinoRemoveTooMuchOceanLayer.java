package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum DinoRemoveTooMuchOceanLayer implements ICastleTransformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        return DinoBiomeProvider.isShallowOcean(center) && DinoBiomeProvider.isShallowOcean(north) &&
                DinoBiomeProvider.isShallowOcean(west) && DinoBiomeProvider.isShallowOcean(east) &&
                DinoBiomeProvider.isShallowOcean(south) && context.random(2) == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getOrDefault(DinoBiomeProvider.OCEAN)) ? DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getOrDefault(DinoBiomeProvider.GRASSLAND)) : center;

    }
}
