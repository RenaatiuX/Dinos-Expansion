package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import com.rena.dinosexpansion.common.world.dimension.DinoChunkGenerator;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DimensionInit {

    public static final RegistryKey<World> DINO_DIMENSION = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
            new ResourceLocation(DinosExpansion.MOD_ID, "dino_dimension"));

    public static void registerDimension() {
        Registry.register(Registry.BIOME_PROVIDER_CODEC, new ResourceLocation(DinosExpansion.MOD_ID, "dino_biome_source"),
                DinoBiomeProvider.CODEC);
        Registry.register(Registry.CHUNK_GENERATOR_CODEC, new ResourceLocation(DinosExpansion.MOD_ID, "dino_chunk_generator"),
                DinoChunkGenerator.CODEC);
    }

}
