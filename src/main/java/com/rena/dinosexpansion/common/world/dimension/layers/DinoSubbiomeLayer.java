package com.rena.dinosexpansion.common.world.dimension.layers;

import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import net.minecraft.network.play.client.CEditBookPacket;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

import java.util.function.Supplier;

public enum DinoSubbiomeLayer implements ICastleTransformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        for (BiomeBase base : BiomeBase.BIOMES){
            if (DinoLayerUtil.getBiomeId(base.getKey()) == center){
                for (BiomeBase.Subbiome subbiome : base.getSubBiomes()){
                    if (context.random(subbiome.getProbability()) == 0){
                        return DinoLayerUtil.getBiomeId(subbiome.getBiome().get());
                    }
                }
                break;
            }
        }
        return center;
    }
}
