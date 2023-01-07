package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum AddBiomesToLayer implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, IArea areaLayer, IArea biomes, int x, int z) {
        int biomesValue = biomes.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        int areaLayerValue = areaLayer.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        //basically adding all the biomes to the layer or the islands
        if(areaLayerValue == 1 || areaLayerValue == 2){
            //just for safety so there isnt a are where the layer says land but the biomes doesnt have value
            if (biomesValue > 0)
                return biomesValue;
            else
                return -10;
        }
        return areaLayerValue;
    }
}
