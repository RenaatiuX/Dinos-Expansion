package com.rena.dinosexpansion.common.world.biome;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;

public class ModConfiguredSurfaceBuilders {

    private static <SC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<SC> register(String name, ConfiguredSurfaceBuilder<SC> configuredSurfaceBuilder) {

        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER,
                new ResourceLocation(DinosExpansion.MOD_ID, name), configuredSurfaceBuilder);
    }

}
