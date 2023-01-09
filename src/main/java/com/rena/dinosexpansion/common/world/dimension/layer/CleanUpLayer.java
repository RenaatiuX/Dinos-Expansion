package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum CleanUpLayer implements ICastleTransformer {
    INSTANCE;
    //this should clean up all the -10
    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (center == -10){
            switch (context.random(4)){
                case 0:
                    if (north > 0)
                        return north;
                case 1:
                    if (west > 0)
                        return west;
                case 2:
                    if (south > 0)
                        return south;
                default:
                    return east;

            }
        }
        return center;
    }
}
