package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum DinoOceanLayer implements IAreaTransformer0 {
    INSTANCE;

    @Override
    public int apply(INoiseRandom randomNoise, int x, int z) {
        ImprovedNoiseGenerator improvednoisegenerator = randomNoise.getNoiseGenerator();
        double d0 = improvednoisegenerator.func_215456_a((double)x / 8.0D, (double)z / 8.0D, 0.0D, 0.0D, 0.0D);
        if (d0 > 0.4D) {
            //TODO WARM OCEAN
            return 44;
        } else if (d0 > 0.2D) {
            return DinoLayerUtil.getBiomeId(BiomeInit.LUKEWARM_OCEAN);
        } else if (d0 < -0.4D) {
            //TODO FROZEN OCEAN
            return 10;
        } else {
            //TODO COLD OCEAN : NORMAL OCEAN
            return d0 < -0.2D ? 46 : 0;
        }
    }
}
