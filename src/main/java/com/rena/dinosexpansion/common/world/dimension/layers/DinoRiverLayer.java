package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum DinoRiverLayer implements ICastleTransformer {
    INSTANCE;

    DinoRiverLayer() {

    }

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (shouldRiver(center, west, south, east, north)) {
            return DinoLayerUtil.getBiomeId(BiomeInit.RIVER.getKey());
        } else {
            return -1;
        }
    }

    boolean shouldRiver(int mid, int left, int down, int right, int up) {
        return shouldRiver(mid, left) || shouldRiver(mid, right) || shouldRiver(mid, down) || shouldRiver(mid, up);
    }

    boolean shouldRiver(int id1, int id2) {
        return id1 != id2;
    }
}
