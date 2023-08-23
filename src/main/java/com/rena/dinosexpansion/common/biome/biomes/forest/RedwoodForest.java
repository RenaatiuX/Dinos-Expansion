package com.rena.dinosexpansion.common.biome.biomes.forest;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;

public class RedwoodForest extends BiomeBase {
    public RedwoodForest(Biome.Climate climate, Biome.Category category, float depth, float scale, BiomeAmbience effects, BiomeGenerationSettings biomeGenerationSettings, MobSpawnInfo mobSpawnInfo) {
        super(climate, category, depth, scale, effects, biomeGenerationSettings, mobSpawnInfo);
    }
}
