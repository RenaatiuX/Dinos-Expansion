package com.rena.dinosexpansion.common.world.dimension.layers;

import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.world.biome.Biome;
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
        return id1 != id2 && !areSubbiomes(id1, id2);
    }

    boolean areSubbiomes(int id1, int id2) {
        for (BiomeBase base : BiomeBase.BIOMES) {
            if (id1 == DinoLayerUtil.getBiomeId(base)) {
                if (base.getSubBiomes().stream().map(Pair::getFirst).filter(b -> DinoLayerUtil.getBiomeId(b.get()) == id2).count() > 0)
                    return true;
            } else if (id2 == DinoLayerUtil.getBiomeId(base)) {
                if (base.getSubBiomes().stream().map(Pair::getFirst).filter(b -> DinoLayerUtil.getBiomeId(b.get()) == id1).count() > 0)
                    return true;
            }

        }
        return false;
    }
}
