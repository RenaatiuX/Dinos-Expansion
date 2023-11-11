package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum DinoSubbiomeLayer implements ICastleTransformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        for (BiomeBase base : BiomeBase.LAND_BIOMES) {
            int id = DinoLayerUtil.getBiomeId(base.getKey());
            if (id == center) {
                for (BiomeBase.Subbiome subbiome : base.getSubBiomes()) {
                    if ((!subbiome.isNeedsSurroundingBiomes() || DinoLayerUtil.check(id, north, west, south, east)) && context.random(subbiome.getProbability()) == 0) {
                        return DinoLayerUtil.getBiomeId(subbiome.getBiome().get());
                    }
                }
                break;
            }
        }
        return center;
    }


}
