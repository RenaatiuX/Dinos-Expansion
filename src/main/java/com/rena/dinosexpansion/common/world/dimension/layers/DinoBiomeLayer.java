package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public class DinoBiomeLayer implements IC0Transformer {
    private static final int UNCOMMON_BIOME_CHANCE = 8;
    private static final int RARE_BIOME_CHANCE = 16;

    protected int[] commonBiomes = new int[]{
            DinoLayerUtil.getBiomeId(BiomeInit.DESERT.getKey())
    };
    protected int[] uncommonBiomes = new int[]{
            DinoLayerUtil.getBiomeId(BiomeInit.DESERT.getKey())
    };
    protected int[] rareBiomes = new int[]{
            DinoLayerUtil.getBiomeId(BiomeInit.RED_DESERT.getKey()),
            DinoLayerUtil.getBiomeId(BiomeInit.ALPS.getKey())
    };

    @Override
    public int apply(INoiseRandom context, int value) {
        if (value == 1) {
            if (context.random(RARE_BIOME_CHANCE) == 0) {
                return rareBiomes[context.random(rareBiomes.length)];
            } else if (context.random(UNCOMMON_BIOME_CHANCE) == 0) {
                return uncommonBiomes[context.random(uncommonBiomes.length)];
            } else {
                return commonBiomes[context.random(commonBiomes.length)];
            }
        }
        return value;
    }
}
