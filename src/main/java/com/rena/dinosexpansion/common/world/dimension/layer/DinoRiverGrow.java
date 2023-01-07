package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum DinoRiverGrow implements ICastleTransformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (!isOcean(center)){
            //connection to the ocean
            int countOcean = 0;
            int countRiver = 0;
            if (isOcean(north))
                countOcean++;
            if (isOcean(west))
                countOcean++;
            if (isOcean(south))
                countOcean++;
            if (isOcean(east))
                countOcean++;
            if (isRiver(north))
                countRiver++;
            if (isRiver(west))
                countRiver++;
            if (isRiver(south))
                countRiver++;
            if (isRiver(east))
                countRiver++;
            if (countOcean > 0 && countRiver > 0)
                return 7;
            if (countRiver >= 2)
                return 7;
            if (countRiver == 1 && context.random(40) == 0){
                return 7;
            }
        }
        return center;
    }

    private static boolean isRiver(int position){
        return position == 7;
    }

    private static boolean isOcean(int position){
        return position == 0;
    }
}
