package com.rena.dinosexpansion.common.world.dimension.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum DinoMixOceansLayer implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom noise, IArea oceans, IArea biomes, int x, int z) {
        int ocean = oceans.getValue(getOffsetX(x), getOffsetZ(z));
        int biome = biomes.getValue(getOffsetX(x), getOffsetZ(z));

        if (biome == 0){
            return ocean;
        }
        return biome;
    }
}
