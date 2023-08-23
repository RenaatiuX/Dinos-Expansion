package com.rena.dinosexpansion.common.biome.util;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BiomeCreator {

    public static Biome.Builder makeGenericDinoOcean(boolean deep, int waterColor, int waterFogColor){
        Biome.Builder builder = new Biome.Builder();
        builder.precipitation(Biome.RainType.RAIN)
                .category(Biome.Category.OCEAN)
                .depth(deep ? -1.8F : -1.0F)
                .scale(0.1F).temperature(0.5F)
                .downfall(0.5F)
                .setEffects(new net.minecraft.world.biome.BiomeAmbience.Builder()
                        .setWaterColor(waterColor)
                        .setWaterFogColor(waterFogColor)
                        .setFogColor(12638463)
                        .withSkyColor(calculateSkyColor(0.5F)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build());
        return builder;
    }

    public static Biome.Builder createLukewarmDinoBiomeBiome(boolean deep){
        Biome.Builder builder = BiomeCreator.makeGenericDinoOcean(deep, BiomeBase.LUKE_WARM_OCEAN_WATER_COLOUR, BiomeBase.LUKE_WARM_OCEAN_WATER_FOG_COLOUR);

        //mobs
        MobSpawnInfo.Builder spawnerBuilder =  new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withBatsAndHostiles(spawnerBuilder);
        spawnerBuilder.withSpawner(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(EntityType.SQUID, 8, 1, 4));
        //TODO add our own mobs here

        //generation settings
        BiomeGenerationSettings.Builder generationBuilder = BiomeCreator.getOceanGenerationSettingsBuilder(ConfiguredSurfaceBuilders.OCEAN_SAND, false, true, deep);

        generationBuilder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, deep ? Features.SEAGRASS_DEEP_WARM : Features.SEAGRASS_WARM);

        DefaultBiomeFeatures.withWarmKelp(generationBuilder);
        DefaultBiomeFeatures.withFrozenTopLayer(generationBuilder);


        builder.withGenerationSettings(generationBuilder.build());
        builder.withMobSpawnSettings(spawnerBuilder.build());
        return builder;
    }


    public static net.minecraft.world.biome.BiomeGenerationSettings.Builder getOceanGenerationSettingsBuilder(ConfiguredSurfaceBuilder<SurfaceBuilderConfig> surfaceBuilder, boolean hasOceanMonument, boolean isWarmOcean, boolean isDeepVariant) {
        net.minecraft.world.biome.BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new net.minecraft.world.biome.BiomeGenerationSettings.Builder()).withSurfaceBuilder(surfaceBuilder);
        StructureFeature<?, ?> structurefeature = isWarmOcean ? StructureFeatures.OCEAN_RUIN_WARM : StructureFeatures.OCEAN_RUIN_COLD;
        if (isDeepVariant) {
            if (hasOceanMonument) {
                biomegenerationsettings$builder.withStructure(StructureFeatures.MONUMENT);
            }

            DefaultBiomeFeatures.withOceanStructures(biomegenerationsettings$builder);
            biomegenerationsettings$builder.withStructure(structurefeature);
        } else {
            biomegenerationsettings$builder.withStructure(structurefeature);
            if (hasOceanMonument) {
                biomegenerationsettings$builder.withStructure(StructureFeatures.MONUMENT);
            }

            DefaultBiomeFeatures.withOceanStructures(biomegenerationsettings$builder);
        }

        biomegenerationsettings$builder.withStructure(StructureFeatures.RUINED_PORTAL_OCEAN);
        DefaultBiomeFeatures.withOceanCavesAndCanyons(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withLavaAndWaterLakes(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withMonsterRoom(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withCommonOverworldBlocks(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withOverworldOres(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withDisks(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withTreesInWater(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withDefaultFlowers(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withBadlandsGrass(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(biomegenerationsettings$builder);
        DefaultBiomeFeatures.withLavaAndWaterSprings(biomegenerationsettings$builder);
        return biomegenerationsettings$builder;
    }



    public static int calculateSkyColor(float temperature) {
        float colour = temperature / 3.0F;
        colour = MathHelper.clamp(colour, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - colour * 0.05F, 0.5F + colour * 0.1F, 1.0F);
    }
}
