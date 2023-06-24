package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum DeepOceanLayer implements IAreaTransformer1, IDimOffset0Transformer {
    INSTANCE;


    @Override
    public int apply(IExtendedNoiseRandom<?> context, IArea area, int x, int z) {
        int currentCenter = area.getValue(x, z);
        for (int dx = x - 5; dx <= x + 5; dx++) {
            for (int dz = z - 5; dz <= z + 5; dz++) {
                if (!DinoLayerUtil.isShallowDinoOcean(area.getValue(x + dx, z + dz)))
                    return currentCenter;
            }
        }

        //make the deep variants
        if (currentCenter == DinoLayerUtil.getBiomeId(BiomeInit.LUKEWARM_OCEAN))
            return DinoLayerUtil.getBiomeId(BiomeInit.LUKEWARM_DEEP_OCEAN);
        if (currentCenter == DinoLayerUtil.getBiomeId(BiomeInit.COLD_OCEAN))
            return DinoLayerUtil.getBiomeId(BiomeInit.DEEP_COLD_OCEAN);
        if (currentCenter == DinoLayerUtil.getBiomeId(BiomeInit.WARM_OCEAN))
            return DinoLayerUtil.getBiomeId(BiomeInit.DEEP_WARM_OCEAN);
        if (currentCenter == DinoLayerUtil.getBiomeId(BiomeInit.FROZEN_OCEAN))
            return DinoLayerUtil.getBiomeId(BiomeInit.DEEP_FROZEN_OCEAN);
        return DinoLayerUtil.getBiomeId(BiomeInit.DEEP_OCEAN);
    }
}
