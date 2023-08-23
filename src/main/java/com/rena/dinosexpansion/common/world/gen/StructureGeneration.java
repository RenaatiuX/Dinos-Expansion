package com.rena.dinosexpansion.common.world.gen;

import com.rena.dinosexpansion.common.world.gen.feature.vulcano.VulcanoConfig;
import com.rena.dinosexpansion.core.init.BiomeInit;
import com.rena.dinosexpansion.core.init.ConfiguredStructureInit;
import com.rena.dinosexpansion.core.init.StructureInit;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class StructureGeneration {

    public static void generateStructures(final BiomeLoadingEvent event){
        RegistryKey<Biome> key = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
        if (types.contains(BiomeDictionary.Type.HILLS)){
            List<Supplier<StructureFeature<?, ?>>> structures =  event.getGeneration().getStructures();
            structures.add(() -> ConfiguredStructureInit.HERMIT_HOUSE);
        }
        if (ForgeRegistries.BIOMES.getValue(event.getName()) == BiomeInit.VULCANO.getBiome()){
            event.getGeneration().getStructures().add(() -> ConfiguredStructureInit.VULCANO);
        }
    }
}
