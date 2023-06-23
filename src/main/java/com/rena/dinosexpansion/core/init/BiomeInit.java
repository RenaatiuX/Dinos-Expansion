package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.biome.biomes.desert.Desert;
import com.rena.dinosexpansion.common.biome.biomes.desert.RedDesert;
import com.rena.dinosexpansion.common.biome.biomes.desert.subbiomes.DesertHills;
import com.rena.dinosexpansion.common.biome.biomes.river.River;
import com.rena.dinosexpansion.common.biome.util.BiomeData;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BiomeInit {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, DinosExpansion.MOD_ID);

    public static List<PreserveBiomeOrder> biomeList = new ArrayList<>();
    private static Set<Integer> integerList = new HashSet<>();

    public static final BiomeBase DESERT = new Desert();
    public static final BiomeBase DESERT_HILLS = new DesertHills();
    public static final BiomeBase RIVER = new River();
    public static final BiomeBase RED_DESERT = new RedDesert();

    public static final RegistryObject<Biome> DESERT_BIOME = register("desert", DESERT::getBiome, 1);
    public static final RegistryObject<Biome> DESERT_HILLS_BIOME = register("desert_hills", DESERT_HILLS::getBiome, 2);
    public static final RegistryObject<Biome> RIVER_BIOME = register("river", RIVER::getBiome, 3);
    public static final RegistryObject<Biome> RED_DESERT_BIOME = register("red_desert", RED_DESERT::getBiome, 4);

    public static RegistryObject<Biome> register(String name, Supplier<? extends Biome> biomeSupplier, int numericalID) {
        ResourceLocation id = new ResourceLocation(DinosExpansion.MOD_ID, name);
        if (WorldGenRegistries.BIOME.keySet().contains(id)) {
            throw new IllegalStateException("Biome ID: \"" + id + "\" already exists in the Biome registry!");
        }
        RegistryObject<Biome> biomeRegistryObject = BiomeInit.BIOMES.register(name, biomeSupplier);
        biomeList.add(new PreserveBiomeOrder(biomeRegistryObject, numericalID));
        integerList.add(numericalID);

        return biomeRegistryObject;
    }

    public static void addBiomeEntries() {
        for (BiomeData biomeData : BiomeBase.biomeData) {
            List<BiomeDictionary.Type> dictionaryList = Arrays.stream(biomeData.getDictionaryTypes()).collect(Collectors.toList());
            ResourceLocation key = WorldGenRegistries.BIOME.getKey(biomeData.getBiome());
            if (biomeData.getBiomeWeight() > 0) {
                BiomeManager.addBiome(biomeData.getBiomeType(), new BiomeManager.BiomeEntry(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, key), biomeData.getBiomeWeight()));
            }
        }
    }

    public static void fillBiomeDictionary() {
        for (BiomeData biomeData : BiomeBase.biomeData) {
            BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, WorldGenRegistries.BIOME.getKey(biomeData.getBiome())), biomeData.getDictionaryTypes());
        }
    }


    public static class PreserveBiomeOrder {
        private final RegistryObject<Biome> biome;
        private final int orderPosition;

        public PreserveBiomeOrder(RegistryObject<Biome> biome, int orderPosition) {
            this.biome = biome;
            this.orderPosition = orderPosition;
        }

        public RegistryObject<Biome> getBiome() {
            return biome;
        }

        public int getOrderPosition() {
            return orderPosition;
        }

    }
}
