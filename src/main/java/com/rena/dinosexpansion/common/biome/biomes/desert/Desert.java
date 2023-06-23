package com.rena.dinosexpansion.common.biome.biomes.desert;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.util.WeightedList;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

public class Desert extends BiomeBase {
    private static final Biome.RainType PRECIPITATION = Biome.RainType.NONE;
    private static final Biome.Category CATEGORY = Biome.Category.DESERT;
    private static final float DEPTH = 0.125F;
    private static final float SCALE = 0.05F;
    private static final float TEMPERATURE = 2.0F;
    private static final float DOWNFALL = 0.0F;

    private static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    private static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
    private static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(ConfiguredSurfaceBuilders.DESERT);

    public Desert() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(BiomeBase.BASE_WATER_COLOUR)
                .setWaterFogColor(BiomeBase.BASE_WATER_FOG_COLOUR).setFogColor(BiomeBase.BASE_FOG_COLOUR).withSkyColor(BiomeBase.calculateSkyColor(0.8F))
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build());
        addSubBiome(() -> BiomeInit.DESERT_HILLS.getKey(), 10);
    }

    @Override
    public WeightedList<Biome> getHills() {
        WeightedList<Biome> biomeWeightedList = new WeightedList<>();
        biomeWeightedList.addWeighted(BiomeInit.DESERT_HILLS_BIOME.get(), 5);
        return biomeWeightedList;
    }

    @Override
    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.HOT, BiomeDictionary.Type.DRY, BiomeDictionary.Type.SANDY};
    }

    @Override
    public BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.DESERT;
    }

    @Override
    public int getWeight() {
        return 4;
    }
}

