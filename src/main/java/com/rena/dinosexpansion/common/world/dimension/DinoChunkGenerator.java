package com.rena.dinosexpansion.common.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public class DinoChunkGenerator extends NoiseChunkGenerator {
    public static final Codec<DinoChunkGenerator> CODEC = RecordCodecBuilder.create((c) ->
            c.group(BiomeProvider.CODEC.fieldOf("biome_source")
            .forGetter((chunkGenerator) -> chunkGenerator.biomeProvider),
            Codec.LONG.fieldOf("seed").orElseGet(SeedBearer::giveMeSeed)
                    .forGetter((chunkGenerator) ->
                    chunkGenerator.field_236084_w_),
            DimensionSettings.DIMENSION_SETTINGS_CODEC.fieldOf("settings")
                    .forGetter((chunkGenerator) -> chunkGenerator.field_236080_h_))
            .apply(c, c.stable(DinoChunkGenerator::new)));

    public DinoChunkGenerator(BiomeProvider biomeProvider, long seed, Supplier<DimensionSettings> settings) {
        super(biomeProvider, seed, settings);
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return CODEC;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ChunkGenerator func_230349_a_(long seed) {
        return new DinoChunkGenerator(biomeProvider.getBiomeProvider(seed), seed, field_236080_h_);
    }
}
