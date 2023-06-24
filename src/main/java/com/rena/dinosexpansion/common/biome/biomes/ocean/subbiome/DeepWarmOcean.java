package com.rena.dinosexpansion.common.biome.biomes.ocean.subbiome;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

public class DeepWarmOcean extends BiomeBase {

    private static final Biome.RainType PRECIPITATION = Biome.RainType.RAIN;
    private static final Biome.Category CATEGORY = Biome.Category.OCEAN;
    private static final float DEPTH = -1.0F;
    private static final float SCALE = 0.1F;
    private static final float TEMPERATURE = 0.5F;
    private static final float DOWNFALL = 0.5F;
    private static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    private static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
    private static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.FULL_SAND);

    public DeepWarmOcean() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(BiomeBase.WARM_OCEAN_WATER_COLOUR)
                .setWaterFogColor(BiomeBase.WARM_OCEAN_WATER_FOG_COLOUR).setFogColor(BiomeBase.BASE_FOG_COLOUR).withSkyColor(BiomeBase.calculateSkyColor(0.5F))
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build());
    }

    @Override
    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.OCEAN};
    }

    @Override
    public net.minecraftforge.common.BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.COOL;
    }

    static {

        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_DEEP_WARM);
        DefaultBiomeFeatures.withSimpleSeagrass(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withFrozenTopLayer(GENERATION_SETTINGS);
    }

}
