package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraftforge.common.BiomeManager;

public enum AddTemperature implements IAreaTransformer0 {
    INSTANCE;

    //adds BiomeTypes with an offset of 100
    //add temperature values all over the place
    //this is weighted so the more biomes are in one zone the more of these zones will spawn
    @Override
    public int apply(INoiseRandom context, int x, int z) {
        int icyWeight = DinoBiomeLayerMixer.NORMAL_LANDBIOMES.get(BiomeManager.BiomeType.ICY).size();
        int coolWeight = DinoBiomeLayerMixer.NORMAL_LANDBIOMES.get(BiomeManager.BiomeType.COOL).size();
        int warmWeight = DinoBiomeLayerMixer.NORMAL_LANDBIOMES.get(BiomeManager.BiomeType.WARM).size();
        int desertWeight = DinoBiomeLayerMixer.NORMAL_LANDBIOMES.get(BiomeManager.BiomeType.DESERT).size();
        int rand = context.random(icyWeight + coolWeight + warmWeight + desertWeight);
        if (rand < icyWeight) {
            return BiomeManager.BiomeType.ICY.ordinal() + 100;
        }
        if (rand - icyWeight < coolWeight) {
            return BiomeManager.BiomeType.COOL.ordinal() + 100;
        }
        if (rand - icyWeight - coolWeight < warmWeight) {
            return BiomeManager.BiomeType.WARM.ordinal() + 100;
        }
        return BiomeManager.BiomeType.DESERT.ordinal() + 100;
    }
}
