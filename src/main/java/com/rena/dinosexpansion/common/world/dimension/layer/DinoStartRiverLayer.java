package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public enum DinoStartRiverLayer implements IC0Transformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int value) {
        return DinoBiomeProvider.isShallowOcean(value) ? value : context.random(299999) + 2;
    }
}
