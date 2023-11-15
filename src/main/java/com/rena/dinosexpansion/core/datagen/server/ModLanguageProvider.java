package com.rena.dinosexpansion.core.datagen.server;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.commands.TameCommand;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.common.util.enums.AttackOrder;
import com.rena.dinosexpansion.common.util.enums.MoveOrder;
import com.rena.dinosexpansion.core.init.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistryEntry;

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
        add(BlockInit.QUICKSAND.get(), "Quicksand");
        add(BlockInit.RUBY_ORE.get(), "Ruby Ore");
        add(BlockInit.SAPPHIRE_ORE.get(), "Sapphire Ore");
        add(BlockInit.MORTAR.get(), "Mortar");
        add(BlockInit.CAMPANILE_SHELL_COMMON.get(), "Campanile Common Shell");
        add(BlockInit.CAMPANILE_SHELL_UNCOMMON.get(), "Campanile Uncommon Shell");
        add(BlockInit.RUBY_BLOCK.get(), "Ruby Block");
        add(BlockInit.SAPPHIRE_BLOCK.get(), "Sapphire Block");
        add(BlockInit.COOKSONIA.get(), "Cooksonia");
        add(BlockInit.PROTOTAXITES.get(), "Prototaxites");
        add(BlockInit.CRATAEGUS_PLANKS.get(), "Crataegus Planks");
        add(BlockInit.CRATAEGUS_LOG.get(), "Crataegus Log");
        add(BlockInit.STRIPPED_CRATAEGUS_LOG.get(), "Stripped Crataegus Log");
        add(BlockInit.CRATAEGUS_DOOR.get(), "Crataegus Door");
        add(BlockInit.CRATAEGUS_LEAVES.get(), "Crataegus Leaves");
        add(BlockInit.MUD.get(), "Mud");
        add(BlockInit.CRATEAGUS_SIGN.get(), "Crataegus Sign");
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
        add(ItemInit.RUBY_ARROW.get(), "Ruby Arrow");
        add(ItemInit.SAPPHIRE_ARROW.get(), "Sapphire Arrow");
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
        add(ItemInit.CAMPANILE_SPAEN_EGG.get(), "Campanile Spawn Egg");
        add(ItemInit.DRYOSAURUS_SPAWN_EGG.get(), "Dryosaurus Spawn Egg");
        add(ItemInit.WETHERELLUS_SPAWN_EGG.get(), "Wetherellus Spawn Egg");
        add(ItemInit.AEGIROCASSIS_SPAWN_EGG.get(), "Aegirocassis Spawn Egg");
        add(ItemInit.SQUALODON_SPAWN_EGG.get(), "Squalodon Spawn Egg");
        add(ItemInit.ANOMALOCARIS_SPAWN_EGG.get(), "Anomalocaris Spawn Egg");
        add(ItemInit.BELANTSEA_SPAWN_EGG.get(), "Belantsea Spawn Egg");
        add(ItemInit.ACANTHODES_SPAWN_EGG.get(), "Acanthodes Spawn Egg");
        add(ItemInit.MEGANEURA_SPAWN_EGG.get(), "Meganeura Spawn Egg");
        add(ItemInit.CERATOSARUS_SPAWN_EGG.get(), "Ceratosaurus Spawn Egg");
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
        add(ItemInit.RAW_ASTORGOSUCHUS_MEAT.get(), "Raw Astorgosuchus Meat");
        add(ItemInit.COOKED_ASTORGOSUCHUS_MEAT.get(), "Cooked Astorgosuchus Meat");
        add(ItemInit.RAW_PARAPUZOSIA_TENTACLE.get(), "Raw Parapuzosia Tentacle");
        add(ItemInit.COOKED_PARAPUZOSIA_TENTACLE.get(), "Cooked Parapuzosia Tentacle");
        add(ItemInit.RAW_DRYOSAURUS_MEAT.get(), "Raw Dryosaurus Meat");
        add(ItemInit.COOKED_DRYOSAURUS_MEAT.get(), "Cooked Dryosaurus Meat");
        add(ItemInit.RAW_ANOMALOCARIS_TAIL.get(), "Raw Anomalocaris Tail");
        add(ItemInit.COOKED_ANOMALOCARIS_TAIL.get(), "Cooked Anomalocaris Tail");
        add(ItemInit.BLOWGUN.get(), "Blowgun");
        add(ItemInit.DART.get(), "Dart");
        add(ItemInit.DIAMOND_CHAKRAM.get(), "Diamond Chakram");
        add(ItemInit.EMERALD_CHAKRAM.get(), "Emerald Chakram");
        add(ItemInit.WOODEN_CHAKRAM.get(), "Wood Chakram");
        add(ItemInit.GOLD_CHAKRAM.get(), "Golden Chakram");
        add(ItemInit.STONE_CHAKRAM.get(), "Stone Chakram");
        add(ItemInit.IRON_CHAKRAM.get(), "Iron Chakram");
        add(ItemInit.NETHERITE_CHAKRAM.get(), "Netherite Chakram");
        add(ItemInit.RUBY_CHAKRAM.get(), "Ruby Chakram");
        add(ItemInit.SAPPHIRE_CHAKRAM.get(), "Sapphire Chakram");
        add(ItemInit.WOODEN_SPEAR.get(), "Wood Spear");
        add(ItemInit.EMERALD_SPEAR.get(), "Emerald Spear");
        add(ItemInit.NETHERITE_SPEAR.get(), "Netherite Spear");
        add(ItemInit.IRON_SPEAR.get(), "Iron Spear");
        add(ItemInit.STONE_SPEAR.get(), "Stone Spear");
        add(ItemInit.DIAMOND_SPEAR.get(), "Diamond Spear");
        add(ItemInit.GOLD_SPEAR.get(), "Golden Spear");
        add(ItemInit.RUBY_SPEAR.get(), "Ruby Spear");
        add(ItemInit.SAPPHIRE_SPEAR.get(), "Sapphire Spear");
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
        add(ItemInit.ACANTHODES_BUCKET.get(), "Bucket of Acanthodes");
        add(ItemInit.BELANTSEA_BUCKET.get(), "Bucket of Belantsea");
        add(ItemInit.ELECTRONICS_PARTS.get(), "Electronics Parts");
        add(ItemInit.OIL.get(), "Oil");
        add(ItemInit.REDWOOD_STICK.get(), toTitleCase(ItemInit.REDWOOD_STICK.get()));
        add(ItemInit.CRATAEGUS_STICK.get(), toTitleCase(ItemInit.CRATAEGUS_STICK.get()));
        add(ItemInit.METAL_SCRAP.get(), "Metal Scrap");
        add(ItemInit.EXPLORER_JOURNAL.get(), "Explorer Journal");
        add(ItemInit.EXPLORER_JOURNAL_PAGE.get(), "Explorer Journal Page");
        add(ItemInit.SPECIAL_FRUIT.get(), "Special Fruit");
        add(ItemInit.WOOD_BOOMERANG.get(), "Wooden Boomerang");
        add(ItemInit.IRON_BOOMERANG.get(), "Iron Boomerang");
        add(ItemInit.DIAMOND_BOOMERANG.get(), "Diamond Boomerang");
        add(ItemInit.STONE_BOOMERANG.get(), "Stone Boomerang");
        add(ItemInit.GOLDEN_BOOMERANG.get(), "Golden Boomerang");
        add(ItemInit.NETHERITE_BOOMERANG.get(), "Netherite Boomerang");
        add(ItemInit.EMERALD_BOOMERANG.get(), "Emerald Boomerang");
        add(ItemInit.RUBY_BOOMERANG.get(), "Ruby Boomerang");
        add(ItemInit.SAPPHIRE_BOOMERANG.get(), "Sapphire Boomerang");
        add(ItemInit.SLINGSHOT.get(), "Slingshot");
        add(ItemInit.WOODEN_HATCHET.get(), "Wooden Hatchet");
        add(ItemInit.STONE_HATCHET.get(), "Stone Hatchet");
        add(ItemInit.IRON_HATCHET.get(), "Iron Hatchet");
        add(ItemInit.GOLD_HATCHET.get(), "Golden Hatchet");
        add(ItemInit.DIAMOND_HATCHET.get(), "Diamond Hatchet");
        add(ItemInit.NETHERITE_HATCHET.get(), "Netherite Hatchet");
        add(ItemInit.EMERALD_HATCHET.get(), "Emerald Hatchet");
        add(ItemInit.RUBY_HATCHET.get(), "Ruby Hatchet");
        add(ItemInit.SAPPHIRE_HATCHET.get(), "Sapphire Hatchet");
        add(ItemInit.WETHERELLUS_BUCKET.get(), "Bucket of Wetherellus");
        add(ItemInit.QUICKSAND_BUCKET.get(), "Bucket of Quicksand");
        add(ItemInit.GLOW_STICK.get(), "Glowstick");
        add(ItemInit.BAMBOO_SPYGLASS.get(), "Spyglass");
        add(ItemInit.TELEPORT_ITEM.get(), "Teleport Machine");
        add(ItemInit.NARCOTIC_BERRIES.get(), "Narcotic Berries");
        add(ItemInit.ORANGE_BERRIES.get(), "Orange Berries");
        add(ItemInit.YELLOW_BERRIES.get(), "Yellow Berries");
        add(ItemInit.EGGPLANT.get(), "Eggplant");
        add(ItemInit.EGGPLANT_SEED.get(), "Eggplant Seed");
        add(ItemInit.CORN.get(), "Corn");
        add(ItemInit.CORN_SEED.get(), "Corn Seed");
        add(ItemInit.ONION.get(), "Onion");
        add(ItemInit.SPINACH.get(), "Spinach");
        add(ItemInit.SPINACH_SEED.get(), "Spinach Seed");
        add(ItemInit.TOMATO.get(), "Tomato");
        add(ItemInit.CUCUMBER.get(), "Cucumber");
        add(ItemInit.CUCUMBER_SEED.get(), "Cucumber Seed");
        add(ItemInit.LETTUCE.get(), "Lettuce");
        add(ItemInit.LETTUCE_SEED.get(), "Lettuce Seed");
        add(ItemInit.RUBY.get(), "Ruby");
        add(ItemInit.SAPPHIRE.get(), "Sapphire");
        add(ItemInit.CAMPANILE_GOO.get(), "Campanile Goo");
        add(ItemInit.CRATAEGUS_BOAT_ITEM.get(), "Crataegus Boat");
        add(ItemInit.RUBY_SWORD.get(), "Ruby Sword");
        add(ItemInit.RUBY_HOE.get(), "Ruby Hoe");
        add(ItemInit.RUBY_PICKAXE.get(), "Ruby Pickaxe");
        add(ItemInit.RUBY_SHOVEL.get(), "Ruby Shovel");
        add(ItemInit.RUBY_AXE.get(), "Ruby Axe");
        add(ItemInit.SAPPHIRE_SWORD.get(), "Sapphire Sword");
        add(ItemInit.SAPPHIRE_HOE.get(), "Sapphire Hoe");
        add(ItemInit.SAPPHIRE_PICKAXE.get(), "Sapphire Pickaxe");
        add(ItemInit.SAPPHIRE_SHOVEL.get(), "Sapphire Shovel");
        add(ItemInit.SAPPHIRE_AXE.get(), "Sapphire Axe");
        add(EntityInit.PARAPUZOSIA.get(), "Parapuzosia");
        add(EntityInit.EOSQUALODON.get(), "Eosqualodon");
        add(EntityInit.MEGA_PIRANHA.get(), "Mega Piranha");
        add(EntityInit.DIMORPHODON.get(), "Dimorphodon");
        add(EntityInit.ASTORGOSUCHUS.get(), "Astorgosuchus");
        add(EntityInit.CAMPANILE.get(), "Campanile");
        add(EntityInit.DRYOSAURUS.get(), "Dryosaurus");
        add(EntityInit.AEGIROCASSIS.get(), "Aegirocassis");
        add(EntityInit.HERMIT.get(), "Hermit");
        add(EntityInit.CERATOSAURUS.get(), "Ceratosaurus");


        BiomeInit.BIOMES.getEntries().forEach(this::add);

        add(Dinosaur.Gender.FEMALE.getDisplayName(), "Female");
        add(Dinosaur.Gender.MALE.getDisplayName(), "Male");

        add(Dinosaur.Rarity.COMMON.getDisplayName(), "Common");
        add(Dinosaur.Rarity.UNCOMMON.getDisplayName(), "Uncommon");
        add(Dinosaur.Rarity.RARE.getDisplayName(), "Rare");
        add(Dinosaur.Rarity.EPIC.getDisplayName(), "Epic");
        add(Dinosaur.Rarity.LEGENDARY.getDisplayName(), "Legendary");

        add(AttackOrder.NEUTRAL.getDisplayName(), "Neutral");
        add(AttackOrder.HOSTILE.getDisplayName(), "Hostile");
        add(AttackOrder.PASSIVE.getDisplayName(), "Passive");
        add(AttackOrder.PROTECTING.getDisplayName(), "Protecting");

        add(MoveOrder.IDLE.getDisplayName(), "Idle");
        add(MoveOrder.FOLLOW.getDisplayName(), "Follow");
        add(MoveOrder.WANDER.getDisplayName(), "Wander");
        add(MoveOrder.SIT_SHOULDER.getDisplayName(), "Sit Shoulder");

        add(SleepRhythmGoal.SleepRhythm.NONE.getDisplayName(), "None");
        add(SleepRhythmGoal.SleepRhythm.NOCTURNAL.getDisplayName(), "Nocturnal");
        add(SleepRhythmGoal.SleepRhythm.DIURNAL.getDisplayName(), "Diurnal");

        add("dinosexpansion.order_screen.title", "%s");


        add("taming_screen.dinosexpansion.narcotic", "Narcotic: %s / %d");
        add("taming_screen.dinosexpansion.hunger", "Hunger: %s / %d");
        add("taming_screen.dinosexpansion.taming_progress", "Taming Progress: %s / 100");

        add("dinosexpansion.gender", "Gender: %s");
        add("dinosexpansion.level", "Level: %s");
        add("dinosexpansion.sleep_rhythm", "Sleep Rhythm: %s");

        add("dinosexpansion.survival_instinct_cooldown", "Cooldown: %d");

        add("dinosexpansion.modifiers.hand", "When Throwing:");
        add("dinosexpansion.boomerang.damage", "%d Damage");
        add("dinosexpansion.boomerang.range", "%d Range");;

        add("dinosexpansion.taming_gui.title", "%s");

        add(TameCommand.TAME_NO_DINOSAUR, "the entity you are pointing at is no dinosaur, was %s instead");
        add(TameCommand.TAME_SUCCESS, "this dinosaur %s was successfully tamed");
        add(TameCommand.TAME_NO_HIT, "you have to point with ur cursor to an entity");


    }

    public void add(ItemGroup group, String key) {
        add(group.getGroupName().getString(), key);
    }

    public void add(RegistryObject<Biome> obj) {
        add("biome." + DinosExpansion.MOD_ID + "." + obj.getId().getPath(), toTitleCase(obj.getId().getPath()));
    }

    public void add(ITextComponent component, String name) {
        add(component.getString(), name);
    }

    public static String toTitleCase(ForgeRegistryEntry<?> input) {
        return toTitleCase(input.getRegistryName().getPath());
    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (c == '_') {
                nextTitleCase = true;
                titleCase.append(" ");
                continue;
            }

            if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
