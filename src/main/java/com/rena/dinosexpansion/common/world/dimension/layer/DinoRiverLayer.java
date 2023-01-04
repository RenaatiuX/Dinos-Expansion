package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum DinoRiverLayer implements ICastleTransformer {

    INSTANCE;
    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (center != north || center != east || center != south || center != west) {
            return DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                    DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.RIVER));
        }
        return -1;
    }

}
