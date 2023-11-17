package com.rena.dinosexpansion.common.biome.biomes.swamp;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.gen.surfacebuilder.DinoSurfaceBuilders;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.BiomeDictionary;

public class Quagmire extends BiomeBase {

    private static final Biome.RainType PRECIPITATION = Biome.RainType.RAIN;
    private static final Biome.Category CATEGORY = Biome.Category.SWAMP;
    private static final float DEPTH = -0.2F;
    private static final float SCALE = 0.1F;
    private static final float TEMPERATURE = 0.6F;
    private static final float DOWNFALL = 0.9F;
    private static final int WATER_COLOR = 5257771;
    private static final int WATER_FOG_COLOR = 13390080;
    private static final int GRASS_COLOR = 9666656;
    private static final int FOLIAGE_COLOR = 11182194;
    private static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    private static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder();
    private static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(DinoSurfaceBuilders.MUD::get);

    public Quagmire() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(WATER_COLOR)
                .setWaterFogColor(WATER_FOG_COLOR).setFogColor(BiomeBase.BASE_FOG_COLOUR)
                .withGrassColor(GRASS_COLOR).withFoliageColor(FOLIAGE_COLOR).withSkyColor(BiomeBase.calculateSkyColor(0.8F))
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build());
    }

    @Override
    public int getWeight() {
        return 2;
    }

    @Override
    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.SWAMP};
    }

    @Override
    public BiomeType[] getBiomeType() {
        return new BiomeType[]{BiomeType.WARM};
    }
}
