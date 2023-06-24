package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

import java.util.stream.Collectors;

public enum DinoOceanLayer implements IAreaTransformer1, IDimOffset0Transformer {
    INSTANCE;


    @Override
    public int apply(IExtendedNoiseRandom<?> context, IArea area, int x, int z) {
        int id = area.getValue(x, z);
        int typeID = id - 100;
        if (typeID >= 0) {
            BiomeBase.BiomeType type = BiomeBase.BiomeType.values()[typeID];
            BiomeBase[] result = BiomeBase.SHALLOW_OCEANS.stream().filter(b -> DinoLayerUtil.contains(b.getBiomeType(), type)).collect(Collectors.toList()).toArray(new BiomeBase[0]);
            if (result.length > 0)
                return DinoLayerUtil.getBiomeId(DinoLayerUtil.weightedRandom(context, result));
            else
                return  DinoLayerUtil.getBiomeId(BiomeInit.OCEAN);
        }
        return -1;
    }
}
