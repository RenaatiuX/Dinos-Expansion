package com.rena.dinosexpansion.common.biome.biomes.ocean.subbiome;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

public class DeepFrozenOcean extends BiomeBase {
    private static final Biome.RainType PRECIPITATION = Biome.RainType.RAIN;
    private static final Biome.Category CATEGORY = Biome.Category.OCEAN;
    private static final float DEPTH = -1.8F;
    private static final float SCALE = 0.1F;
    private static final float TEMPERATURE = 0.5F;
    private static final float DOWNFALL = 0.5F;
    private static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.FROZEN, DOWNFALL);
    private static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
    private static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.FROZEN_OCEAN);

    public DeepFrozenOcean() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(BiomeBase.FROZE_OCEAN_WATER_COLOUR)
                .setWaterFogColor(BiomeBase.FROZE_OCEAN_WATER_FOG_COLOUR).setFogColor(BiomeBase.BASE_FOG_COLOUR).withSkyColor(BiomeBase.calculateSkyColor(0.5F))
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build());
    }

    @Override
    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.OCEAN};
    }

    @Override
    public BiomeType[] getBiomeType() {
        return new BiomeType[]{BiomeType.ICY};
    }

    @Override
    public boolean isDeepOcean() {
        return true;
    }

    static {

        DefaultBiomeFeatures.withOceanCavesAndCanyons(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withLavaAndWaterLakes(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withIcebergs(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withBlueIce(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withCommonOverworldBlocks(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withOverworldOres(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withDisks(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withTreesInWater(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withDefaultFlowers(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withBadlandsGrass(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withNormalMushroomGeneration(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withLavaAndWaterSprings(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withFrozenTopLayer(GENERATION_SETTINGS);
    }

}
