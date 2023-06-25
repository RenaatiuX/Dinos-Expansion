package com.rena.dinosexpansion.common.world.gen.surfacebuilder;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class DinoSurfaceBuilders {

    //Surface
    public static final SurfaceBuilderConfig ALPS_SB = new SurfaceBuilderConfig(Blocks.SNOW_BLOCK.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState());
    public static final SurfaceBuilderConfig RED_SAND_SB = new SurfaceBuilderConfig(Blocks.RED_SAND.getDefaultState(), Blocks.RED_SAND.getDefaultState(), Blocks.RED_SAND.getDefaultState());
    public static final SurfaceBuilderConfig OASIS_SB = new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState());
    public static final SurfaceBuilderConfig DENSE_SWAMP_SB = new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState());
    public static final SurfaceBuilderConfig CHERRY_FOREST_SB = new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState());
    //Config
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> ALPS = SurfaceBuilder.DEFAULT.func_242929_a(ALPS_SB);
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> RED_SAND = SurfaceBuilder.DEFAULT.func_242929_a(RED_SAND_SB);
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> OASIS = SurfaceBuilder.DEFAULT.func_242929_a(OASIS_SB);
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> DENSE_SWAMP = SurfaceBuilder.SWAMP.func_242929_a(DENSE_SWAMP_SB);
    public static final ConfiguredSurfaceBuilder<SurfaceBuilderConfig> CHERRY_FOREST = SurfaceBuilder.DEFAULT.func_242929_a(CHERRY_FOREST_SB);
}
