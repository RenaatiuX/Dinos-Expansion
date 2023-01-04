package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BiomeInit {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, DinosExpansion.MOD_ID);

    // Dummy biomes to reserve the numeric ID safely for the json biomes to overwrite.
    // No static variable to hold as these dummy biomes should NOT be held and referenced elsewhere.
    // Technically, this isn't needed at all for my mod. Legacy code.
    static {
        createBiome("arctic", BiomeMaker::makeVoidBiome);
        createBiome("badlands", BiomeMaker::makeVoidBiome);
        createBiome("beach", BiomeMaker::makeVoidBiome);
        createBiome("snow_beach", BiomeMaker::makeVoidBiome);
        createBiome("stone_shore", BiomeMaker::makeVoidBiome);
        createBiome("swamp", BiomeMaker::makeVoidBiome);
        createBiome("canyon", BiomeMaker::makeVoidBiome);
        createBiome("dunes", BiomeMaker::makeVoidBiome);
        createBiome("grassland", BiomeMaker::makeVoidBiome);
        createBiome("high_desert", BiomeMaker::makeVoidBiome);
        createBiome("low_desert", BiomeMaker::makeVoidBiome);
        createBiome("jungle", BiomeMaker::makeVoidBiome);
        createBiome("mountain", BiomeMaker::makeVoidBiome);
        createBiome("ocean", BiomeMaker::makeVoidBiome);
        createBiome("frozen_ocean", BiomeMaker::makeVoidBiome);
        createBiome("warm_ocean", BiomeMaker::makeVoidBiome);
        createBiome("lukewarm_ocean", BiomeMaker::makeVoidBiome);
        createBiome("cold_ocean", BiomeMaker::makeVoidBiome);
        createBiome("deep_ocean", BiomeMaker::makeVoidBiome);
        createBiome("deep_frozen_ocean", BiomeMaker::makeVoidBiome);
        createBiome("deep_warm_ocean", BiomeMaker::makeVoidBiome);
        createBiome("deep_lukewarm_ocean", BiomeMaker::makeVoidBiome);
        createBiome("deep_cold_ocean", BiomeMaker::makeVoidBiome);
        createBiome("redwood_forest", BiomeMaker::makeVoidBiome);
        createBiome("tundra", BiomeMaker::makeVoidBiome);
        createBiome("river", BiomeMaker::makeVoidBiome);
        createBiome("frozen_river", BiomeMaker::makeVoidBiome);
    }

    public static RegistryObject<Biome> createBiome(String name, Supplier<Biome> biome) {
        return BIOMES.register(name, biome);
    }
}
