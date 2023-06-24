package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

import java.util.Arrays;

public enum TemperatureLayer implements IAreaTransformer0 {
    INSTANCE;


    @Override
    public int apply(INoiseRandom noise, int x, int z) {
        int totalWeight = Arrays.stream(BiomeBase.BiomeType.values()).mapToInt(BiomeBase.BiomeType::getWeight).sum();
        for (BiomeBase.BiomeType type : BiomeBase.BiomeType.values()){
            if (noise.random(totalWeight) <= type.getWeight()){
                return 100 + type.ordinal();
            }else {
                totalWeight -= type.getWeight();
            }
        }
        return 0;
    }
}
