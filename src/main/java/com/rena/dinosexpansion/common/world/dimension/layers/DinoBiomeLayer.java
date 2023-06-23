package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class DinoBiomeLayer implements IAreaTransformer0 {
    private static final int UNCOMMON_BIOME_CHANCE = 8;
    private static final int RARE_BIOME_CHANCE = 16;

    protected int[] commonBiomes = new int[] {
    };
    protected int[] uncommonBiomes = new int[] {

    };
    protected int[] rareBiomes = new int[] {
            DinoLayerUtil.getBiomeId(BiomeInit.DESERT.getKey()),
    };

    @Override
    public int apply(INoiseRandom iNoiseRandom, int x, int z) {
        if (iNoiseRandom.random(RARE_BIOME_CHANCE) == 0) {
            return rareBiomes[iNoiseRandom.random(rareBiomes.length)];
        } else if (iNoiseRandom.random(UNCOMMON_BIOME_CHANCE) == 0) {
            return uncommonBiomes[iNoiseRandom.random(uncommonBiomes.length)];
        } else {
            return commonBiomes[iNoiseRandom.random(commonBiomes.length)];
        }
    }
}
