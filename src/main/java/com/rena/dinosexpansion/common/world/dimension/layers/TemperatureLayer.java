package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

import java.util.Arrays;

public enum TemperatureLayer implements IAreaTransformer0 {
    INSTANCE;


    @Override
    public int apply(INoiseRandom noise, int x, int z) {
        int totalWeight = Arrays.stream(BiomeBase.BiomeType.values()).mapToInt(BiomeBase.BiomeType::getWeight).sum();
        int cumulativeWeight = 0;
        int randomWright = noise.random(totalWeight);
        for (BiomeBase.BiomeType type : BiomeBase.BiomeType.values()) {
            cumulativeWeight += type.getWeight();
            if (randomWright < cumulativeWeight) {
                return 100 + type.ordinal();
            } else {
                totalWeight -= type.getWeight();
            }
        }
        System.out.println("something went wrong");
        return 0;
    }
}
