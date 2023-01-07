package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public enum DinoStartRiver implements IC0Transformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int value) {
        return value == 0 ? value : context.random(299999) + 2;
    }
}
