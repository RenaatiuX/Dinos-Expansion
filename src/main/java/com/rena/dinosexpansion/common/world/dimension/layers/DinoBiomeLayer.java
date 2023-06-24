package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraftforge.common.BiomeManager;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DinoBiomeLayer implements IC0Transformer {

    protected List<BiomeBase>[] biomes = new LinkedList[BiomeBase.BiomeType.values().length];

    public DinoBiomeLayer() {
        for (int i = 0; i < biomes.length; i++) {
            biomes[i] = new LinkedList<>();
        }
        for (BiomeBase base : BiomeBase.BIOMES) {
            for (BiomeBase.BiomeType type : base.getBiomeType())
                biomes[type.ordinal()].add(base);
        }
    }

    @Override
    public int apply(INoiseRandom context, int value) {
        if (value - 100 >= 0) {
            int typeID = value - 100;
            BiomeBase.BiomeType type = BiomeBase.BiomeType.values()[typeID];
            BiomeBase[] result = BiomeBase.BIOMES.stream().filter(b -> DinoLayerUtil.contains(b.getBiomeType(), type)).collect(Collectors.toList()).toArray(new BiomeBase[0]);
            if (result.length > 0)
                return DinoLayerUtil.getBiomeId(DinoLayerUtil.weightedRandom(context, result));
            else
                return  DinoLayerUtil.getBiomeId(Biomes.PLAINS);
        }
        return value;
    }
}
