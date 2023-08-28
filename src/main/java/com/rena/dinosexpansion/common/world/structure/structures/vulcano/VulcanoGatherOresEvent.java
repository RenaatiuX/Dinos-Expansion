package com.rena.dinosexpansion.common.world.structure.structures.vulcano;

import com.google.common.collect.ImmutableList;
import com.rena.dinosexpansion.common.world.gen.feature.vulcano.VulcanoConfig;
import net.minecraft.block.Block;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

public class VulcanoGatherOresEvent extends Event {
    protected final List<VulcanoConfig.VulcanoOreHolder> ores;

    protected DynamicRegistries dynamicRegistryManager;
    protected ChunkGenerator chunkGenerator;
    protected TemplateManager templateManagerIn;
    protected int chunkX;
    protected int chunkZ;
    protected Biome biomeIn;
    protected VulcanoConfig config;

    public VulcanoGatherOresEvent(List<VulcanoConfig.VulcanoOreHolder> ores, DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, VulcanoConfig config) {
        this.ores = ores;
        this.dynamicRegistryManager = dynamicRegistryManager;
        this.chunkGenerator = chunkGenerator;
        this.templateManagerIn = templateManagerIn;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.biomeIn = biomeIn;
        this.config = config;
    }

    public DynamicRegistries getDynamicRegistryManager() {
        return dynamicRegistryManager;
    }

    public ChunkGenerator getChunkGenerator() {
        return chunkGenerator;
    }

    public TemplateManager getTemplateManagerIn() {
        return templateManagerIn;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public Biome getBiomeIn() {
        return biomeIn;
    }

    public VulcanoConfig getConfig() {
        return config;
    }

    /**
     * immutable list of the ores that are already generating inside the vulcano
     */
    public List<VulcanoConfig.VulcanoOreHolder> getOres() {
        return ImmutableList.copyOf(this.ores);
    }

    /**
     *
     * @param ore the ore u want to add to this vulcano
     * @param chance the chance 1 / chance
     * @param minOre the min amount of ores that should spawn
     * @param maxOre the max amount of ores that should spawn
     * @param canLookOutside this defines whether the ore can be seen from outside
     */
    public void addOre(BlockStateProvider ore, int chance, int minOre, int maxOre,int repetitions, boolean canLookOutside){
        this.ores.add(new VulcanoConfig.VulcanoOreHolder(ore, chance, minOre, maxOre, repetitions, canLookOutside));
    }

    public void addOre(BlockStateProvider ore, int chance, int minOre, int maxOre, int repetitions){
        addOre(ore, chance, minOre, maxOre,repetitions, false);
    }

    public void addOre(Block ore, int chance, int minOre, int maxOre, int repetitions, boolean canLookOutside){
        this.ores.add(new VulcanoConfig.VulcanoOreHolder(new SimpleBlockStateProvider(ore.getDefaultState()), chance, minOre, maxOre, repetitions, canLookOutside));
    }

    public void addOre(Block ore, int chance, int minOre, int maxOre, int repetitions){
        addOre(ore, chance, minOre, maxOre,repetitions, false);
    }


}
