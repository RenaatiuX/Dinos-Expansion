package com.rena.dinosexpansion.common.world.dimension.layers;

import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

import java.util.function.Supplier;

public enum DinoSubbiomeLayer implements IC1Transformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom iNoiseRandom, int i) {
        for (BiomeBase base : BiomeBase.BIOMES){
            if (DinoLayerUtil.getBiomeId(base.getKey()) == i){
                for (Pair<Supplier<RegistryKey<Biome>>, Integer> subbiome : base.getSubBiomes()){
                    if (iNoiseRandom.random(subbiome.getSecond()) == 0){
                        return DinoLayerUtil.getBiomeId(subbiome.getFirst().get());
                    }
                }
                break;
            }
        }
        return i;
    }
}
