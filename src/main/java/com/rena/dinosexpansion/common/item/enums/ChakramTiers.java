package com.rena.dinosexpansion.common.item.enums;

import com.rena.dinosexpansion.common.item.TieredChakram;

public enum ChakramTiers implements TieredChakram.ChakramTier {

    WOODEN_CHAKRAM(0.3D, 0.3D, 25, 72),
    STONE_CHRAKRAM(0.5D, 0.5D, 20, 135),
    IRON_CHAKRAM(0.7D, 0.7D, 15, 473),
    GOLD_CHAKRAM(0.3D, 0.3D, 25, 42),
    DIAMOND_CHAKRAM(0.9D, 0.9D, 13, 1320),
    EMERALD_CHAKRAM(1.0D, 1.0D, 10, 1872),
    NETHERITE_CHAKRAM(1.1D, 1.1D, 15, 2343);

    private final double damageAddition, speedAddition;
    private final int enchantability, durability;

    ChakramTiers(double damageAddition, double speedAddition, int enchantability, int durability) {
        this.damageAddition = damageAddition;
        this.speedAddition = speedAddition;
        this.enchantability = enchantability;
        this.durability = durability;
    }

    @Override
    public double getDamageAddition() {
        return this.damageAddition;
    }

    @Override
    public double getSpeedAddition() {
        return this.speedAddition;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }
}
