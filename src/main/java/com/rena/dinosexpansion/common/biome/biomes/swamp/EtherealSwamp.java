package com.rena.dinosexpansion.common.biome.biomes.swamp;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.gen.surfacebuilder.DinoSurfaceBuilders;
import com.rena.dinosexpansion.core.init.FeatureInit;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeDictionary;

public class EtherealSwamp extends BiomeBase {

    private static final Biome.RainType PRECIPITATION = Biome.RainType.RAIN;
    private static final Biome.Category CATEGORY = Biome.Category.SWAMP;
    private static final float DEPTH = -0.22F;
    private static final float SCALE = 0.1F;
    private static final float TEMPERATURE = 0.8F;
    private static final float DOWNFALL = 0.8F;
    private static final int WATER_COLOR = 6388580;
    private static final int WATER_FOG_COLOR = 2302743;
    private static final int GRASS_COLOR = 6337104;
    private static final int FOLIAGE_COLOR = 6337104;
    private static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    private static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
    private static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(DinoSurfaceBuilders.ETHEREAL_SWAMP::get);

    public EtherealSwamp() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(WATER_COLOR)
                .setWaterFogColor(WATER_FOG_COLOR).setFogColor(12638463).withGrassColor(GRASS_COLOR)
                .withFoliageColor(FOLIAGE_COLOR).withSkyColor(BiomeBase.calculateSkyColor(0.8F))
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build());
    }

    @Override
    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.WET, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.WATER};
    }

    @Override
    public BiomeType[] getBiomeType() {
        return new BiomeType[]{BiomeType.COOL};
    }

    @Override
    public int getWeight() {
        return 6;
    }

    static {
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FeatureInit.ConfiguredFeatures.OSMUNDA);
    }
}
