package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DimensionInit {

    public static RegistryKey<World> DINO_DIMENSION = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
            new ResourceLocation(DinosExpansion.MOD_ID, "dino_dimension"));

}
