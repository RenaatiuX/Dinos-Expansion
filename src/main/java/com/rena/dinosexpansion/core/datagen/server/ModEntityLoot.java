package com.rena.dinosexpansion.core.datagen.server;

import com.google.common.collect.Lists;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.terrestrial.ambient.Campanile;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.rena.dinosexpansion.core.init.EntityInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import com.rena.dinosexpansion.core.init.ModLootTables;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChanceWithLooting;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.IItemProvider;

import java.util.List;

public class ModEntityLoot extends EntityLootTables {
    List<EntityType<?>> knownEntities = Lists.newArrayList();


    @Override
    protected void addTables() {
        registerLootTable(Campanile.getLootTable(true, Dinosaur.Rarity.COMMON), LootTable.builder().addLootPool(makePool(BlockInit.CAMPANILE_SHELL_COMMON.get(), .5f, 0.25f, true)));
        registerLootTable(Campanile.getLootTable(true, Dinosaur.Rarity.UNCOMMON), LootTable.builder().addLootPool(makePool(BlockInit.CAMPANILE_SHELL_UNCOMMON.get(), .3f,.2f, true)));

        registerLootTable(Campanile.getLootTable(false, Dinosaur.Rarity.COMMON), LootTable.builder().addLootPool(makePool(ItemInit.CAMPANILE_GOO.get(), .5f, 0.25f, true)));
        registerLootTable(Campanile.getLootTable(false, Dinosaur.Rarity.UNCOMMON), LootTable.builder().addLootPool(makePool(ItemInit.CAMPANILE_GOO.get(), -1, 1,0, 1, true)));

        registerLootTable(Dinosaur.getLootTableForRarity(EntityInit.DIMORPHODON.getId(), Dinosaur.Rarity.LEGENDARY), LootTable.builder()
                .addLootPool(makePool(ItemInit.ELECTRONICS_PARTS.get(),1, 4, .2f, .2f, true))
                .addLootPool(makePool(ItemInit.OIL.get(), 1 ,4, .3f, .2f, true))
                .addLootPool(makePool(ItemInit.SCRAP.get(), 1, 4, .6f, .2f, true)
        ));
        registerLootTable(Dinosaur.getLootTableForRarity(EntityInit.DIMORPHODON.getId(), Dinosaur.Rarity.RARE), LootTable.builder()
                .addLootPool(makePool(ItemInit.RAW_DIMORPHODON_MEAT.get(), 1, 3, 0, 2, true)));
        registerLootTable(Dinosaur.getLootTableForRarity(EntityInit.DIMORPHODON.getId(), Dinosaur.Rarity.UNCOMMON), LootTable.builder()
                .addLootPool(makePool(ItemInit.RAW_DIMORPHODON_MEAT.get(), 0, 1, 0, 1, true)));
        registerLootTable(Dinosaur.getLootTableForRarity(EntityInit.DIMORPHODON.getId(), Dinosaur.Rarity.COMMON), LootTable.builder()
                .addLootPool(makePool(ItemInit.RAW_DIMORPHODON_MEAT.get(), 0.5f, .4f, true)));
    }


    public static LootPool.Builder makePool(IItemProvider drop, int uniformMin, int uniformMax, int lootingBonusMin, int lootingBonusMax, boolean killedByPlayer){
        LootPool.Builder builderPool = LootPool.builder().rolls(ConstantRange.of(1)).addEntry(
                ItemLootEntry.builder(drop.asItem())
                        .acceptFunction(SetCount.builder(RandomValueRange.of(uniformMin, uniformMax)))
                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(lootingBonusMin, lootingBonusMax))));
        if (killedByPlayer){
            builderPool = builderPool.acceptCondition(KilledByPlayer.builder());
        }
        return builderPool;
    }

    public static LootPool.Builder makePool(IItemProvider drop, int constantAmount, int lootingBonusMin, int lootingBonusMax, boolean killedByPlayer){
        LootPool.Builder builderPool = LootPool.builder().rolls(ConstantRange.of(1)).addEntry(
                ItemLootEntry.builder(drop.asItem())
                        .acceptFunction(SetCount.builder(ConstantRange.of(constantAmount)))
                        .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(lootingBonusMin, lootingBonusMax))));
        if (killedByPlayer){
            builderPool = builderPool.acceptCondition(KilledByPlayer.builder());
        }
        return builderPool;
    }

    public static LootPool.Builder makePool(IItemProvider drop, float chance, float lootingBonusChance, boolean killedByPlayer){
        LootPool.Builder builderPool = LootPool.builder().rolls(ConstantRange.of(1)).addEntry(
                ItemLootEntry.builder(drop.asItem())
                        .acceptCondition(RandomChanceWithLooting.builder(chance, lootingBonusChance)));
        if (killedByPlayer){
            builderPool = builderPool.acceptCondition(KilledByPlayer.builder());
        }
        return builderPool;
    }

    public static LootPool.Builder makePool(IItemProvider drop, int count, float chance, float lootingBonusChance, boolean killedByPlayer){
        LootPool.Builder builderPool = LootPool.builder().rolls(ConstantRange.of(1)).addEntry(
                ItemLootEntry.builder(drop.asItem())
                        .acceptFunction(SetCount.builder(ConstantRange.of(count)))
                        .acceptCondition(RandomChanceWithLooting.builder(chance, lootingBonusChance)));
        if (killedByPlayer){
            builderPool = builderPool.acceptCondition(KilledByPlayer.builder());
        }
        return builderPool;
    }

    public static LootPool.Builder makePool(IItemProvider drop, int minCount,int maxCount, float chance, float lootingBonusChance, boolean killedByPlayer){
        LootPool.Builder builderPool = LootPool.builder().rolls(ConstantRange.of(1)).addEntry(
                ItemLootEntry.builder(drop.asItem())
                        .acceptFunction(SetCount.builder(RandomValueRange.of(minCount, maxCount)))
                        .acceptCondition(RandomChanceWithLooting.builder(chance, lootingBonusChance)));
        if (killedByPlayer){
            builderPool = builderPool.acceptCondition(KilledByPlayer.builder());
        }
        return builderPool;
    }



    @Override
    protected void registerLootTable(EntityType<?> type, LootTable.Builder table) {
        this.knownEntities.add(type);
        super.registerLootTable(type, table);
    }

    @Override
    public List<EntityType<?>> getKnownEntities() {
        return knownEntities;
    }
}
