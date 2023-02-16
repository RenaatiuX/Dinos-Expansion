package com.rena.dinosexpansion.common.item.enums;

import com.rena.dinosexpansion.common.item.TieredHatchet;

public enum HatchetTiers implements TieredHatchet.HatchetTier {
    WOODEN_HATCHET(0.4D, 0.1D, 25, 72),;

    private final double damageAddition, speedAddition;
    private final int enchantability, durability;

    HatchetTiers(double damageAddition, double speedAddition, int enchantability, int durability) {
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
