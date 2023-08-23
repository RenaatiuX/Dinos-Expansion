package com.rena.dinosexpansion.common.item.enums;

import com.rena.dinosexpansion.common.item.TieredHatchet;

public enum HatchetTiers implements TieredHatchet.HatchetTier {
    WOODEN_HATCHET(0.4D, 0.1D, 25, 72),
    STONE_HATCHET(0.5D,0.2D, 18, 150),
    IRON_HATCHET(0.6D,0.3D, 15, 273),
    GOLD_HATCHET(0.4D, 0.1D, 32, 72),
    DIAMOND_HATCHET(0.7D, 0.4D, 10, 643),
    NETHERITE_HATCHET(0.8D, 0.5D, 10, 840),
    EMERALD_HATCHET(0.9D, 0.6D, 20, 1253),
    RUBY_HATCHET(1.0D, 0.7D, 15, 1498),
    SAPPHIRE_HATCHET(1.1D, 0.8D, 10, 1673);

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
