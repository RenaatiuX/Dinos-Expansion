package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraftforge.common.BiomeManager;

public enum AddTeperature implements IC0Transformer {
    INSTANCE;

    //adds BiomeTypes with an offset of 100
    //just to land not to islands nor to ocean
    @Override
    public int apply(INoiseRandom context, int value) {
        if (value == 1) {
            while (true) {
                int type = context.random(BiomeManager.BiomeType.values().length);
                if (BiomeManager.BiomeType.values()[type] == BiomeManager.BiomeType.DESERT_LEGACY)
                    continue;
                return type + 100;
            }
        }
        return value;
    }
}
