package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum AddNotDeepOceanAroundIslands implements ICastleTransformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (center == 0 && (north == 2 ||south == 2 || east == 2 || west == 2)) // is there an island besides this one
            return 3;
        return center;
    }
}
