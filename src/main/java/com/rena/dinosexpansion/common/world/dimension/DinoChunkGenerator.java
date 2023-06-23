package com.rena.dinosexpansion.common.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;

import java.util.function.Supplier;

public class DinoChunkGenerator extends NoiseChunkGenerator {

    public static final Codec<DinoChunkGenerator> CODEC = RecordCodecBuilder.create((c) -> {
        return c.group(BiomeProvider.CODEC.fieldOf("biome_source").forGetter((chunkGenerator) -> {
            return chunkGenerator.biomeProvider;
        }), Codec.LONG.fieldOf("seed").orElseGet(WorldSeedHolder::getSeed).forGetter((chunkGenerator) -> {
            return chunkGenerator.seed;
        }), DimensionSettings.DIMENSION_SETTINGS_CODEC.fieldOf("settings").forGetter((chunkGenerator) -> {
            return chunkGenerator.field_236080_h_;
        })).apply(c, c.stable(DinoChunkGenerator::new));
    });
    private long seed;
    public static long hackSeed;

    public DinoChunkGenerator(BiomeProvider provider, long seed, Supplier<DimensionSettings> settingsIn) {
        super(provider, seed, settingsIn);
        this.seed = WorldSeedHolder.getSeed();
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_()
    {
        return CODEC;
    }

    @Override
    public ChunkGenerator func_230349_a_(long seed)
    {
        return new DinoChunkGenerator(biomeProvider.getBiomeProvider(WorldSeedHolder.getSeed()), WorldSeedHolder.getSeed(), getDimensionSettings());
    }

    private Supplier<DimensionSettings> getDimensionSettings()
    {
        return this.field_236080_h_;
    }
}
