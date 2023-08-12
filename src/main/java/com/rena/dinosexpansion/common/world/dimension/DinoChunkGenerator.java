package com.rena.dinosexpansion.common.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;
import java.util.function.Supplier;

public class DinoChunkGenerator extends NoiseChunkGenerator {

    public static final Codec<DinoChunkGenerator> CODEC = RecordCodecBuilder.create((c) -> {
        return c.group(BiomeProvider.CODEC.fieldOf("biome_source").forGetter((chunkGenerator) -> {
            return chunkGenerator.biomeProvider;
        }), Codec.LONG.fieldOf("seed").orElseGet(() -> 0L).forGetter((chunkGenerator) -> {
            return chunkGenerator.seed;
        }), DimensionSettings.DIMENSION_SETTINGS_CODEC.fieldOf("settings").forGetter((chunkGenerator) -> {
            return chunkGenerator.settings;
        })).apply(c, c.stable(DinoChunkGenerator::new));
    });
    private long seed;
    public static long hackSeed;

    public Supplier<DimensionSettings> settings;

    public DinoChunkGenerator(BiomeProvider provider, long seed, Supplier<DimensionSettings> settingsIn) {
        super(provider, seed, settingsIn);
        this.seed = seed;
        this.settings = settingsIn;
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return CODEC;
    }

    @Override
    public ChunkGenerator func_230349_a_(long seed) {
        return new DinoChunkGenerator(biomeProvider.getBiomeProvider(seed), seed, getDimensionSettings());
    }

    private Supplier<DimensionSettings> getDimensionSettings() {
        return this.settings;
    }

    protected DinoBiomeProvider getIfPossible() {
        if (this.biomeProvider instanceof DinoBiomeProvider) {
            return (DinoBiomeProvider) this.biomeProvider;
        }
        return null;
    }


}
