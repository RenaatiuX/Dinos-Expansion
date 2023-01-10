package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;
import net.minecraftforge.common.BiomeManager;

public enum RiverTemperatureMixer implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, IArea river, IArea temperature, int x, int z) {
        int riverValue = river.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        int temperatureValue = temperature.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        if (temperatureValue < 0)
            return -1;
        else if (temperatureValue - 100 >= 0 && riverValue == 7){
            BiomeManager.BiomeType type = BiomeManager.BiomeType.values()[temperatureValue - 100];
            switch (type){
                case COOL:
                case ICY:
                    return DinoBiomeProvider.getId(DinoBiomeProvider.FROZEN_RIVER);
                default:
                    return DinoBiomeProvider.getId(DinoBiomeProvider.RIVER);
            }
        }
        return -1;
    }
}
