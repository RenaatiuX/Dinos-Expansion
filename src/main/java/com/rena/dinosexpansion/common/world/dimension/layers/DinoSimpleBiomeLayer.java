package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum DinoSimpleBiomeLayer implements IAreaTransformer0, IDimOffset0Transformer {
    INSTANCE;


    @Override
    public int apply(INoiseRandom context, int x, int z) {
        return DinoLayerUtil.getBiomeId(BiomeBase.LAND_BIOMES.get(context.random(BiomeBase.LAND_BIOMES.size())));
    }
}
