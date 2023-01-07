package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum EnlargeIslands implements ICastleTransformer {
    INSTANCE;

    //0 = ocean
    // 1 = land
    //2 = island
    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (center == 0 &&
                (north == 2 ||south == 2 || east == 2 || west == 2) && // is there an island besides this one
                !(north == 1 || west == 1 || south == 1 || east == 1) && // this ensures no island will connect to land
                context.random(3) == 0) // controls how bit the islands might get
            return 2;
        return center;
    }
}
