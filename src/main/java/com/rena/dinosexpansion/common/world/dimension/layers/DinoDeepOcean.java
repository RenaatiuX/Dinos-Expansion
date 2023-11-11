package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

import static com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil.*;

public enum DinoDeepOcean implements ICastleTransformer {
    INSTANCE;


    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (isOcean(north) && isOcean(south) && isOcean(east) && isOcean(west) && isShallowDinoOcean(center) && context.random(3) == 0) {

            if (center == getBiomeId(BiomeInit.COLD_OCEAN)) {
                return getBiomeId(BiomeInit.DEEP_COLD_OCEAN);
            }
            if (center == getBiomeId(BiomeInit.WARM_OCEAN)) {
                return getBiomeId(BiomeInit.DEEP_WARM_OCEAN);
            }
            if (center == getBiomeId(BiomeInit.FROZEN_OCEAN)) {
                return getBiomeId(BiomeInit.DEEP_FROZEN_OCEAN);
            }
            if (center == getBiomeId(BiomeInit.LUKEWARM_OCEAN)) {
                return getBiomeId(BiomeInit.LUKEWARM_DEEP_OCEAN);
            }
            return getBiomeId(BiomeInit.DEEP_OCEAN);
        }
        return center;
    }
}
