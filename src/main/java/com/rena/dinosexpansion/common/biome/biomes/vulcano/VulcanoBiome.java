package com.rena.dinosexpansion.common.biome.biomes.vulcano;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.biome.util.BiomeCreator;
import com.rena.dinosexpansion.core.init.ConfiguredStructureInit;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class VulcanoBiome extends BiomeBase {


    private static final Biome.Climate CLIMATE = new Biome.Climate(Biome.RainType.NONE, 2f, Biome.TemperatureModifier.NONE, .15f);

    protected static final BiomeAmbience BIOME_AMBIENCE = new BiomeAmbience.Builder().setFogColor(6840176).setWaterFogColor(4341314).setWaterColor(4159204).setParticle(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.118093334F)).withSkyColor(BiomeCreator.calculateSkyColor(2.0F)).build();

    protected static final BiomeGenerationSettings GENERATION_SETTINGS = new BiomeGenerationSettings.Builder().withCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.CAVE).withSurfaceBuilder(() -> SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(Blocks.BASALT.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.GRAVEL.getDefaultState()))).build();

    protected static final MobSpawnInfo MOB_SPAWNS = new MobSpawnInfo.Builder().withSpawnCost(EntityType.MAGMA_CUBE, 2, 10).withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.MAGMA_CUBE, 10, 1, 3)).build();

    public VulcanoBiome() {
        super(CLIMATE, Biome.Category.NONE, .2f, .4f, BIOME_AMBIENCE, GENERATION_SETTINGS, MOB_SPAWNS);
    }


    @Override
    public BiomeType[] getBiomeType() {
        return new BiomeType[]{BiomeType.NORMAL, BiomeType.HOT, BiomeType.LUKEWARM};
    }

    @Override
    public int getWeight() {
        return 5;
    }
}
