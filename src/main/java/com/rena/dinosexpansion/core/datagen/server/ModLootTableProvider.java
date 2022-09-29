package com.rena.dinosexpansion.core.datagen.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator p_124437_) {
        super(p_124437_);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(ModBlockLoot::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((id, table) -> LootTableManager.validateLootTable(validationtracker, id, table));
    }

    public static final class ModBlockLoot extends BlockLootTables {
     private List<Block> knownBlocks = Lists.newArrayList();

        @Override
        protected void addTables() {
           registerDropSelfLootTable(BlockInit.FUTURISTIC_BLOCK_OFF1.get());
            registerDropSelfLootTable(BlockInit.FUTURISTIC_BLOCK_OFF2.get());
            registerDropSelfLootTable(BlockInit.FUTURISTIC_BLOCK_ON1.get());
            registerDropSelfLootTable(BlockInit.FUTURISTIC_BLOCK_ON2.get());
            registerDropSelfLootTable(BlockInit.MOSSY_FUTURISTIC_BLOCK1.get());
            registerDropSelfLootTable(BlockInit.MOSSY_FUTURISTIC_BLOCK2.get());

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
}
