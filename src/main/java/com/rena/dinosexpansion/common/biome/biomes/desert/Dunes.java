package com.rena.dinosexpansion.common.biome.biomes.desert;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import net.minecraft.world.biome.*;

public class Dunes extends BiomeBase {
    public Dunes(Biome.Climate climate, Biome.Category category, float depth, float scale, BiomeAmbience effects, BiomeGenerationSettings biomeGenerationSettings, MobSpawnInfo mobSpawnInfo) {
        super(climate, category, depth, scale, effects, biomeGenerationSettings, mobSpawnInfo);
    }
    /*static final Biome.RainType PRECIPITATION = Biome.RainType.NONE;
    static final Biome.Category CATEGORY = Biome.Category.DESERT;
    static final float DEPTH = 1.3F;
    static final float SCALE = 0.5F;
    static final float TEMPERATURE = 2.00F;
    static final float DOWNFALL = 0.0F;

    static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
    static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(SURFACE_BUILDER);

    public Dunes() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(BiomeBase.BASE_WATER_COLOUR).setWaterFogColor(BiomeBase.BASE_WATER_FOG_COLOUR)
                .setFogColor(12638463).withSkyColor(BiomeBase.calculateSkyColor(0.8F)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build(),
                GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build());
    }*/
}
