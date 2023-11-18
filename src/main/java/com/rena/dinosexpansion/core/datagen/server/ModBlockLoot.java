package com.rena.dinosexpansion.core.datagen.server;

import com.google.common.collect.Lists;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.util.IItemProvider;

import java.util.List;
import java.util.function.Supplier;

public class ModBlockLoot extends BlockLootTables {
    private List<Block> knownBlocks = Lists.newArrayList();

    @Override
    protected void addTables() {
        registerDropSelfLootTable(BlockInit.FUTURISTIC_BLOCK_OFF1.get());
        registerDropSelfLootTable(BlockInit.FUTURISTIC_BLOCK_OFF2.get());
        registerDropSelfLootTable(BlockInit.FUTURISTIC_BLOCK_ON1.get());
        registerDropSelfLootTable(BlockInit.FUTURISTIC_BLOCK_ON2.get());
        registerDropSelfLootTable(BlockInit.MOSSY_FUTURISTIC_BLOCK1.get());
        registerDropSelfLootTable(BlockInit.MOSSY_FUTURISTIC_BLOCK2.get());

        selfDrop(BlockInit.FUTURISTIC_BLOCK_OFF1_SLAB.get(), BlockInit.FUTURISTIC_BLOCK_OFF1_STAIRS.get(), BlockInit.FUTURISTIC_BLOCK_OFF1_FENCE.get(), BlockInit.FUTURISTIC_BLOCK_OFF1_WALL.get());
        selfDrop(BlockInit.FUTURISTIC_BLOCK_OFF2_SLAB.get(), BlockInit.FUTURISTIC_BLOCK_OFF2_STAIRS.get(), BlockInit.FUTURISTIC_BLOCK_OFF2_FENCE.get(), BlockInit.FUTURISTIC_BLOCK_OFF2_WALL.get());

        selfDrop(BlockInit.FUTURISTIC_BLOCK_ON1_SLAB.get(), BlockInit.FUTURISTIC_BLOCK_ON1_STAIRS.get(), BlockInit.FUTURISTIC_BLOCK_ON1_FENCE.get(), BlockInit.FUTURISTIC_BLOCK_ON1_WALL.get());
        selfDrop(BlockInit.FUTURISTIC_BLOCK_ON2_SLAB.get(), BlockInit.FUTURISTIC_BLOCK_ON2_STAIRS.get(), BlockInit.FUTURISTIC_BLOCK_ON2_FENCE.get(), BlockInit.FUTURISTIC_BLOCK_ON2_WALL.get());

        selfDrop(BlockInit.MOSSY_FUTURISTIC_BLOCK1_SLAB.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK1_STAIRS.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK1_FENCE.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK1_WALL.get());
        selfDrop(BlockInit.MOSSY_FUTURISTIC_BLOCK2_SLAB.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK2_STAIRS.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK2_FENCE.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK2_WALL.get());

        registerDropSelfLootTable(BlockInit.CAMPANILE_SHELL_COMMON.get());
        registerDropSelfLootTable(BlockInit.CAMPANILE_SHELL_UNCOMMON.get());
        selfDrop(BlockInit.REDWOOD_SAPLING, BlockInit.RUBY_BLOCK, BlockInit.SAPPHIRE_BLOCK);
        selfDrop(BlockInit.GLOW_STICK.get(), BlockInit.REDWOOD_LOG.get(), BlockInit.CRATAEGUS_LOG.get(), BlockInit.CRATAEGUS_DOOR.get(), BlockInit.CRATAEGUS_PLANKS.get(), BlockInit.STRIPPED_CRATAEGUS_LOG.get());
        selfDrop(BlockInit.MORTAR.get(), BlockInit.REDWOOD_LOG.get());
        selfDrop(BlockInit.CRATEAGUS_SIGN.get());
        ore(BlockInit.SAPPHIRE_ORE.get(), ItemInit.SAPPHIRE.get());
        ore(BlockInit.RUBY_ORE.get(), ItemInit.RUBY.get());
    }

    public void ore(Block block, IItemProvider item){
        this.registerLootTable(block, (ore) -> droppingItemWithFortune(ore, item.asItem()));
    }



    public void selfDrop(Supplier<Block>... blocks) {
        for (Supplier<Block> block : blocks) {
            registerDropSelfLootTable(block.get());
        }
    }

    public void selfDrop(Block... blocks) {
        for (Block block : blocks) {
            registerDropSelfLootTable(block);
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return this.knownBlocks;
    }


    @Override
    protected void registerLootTable(Block block, LootTable.Builder builder) {
        super.registerLootTable(block, builder);
        this.knownBlocks.add(block);
    }

}
