package com.rena.dinosexpansion.common.item.enums;

import com.rena.dinosexpansion.common.item.TieredHatchet;

public enum HatchetTiers implements TieredHatchet.HatchetTier {
    WOODEN_HATCHET(0.4D, 0.1f, 25, 72),
    STONE_HATCHET(0.5D,0.2f, 18, 150),
    IRON_HATCHET(0.6D,0.3f, 15, 273),
    GOLD_HATCHET(0.4D, 0.1f, 32, 72),
    DIAMOND_HATCHET(0.7D, 0.4f, 10, 643),
    NETHERITE_HATCHET(0.8D, 0.5f, 10, 840),
    EMERALD_HATCHET(0.9D, 0.6f, 20, 1253),
    RUBY_HATCHET(1.0D, 0.7f, 15, 1498),
    SAPPHIRE_HATCHET(1.1D, 0.8f, 10, 1673);

    private final double damageAddition;
    private final float speedAddition;
    private final int enchantability, durability;

    HatchetTiers(double damageAddition, float speedAddition, int enchantability, int durability) {
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
    public float getSpeedAddition() {
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
