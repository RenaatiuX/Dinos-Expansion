package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeInit {


    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, DinosExpansion.MOD_ID);
}
