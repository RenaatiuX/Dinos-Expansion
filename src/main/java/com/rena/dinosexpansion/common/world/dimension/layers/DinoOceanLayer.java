package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

import java.util.stream.Collectors;

public enum DinoOceanLayer implements IAreaTransformer0, IDimOffset0Transformer {
    INSTANCE;


    @Override
    public int apply(INoiseRandom context, int x, int z) {
       int id = DinoLayerUtil.getBiomeId(BiomeBase.SHALLOW_OCEANS.get(context.random(BiomeBase.SHALLOW_OCEANS.size())));
       return id;
    }
}
