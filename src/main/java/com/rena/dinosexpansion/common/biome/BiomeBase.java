package com.rena.dinosexpansion.common.biome;

import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.common.biome.util.BiomeData;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public class BiomeBase {

    public static final int BASE_WATER_COLOUR = 4159204;
    public static final int BASE_WATER_FOG_COLOUR = 329011;

    public static final int FROZE_OCEAN_WATER_COLOUR = 3750089;
    public static final int FROZE_OCEAN_WATER_FOG_COLOUR = 329011;

    public static final int LUKE_WARM_OCEAN_WATER_COLOUR = 4566514;
    public static final int LUKE_WARM_OCEAN_WATER_FOG_COLOUR = 267827;

    public static final int WARM_OCEAN_WATER_COLOUR = 4445678;
    public static final int WARM_OCEAN_WATER_FOG_COLOUR = 270131;

    public static final int COLD_OCEAN_WATER_COLOUR = 4020182;
    public static final int COLD_OCEAN_WATER_FOG_COLOUR = 329011;

    public static final int BASE_FOG_COLOUR = 12638463;

    public static final Map<ResourceLocation, WeightedList<ResourceLocation>> BIOME_TO_HILLS_LIST = new HashMap<>();
    public static final Map<ResourceLocation, ResourceLocation> BIOME_TO_BEACH_LIST = new HashMap<>();
    public static final Map<ResourceLocation, ResourceLocation> BIOME_TO_EDGE_LIST = new HashMap<>();
    public static final Map<ResourceLocation, ResourceLocation> BIOME_TO_RIVER_LIST = new HashMap<>();
    public static final List<BiomeBase> BIOMES = new ArrayList<>();
    public static List<BiomeData> biomeData = new ArrayList<>();

    private final Biome biome;

    private final List<Pair<Supplier<RegistryKey<Biome>>, Integer>> subBiomes = new ArrayList<>();


    public BiomeBase(Biome.Climate climate, Biome.Category category, float depth, float scale, BiomeAmbience effects, BiomeGenerationSettings biomeGenerationSettings, MobSpawnInfo mobSpawnInfo) {
        biome = new Biome(climate, category, depth, scale, effects, biomeGenerationSettings, mobSpawnInfo);
        BIOMES.add(this);
    }

    public BiomeBase(Biome.Builder builder) {
        this.biome = builder.build();
        BIOMES.add(this);
    }

    public BiomeBase(Biome biome) {
        this.biome = biome;
        BIOMES.add(this);
    }

    /**
     *
     * @param subBiome the sub biome that then should spawn inside that biome
     * @param probability the probability thats int range from [0, probability) = 0
     */
    public void addSubBiome(Supplier<RegistryKey<Biome>> subBiome, int probability){
        this.subBiomes.add(Pair.of(subBiome, probability));
    }

    public Biome getBiome() {
        return this.biome;
    }

    public static int calculateSkyColor(float temperature) {
        float colour = temperature / 3.0F;
        colour = MathHelper.clamp(colour, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - colour * 0.05F, 0.5F + colour * 0.1F, 1.0F);
    }


    public Biome getRiver() {
        return WorldGenRegistries.BIOME.getOrThrow(Biomes.RIVER);
    }

    public WeightedList<Biome> getHills() {
        return new WeightedList<>();
    }

    @Nullable
    public Biome getEdge() {
        return null;
    }

    @Nullable
    public Biome getBeach() {
        return WorldGenRegistries.BIOME.getOrThrow(Biomes.BEACH);
    }

    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[] {BiomeDictionary.Type.OVERWORLD};
    }

    public List<Pair<Supplier<RegistryKey<Biome>>, Integer>> getSubBiomes() {
        return subBiomes;
    }

    public BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.WARM;
    }

    public int getWeight() {
        return 5;
    }

    public RegistryKey<Biome> getKey() {
        return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(this.biome)));
    }


}
