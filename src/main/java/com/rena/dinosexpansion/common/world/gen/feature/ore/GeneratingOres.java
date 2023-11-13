package com.rena.dinosexpansion.common.world.gen.feature.ore;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.core.init.BiomeInit;
import com.rena.dinosexpansion.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum GeneratingOres {

    RUBY(BlockInit.RUBY_ORE, 48, 8, 12, BiomeInit.BIOMES.getEntries().stream().map(RegistryObject::getId).collect(Collectors.toList())::contains),
    SAPPHIRE(BlockInit.SAPPHIRE_ORE, 30, 8, 8, BiomeInit.BIOMES.getEntries().stream().map(RegistryObject::getId).collect(Collectors.toList())::contains);
    private final Supplier<Block> block;
    private final int maxHeight, minHeight, maxVeinSize;

    private final RuleTest filler;

    private final Predicate<ResourceLocation> allowedToSpawn;

    GeneratingOres(Supplier<Block> block, int maxHeight, int minHeight, int veinSize, RuleTest filler, Predicate<ResourceLocation> allowedToSpawn) {
        this.block = block;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.maxVeinSize = veinSize;
        this.filler = filler;
        this.allowedToSpawn = allowedToSpawn;
    }

    GeneratingOres(Supplier<Block> block, int maxHeight, int minHeight, int veinSize, Predicate<ResourceLocation> biomeFilter) {
        this(block, maxHeight, minHeight, veinSize, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, biomeFilter);
    }

    GeneratingOres(Supplier<Block> block, int maxHeight, int minHeight, int veinSize, RuleTest replacables) {
        this(block, maxHeight, minHeight, veinSize, replacables, biome -> true);
    }

    GeneratingOres(Supplier<Block> block, int maxHeight, int minHeight, int veinSize) {
       this(block, maxHeight, minHeight, veinSize, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, b -> true);
    }

    public Block getBlock() {
        return block.get();
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public RuleTest fills(){
        return filler == null ? OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD : this.filler;
    }

    public boolean shouldGenerate(ResourceLocation name){
        return this.allowedToSpawn.test(name);
    }

    private static RegistryKey<Biome> makeKey(ResourceLocation name) {
        return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, name);
    }
}
