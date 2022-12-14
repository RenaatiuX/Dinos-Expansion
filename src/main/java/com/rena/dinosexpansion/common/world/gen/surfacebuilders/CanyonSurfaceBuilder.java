package com.rena.dinosexpansion.common.world.gen.surfacebuilders;

import com.mojang.serialization.Codec;
import com.rena.dinosexpansion.common.world.util.FastNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class CanyonSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public CanyonSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    protected long seed;
    protected FastNoise noiseGen = null;
    protected FastNoise noiseGen2 = null;
    protected FastNoise noiseGen3 = null;
    protected FastNoise noiseGen4 = null;
    protected FastNoise noiseGen5 = null;
    protected FastNoise noiseGen6 = null;
    public BlockState layerBlock = Blocks.TERRACOTTA.getDefaultState();

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        initNoise(seed);
        BlockPos.Mutable block = new BlockPos.Mutable();
        int xPos = x & 15;
        int zPos = z & 15;

        double canyonShapingNoise1 = noiseGen.GetNoise(x, z) * 10;
        double canyonShapingNoise2 = noiseGen6.GetNoise(x, z) * 10;
        double noiseSample2 = noiseGen2.GetNoise(x, z);
        double noiseSample3 = noiseGen3.GetNoise(x, z);
        double noiseSample4 = noiseGen4.GetNoise(x * 1.1F, z * 1.34F);

        double totalNoiseSample = (noiseSample2 * 15) + (noiseSample3 * 9) * noiseSample3;

        double simulateErosion = Math.abs((noiseSample2) * 8 + (noiseSample4 * (4 + (noiseSample2 * 3))));

        //0.03 is effectively one block w/ the ridged noise sample.
        if (canyonShapingNoise2 > 9.0) {
            for (int yPos = startHeight; yPos >= seaLevel - totalNoiseSample; --yPos) {
                block.setPos(xPos, yPos, zPos);
                double noiseSample5 = noiseGen5.GetNoise(x * 1.1F, yPos, z * 1.34F);

                if (canyonShapingNoise2 < 9.06) {
                    if (yPos < startHeight - 10 - simulateErosion - (noiseSample5 * 6))
                        chunkIn.setBlockState(block, Blocks.STONE.getDefaultState(), false);
                    else
                        chunkIn.setBlockState(block, Blocks.AIR.getDefaultState(), false);
                } else if (canyonShapingNoise2 < 9.12) {
                    if (yPos < startHeight - 13 - simulateErosion - (noiseSample5 * 6))
                        chunkIn.setBlockState(block, Blocks.STONE.getDefaultState(), false);
                    else
                        chunkIn.setBlockState(block, Blocks.AIR.getDefaultState(), false);
                } else if (canyonShapingNoise2 < 9.18) {
                    if (yPos < startHeight - 16 - simulateErosion - (noiseSample5 * 6))
                        chunkIn.setBlockState(block, Blocks.STONE.getDefaultState(), false);
                    else
                        chunkIn.setBlockState(block, Blocks.AIR.getDefaultState(), false);
                } else if (canyonShapingNoise2 < 9.24) {
                    if (yPos < startHeight - 19 - simulateErosion - (noiseSample5 * 6))
                        chunkIn.setBlockState(block, Blocks.STONE.getDefaultState(), false);
                    else
                        chunkIn.setBlockState(block, Blocks.AIR.getDefaultState(), false);
                } else if (canyonShapingNoise2 < 9.45) {
                    if (yPos < startHeight - 22 - simulateErosion - (noiseSample5 * 6))
                        chunkIn.setBlockState(block, Blocks.STONE.getDefaultState(), false);
                    else
                        chunkIn.setBlockState(block, Blocks.AIR.getDefaultState(), false);
                } else if (canyonShapingNoise2 < 9.51) {
                    if (yPos < startHeight - 32 - simulateErosion - (noiseSample5 * 6))
                        chunkIn.setBlockState(block, Blocks.STONE.getDefaultState(), false);
                    else
                        chunkIn.setBlockState(block, Blocks.AIR.getDefaultState(), false);
                } else if (canyonShapingNoise2 < 9.57) {
                    if (yPos < startHeight - 35 - simulateErosion - (noiseSample5 * 6))
                        chunkIn.setBlockState(block, Blocks.STONE.getDefaultState(), false);
                    else
                        chunkIn.setBlockState(block, Blocks.AIR.getDefaultState(), false);
                } else if (canyonShapingNoise2 < 9.63) {
                    if (yPos < startHeight - 38 - simulateErosion - (noiseSample5 * 6))
                        chunkIn.setBlockState(block, Blocks.STONE.getDefaultState(), false);
                    else
                        chunkIn.setBlockState(block, Blocks.AIR.getDefaultState(), false);
                } else if (canyonShapingNoise2 < 9.69) {
                    if (yPos < startHeight - 41 - simulateErosion - (noiseSample5 * 6))
                        chunkIn.setBlockState(block, Blocks.STONE.getDefaultState(), false);
                    else
                        chunkIn.setBlockState(block, Blocks.AIR.getDefaultState(), false);
                } else {
                    if (yPos >= seaLevel - 1) {
                        chunkIn.setBlockState(block, Blocks.AIR.getDefaultState(), false);
                    }
                }
            }
        }

        SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, config);


        for (int y = seaLevel - 1; y >= seaLevel - 20; y--) {
            block.setPos(xPos, y, zPos);
            if (chunkIn.getBlockState(block).isAir()) {
                chunkIn.setBlockState(block, Blocks.WATER.getDefaultState(), false);
                chunkIn.getFluidsToBeTicked().scheduleTick(block, Fluids.WATER, 0);
            }
        }
    }

    public void initNoise(long seed) {
        if (noiseGen == null) {
            noiseGen = new FastNoise((int) seed);
            noiseGen.SetFractalType(FastNoise.FractalType.RigidMulti);
            noiseGen.SetNoiseType(FastNoise.NoiseType.SimplexFractal);
            noiseGen.SetGradientPerturbAmp(1);
            noiseGen.SetFractalOctaves(1);
            noiseGen.SetFractalGain(0.3f);
            noiseGen.SetFrequency(0.0012F);
        }
        if (noiseGen2 == null) {
            noiseGen2 = new FastNoise((int) seed + 20);
            noiseGen2.SetNoiseType(FastNoise.NoiseType.Simplex);
            noiseGen2.SetFractalOctaves(2);
            noiseGen2.SetFractalGain(0.3f);
            noiseGen2.SetFrequency(0.01F);
        }
        if (noiseGen3 == null) {
            noiseGen3 = new FastNoise((int) seed + 277);
            noiseGen3.SetNoiseType(FastNoise.NoiseType.ValueFractal);
            noiseGen3.SetFractalOctaves(1);
            noiseGen3.SetFractalGain(0.3f);
            noiseGen3.SetFrequency(0.004F);
        }

        if (noiseGen4 == null) {
            noiseGen4 = new FastNoise((int) seed + 234233);
            noiseGen4.SetNoiseType(FastNoise.NoiseType.ValueFractal);
            noiseGen4.SetFractalOctaves(1);
            noiseGen4.SetFractalGain(0.3f);
            noiseGen4.SetFrequency(0.01F);
        }
        if (noiseGen5 == null) {
            noiseGen5 = new FastNoise((int) seed + 234233);
            noiseGen5.SetNoiseType(FastNoise.NoiseType.Simplex);
            noiseGen5.SetFractalOctaves(1);
            noiseGen5.SetFractalGain(0.3f);
            noiseGen5.SetFrequency(0.001F);
        }

        if (noiseGen6 == null) {
            noiseGen6 = new FastNoise((int) seed + 395958);
            noiseGen6.SetFractalType(FastNoise.FractalType.RigidMulti);
            noiseGen6.SetNoiseType(FastNoise.NoiseType.SimplexFractal);
            noiseGen6.SetGradientPerturbAmp(1);
            noiseGen6.SetFractalOctaves(1);
            noiseGen6.SetFractalGain(0.3f);
            noiseGen6.SetFrequency(0.0012F);
        }
    }
}
