package com.rena.dinosexpansion.common.biome.biomes.swamp;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.gen.surfacebuilder.DinoSurfaceBuilders;
import com.rena.dinosexpansion.core.init.SurfaceBuilderInit;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class Marshland extends BiomeBase {

    static final Biome.RainType PRECIPITATION = Biome.RainType.RAIN;
    static final Biome.Category CATEGORY = Biome.Category.SWAMP;
    static final float DEPTH = -0.15F;
    static final float SCALE = -0.05F;
    static final float TEMPERATURE = 0.65F;
    static final float DOWNFALL = 0.7F;
    private static final int WATER_COLOR = 4159204;
    private static final int WATER_FOG_COLOR = 329011;

    static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
    static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(DinoSurfaceBuilders.ALPS::get);

    public Marshland() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(WATER_COLOR).setWaterFogColor(WATER_FOG_COLOR)
                .setFogColor(BiomeBase.BASE_FOG_COLOUR).withSkyColor(BiomeBase.calculateSkyColor(0.8F))
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build());
    }
}
