package com.rena.dinosexpansion.common.item;

public enum ChakramTiers implements TieredChakram.ChakramTier {

    WOODEN_CHAKRAM(0.5D, 0.5D, 25, 72);

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
