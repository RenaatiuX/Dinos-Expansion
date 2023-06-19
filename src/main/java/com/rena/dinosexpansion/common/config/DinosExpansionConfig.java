package com.rena.dinosexpansion.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class DinosExpansionConfig {

    public static final ForgeConfigSpec.Builder BUILDER;
    public static final ForgeConfigSpec.IntValue MAX_LEVEL, MAX_XP, XP_INCREASE, NARCOTIC_NEEDED_PERCENT,
            WOOD_BOOMERANG_RANGE, WOOD_BOOMERANG_DAMAGE, IRON_BOOMERANG_RANGE, IRON_BOOMERANG_DAMAGE,
            DIAMOND_BOOMERANG_RANGE, DIAMOND_BOOMERANG_DAMAGE, LEVEL_OFFSET;
    public static final ForgeConfigSpec.DoubleValue ATTACK_DAMAGE_PER_LEVEL, HEALTH_PER_LEVEL, ARMOR_PER_LEVEL;
    public static final ForgeConfigSpec.BooleanValue TURN_AROUND_ITEM, TURN_AROUND_MOB, TURN_AROUND_BUTTON,
            BREAKS_TORCHES, BREAKS_FLOWERS, BREAKS_GRASS, BREAKS_TALLGRASS,
            ACTIVATES_LEVERS, ACTIVATES_BUTTONS, ACTIVATES_PRESSURES_PLATES, ACTIVATES_TRIP_WIRE,
            SHOW_LEVEL_ABOVE_HEAD, SPAWN_DINOSAUR_OVERWORLD;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("Dinos");
        builder.comment("this defines the max Level Dios can have");
        MAX_LEVEL = builder.defineInRange("max_level", 50, 10, 450);
        builder.comment("this defines how many level higher from defaul u want ur Dino to spawn");
        LEVEL_OFFSET = builder.defineInRange("level_offset", 0, 0, Integer.MAX_VALUE);
        builder.comment("needed xp to increase the level", "one Xp is gained when the dinosaur hits something and 2 Xp are gained when the dinosaur kills/breaks something");
        MAX_XP = builder.defineInRange("max_Xp", 10, 1, Integer.MAX_VALUE);
        builder.comment("this defines how many more xp u need for every level after 0",
                "so the maxXP is calculated: max_Xp + level*xp_increase");
        XP_INCREASE = builder.defineInRange("xp_increase", 3, 0, 100);
        builder.comment("this defines how many max health the dino gets when leveling up");
        HEALTH_PER_LEVEL = builder.defineInRange("healt_per_level", 10f, 0f, 255);
        builder.comment("this defines how many attack damage the dino gets additional when leveling up");
        ATTACK_DAMAGE_PER_LEVEL = builder.defineInRange("attack_damage_per_level", .1f, 0f, 255f);
        builder.comment("this defines how much armor a dinosaur will get per Level");
        ARMOR_PER_LEVEL = builder.defineInRange("armor_per_level", .1f, 0f, 255f);
        builder.comment("this defines how many narcitocs u will need to make the dinosaur go knocked out.", "this value is in percent so u can just modify it for all entities");
        NARCOTIC_NEEDED_PERCENT = builder.defineInRange("narcotic_percent", 100, 0, 100);
        builder.comment("this defines whether the level of a dino should be displayed above the head of an Dino");
        SHOW_LEVEL_ABOVE_HEAD = builder.define("show_level", true);
        builder.comment("This defines if dinosaurs can spawn in the overworld");
        SPAWN_DINOSAUR_OVERWORLD = builder.define("spawn_dinosaur", false);
        builder.pop();

        builder.push("Boomerang");
        TURN_AROUND_ITEM = builder.comment("Comes back to the player after picking up items.").define("turnAroundItem", true);
        TURN_AROUND_MOB = builder.comment("Comes back to the player after hitting a mob.").define("turnAroundMob", true);
        TURN_AROUND_BUTTON = builder.comment("Comes back to player after hitting a button.").define("turnAroundButton", true);
        WOOD_BOOMERANG_RANGE = builder.comment("The maximum range of travel before returning to player.").defineInRange("WoodBoomerangRange", 30, 1, 200);
        WOOD_BOOMERANG_DAMAGE = builder.comment("The amount of damage that is done when hitting any living entity.").defineInRange("WoodBoomerangDamage", 4, 1, 500);
        IRON_BOOMERANG_RANGE = builder.comment("The maximum range of travel before returning to player.").defineInRange("IronBoomerangRange", 35, 1, 200);
        IRON_BOOMERANG_DAMAGE = builder.comment("The amount of damage that is done when hitting any living entity.").defineInRange("IronBoomerangDamage", 6, 1, 500);
        DIAMOND_BOOMERANG_RANGE = builder.comment("The maximum range of travel before returning to player.").defineInRange("DiamondBoomerangRange", 40, 1, 200);
        DIAMOND_BOOMERANG_DAMAGE = builder.comment("The amount of damage that is done when hitting any living entity.").defineInRange("DiamondBoomerangDamage", 7, 1, 500);
        BREAKS_TORCHES = builder.comment("Can boomerang break torches.").define("breaksTorches", true);
        BREAKS_FLOWERS = builder.comment("Can boomerang break Flowers.").define("breaksFlowers", true);
        BREAKS_GRASS = builder.comment("Can boomerang break Grass.").define("breaksGrass", true);
        BREAKS_TALLGRASS = builder.comment("Can boomerang break Tall Grass.").define("breaksTallGrass", true);
        ACTIVATES_LEVERS = builder.comment("Can boomerang switch levers on and off.").define("activatesLevers", true);
        ACTIVATES_BUTTONS = builder.comment("Can boomerang activate/push buttons.").define("activatesButtons", true);
        ACTIVATES_PRESSURES_PLATES = builder.comment("Can boomerang activate regular and lightweight pressure plates.").define("activatesPressurePlates", true);
        ACTIVATES_TRIP_WIRE = builder.comment("Can boomerang activate/trigger tripwire(s).").define("activatesTripWire", true);
        builder.pop();

        BUILDER =  builder;
    }

}
