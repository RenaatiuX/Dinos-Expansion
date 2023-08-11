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

public class DinoChunkGenerator extends ChunkGenerator {

    public static final Codec<DinoChunkGenerator> CODEC = RecordCodecBuilder.create((c) -> {
        return c.group(BiomeProvider.CODEC.fieldOf("biome_source").forGetter((chunkGenerator) -> {
            return chunkGenerator.biomeProvider;
        }), Codec.LONG.fieldOf("seed").orElseGet(WorldSeedHolder::getSeed).forGetter((chunkGenerator) -> {
            return chunkGenerator.seed;
        }), DimensionSettings.DIMENSION_SETTINGS_CODEC.fieldOf("settings").forGetter((chunkGenerator) -> {
            return chunkGenerator.settings;
        })).apply(c, c.stable(DinoChunkGenerator::new));
    });
    private long seed;
    public static long hackSeed;

    public Supplier<DimensionSettings> settings;

    public DinoChunkGenerator(BiomeProvider provider, long seed, Supplier<DimensionSettings> settingsIn) {
        super(provider, settingsIn.get().getStructures());
        this.seed = seed;
        this.settings = settingsIn;
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return CODEC;
    }

    @Override
    public void generateSurface(WorldGenRegion genRegion, IChunk chunk) {
        ChunkPos chunkpos = chunk.getPos();
        int i = chunkpos.x;
        int j = chunkpos.z;
        SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
        sharedseedrandom.setBaseChunkSeed(i, j);
        ChunkPos chunkpos1 = chunk.getPos();
        int k = chunkpos1.getXStart();
        int l = chunkpos1.getZStart();
        double d0 = 0.0625D;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for (int i1 = 0; i1 < 16; ++i1) {
            for (int j1 = 0; j1 < 16; ++j1) {
                int k1 = k + i1;
                int l1 = l + j1;
                int i2 = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE_WG, i1, j1) + 1;
                double d1 = getIfPossible().surface.noiseAt(k1, l1);
                genRegion.getBiome(blockpos$mutable.setPos(k + i1, i2, l + j1)).buildSurface(sharedseedrandom, chunk, k1, l1, i2, d1, this.settings.get().getDefaultBlock(), this.settings.get().getDefaultFluid(), this.getSeaLevel(), genRegion.getSeed());
                //chunk.setBlockState(blockpos$mutable.setPos(k1, d1, l1), Blocks.DIAMOND_BLOCK.getDefaultState(), false);
            }
        }

        this.makeBedrock(chunk, sharedseedrandom);

    }

    @Override
    public void func_230352_b_(IWorld p_230352_1_, StructureManager p_230352_2_, IChunk p_230352_3_) {

    }

    public void makeBedrock(IChunk chunkIn, Random rand) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int i = chunkIn.getPos().getXStart();
        int j = chunkIn.getPos().getZStart();
        DimensionSettings dimensionsettings = this.settings.get();
        int k = dimensionsettings.getBedrockFloorPosition();
        int l = this.getMaxBuildHeight() - 1 - dimensionsettings.getBedrockRoofPosition();
        int i1 = 5;
        boolean flag = l + 4 >= 0 && l < this.getMaxBuildHeight();
        boolean flag1 = k + 4 >= 0 && k < this.getMaxBuildHeight();
        if (flag || flag1) {
            for (BlockPos blockpos : BlockPos.getAllInBoxMutable(i, 0, j, i + 15, 0, j + 15)) {
                if (flag) {
                    for (int j1 = 0; j1 < 5; ++j1) {
                        if (j1 <= rand.nextInt(5)) {
                            chunkIn.setBlockState(blockpos$mutable.setPos(blockpos.getX(), l - j1, blockpos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                        }
                    }
                }

                if (flag1) {
                    for (int k1 = 4; k1 >= 0; --k1) {
                        if (k1 <= rand.nextInt(5)) {
                            chunkIn.setBlockState(blockpos$mutable.setPos(blockpos.getX(), k + k1, blockpos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                        }
                    }
                }
            }

        }
    }

    public int getSeaLevel() {
        return this.settings.get().getSeaLevel();
    }


    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        return (int) getIfPossible().surface.noiseAt(x, z);
    }

    @Override
    public IBlockReader func_230348_a_(int x, int z) {
        int height = Math.max(this.getSeaLevel(), getHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG));

        BlockState[] states = new BlockState[height + 1];
        for (int y = 0; y <= height; y++) {
            states[y] = getStateForY(y, height);
        }

        return new Blockreader(states);
    }

    protected BlockState getStateForY(int y, int maxHeight) {
        if (y < this.getSeaLevel() && y <= maxHeight) {
            return this.settings.get().getDefaultFluid();
        }
        return this.settings.get().getDefaultBlock();

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
