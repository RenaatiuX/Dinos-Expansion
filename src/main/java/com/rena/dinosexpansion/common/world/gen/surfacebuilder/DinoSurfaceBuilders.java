package com.rena.dinosexpansion.common.world.gen.surfacebuilder;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.rena.dinosexpansion.core.init.SurfaceBuilderInit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class DinoSurfaceBuilders {
    //Surface
    public static final Supplier<SurfaceBuilderConfig> ALPS_SB = () -> new SurfaceBuilderConfig(Blocks.SNOW_BLOCK.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState());
    public static final Supplier<SurfaceBuilderConfig> RED_SAND_SB = () -> new SurfaceBuilderConfig(Blocks.RED_SAND.getDefaultState(), Blocks.RED_SAND.getDefaultState(), Blocks.RED_SAND.getDefaultState());
    public static final Supplier<SurfaceBuilderConfig> OASIS_SB = () -> new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState());
    public static final Supplier<SurfaceBuilderConfig> DENSE_SWAMP_SB = () -> new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState());
    public static final Supplier<SurfaceBuilderConfig> ROUNDED_HILLS_SB = () -> new SurfaceBuilderConfig(Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.DIRT.getDefaultState());
    public static final Supplier<SurfaceBuilderConfig> CHERRY_FOREST_SB = () -> new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState());
    public static final Supplier<SurfaceBuilderConfig> MUD_SB = () -> new SurfaceBuilderConfig(BlockInit.MUD.get().getDefaultState(), BlockInit.MUD.get().getDefaultState(), BlockInit.MUD.get().getDefaultState());

    public static final Supplier<ConfiguredSurfaceBuilder<SurfaceBuilderConfig>> DUNES = () -> SurfaceBuilderInit.DUNES.get().func_242929_a(SurfaceBuilder.SAND_SAND_GRAVEL_CONFIG);
    //Config
    public static final Supplier<ConfiguredSurfaceBuilder<SurfaceBuilderConfig>> ALPS = () -> SurfaceBuilder.DEFAULT.func_242929_a(ALPS_SB.get());
    public static final Supplier<ConfiguredSurfaceBuilder<SurfaceBuilderConfig>> RED_SAND = () -> SurfaceBuilder.DEFAULT.func_242929_a(RED_SAND_SB.get());
    public static final Supplier<ConfiguredSurfaceBuilder<SurfaceBuilderConfig>> OASIS = () -> SurfaceBuilder.DEFAULT.func_242929_a(OASIS_SB.get());
    public static final Supplier<ConfiguredSurfaceBuilder<SurfaceBuilderConfig>> DENSE_SWAMP = () -> SurfaceBuilder.SWAMP.func_242929_a(DENSE_SWAMP_SB.get());
    public static final Supplier<ConfiguredSurfaceBuilder<SurfaceBuilderConfig>> CHERRY_FOREST = () -> SurfaceBuilder.DEFAULT.func_242929_a(CHERRY_FOREST_SB.get());
    public static final Supplier<ConfiguredSurfaceBuilder<SurfaceBuilderConfig>> MARSHLAND = () -> SurfaceBuilderInit.MARSHLAND.get().func_242929_a(DENSE_SWAMP_SB.get());
    public static final Supplier<ConfiguredSurfaceBuilder<SurfaceBuilderConfig>> ROUNDED_HILLS = () -> SurfaceBuilderInit.ROUNDED_HILLS.get().func_242929_a(ROUNDED_HILLS_SB.get());
}
