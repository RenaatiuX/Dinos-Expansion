package com.rena.dinosexpansion.common.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.world.dimension.layer.*;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.*;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
public class DinoBiomeProvider extends BiomeProvider {

    public static final Codec<DinoBiomeProvider> CODEC =
            RecordCodecBuilder.create((instance) -> instance.group(
                            RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY)
                                    .forGetter((vanillaLayeredBiomeSource) ->
                                            vanillaLayeredBiomeSource.BIOME_REGISTRY))
                    .apply(instance, instance.stable(DinoBiomeProvider::new)));

    public static ResourceLocation ARCTIC = new ResourceLocation(DinosExpansion.MOD_ID, "arctic");
    public static ResourceLocation BADLANDS = new ResourceLocation(DinosExpansion.MOD_ID, "badlands");
    public static ResourceLocation BEACH = new ResourceLocation(DinosExpansion.MOD_ID, "beach");
    public static ResourceLocation SNOW_BEACH = new ResourceLocation(DinosExpansion.MOD_ID, "snow_beach");
    public static ResourceLocation STONE_SHORE = new ResourceLocation(DinosExpansion.MOD_ID, "stone_shore");
    public static ResourceLocation SWAMP = new ResourceLocation(DinosExpansion.MOD_ID, "swamp");
    public static ResourceLocation CANYON = new ResourceLocation(DinosExpansion.MOD_ID, "canyon");
    public static ResourceLocation DUNES = new ResourceLocation(DinosExpansion.MOD_ID, "dunes");
    public static ResourceLocation GRASSLAND = new ResourceLocation(DinosExpansion.MOD_ID, "grassland");
    public static ResourceLocation HIGH_DESERT = new ResourceLocation(DinosExpansion.MOD_ID, "high_desert");
    public static ResourceLocation LOW_DESERT = new ResourceLocation(DinosExpansion.MOD_ID, "low_desert");
    public static ResourceLocation JUNGLE = new ResourceLocation(DinosExpansion.MOD_ID, "jungle");
    public static ResourceLocation MOUNTAIN = new ResourceLocation(DinosExpansion.MOD_ID, "mountain");
    public static ResourceLocation OCEAN = new ResourceLocation(DinosExpansion.MOD_ID, "ocean");
    public static ResourceLocation FROZEN_OCEAN = new ResourceLocation(DinosExpansion.MOD_ID, "frozen_ocean");
    public static ResourceLocation WARM_OCEAN = new ResourceLocation(DinosExpansion.MOD_ID, "warm_ocean");
    public static ResourceLocation LUKEWARM_OCEAN = new ResourceLocation(DinosExpansion.MOD_ID, "lukewarm_ocean");
    public static ResourceLocation COLD_OCEAN = new ResourceLocation(DinosExpansion.MOD_ID, "cold_ocean");
    public static ResourceLocation DEEP_OCEAN = new ResourceLocation(DinosExpansion.MOD_ID, "deep_ocean");
    public static ResourceLocation DEEP_FROZEN_OCEAN = new ResourceLocation(DinosExpansion.MOD_ID, "deep_frozen_ocean");
    public static ResourceLocation DEEP_WARM_OCEAN = new ResourceLocation(DinosExpansion.MOD_ID, "deep_warm_ocean");
    public static ResourceLocation DEEP_LUKEWARM_OCEAN = new ResourceLocation(DinosExpansion.MOD_ID, "deep_lukewarm_ocean");
    public static ResourceLocation DEEP_COLD_OCEAN = new ResourceLocation(DinosExpansion.MOD_ID, "deep_cold_ocean");
    public static ResourceLocation REDWOOD_FOREST = new ResourceLocation(DinosExpansion.MOD_ID, "redwood_forest");
    public static ResourceLocation TUNDRA = new ResourceLocation(DinosExpansion.MOD_ID, "tundra");
    public static ResourceLocation RIVER = new ResourceLocation(DinosExpansion.MOD_ID, "river");
    public static ResourceLocation FROZEN_RIVER = new ResourceLocation(DinosExpansion.MOD_ID, "frozen_river");

    private final Layer genBiomes;
    private final Registry<Biome> BIOME_REGISTRY;
    public static Registry<Biome> LAYERS_BIOME_REGISTRY;
    public static List<Biome> NONSTANDARD_BIOME = new ArrayList<>();
    private static int gen = 4;

    public DinoBiomeProvider(Registry<Biome> biomeRegistry) {
        this(0, biomeRegistry);
    }

    public DinoBiomeProvider(long seed, Registry<Biome> biomeRegistry) {
        super(biomeRegistry.getEntries().stream()
                .filter(entry -> entry.getKey().getLocation().getNamespace().equals(DinosExpansion.MOD_ID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));

        NONSTANDARD_BIOME = this.biomes.stream()
                .filter(biome -> {
                    ResourceLocation rlKey = biomeRegistry.getKey(biome);
                    return !rlKey.equals(ARCTIC) &&
                            !rlKey.equals(BADLANDS) &&
                            !rlKey.equals(BEACH) &&
                            !rlKey.equals(SNOW_BEACH) &&
                            !rlKey.equals(STONE_SHORE) &&
                            !rlKey.equals(SWAMP) &&
                            !rlKey.equals(CANYON) &&
                            !rlKey.equals(DUNES) &&
                            !rlKey.equals(GRASSLAND) &&
                            !rlKey.equals(HIGH_DESERT) &&
                            !rlKey.equals(LOW_DESERT) &&
                            !rlKey.equals(JUNGLE) &&
                            !rlKey.equals(MOUNTAIN) &&
                            !rlKey.equals(OCEAN) &&
                            !rlKey.equals(COLD_OCEAN) &&
                            !rlKey.equals(FROZEN_OCEAN) &&
                            !rlKey.equals(WARM_OCEAN) &&
                            !rlKey.equals(LUKEWARM_OCEAN) &&
                            !rlKey.equals(DEEP_OCEAN) &&
                            !rlKey.equals(DEEP_FROZEN_OCEAN) &&
                            !rlKey.equals(DEEP_WARM_OCEAN) &&
                            !rlKey.equals(DEEP_LUKEWARM_OCEAN) &&
                            !rlKey.equals(DEEP_COLD_OCEAN) &&
                            !rlKey.equals(REDWOOD_FOREST) &&
                            !rlKey.equals(TUNDRA) &&
                            !rlKey.equals(RIVER) &&
                            !rlKey.equals(FROZEN_RIVER);
                })
                .collect(Collectors.toList());

        SeedBearer.putInSeed(seed);
        this.BIOME_REGISTRY = biomeRegistry;
        DinoBiomeProvider.LAYERS_BIOME_REGISTRY = biomeRegistry;
        this.genBiomes = buildWorldProcedure(seed);
    }

    public static Layer buildWorldProcedure(long seed) {
        IAreaFactory<LazyArea> layerFactory = customTry((salt) ->
                new LazyAreaLayerContext(25, seed, salt));
        return new Layer(layerFactory);
    }

    public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> repeat(long seed, IAreaTransformer1 parent, IAreaFactory<T> p_202829_3_, int count, LongFunction<C> contextFactory) {
        IAreaFactory<T> iareafactory = p_202829_3_;
        for (int i = 0; i < count; ++i) {
            iareafactory = parent.apply(contextFactory.apply(seed + (long) i), iareafactory);
        }
        return iareafactory;
    }

    /*public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> build(LongFunction<C> contextFactory) {

        IAreaFactory<T> iareafactory = DinoIslandLayer.INSTANCE.apply(contextFactory.apply(1L));
        iareafactory = ZoomLayer.FUZZY.apply(contextFactory.apply(2000L), iareafactory);
        iareafactory = DinoAddIslandLayer.INSTANCE.apply(contextFactory.apply(1L), iareafactory);
        iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2001L), iareafactory);
        iareafactory = DinoAddIslandLayer.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
        iareafactory = DinoAddIslandLayer.INSTANCE.apply(contextFactory.apply(50L), iareafactory);
        iareafactory = DinoAddIslandLayer.INSTANCE.apply(contextFactory.apply(70L), iareafactory);
        iareafactory = DinoRemoveTooMuchOceanLayer.INSTANCE.apply(contextFactory.apply(2L), iareafactory);

        IAreaFactory<T> iareafactory1 = DinoOceanLayer.INSTANCE.apply(contextFactory.apply(2L));
        iareafactory1 = repeat(2001L, ZoomLayer.NORMAL, iareafactory1, 6, contextFactory);
        iareafactory = DinoSnowLayer.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
        iareafactory = DinoAddIslandLayer.INSTANCE.apply(contextFactory.apply(3L), iareafactory);
        iareafactory = DinoEdgeLayer.CoolWarm.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
        iareafactory = DinoEdgeLayer.HeatIce.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
        iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2002L), iareafactory);
        iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2003L), iareafactory);
        iareafactory = DinoAddIslandLayer.INSTANCE.apply(contextFactory.apply(4L), iareafactory);
        iareafactory = DinoDeepOceanLayer.INSTANCE.apply(contextFactory.apply(4L), iareafactory);
        iareafactory = repeat(1000L, ZoomLayer.NORMAL, iareafactory, 0, contextFactory);
        IAreaFactory<T> iareafactory2 = repeat(1000L, ZoomLayer.NORMAL, iareafactory, 0, contextFactory);
        iareafactory2 = StartRiverLayer.INSTANCE.apply(contextFactory.apply(100L), iareafactory2);
        IAreaFactory<T> iareafactory3 = DinoBiomeLayer.INSTANCE.apply(contextFactory.apply(200L), iareafactory);
        IAreaFactory<T> iareafactory4 = repeat(1000L, ZoomLayer.NORMAL, iareafactory3, 2, contextFactory);
        iareafactory2 = repeat(1000L, ZoomLayer.NORMAL, iareafactory2, 2, contextFactory);

        iareafactory2 = RiverLayer.INSTANCE.apply(contextFactory.apply(1L), iareafactory2);
        iareafactory2 = SmoothLayer.INSTANCE.apply(contextFactory.apply(1000L), iareafactory2);

        for (int i = 0; i < gen; ++i) {
            iareafactory3 = ZoomLayer.NORMAL.apply(contextFactory.apply(1000 + i), iareafactory3);
            if (i == 0) {
                iareafactory3 = DinoAddIslandLayer.INSTANCE.apply(contextFactory.apply(3L), iareafactory3);
            }

            if (i == 1 || gen == 1) {
                iareafactory3 = DinoShoreLayer.INSTANCE.apply(contextFactory.apply(1000L), iareafactory3);
            }
        }

        iareafactory3 = SmoothLayer.INSTANCE.apply(contextFactory.apply(1000L), iareafactory3);
        iareafactory3 = DinoRiverMixLayer.INSTANCE.apply(contextFactory.apply(100L), iareafactory3, iareafactory2);
        iareafactory3 = DinoMixOceansLayer.INSTANCE.apply(contextFactory.apply(100L), iareafactory3, iareafactory);

        return iareafactory3;
    }*/


    public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> customTry(LongFunction<C> contextFactory) {
        //creates water and land "dots" in the world noted with 0 = ocean and 1 = land
        IAreaFactory<T> areaFactory = WaterLandLayer.INSTANCE.apply(contextFactory.apply(1000L));
        //makes these generated dos bigger
        areaFactory = repeat(1001L, ZoomLayer.NORMAL, areaFactory, 3, contextFactory);
        //from here there are little islands over ocean biomes spread, they share as biomeId 2
        areaFactory = AddSmallIslands.INSTANCE.apply(contextFactory.apply(2001L), areaFactory);
        //makes the islands bigger without connecting them to land
       areaFactory = EnlargeIslands.INSTANCE.apply(contextFactory.apply(2002L), areaFactory);
       areaFactory = AddNotDeepOceanAroundIslands.INSTANCE.apply(contextFactory.apply(2003L), areaFactory);
       //0 = ocean
        // 1 = land
        //2 = island
        //3 = not deep ocean(sourrounding of the islands so they come on top
        IAreaFactory<T> temperature = AddTemperature.INSTANCE.apply(contextFactory.apply(2004L));
        temperature = repeat(2005L, ZoomLayer.NORMAL, temperature, 4, contextFactory);
        temperature = repeat(2005L, SmoothLayer.INSTANCE, temperature, 10, contextFactory);

        //adding rivers to the map and smoothing them out and make the regard temperature
        IAreaFactory<T> river = DinoStartRiver.INSTANCE.apply(contextFactory.apply(2101L), areaFactory);
        river = repeat(2102L, DinoRiverGrow.INSTANCE, river,3,  contextFactory);
        //this makes everyting above and equal to 7 to 7 and everything else to -1
        river = CleanRiverLayer.INSTANCE.apply(contextFactory.apply(2250L), river);
        //at this point there are actual BiomeIds of the DinoRiver
        river = RiverTemperatureMixer.INSTANCE.apply(contextFactory.apply(2301L), river, temperature);

        //TODO OCEAN

        //adding Biomes regarding  the local temperature
        areaFactory = DinoBiomeLayerMixer.INSTANCE.apply(contextFactory.apply(1L), areaFactory, temperature);

        areaFactory = TransformOcean.INSTANCE.apply(contextFactory.apply(3000L), areaFactory);
        areaFactory = MakeBeaches.INSTANCE.apply(contextFactory.apply(3001L), areaFactory);
        areaFactory = repeat(1001L, ZoomLayer.NORMAL, areaFactory, 4, contextFactory);
        areaFactory = repeat(5001L, SmoothLayer.INSTANCE, areaFactory, 2, contextFactory);
        //areaFactory = DinoMixRiverIntoArea.INSTANCE.apply(contextFactory.apply(6000L), areaFactory, river);

        //just to help to fill with ocean and beach biomes so i can see the generated map
        //command: /execute in dinosexpansion:dino_dimension run tp ~ ~ ~


        return areaFactory;
    }

    public static boolean isLand(int biomeIn) {
        return biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getOrDefault(DinoBiomeProvider.ARCTIC)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.BADLANDS)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.SWAMP)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.CANYON)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.DUNES)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.GRASSLAND)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.HIGH_DESERT)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.LOW_DESERT)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.JUNGLE)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.MOUNTAIN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.REDWOOD_FOREST)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(
                        DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.TUNDRA));
    }


    public static int[] getAllOceanBiomes(){
        return new int[]{
                getId(OCEAN),
                getId(WARM_OCEAN),
                getId(LUKEWARM_OCEAN),
                getId(COLD_OCEAN),
                getId(FROZEN_OCEAN),
                getId(DEEP_OCEAN),
                getId(DEEP_WARM_OCEAN),
                getId(DEEP_LUKEWARM_OCEAN),
                getId(DEEP_COLD_OCEAN),
                getId(DEEP_FROZEN_OCEAN)
        };
    }

    public static int[] getColdOceanBiomes(){
        return new int[]{
                getId(DEEP_COLD_OCEAN),
                getId(DEEP_FROZEN_OCEAN),
                getId(COLD_OCEAN),
                getId(FROZEN_OCEAN)
        };
    }

    public static int[] getWarmOceanBiomes(){
        return new int[]{
                getId(WARM_OCEAN),
                getId(LUKEWARM_OCEAN),
                getId(DEEP_WARM_OCEAN),
                getId(DEEP_LUKEWARM_OCEAN)
        };
    }

    public static int[] getNormalOceanBiomes(){
        return new int[]{
                getId(OCEAN),
                getId(DEEP_OCEAN)
        };
    }

    public static boolean isOcean(int biomeIn) {
        return biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getOrDefault(DinoBiomeProvider.OCEAN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.WARM_OCEAN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.LUKEWARM_OCEAN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.COLD_OCEAN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.FROZEN_OCEAN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.DEEP_OCEAN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.DEEP_WARM_OCEAN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.DEEP_LUKEWARM_OCEAN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.DEEP_COLD_OCEAN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.DEEP_FROZEN_OCEAN));
    }

    public static boolean isShallowOcean(int biomeIn) {
        return biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getOrDefault(DinoBiomeProvider.OCEAN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.WARM_OCEAN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.LUKEWARM_OCEAN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.COLD_OCEAN)) ||
                biomeIn == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.FROZEN_OCEAN));
    }

    public static boolean isBeach(int id){
        return id == getId(BEACH) || id == getId(SNOW_BEACH);
    }

    public static int getId(ResourceLocation biome){
        return LAYERS_BIOME_REGISTRY.getId(LAYERS_BIOME_REGISTRY.getOrDefault(biome));
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    @Override
    public BiomeProvider getBiomeProvider(long seed) {
        return new DinoBiomeProvider(seed, this.BIOME_REGISTRY);
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return this.sample(this.BIOME_REGISTRY, x, z);
    }

    //TODO Don't delete this, the reason for this is because the original
    // sample method vanilla uses is bugged with json biomes. This version is safer
    // and wont throw errors in console about unknown biomes
    public Biome sample(Registry<Biome> dynamicBiomeRegistry, int x, int z) {
        int resultBiomeID = this.genBiomes.field_215742_b.getValue(x, z);
        Biome biome = dynamicBiomeRegistry.getByValue(resultBiomeID);
        if (biome == null) {
            if (SharedConstants.developmentMode) {
                throw Util.pauseDevMode(new IllegalStateException("Unknown biome id: " + resultBiomeID));
            } else {
                // Spawn ocean if we can't resolve the biome from the layers.
                RegistryKey<Biome> backupBiomeKey = BiomeRegistry.getKeyFromID(0);
                DinosExpansion.LOGGER.warn("Unknown biome id: ${}. Will spawn ${} instead.", resultBiomeID, backupBiomeKey.getLocation());
                return dynamicBiomeRegistry.getValueForKey(backupBiomeKey);
            }
        } else {
            return biome;
        }
    }
}
