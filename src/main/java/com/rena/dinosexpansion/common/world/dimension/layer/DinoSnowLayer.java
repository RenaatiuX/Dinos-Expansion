package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public enum DinoSnowLayer implements IC1Transformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int value) {
        if (DinoBiomeProvider.isShallowOcean(value)) {
            return value;
        } else {
            int i = context.random(6);
            if (i == 0) {
                return DinoBiomeProvider.getId(DinoBiomeProvider.BEACH);
            } else {
                return i == 1 ? DinoBiomeProvider.getId(DinoBiomeProvider.STONE_SHORE) : DinoBiomeProvider.getId(DinoBiomeProvider.GRASSLAND);
            }
        }
    }
}
