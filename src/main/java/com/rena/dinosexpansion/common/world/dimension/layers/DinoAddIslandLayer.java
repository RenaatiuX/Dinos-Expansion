package com.rena.dinosexpansion.common.world.dimension.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

import static com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil.*;

public enum DinoAddIslandLayer implements ICastleTransformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (isSshallowOcean(north) && isSshallowOcean(west) && isSshallowOcean(south) && isSshallowOcean(east) && isSshallowOcean(center) && context.random(10) == 0){
            return 1;
        }
        return 0;
    }

    private boolean isSshallowOcean(int id){
        return isShallowDinoOcean(id) || isVanillaOcean(id);
    }
}
