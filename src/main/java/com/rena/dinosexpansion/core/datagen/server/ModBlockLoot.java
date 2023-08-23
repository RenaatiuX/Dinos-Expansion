package com.rena.dinosexpansion.core.datagen.server;

import com.google.common.collect.Lists;
import com.rena.dinosexpansion.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.LootTable;

import java.util.List;

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
        registerDropSelfLootTable(BlockInit.CAMPANILE_SHELL_COMMON.get());
        registerDropSelfLootTable(BlockInit.CAMPANILE_SHELL_UNCOMMON.get());

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
