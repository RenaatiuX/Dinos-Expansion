package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum DinoMixRiverIntoArea implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, IArea area, IArea river, int x, int z) {
        int areaValue = area.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        int riverValue = river.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        if (riverValue > 0 && DinoBiomeProvider.isLand(areaValue)){
            return riverValue;
        }
        return areaValue;
    }
}
