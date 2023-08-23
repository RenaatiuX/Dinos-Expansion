package com.rena.dinosexpansion.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class DInoDesertSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public DInoDesertSurfaceBuilder() {
        super(SurfaceBuilderConfig.CODEC);
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {

        // Adjust the noise value to control the dune formation
        noise = (noise + 1.0) / 2.0; // Convert noise to a range between 0 and 1

        // Determine the height of the dune at this location
        int duneHeight = (int) (noise * 4) + 2; // Adjust the multiplier to control dune height
        int maxHeight = startHeight + duneHeight;

        // Loop through the Y levels and set the blocks accordingly
        for (int y = startHeight; y <= maxHeight; y++) {
            if (y <= seaLevel) {
                // Place default fluid (water or lava) below sea level
                chunkIn.setBlockState(new BlockPos(x, y, z), defaultFluid.getBlockState(), false);
            } else {
                // Place default block on top of the dune
                chunkIn.setBlockState(new BlockPos(x,y,z), config.getTop(), false);
            }
        }

        // Set remaining space to defaultBlock
        for (int y = maxHeight + 1; y <= startHeight + 8; y++) {
            chunkIn.setBlockState(new BlockPos(x,y,z), config.getTop(), false);
        }
    }
}
