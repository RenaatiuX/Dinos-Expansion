package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum AddSmallIslands implements ICastleTransformer {
    INSTANCE;
    //at this state
    // 0 = ocean
    // 1 = land
    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (north == 0 && south == 0 && west == 0 && east == 0 && context.random(60) == 0){
            return 2;
        }
        return center;
    }
}
