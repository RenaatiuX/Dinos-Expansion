package com.rena.dinosexpansion.common.world.dimension.layers;

import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

import static com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil.*;

public enum DinoRiverLayer implements ICastleTransformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (!isOneRiver(north, west, south, east, center) && shouldRiver(center, west, south, east, north)) {
            return DinoLayerUtil.getBiomeId(BiomeInit.RIVER.getKey());
        } else {
            return -1;
        }
    }

    boolean isOneRiver(int north, int west, int south, int east, int center){
        return DinoLayerUtil.isRiver(center) || DinoLayerUtil.isRiver(north) || DinoLayerUtil.isRiver(west) || DinoLayerUtil.isRiver(south) || DinoLayerUtil.isRiver(east);
    }

    boolean shouldRiver(int mid, int left, int down, int right, int up) {
        if (isOcean(left) || isOcean(down) || isOcean(right) || isOcean(up))
            return false;
        return shouldRiver(right, left) || shouldRiver(down, up);
    }

    boolean shouldRiver(int id1, int id2) {
        return id1 != id2 && !areSubbiomes(id1, id2);
    }


}
