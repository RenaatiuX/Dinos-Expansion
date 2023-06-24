package com.rena.dinosexpansion.common.world.dimension.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum DinoMixLand implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, IArea oceanLand, IArea biomes, int x, int z) {
        int oceanLandId = oceanLand.getValue(getOffsetX(x), getOffsetZ(z));
        int biome = biomes.getValue(getOffsetX(x), getOffsetZ(z));
        if (oceanLandId == 1){
            return biome;
        }
        return oceanLandId;
    }
}
