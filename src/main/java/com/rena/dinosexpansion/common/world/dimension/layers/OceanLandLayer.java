package com.rena.dinosexpansion.common.world.dimension.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum OceanLandLayer implements IAreaTransformer0 {
    INSTANCE;

    @Override
    public int apply(INoiseRandom noiseRandom, int x, int z) {
        return noiseRandom.random(10) == 0 ? 1 : 0;
    }
}
