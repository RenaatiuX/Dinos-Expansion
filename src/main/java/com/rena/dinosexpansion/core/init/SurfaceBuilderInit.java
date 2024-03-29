package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SurfaceBuilderInit {

    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, DinosExpansion.MOD_ID);

    public static final SurfaceBuilder<SurfaceBuilderConfig> MARSHLAND_SB = new MarshlandSB(SurfaceBuilderConfig.CODEC);
    public static final SurfaceBuilder<SurfaceBuilderConfig> ROUNDED_HILLS_SB = new RoundedHillsSB(SurfaceBuilderConfig.CODEC);
    public static final SurfaceBuilder<SurfaceBuilderConfig> QUAGMIRE_SB = new QuagmireSB(SurfaceBuilderConfig.CODEC);
    public static final SurfaceBuilder<SurfaceBuilderConfig> LAKE_SB = new LakeSB(SurfaceBuilderConfig.CODEC);

    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> DUNES = SURFACE_BUILDERS
            .register("dunes", DInoDesertSurfaceBuilder::new);
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> MARSHLAND = SURFACE_BUILDERS
            .register("marshland", () -> MARSHLAND_SB);
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> ROUNDED_HILLS = SURFACE_BUILDERS
            .register("rounded_hills", () -> ROUNDED_HILLS_SB);
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> QUAGMIRE = SURFACE_BUILDERS
            .register("quagmire", () -> QUAGMIRE_SB);
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> LAKE = SURFACE_BUILDERS
            .register("lake", () -> LAKE_SB);

}
