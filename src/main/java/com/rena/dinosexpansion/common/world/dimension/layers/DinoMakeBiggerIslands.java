package com.rena.dinosexpansion.common.world.dimension.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum DinoMakeBiggerIslands implements ICastleTransformer {
    INSTANCE;


    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        //center is Ocean
        if (center == 0){
            //one of the surrounding stuff is Land
            if (north + west + south + east >= 1 && context.random(10) == 0){
                return 1;
            }
        }
        return center;
    }

}
