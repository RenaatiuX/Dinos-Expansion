package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IBishopTransformer;

public enum DinoAddIslandLayer implements IBishopTransformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int x, int southEast, int southWest, int northEast, int northWest) {
        if (!DinoBiomeProvider.isShallowOcean(northWest) || DinoBiomeProvider.isShallowOcean(northEast) && DinoBiomeProvider.isShallowOcean(southWest) && DinoBiomeProvider.isShallowOcean(x) && DinoBiomeProvider.isShallowOcean(southEast)) {
            if (!DinoBiomeProvider.isShallowOcean(northWest) && (DinoBiomeProvider.isShallowOcean(northEast) || DinoBiomeProvider.isShallowOcean(x) || DinoBiomeProvider.isShallowOcean(southWest) || DinoBiomeProvider.isShallowOcean(southEast)) && context.random(5) == 0) {
                if (DinoBiomeProvider.isShallowOcean(northEast)) {
                    return northWest == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.GRASSLAND)) ?
                            DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.GRASSLAND)) : northEast;
                }

                if (DinoBiomeProvider.isShallowOcean(x)) {
                    return northWest == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.GRASSLAND)) ? DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.GRASSLAND)) : x;
                }

                if (DinoBiomeProvider.isShallowOcean(southWest)) {
                    return northWest == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.GRASSLAND)) ? DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.GRASSLAND)) : southWest;
                }

                if (DinoBiomeProvider.isShallowOcean(southEast)) {
                    return northWest == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.GRASSLAND)) ? DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.GRASSLAND)) : southEast;
                }
            }

            return northWest;
        } else {
            int i = 1;
            int j = 1;
            if (!DinoBiomeProvider.isShallowOcean(northEast) && context.random(i++) == 0) {
                j = northEast;
            }

            if (!DinoBiomeProvider.isShallowOcean(southWest) && context.random(i++) == 0) {
                j = southWest;
            }

            if (!DinoBiomeProvider.isShallowOcean(x) && context.random(i++) == 0) {
                j = x;
            }

            if (!DinoBiomeProvider.isShallowOcean(southEast) && context.random(i++) == 0) {
                j = southEast;
            }

            if (context.random(3) == 0) {
                return j;
            } else {
                return j == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.GRASSLAND)) ?
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.GRASSLAND)) : northWest;
            }
        }
    }
}
