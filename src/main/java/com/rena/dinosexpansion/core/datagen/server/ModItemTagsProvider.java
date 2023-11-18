package com.rena.dinosexpansion.core.datagen.server;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.item.TieredChakram;
import com.rena.dinosexpansion.common.item.TieredSpear;
import com.rena.dinosexpansion.common.item.arrow.TinyRockItem;
import com.rena.dinosexpansion.core.init.ItemInit;
import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator p_i244817_1_, BlockTagsProvider p_i244817_2_, @Nullable ExistingFileHelper p_i244817_4_) {
        super(p_i244817_1_, p_i244817_2_, DinosExpansion.MOD_ID, p_i244817_4_);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(ItemTags.ARROWS).add(ItemInit.TRANQUILLIZER_ARROW.get(), ItemInit.WOODEN_ARROW.get(), ItemInit.STONE_ARROW.get());
        getOrCreateBuilder(ModTags.Items.COMPOUND_ARROWS).add(ItemInit.COMPOUND_ARROW.get());
        getOrCreateBuilder(ModTags.Items.KIBBLE).add(ItemInit.KIBBLE_SUPERIOR.get(), ItemInit.KIBBLE_REGULAR.get(), ItemInit.KIBBLE_SIMPLE.get(), ItemInit.KIBBLE_BASIC.get(), ItemInit.KIBBLE_EXCEPTIONAL.get(), ItemInit.KIBBLE_EXTRAORDINARY.get());
        TagsProvider.Builder<Item> builder = getOrCreateBuilder(ModTags.Items.AQUATIC_ENCHANT);
        for (Item item : ForgeRegistries.ITEMS){
            if (item instanceof TieredChakram)
                builder.add(item);
            if (item instanceof TinyRockItem)
                builder.add(item);
            if (item instanceof TieredSpear)
                builder.add(item);
        }
        //add more food here
        getOrCreateBuilder(ModTags.Items.EOSQUALODON_FOOD).add(Items.SALMON);
        getOrCreateBuilder(ModTags.Items.DIMORPHODON_FOOD).add(Items.COD).add(Items.SALMON).add(Items.TROPICAL_FISH).add(Items.PUFFERFISH);
        getOrCreateBuilder(ModTags.Items.CERATOSAURUS_FOOD).add(Items.CHICKEN).add(Items.COOKED_CHICKEN).add(Items.BEEF).add(Items.COOKED_CHICKEN).add(Items.COOKED_MUTTON).add(Items.MUTTON).add(Items.PORKCHOP).add(Items.COOKED_PORKCHOP);
        getOrCreateBuilder(ModTags.Items.DINO_STICKS).add(ItemInit.REDWOOD_STICK.get(), ItemInit.CRATAEGUS_STICK.get());
        getOrCreateBuilder(ModTags.Items.CAN_DESTROY_FUTURISTIC_BLOCKS).add(ItemInit.SAPPHIRE_PICKAXE.get());
        getOrCreateBuilder(ModTags.Items.TIME_MACHINE_FUEL).add(Items.ENDER_PEARL);
    }
}
