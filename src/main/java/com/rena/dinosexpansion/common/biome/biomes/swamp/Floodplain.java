package com.rena.dinosexpansion.common.biome.biomes.swamp;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.gen.surfacebuilder.DinoSurfaceBuilders;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.common.BiomeDictionary;

public class Floodplain extends BiomeBase {

    static final Biome.RainType PRECIPITATION = Biome.RainType.RAIN;
    static final Biome.Category CATEGORY = Biome.Category.SWAMP;
    static final float DEPTH = -0.275F;
    static final float SCALE = 0.0F;
    static final float TEMPERATURE = 0.9F;
    static final float DOWNFALL = 1.2F;
    private static final int WATER_COLOR = 4566514;
    private static final int WATER_FOG_COLOR = 267827;

    static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
    static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(DinoSurfaceBuilders.DENSE_SWAMP::get);

    public Floodplain() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(WATER_COLOR).setWaterFogColor(WATER_FOG_COLOR)
                .setFogColor(BiomeBase.BASE_FOG_COLOUR).withSkyColor(BiomeBase.calculateSkyColor(0.8F))
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build());
    }

    @Override
    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WET};
    }

    @Override
    public BiomeType[] getBiomeType() {
        return new BiomeType[]{BiomeType.WARM};
    }

    @Override
    public int getWeight() {
        return 2;
    }

    static {
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP);
        DefaultBiomeFeatures.withLavaAndWaterLakes(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withCommonOverworldBlocks(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withClayDisks(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withSwampVegetation(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withNormalMushroomGeneration(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withSwampSugarcaneAndPumpkin(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withLavaAndWaterSprings(GENERATION_SETTINGS);
    }
}
