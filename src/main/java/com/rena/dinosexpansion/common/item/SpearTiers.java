package com.rena.dinosexpansion.common.item;

public enum SpearTiers implements TieredSpear.SpearTier {

    WOODEN_SPEAR(2.0D, 0.0D, 15, 59),
    STONE_SPEAR(3.0D, 0.0D, 5, 131),
    IRON_SPEAR(4.0D, 0.2D, 14, 250),
    DIAMOND_SPEAR(5.0D, 0.3D, 10, 1561),
    GOLD_SPEAR(2.0D, 0.5D,22, 32),
    NETHERITE_SPEAR(6.0D, 0.4D, 15, 2031),
    EMERALD_SPEAR(7.0D, 0.3D, 5, 2580);

    private final double damageAddition, speedAddition;
    private final int enchantability, durability;

    SpearTiers(double damageAddition, double speedAddition, int enchantability, int durability) {
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
    public double getAttackSpeed() {
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
