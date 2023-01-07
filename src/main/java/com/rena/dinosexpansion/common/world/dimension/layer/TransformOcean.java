package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public enum TransformOcean implements IC0Transformer {
    INSTANCE;
    //TODO transform it into real biomes

    @Override
    public int apply(INoiseRandom context, int value) {
        if (value == 3)
            return DinoBiomeProvider.getId(DinoBiomeProvider.OCEAN);
        return value == 0 ? DinoBiomeProvider.getId(DinoBiomeProvider.DEEP_OCEAN) : value;
    }
}
