package com.rena.dinosexpansion.common.biome.biomes.artic;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.gen.surfacebuilder.DinoSurfaceBuilders;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

public class Arctic extends BiomeBase {
    private static final Biome.RainType PRECIPITATION = Biome.RainType.SNOW;
    private static final Biome.Category CATEGORY = Biome.Category.ICY;
    private static final float DEPTH = -0.1F;
    private static final float SCALE = 0.5F;
    private static final float TEMPERATURE = -0.9F;
    private static final float DOWNFALL = 0.2F;

    private static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    private static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder();
    private static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(DinoSurfaceBuilders.ALPS);

    public Arctic() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(BiomeBase.BASE_WATER_COLOUR)
                .setWaterFogColor(BiomeBase.BASE_WATER_FOG_COLOUR).setFogColor(BiomeBase.BASE_FOG_COLOUR).withSkyColor(BiomeBase.calculateSkyColor(0.8F))
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build());
    }

    @Override
    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[] {BiomeDictionary.Type.SNOWY, BiomeDictionary.Type.COLD};
    }

    @Override
    public net.minecraftforge.common.BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.COOL;
    }
}
