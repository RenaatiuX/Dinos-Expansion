package com.rena.dinosexpansion.common.biome.biomes.desert.subbiomes;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.core.init.FeatureInit;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;

public class DesertHills extends BiomeBase {

    private static final Biome.RainType PRECIPITATION = Biome.RainType.NONE;
    private static final Biome.Category CATEGORY = Biome.Category.DESERT;
    private static final float DEPTH = 0.45F;
    private static final float SCALE = 0.3F;
    private static final float TEMPERATURE = 2.0F;
    private static final float DOWNFALL = 0.0F;

    private static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    private static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
    private static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.DESERT);


    public DesertHills() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(BiomeBase.BASE_WATER_COLOUR)
                .setWaterFogColor(BiomeBase.BASE_WATER_FOG_COLOUR).setFogColor(BiomeBase.BASE_FOG_COLOUR).withSkyColor(calculateSkyColor(0.8F))
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build());
    }

    @Override
    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[] {BiomeDictionary.Type.HOT, BiomeDictionary.Type.DRY, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.HILLS};
    }

    @Override
    public boolean isSubbiome() {
        return true;
    }
}
