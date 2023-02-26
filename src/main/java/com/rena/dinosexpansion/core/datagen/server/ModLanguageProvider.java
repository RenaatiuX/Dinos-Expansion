package com.rena.dinosexpansion.core.datagen.server;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
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
        add(BlockInit.LAVENDER.get(), "Lavender");
        add(BlockInit.LEMON_VERBENA.get(), "Lemon Verbena");
        add(BlockInit.ARCHAEOSIGILLARIA.get(), "Archaeosigillaria");
        add(BlockInit.CEPHALOTAXUS.get(), "Cephalotaxus");
        add(BlockInit.DILLHOFFIA.get(), "Dillhoffia");
        add(BlockInit.EPHEDRA.get(), "Ephedra");
        add(BlockInit.OSMUNDA.get(), "Osmunda");
        add(BlockInit.SARRACENIA.get(), "Sarracenia");
        add(BlockInit.VACCINIUM.get(), "Vaccinium");
        add(BlockInit.ZAMITES.get(), "Zamites");
        add(BlockInit.WELWITSCHIA.get(), "Welwitschia");
        add(BlockInit.PACHYPODA.get(), "Pachypoda");
        add(BlockInit.HORSETAIL.get(), "Horsetail");
        add(BlockInit.FOOZIA.get(), "Foozia");
        add(BlockInit.DUISBERGIA.get(), "Duisbergia");
        add(BlockInit.BENNETTITALES.get(), "Bennettitales");
        add(BlockInit.CRATAEGUS.get(), "Crataegus");
        add(BlockInit.FLORISSANTIA.get(), "Florissantia");
        add(BlockInit.AMORPHOPHALLUS.get(), "Amorphophallus");
        add(BlockInit.TEMPSKYA.get(), "Tempskya");
        add(BlockInit.REDWOOD_LEAVES.get(), "Redwood Leaves");
        add(BlockInit.REDWOOD_LOG.get(), "Redwood Log");
        add(BlockInit.REDWOOD_SAPLING.get(), "Redwood Sapling");
        add(BlockInit.GEYSER.get(), "Geyser");
        add(ItemInit.COMPOUND_ARROW.get(), "Compound Arrow");
        add(ItemInit.COMPOUND_BOW.get(), "Compound Bow");
        add(ItemInit.TRANQUILLIZER_ARROW.get(), "Tranquillizer Arrow");
        add(ItemInit.BONE_ARROW.get(), "Bone Arrow");
        add(ItemInit.WOODEN_ARROW.get(), "Wooden Arrow");
        add(ItemInit.STONE_ARROW.get(), "Stone Arrow");
        add(ItemInit.IRON_ARROW.get(), "Iron Arrow");
        add(ItemInit.GOLD_ARROW.get(), "Golden Arrow");
        add(ItemInit.DIAMOND_ARROW.get(), "Diamond Arrow");
        add(ItemInit.NETHERITE_ARROW.get(), "Netherite Arrow");
        add(ItemInit.EMERALD_ARROW.get(), "Emerald Arrow");
        add(EnchantmentInit.ICE_ENCHANTMENT.get(), "Iced");
        add(EnchantmentInit.ELECTRIC_ENCHANTMENT.get(), "Electrified");
        add(ModItemGroups.SPAWN_EGGS, "Spawn Eggs");
        add(ModItemGroups.WEAPONS, "Weapons");
        add(ModItemGroups.BLOCKS, "Blocks");
        add(ModItemGroups.ITEMS, "Items");
        add(ItemInit.PARAPUZOSIA_SPAWN_EGG.get(), "Parapuzosia Spawn Egg");
        add(ItemInit.EOSQUALODON_SPAWN_EGG.get(), "Eosqualodon Spawn Egg");
        add(ItemInit.MEGA_PIRANHA_SPAWN_EGG.get(), "MegaPiranha Spawn Egg");
        add(ItemInit.DIMORPHODON_SPAWN_EGG.get(), "Dimorphodon Spawn Egg");
        add(ItemInit.ASTORGOSUCHUS_SPAWN_EGG.get(), "Astorgosuchus Spawn Egg");
        add(EnchantmentInit.AQUATIC_ENCHANT.get(), "Aquatic Throwing");
        add(ItemInit.COOKED_ANKYLOSAURUS_MEAT.get(), "Cooked Ankylosaurus Meat");
        add(ItemInit.RAW_ANKYLOSAURUS_MEAT.get(), "Raw Ankylosaurus Meat");
        add(ItemInit.COOKED_CARNOTAURUS_MEAT.get(), "Cooked Carnotaurus Meat");
        add(ItemInit.RAW_CARNOTAURUS_MEAT.get(), "Raw Carnotaurus Meat");
        add(ItemInit.COOKED_GALLIMIMUS_MEAT.get(), "Cooked Gallimimus Meat");
        add(ItemInit.RAW_GALLIMIMUS_MEAT.get(), "Raw Gallimimus Meat");
        add(ItemInit.COOKED_TRICERATOPS_MEAT.get(), "Cooked Triceratops Meat");
        add(ItemInit.RAW_TRICERATOPS_MEAT.get(), "Raw Triceratops Meat");
        add(ItemInit.RAW_DIMORPHODON_MEAT.get(), "Raw Dimorphodon Meat");
        add(ItemInit.COOKED_DIMORPHODON_MEAT.get(), "Cooked Dimorphodon Meat");
        add(ItemInit.BLOWGUN.get(), "Blowgun");
        add(ItemInit.DART.get(), "Dart");
        add(ItemInit.DIAMOND_CHAKRAM.get(), "Diamond Chakram");
        add(ItemInit.EMERALD_CHAKRAM.get(), "Emerald Chakram");
        add(ItemInit.WOODEN_CHAKRAM.get(), "Wood Chakram");
        add(ItemInit.GOLD_CHAKRAM.get(), "Golden Chakram");
        add(ItemInit.STONE_CHAKRAM.get(), "Stone Chakram");
        add(ItemInit.IRON_CHAKRAM.get(), "Iron Chakram");
        add(ItemInit.NETHERITE_CHAKRAM.get(), "Netherite Chakram");
        add(ItemInit.WOODEN_SPEAR.get(), "Wood Spear");
        add(ItemInit.EMERALD_SPEAR.get(), "Emerald Spear");
        add(ItemInit.NETHERITE_SPEAR.get(), "Netherite Spear");
        add(ItemInit.IRON_SPEAR.get(), "Iron Spear");
        add(ItemInit.STONE_SPEAR.get(), "Stone Spear");
        add(ItemInit.DIAMOND_SPEAR.get(), "Diamond Spear");
        add(ItemInit.GOLD_SPEAR.get(), "Golden Spear");
        add(ItemInit.NARCOTICS.get(), "Narcotics");
        add(ItemInit.TINY_ROCK.get(), "Tiny Rock");
        add(ItemInit.KIBBLE_BASIC.get(), "Basic Kibble");
        add(ItemInit.KIBBLE_SIMPLE.get(), "Simple Kibble");
        add(ItemInit.KIBBLE_REGULAR.get(), "Regular Kibble");
        add(ItemInit.KIBBLE_SUPERIOR.get(), "Superior Kibble");
        add(ItemInit.KIBBLE_EXCEPTIONAL.get(), "Exceptional Kibble");
        add(ItemInit.KIBBLE_EXTRAORDINARY.get(), "Extraordinary Kibble");
        add(ItemInit.DINOPEDIA.get(), "Dinopedia");
        add(ItemInit.MEGA_PIRANHA_BUCKET.get(), "Bucket of MegaPiranha");
        add(ItemInit.ELECTRONICS_PARTS.get(), "Electronics Parts");
        add(ItemInit.OIL.get(), "Oil");
        add(ItemInit.SCRAP.get(), "Scrap");
        add(ItemInit.EXPLORER_JOURNAL.get(), "Explorer Journal");
        add(ItemInit.EXPLORER_JOURNAL_PAGE.get(), "Explorer Journal Page");
        add(ItemInit.SPECIAL_FRUIT.get(), "Special Fruit");
        add(EntityInit.PARAPUZOSIA.get(), "Parapuzosia");
        add(EntityInit.EOSQUALODON.get(), "Eosqualodon");
        add(EntityInit.MEGA_PIRANHA.get(), "Mega Piranha");
        add(EntityInit.DIMORPHODON.get(), "Dimorphodon");
        add(EntityInit.ASTORGOSUCHUS.get(), "Astorgosuchus");
        add(EntityInit.CAMPANILE.get(), "Campanile");
        add(EntityInit.DRYOSAURUS.get(), "Dryosaurus");
        add(EntityInit.AEGIROCASSIS.get(), "Aegirocassis");
        add(EntityInit.HERMIT.get(), "Hermit");
    }

    public void add(ItemGroup group, String key){
        add(group.getGroupName().getString(), key);
    }
}
