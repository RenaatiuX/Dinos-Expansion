package com.rena.dinosexpansion.common.biome;

import com.google.common.collect.ImmutableList;
import com.rena.dinosexpansion.common.biome.util.BiomeData;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
    public static final List<BiomeBase> LAND_BIOMES = new ArrayList<>(), RIVERS = new ArrayList<>(), SHALLOW_OCEANS = new ArrayList<>(), DEEP_OCEANS = new ArrayList<>(), BIOME_BASES = new ArrayList();
    public static List<BiomeData> biomeData = new ArrayList<>();

    private final Biome biome;

    private final List<Subbiome> subBiomes = new ArrayList<>();


    public BiomeBase(Biome.Climate climate, Biome.Category category, float depth, float scale, BiomeAmbience effects, BiomeGenerationSettings biomeGenerationSettings, MobSpawnInfo mobSpawnInfo) {
        biome = new Biome(climate, category, depth, scale, effects, biomeGenerationSettings, mobSpawnInfo);
        addBiome(this);
    }

    public BiomeBase(Biome.Builder builder) {
        this.biome = builder.build();
        addBiome(this);
    }

    public BiomeBase(Biome biome) {
        this.biome = biome;
        addBiome(this);
    }

    private void addBiome(BiomeBase base) {
        if (base.isRiver())
            RIVERS.add(base);
        else if (base.isShallowOcean())
            SHALLOW_OCEANS.add(base);
        else if (base.isDeepOcean())
            DEEP_OCEANS.add(base);
        else if (!base.isSubbiome())
            LAND_BIOMES.add(base);
        BIOME_BASES.add(base);
    }

    /**
     * @param subBiome               the sub biome that then should spawn inside that biome
     * @param probability            the probability that´s int range from [0, probability) = 0
     * @param needsSurroundingBiomes defines if all surrounding biomes has to be this biome
     */
    public void addSubBiome(Supplier<RegistryKey<Biome>> subBiome, int probability, boolean needsSurroundingBiomes) {
        this.subBiomes.add(new Subbiome(subBiome, probability, needsSurroundingBiomes));
    }

    public void addSubBiome(Subbiome... subBiomes) {
        Arrays.stream(subBiomes).forEach(this.subBiomes::add);
    }

    public Biome getBiome() {
        return this.biome;
    }

    public static int calculateSkyColor(float temperature) {
        float colour = temperature / 3.0F;
        colour = MathHelper.clamp(colour, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - colour * 0.05F, 0.5F + colour * 0.1F, 1.0F);
    }

    public boolean isSubbiome() {
        return false;
    }


    public RegistryKey<Biome> getRiver() {
        return BiomeInit.RIVER.getKey();
    }

    public WeightedList<Biome> getHills() {
        return new WeightedList<>();
    }

    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.OVERWORLD};
    }

    /**
     * this defines whether a river should spawn adjacent to this biome
     */
    public boolean canGenerateRiver(){
        return true;
    }

    public List<Subbiome> getSubBiomes() {
        return ImmutableList.copyOf(this.subBiomes);
    }

    public BiomeType[] getBiomeType() {
        return new BiomeType[]{BiomeType.NORMAL};
    }

    public int getWeight() {
        return 5;
    }

    public RegistryKey<Biome> getKey() {
        return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(this.biome)));
    }

    public boolean isRiver() {
        return false;
    }

    public boolean isShallowOcean() {
        return false;
    }

    public boolean isDeepOcean() {
        return false;
    }

    public boolean isOcean() {
        return isShallowOcean() || isDeepOcean();
    }


    public enum BiomeType {
        ICY(-.8),
        COOL(-.5d),
        NORMAL(0d),
        LUKEWARM(.2d),
        WARM(.8),
        HOT(1);

        private final double noiseThreshold;

        /**
         * the range of these types is defined by the next lower value, so in the range of hot hs a range between [hot.noise, warm.noise)
         *
         * @param noiseThreshold this defines how the noise has to be in order to make this kind of biome spawn, if the noiss equals this noise this type is applied
         *                       there is a temperature noise all over the world which defines whether there is a warm or cold biome, its range is from [-1, 1] where -1 is the coldest and 1 is the hottest
         */
        BiomeType(double noiseThreshold) {
            this.noiseThreshold = noiseThreshold;
        }

        public double getNoiseThreshold() {
            return noiseThreshold;
        }

        /**
         * @param noise noise must be between [-1, 1]
         * @return a Corresponding biome type
         */
        public static BiomeType getTypeWithNoise(double noise) {
            /*
            if (noise < -1 || noise > 1)
                throw new IllegalArgumentException(String.format("must be in range of [-1, 1] but was: %s", noise));

             */
            List<BiomeType> sorted = Arrays.stream(BiomeType.values()).sorted(Comparator.comparing(BiomeType::getNoiseThreshold)).collect(Collectors.toList());
            for (int i = 0; i < sorted.size(); i++) {
                if (i - 1 < 0) {
                    if (sorted.get(i).noiseThreshold >= noise) {
                        return sorted.get(i);
                    }

                } else if (i + 1 == sorted.size()) {
                    if (sorted.get(i - 1).noiseThreshold < noise)
                        return sorted.get(i);
                } else if (sorted.get(i - 1).noiseThreshold < noise && sorted.get(i).noiseThreshold >= noise)
                    return sorted.get(i);
            }
            throw new IllegalStateException(String.format("that should never happen, got an invalid noise: %s", noise));
        }
    }

    public static class Subbiome {
        private final Supplier<RegistryKey<Biome>> biome;
        private final int probability;
        private final boolean needsSurroundingBiomes;

        /**
         * @param biome the sub biome that then should spawn inside that biome
         *              * @param probability the probability that´s in range from [0, probability) = 0
         *              * @param needsSurroundingBiomes defines if all surrounding biomes has to be this biome
         */
        public Subbiome(Supplier<RegistryKey<Biome>> biome, int probability, boolean needsSurroundingBiomes) {
            this.biome = biome;
            this.probability = probability;
            this.needsSurroundingBiomes = needsSurroundingBiomes;
        }

        public Supplier<RegistryKey<Biome>> getBiome() {
            return biome;
        }

        public int getProbability() {
            return probability;
        }

        public boolean isNeedsSurroundingBiomes() {
            return needsSurroundingBiomes;
        }
    }


}
