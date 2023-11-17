package com.rena.dinosexpansion.common.biome.biomes.lakes;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.gen.surfacebuilder.DinoSurfaceBuilders;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.BiomeDictionary;

public class Lake extends BiomeBase {

    private static final Biome.RainType PRECIPITATION = Biome.RainType.RAIN;
    private static final Biome.Category CATEGORY = Biome.Category.TAIGA;
    private static final float DEPTH = -0.55F;
    private static final float SCALE = 0.0F;
    private static final float TEMPERATURE = 0.25F;
    private static final float DOWNFALL = 0.8F;
    private static final int GRASS_COLOR = 10662752;
    private static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    private static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder();
    private static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(DinoSurfaceBuilders.LAKE::get);

    public Lake() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(BiomeBase.BASE_WATER_COLOUR)
                .setWaterFogColor(BiomeBase.BASE_WATER_FOG_COLOUR).setFogColor(BiomeBase.BASE_FOG_COLOUR).withGrassColor(GRASS_COLOR)
                .withSkyColor(BiomeBase.calculateSkyColor(0.8F)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE)
                .build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build());
    }

    @Override
    public int getWeight() {
        return 3;
    }

    @Override
    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.FOREST, BiomeDictionary.Type.WATER, BiomeDictionary.Type.CONIFEROUS};
    }

    @Override
    public BiomeType[] getBiomeType() {
        return new BiomeType[]{BiomeType.COOL};
    }

    static {
        DefaultBiomeFeatures.withCavesAndCanyons(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withCommonOverworldBlocks(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withOverworldOres(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withClayDisks(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withDefaultFlowers(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withBadlandsGrass(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withNormalMushroomGeneration(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withLavaAndWaterSprings(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withFrozenTopLayer(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withMountainEdgeTrees(GENERATION_SETTINGS);
    }
}
