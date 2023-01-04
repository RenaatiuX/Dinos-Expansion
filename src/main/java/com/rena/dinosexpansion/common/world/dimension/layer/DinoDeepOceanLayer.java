package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum DinoDeepOceanLayer implements ICastleTransformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (DinoBiomeProvider.isShallowOcean(center)) {
            int i = 0;
            if (DinoBiomeProvider.isShallowOcean(north)) {
                ++i;
            }
            if (DinoBiomeProvider.isShallowOcean(west)) {
                ++i;
            }

            if (DinoBiomeProvider.isShallowOcean(east)) {
                ++i;
            }

            if (DinoBiomeProvider.isShallowOcean(south)) {
                ++i;
            }
            if (i > DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.MOUNTAIN))) {
                if (center == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.WARM_OCEAN))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.DEEP_WARM_OCEAN));
                }

                if (center == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.LUKEWARM_OCEAN))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.DEEP_LUKEWARM_OCEAN));
                }

                if (center == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.OCEAN))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.DEEP_OCEAN));
                }

                if (center == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.COLD_OCEAN))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.DEEP_COLD_OCEAN));
                }

                if (center == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.FROZEN_OCEAN))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.DEEP_FROZEN_OCEAN));
                }

                return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.DEEP_OCEAN));
            }
        }
        return center;
    }
}
