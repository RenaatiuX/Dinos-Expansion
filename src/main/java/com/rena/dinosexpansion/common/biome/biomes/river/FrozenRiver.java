package com.rena.dinosexpansion.common.biome.biomes.river;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;

public class FrozenRiver extends BiomeBase {
    public FrozenRiver(Biome.Climate climate, Biome.Category category, float depth, float scale, BiomeAmbience effects, BiomeGenerationSettings biomeGenerationSettings, MobSpawnInfo mobSpawnInfo) {
        super(climate, category, depth, scale, effects, biomeGenerationSettings, mobSpawnInfo);
    }

    @Override
    public boolean isRiver() {
        return true;
    }
}
