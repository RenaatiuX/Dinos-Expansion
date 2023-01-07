package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public enum CleanRiverLayer implements IC0Transformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int value) {
        return value >= 7 ? 7 : -1;
    }
}
