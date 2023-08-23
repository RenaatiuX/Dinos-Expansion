package com.rena.dinosexpansion.common.biome.biomes.river;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

public class River extends BiomeBase {
    private static final Biome.RainType PRECIPITATION = Biome.RainType.RAIN;
    private static final Biome.Category CATEGORY = Biome.Category.RIVER;
    private static final float DEPTH = -0.5F;
    private static final float SCALE = 0.0F;
    private static final float TEMPERATURE = 0.5F;
    private static final float DOWNFALL = 0.5F;
    private static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    private static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
    private static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
    public River() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(BiomeBase.BASE_WATER_COLOUR)
                .setWaterFogColor(BiomeBase.BASE_WATER_FOG_COLOUR).setFogColor(BiomeBase.BASE_FOG_COLOUR).withSkyColor(BiomeBase.calculateSkyColor(0.8F))
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build());
    }

    @Override
    public boolean isRiver() {
        return true;
    }

    @Override
    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.RIVER};
    }

    @Override
    public int getWeight() {
        return 6;
    }
}
