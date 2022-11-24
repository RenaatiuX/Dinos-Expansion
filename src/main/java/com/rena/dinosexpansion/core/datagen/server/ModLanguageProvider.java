package com.rena.dinosexpansion.core.datagen.server;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.rena.dinosexpansion.core.init.EnchantmentInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import com.rena.dinosexpansion.core.init.ModItemGroups;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen) {
        super(gen, DinosExpansion.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(BlockInit.FUTURISTIC_BLOCK_OFF1.get(), "Unknown Block");
        add(BlockInit.FUTURISTIC_BLOCK_OFF2.get(), "Unknown Block");
        add(BlockInit.FUTURISTIC_BLOCK_ON1.get(), "Unknown Block");
        add(BlockInit.FUTURISTIC_BLOCK_ON2.get(), "Unknown Block");
        add(BlockInit.MOSSY_FUTURISTIC_BLOCK1.get(), "Unknown Block");
        add(BlockInit.MOSSY_FUTURISTIC_BLOCK2.get(), "Unknown Block");
        add(ItemInit.COMPOUND_ARROW.get(), "Compound Arrow");
        add(ItemInit.COMPOUND_BOW.get(), "Compound Bow");
        add(ItemInit.TRANQUILLIZER_ARROW.get(), "Tranquillizer Arrow");
        add(EnchantmentInit.ICE_ENCHANTMENT.get(), "Iced");
        add(EnchantmentInit.ELECTRIC_ENCHANTMENT.get(), "Electrified");
        add(ModItemGroups.SPAWN_EGGS, "Spawn Eggs");
        add(ModItemGroups.WEAPONS, "Weapons");
        add(ModItemGroups.BLOCKS, "Blocks");
        add(ItemInit.PARAPUZOSIA_SPAWN_EGG.get(), "Parapuzosia Spawn Egg");
        add(ItemInit.EOSQUALODON_SPAWN_EGG.get(), "Eosqualodon Spawn Egg");
        add(ItemInit.MEGA_PIRANHA_SPAWN_EGG.get(), "MegaPiranha Spawn Egg");
        add(EnchantmentInit.AQUATIC_ENCHANT.get(), "Aquatic Throwing");
        add(ItemInit.COOKED_ANKYLOSAURUS_MEAT.get(), "Cooked Ankylosaurus Meat");
        add(ItemInit.COOKED_CARNOTAURUS_MEAT.get(), "Cooked Carnotaurus Meat");
        add(ItemInit.COOKED_GALLIMIMUS_MEAT.get(), "Cooked Gallimimus Meat");
        add(ItemInit.COOKED_TRICERATOPS_MEAT.get(), "Cooked Triceratops Meat");
        add(ItemInit.BLOWGUN.get(), "Blowgun");
        add(ItemInit.DART.get(), "Dart");
        add(ItemInit.DIAMOND_CHAKRAM.get(), "Diamond Chakram");
        add(ItemInit.EMERALD_CHAKRAM.get(), "Emerald Chakram");
        add(ItemInit.WOODEN_CHAKRAM.get(), "Wood Chakram");
        add(ItemInit.STONE_CHAKRAM.get(), "Stone Chakram");
        add(ItemInit.IRON_CHAKRAM.get(), "Iron Chakram");
        add(ItemInit.WOODEN_SPEAR.get(), "Wood Spear");
        add(ItemInit.EMERALD_SPEAR.get(), "Emerald Spear");
        add(ItemInit.NETHERITE_SPEAR.get(), "Netherite Spear");
        add(ItemInit.IRON_SPEAR.get(), "Iron Spear");
        add(ItemInit.STONE_SPEAR.get(), "Stone Spear");
        add(ItemInit.DIAMOND_SPEAR.get(), "Diamond Spear");
        add(ItemInit.GOLD_SPEAR.get(), "Golden Spear");
        add(ItemInit.NARCOTICS.get(), "Narcotics");
    }

    public void add(ItemGroup group, String key){
        add(group.getGroupName().getString(), key);
    }
}
