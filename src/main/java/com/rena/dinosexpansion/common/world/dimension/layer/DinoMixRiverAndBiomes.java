package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum DinoMixRiverAndBiomes implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, IArea biomes, IArea river, int x, int z) {
        int biomesValue = biomes.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        int riverValue = river.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        //when a river is in biome are the river will be set
        if (biomesValue >= 0 && riverValue >= 0){
            return riverValue;
        }
        //if there is no river but biomes, biomes will be set
        if (biomesValue >= 0){
            return biomesValue;
        }
        // if neither biomes nor river is set then this will also be "dont Care"
        return -1;
    }
}
