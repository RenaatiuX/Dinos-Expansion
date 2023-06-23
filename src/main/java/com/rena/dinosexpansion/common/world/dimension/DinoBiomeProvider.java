package com.rena.dinosexpansion.common.world.dimension;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;

import java.util.List;

public class DinoBiomeProvider extends BiomeProvider {

    public static final Codec<DinoBiomeProvider> CODEC = RecordCodecBuilder.create((instance)
            -> instance.group(Codec.LONG.fieldOf("seed").orElse(DinoChunkGenerator.hackSeed)
            .forGetter((obj) -> obj.seed), RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY)
            .forGetter((obj) -> obj.registry)).apply(instance, instance.
            stable(DinoBiomeProvider::new)));

    private final long seed;
    private final Registry<Biome> registry;
    private final Layer genBiomes;
    private static final List<RegistryKey<Biome>> POSSIBLE_BIOMES = ImmutableList.of(
            BiomeInit.DESERT.getKey(), BiomeInit.RIVER.getKey(), BiomeInit.RED_DESERT.getKey(),
            BiomeInit.DESERT_HILLS.getKey(), BiomeInit.ALPS.getKey()
    );

    public DinoBiomeProvider(long seed, Registry<Biome> registry) {
        super(POSSIBLE_BIOMES.stream().map(define -> () -> registry.getOrThrow(define)));
        this.seed = WorldSeedHolder.getSeed();
        this.registry = registry;
        this.genBiomes = DinoLayerUtil.makeLayers(WorldSeedHolder.getSeed(), registry);
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    @Override
    public BiomeProvider getBiomeProvider(long l) {
        return new DinoBiomeProvider(seed, registry);
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return this.sample(this.registry, x, z);
    }

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
