package com.rena.dinosexpansion.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class RoundedHillsSB extends SurfaceBuilder<SurfaceBuilderConfig> {
    public RoundedHillsSB(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        int surfaceHeight = startHeight + (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int roundedHeight = startHeight + 3;

        for (int y = seaLevel; y <= surfaceHeight; ++y) {
            if (y <= roundedHeight) {
                double distanceToTop = (double) (roundedHeight - y + 1) / 4.0D;

                double displacement = (Math.sin(distanceToTop * Math.PI - Math.PI / 2) + 1.0D) * distanceToTop;

                double xPos = (double) x + 0.5D + displacement;
                double zPos = (double) z + 0.5D + displacement;

                BlockPos blockPos = new BlockPos(xPos, y, zPos);
                chunkIn.setBlockState(blockPos, defaultBlock, false);
            } else {
                BlockPos blockPos = new BlockPos(x, y, z);
                chunkIn.setBlockState(blockPos, defaultBlock, false);
            }
        }
    }
}
