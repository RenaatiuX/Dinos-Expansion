package com.rena.dinosexpansion.common.world.dimension.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

import static com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil.*;

public enum DinoAddIslandLayer implements ICastleTransformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (north == 0 && west == 0 && south == 0 && east == 0 && center == 0 && context.random(20) == 0){
            return 1;
        }
        return center;
    }
}
