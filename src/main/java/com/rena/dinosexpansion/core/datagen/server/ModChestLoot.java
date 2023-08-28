package com.rena.dinosexpansion.core.datagen.server;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.core.init.ItemInit;
import com.rena.dinosexpansion.core.init.ModLootTables;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.EnchantRandomly;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.function.BiConsumer;

public class ModChestLoot extends ChestLootTables {


    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(ModLootTables.VULCANO_CHEST, LootTable.builder().addLootPool(
                makConstantPoolRandomCount(3, itemHolder(Items.NETHERITE_BLOCK, 1, 1), itemHolder(Items.DIAMOND_BLOCK, 1, 2, 2), itemHolder(ItemInit.DIAMOND_BOOMERANG.get(), 2), itemHolder(Items.ENCHANTED_GOLDEN_APPLE, 1), itemHolder(Items.IRON_BLOCK, 1, 10, 5), itemHolder(Items.GOLD_BLOCK, 1, 5, 6)))
                .addLootPool(makeConstantPool(2, 3, itemHolder(Items.OBSIDIAN, 2, 10, 10), itemHolder(Items.QUARTZ, 2, 7, 5), itemHolder(Items.LAVA_BUCKET, 1), itemHolder(Items.LAPIS_LAZULI, 2, 4, 10), itemHolder(Items.REDSTONE_BLOCK, 2, 4, 2)))
                .addLootPool(makeConstantPool(1, 3, itemHolder(Items.COAL, 2, 10, 20), itemHolder(Items.COAL_BLOCK, 1, 5, 5), itemHolder(Items.FIRE_CHARGE, 1, 3, 6), itemHolder(ItemInit.IRON_BOOMERANG.get(), 1))));
    }

    protected static LootPool.Builder makeConstantPool(int amount, IItemProvider generatedItem){
        return LootPool.builder().rolls(ConstantRange.of(amount)).addEntry(ItemLootEntry.builder(generatedItem));
    }

    protected static LootPool.Builder makeUniformPool(int min,int max, IItemProvider generatedItem){
        return LootPool.builder().rolls(RandomValueRange.of(min, max)).addEntry(ItemLootEntry.builder(generatedItem));
    }

    /**
     *
     * @param amount how many of this item pool should spawn, every spawned item will have different enchantments
     * @param generatedItem the item that should spawn with different enchantments
     * @return
     */
    protected static LootPool.Builder makeConstantPoolWithRandomEnchantments(int amount, IItemProvider generatedItem){
        return LootPool.builder().rolls(ConstantRange.of(amount)).addEntry(ItemLootEntry.builder(generatedItem).acceptFunction(EnchantRandomly.func_215900_c()));
    }

    protected static LootPool.Builder makeConstantPoolWithRandomEnchantments(int min, int max, IItemProvider generatedItem, Collection<Enchantment> validEnchantments){
        EnchantRandomly.Builder builder = new EnchantRandomly.Builder();
        for (Enchantment ench : validEnchantments){
            builder.func_237424_a_(ench);
        }
        return LootPool.builder().rolls(RandomValueRange.of(min, max)).addEntry(ItemLootEntry.builder(generatedItem).acceptFunction(builder));
    }

    protected static LootPool.Builder makeConstantPoolWithRandomEnchantments(int min, int max, IItemProvider generatedItem, Enchantment... validEnchantments){
       return makeConstantPoolWithRandomEnchantments(min, max, generatedItem, Lists.newArrayList(validEnchantments));
    }

    /**
     *
     * @param amount the amount of entries which should be chosen, note that entries can be chosen multiple times
     * @param entries the entries that can be generated
     * @return a Loot pool matching these conditions
     */
    protected static LootPool.Builder makConstantPoolRandomCount(int amount, ItemLootEntryHolder... entries){
        LootPool.Builder builder = LootPool.builder().rolls(ConstantRange.of(amount));
        for (ItemLootEntryHolder itemLoot : entries){
            StandaloneLootEntry.Builder<?> entryBuilder = ItemLootEntry.builder(itemLoot.item).weight(itemLoot.weight);
            if (itemLoot.hasCountFunction())
                entryBuilder.acceptFunction(itemLoot.count);
            builder.addEntry(entryBuilder);
        }

        return builder;
    }

    /**
     * not that the pool entries can be chosen multiple times
     * @param min the min amount of rolls this loot pool will have
     * @param max the max amount of rolls this loot pool will have
     * @param entries the entries that can be generated, first is  the item and second is the weight(chance of spawning)
     * @return a Loot pool matching these conditions
     */
    protected static LootPool.Builder makeConstantPool(int min,int max, ItemLootEntryHolder... entries){
        LootPool.Builder builder = LootPool.builder().rolls(RandomValueRange.of(min, max));
        for (ItemLootEntryHolder itemLoot : entries){
            StandaloneLootEntry.Builder<?> entryBuilder = ItemLootEntry.builder(itemLoot.item).weight(itemLoot.weight);
            if (itemLoot.hasCountFunction())
                entryBuilder.acceptFunction(itemLoot.count);
            builder.addEntry(entryBuilder);
        }

        return builder;
    }

    /**
     *
     * @param chance the chance that this item will generate, the chance has to be between 0(exclusive) and 100(exclusive)
     * @param provider the item we want to generate
     * @param count the count of the item that shouls spawn
     */
    protected static LootPool.Builder makeChancePool(int chance, IItemProvider provider, int count){
        if (chance <= 0 || chance >= 100)
            throw new IllegalArgumentException(String.format("the chance has to be inside of (%s, %s) but was: %s", 0, 100, chance));
        return LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(provider).weight(chance).acceptFunction(SetCount.builder(ConstantRange.of(count)))).addEntry(EmptyLootEntry.func_216167_a().weight(100 - chance));
    }

    protected static LootPool.Builder makeChancePool(int chance, IItemProvider provider){
       return makeChancePool(chance, provider, 1);
    }


    /**
     *
     * @param chance the chance that this item will generate, the chance has to be between 0(exclusive) and 100(exclusive)
     * @param provider the item we want to generate
     * @param countMin the min amount of items that will spawn
     * @param countMax the max amount of items that might spawn
     */
    protected static LootPool.Builder makeChancePool(int chance, IItemProvider provider, int countMin, int countMax){
        if (chance <= 0 || chance >= 100)
            throw new IllegalArgumentException(String.format("the chance has to be inside of (%s, %s) but was: %s", 0, 100, chance));
        return LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(provider).weight(chance).acceptFunction(SetCount.builder(RandomValueRange.of(countMin, countMax)))).addEntry(EmptyLootEntry.func_216167_a().weight(100 - chance));
    }

    /**
     *
     * @param item the item that should be generated
     * @param minCount min count of the item in which a number will be randomly chosen
     * @param maxCount max count of the item in which a number will be randomly chosen
     * @param weight the weight also named chance
     */
    protected static ItemLootEntryHolder itemHolder(IItemProvider item, int minCount, int maxCount, int weight){
        return new ItemLootEntryHolder(SetCount.builder(RandomValueRange.of(minCount,maxCount)), item, weight);
    }

    /**
     *
     * @param item the item that should be generated
     * @param count the count of the item
     * @param weight the weight also named chance
     */
    protected static ItemLootEntryHolder itemHolder(IItemProvider item, int count, int weight){
        return new ItemLootEntryHolder(SetCount.builder(ConstantRange.of(count)), item, weight);
    }

    /**
     *
     * @param item the item that should be generated
     * @param weight the weight also named chance
     */
    protected static ItemLootEntryHolder itemHolder(IItemProvider item, int weight){
        return new ItemLootEntryHolder(null, item, weight);
    }



    public static class ItemLootEntryHolder{
        private final ILootFunction.IBuilder count;
        private final IItemProvider item;
        private final int weight;

        public ItemLootEntryHolder(ILootFunction.IBuilder count, IItemProvider item, int weight) {
            this.count = count;
            this.item = item;
            this.weight = weight;
        }

        public ILootFunction.IBuilder getCount() {
            return count;
        }

        public IItemProvider getItem() {
            return item;
        }

        public boolean hasCountFunction(){
            return this.count != null;
        }

        public int getWeight() {
            return weight;
        }
    }

}
