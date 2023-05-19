package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum StartWaveFunction implements IAreaTransformer0 {
    ;

    @Override
    public int apply(INoiseRandom random, int x, int z) {
        if (x == 0 && z == 0)
            return 111;
        return 111;
    }
}
