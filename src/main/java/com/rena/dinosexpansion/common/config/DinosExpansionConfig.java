package com.rena.dinosexpansion.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class DinosExpansionConfig {

    public static final ForgeConfigSpec.Builder BUILDER;
    public static final ForgeConfigSpec.IntValue MAX_LEVEL, MAX_XP, XP_INCREASE, HEALTH_PER_LEVEL, NARCOTIC_NEEDED_PERCENT;
    public static final ForgeConfigSpec.DoubleValue ATTACK_DAMAGE_PER_LEVEL;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        /**Dinos*/
        builder.push("Dinos");
        builder.comment("this defines the max Level Dios can have");
        MAX_LEVEL = builder.defineInRange("max_level", 50, 10, 450);
        builder.comment("needed xp to increase the level", "one Xp is gained when the dinosaur hits something and 2 Xp are gained when the dinosaur kills/breaks something");
        MAX_XP = builder.defineInRange("max_Xp", 10, 1, Integer.MAX_VALUE);
        builder.comment("this defines how many more xp u need for every level after 0",
                "so the maxXP is calculated: max_Xp + level*xp_increase");
        XP_INCREASE = builder.defineInRange("xp_increase", 3, 0, 100);
        builder.comment("this defines how many max health the dino gets when leveling up");
        HEALTH_PER_LEVEL = builder.defineInRange("healt_per_level", 2, 1, Integer.MAX_VALUE);
        builder.comment("this defines how many attack damage the dino gets additional when leveling up");
        ATTACK_DAMAGE_PER_LEVEL = builder.defineInRange("attack_damage_per_level", 1f, 0f, 100f);
        builder.comment("this defines how many narcitocs u will need to make the dinosaur go knocked out.", "this value is in percent so u can just modify it for all entities");
        NARCOTIC_NEEDED_PERCENT = builder.defineInRange("narcotic_percent", 100, 0, 100);
        builder.pop();
        BUILDER =  builder;
    }


}
