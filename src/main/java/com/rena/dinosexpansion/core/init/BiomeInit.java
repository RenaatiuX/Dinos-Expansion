package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.biome.biomes.artic.Alps;
import com.rena.dinosexpansion.common.biome.biomes.desert.Desert;
import com.rena.dinosexpansion.common.biome.biomes.desert.Dunes;
import com.rena.dinosexpansion.common.biome.biomes.desert.RedDesert;
import com.rena.dinosexpansion.common.biome.biomes.desert.subbiomes.DesertHills;
import com.rena.dinosexpansion.common.biome.biomes.forest.CherryForest;
import com.rena.dinosexpansion.common.biome.biomes.ocean.*;
import com.rena.dinosexpansion.common.biome.biomes.ocean.subbiome.*;
import com.rena.dinosexpansion.common.biome.biomes.river.River;
import com.rena.dinosexpansion.common.biome.biomes.swamp.DenseSwamp;
import com.rena.dinosexpansion.common.biome.biomes.swamp.Floodplain;
import com.rena.dinosexpansion.common.biome.biomes.vulcano.VulcanoBiome;
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

import javax.swing.plaf.PanelUI;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BiomeInit {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, DinosExpansion.MOD_ID);

    public static List<PreserveBiomeOrder> biomeList = new ArrayList<>();
    private static Set<Integer> integerList = new HashSet<>();

    public static final BiomeBase DESERT = new Desert();
    public static final BiomeBase DUNES = new Dunes();
    public static final BiomeBase DESERT_HILLS = new DesertHills();
    public static final BiomeBase RIVER = new River();
    public static final BiomeBase RED_DESERT = new RedDesert();
    public static final BiomeBase ALPS = new Alps();
    public static final BiomeBase OCEAN = new Ocean();
    public static final BiomeBase DEEP_OCEAN = new DeepOcean();
    public static final BiomeBase COLD_OCEAN = new ColdOcean();
    public static final BiomeBase DEEP_COLD_OCEAN = new DeepColdOcean();
    public static final BiomeBase LUKEWARM_OCEAN = new LukewarmOcean();
    public static final BiomeBase LUKEWARM_DEEP_OCEAN = new DeepLukewarmOcean();
    public static final BiomeBase WARM_OCEAN = new WarmOcean();
    public static final BiomeBase DEEP_WARM_OCEAN = new DeepWarmOcean();
    public static final BiomeBase FROZEN_OCEAN = new FrozenOcean();
    public static final BiomeBase DEEP_FROZEN_OCEAN = new DeepFrozenOcean();
    public static final BiomeBase DENSE_SWAMP = new DenseSwamp();
    public static final BiomeBase CHERRY_FOREST = new CherryForest();
    public static final BiomeBase FLOODPLAIN = new Floodplain();

    public static final BiomeBase VULCANO = new VulcanoBiome();

    public static final RegistryObject<Biome> DESERT_BIOME = register("desert", DESERT::getBiome, 1);
    public static final RegistryObject<Biome> DESERT_HILLS_BIOME = register("desert_hills", DESERT_HILLS::getBiome, 2);
    public static final RegistryObject<Biome> RIVER_BIOME = register("river", RIVER::getBiome, 3);
    public static final RegistryObject<Biome> RED_DESERT_BIOME = register("red_desert", RED_DESERT::getBiome, 4);
    public static final RegistryObject<Biome> ALPS_BIOME = register("alps", ALPS::getBiome, 5);
    public static final RegistryObject<Biome> OCEAN_BIOME = register("ocean", OCEAN::getBiome, 6);
    public static final RegistryObject<Biome> DEEP_OCEAN_BIOME = register("deep_ocean", DEEP_OCEAN::getBiome, 7);
    public static final RegistryObject<Biome> COLD_OCEAN_BIOME = register("cold_ocean", COLD_OCEAN::getBiome, 8);
    public static final RegistryObject<Biome> DEEP_COLD_OCEAN_BIOME = register("deep_cold_ocean", DEEP_COLD_OCEAN::getBiome, 9);
    public static final RegistryObject<Biome> LUKEWARM_OCEAN_BIOME = register("lukewarm_ocean", LUKEWARM_OCEAN::getBiome, 10);
    public static final RegistryObject<Biome> DEEP_LUKEWARM_BIOME = register("deep_lukewarm_ocean", LUKEWARM_DEEP_OCEAN::getBiome, 11);
    public static final RegistryObject<Biome> WARM_OCEAN_BIOME = register("warm_ocean", WARM_OCEAN::getBiome, 12);
    public static final RegistryObject<Biome> DEEP_WARM_OCEAN_BIOME = register("deep_warm_ocean", DEEP_WARM_OCEAN::getBiome, 13);
    public static final RegistryObject<Biome> FROZEN_OCEAN_BIOME = register("frozen_ocean", FROZEN_OCEAN::getBiome, 14);
    public static final RegistryObject<Biome> DEEP_FROZEN_OCEAN_BIOME = register("deep_frozen_ocean", DEEP_FROZEN_OCEAN::getBiome, 15);
    public static final RegistryObject<Biome> DENSE_SWAMP_BIOME = register("dense_swamp", DENSE_SWAMP::getBiome, 16);
    public static final RegistryObject<Biome> CHERRY_FOREST_BIOME = register("cherry_forest", CHERRY_FOREST::getBiome, 17);
    public static final RegistryObject<Biome> FLOODPLAIN_BIOME = register("floodplain", FLOODPLAIN::getBiome, 18);

    public static final RegistryObject<Biome> DUNES_BIOME = register("dunes", DUNES::getBiome, 19);
    public static final RegistryObject<Biome> VULCANO_BIOME = register("vulcano", VULCANO::getBiome, 20);

    public static RegistryObject<Biome> register(String name, Supplier<? extends Biome> biomeSupplier, int numericalID) {
        ResourceLocation id = new ResourceLocation(DinosExpansion.MOD_ID, name);
        if (ForgeRegistries.BIOMES.containsKey(id)) {
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
            ResourceLocation key = ForgeRegistries.BIOMES.getKey(biomeData.getBiome());
            if (biomeData.getBiomeWeight() > 0) {
                BiomeManager.addBiome(biomeData.getBiomeType(), new BiomeManager.BiomeEntry(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, key), biomeData.getBiomeWeight()));
            }
        }
    }

    public static void fillBiomeDictionary() {
        for (BiomeData biomeData : BiomeBase.biomeData) {
            BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, ForgeRegistries.BIOMES.getKey(biomeData.getBiome())), biomeData.getDictionaryTypes());
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
