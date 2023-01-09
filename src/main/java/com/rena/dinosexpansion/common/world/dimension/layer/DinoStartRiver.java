package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public enum DinoStartRiver implements IC0Transformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int value) {
        if (value == 0 || value == 2)
            return -1;
        if (context.random(3) == 0)
            return 7;
        return -1;
    }
}
