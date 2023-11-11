package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum DinoRiverMixLayer implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    DinoRiverMixLayer() {

    }

    @Override
    public int apply(INoiseRandom random, IArea river, IArea oceanLand, int x, int z) {
        int i = river.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        int j = oceanLand.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        return DinoLayerUtil.isRiver(i) && !DinoLayerUtil.isOcean(j) ? i : j;
    }
}
