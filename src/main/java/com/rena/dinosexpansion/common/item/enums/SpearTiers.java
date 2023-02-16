package com.rena.dinosexpansion.common.item.enums;

import com.rena.dinosexpansion.common.item.TieredSpear;

public enum SpearTiers implements TieredSpear.SpearTier {

    WOODEN_SPEAR(2.0D, 0.0D, 15, 59, 0.2d, false),
    STONE_SPEAR(3.0D, 0.0D, 5, 131, 0.4d, false),
    IRON_SPEAR(4.0D, 0.2D, 14, 250,0.6d, false),
    DIAMOND_SPEAR(5.0D, 0.3D, 10, 1561,1.4d, true),
    GOLD_SPEAR(2.0D, 0.4D,22, 32,0.7d, false),
    NETHERITE_SPEAR(6.0D, 0.5D, 15, 2031, 2d, true),
    EMERALD_SPEAR(7.0D, 0.6D, 5, 2580,1.5d, true);

    private final double damageAddition, speedAddition, knockbackAddition;
    private final int enchantability, durability;
    private final boolean canPierce;

    SpearTiers(double damageAddition, double speedAddition, int enchantability, int durability, double knockbackAddidtion, boolean canPierce) {
        this.damageAddition = damageAddition;
        this.speedAddition = speedAddition;
        this.enchantability = enchantability;
        this.durability = durability;
        this.canPierce = canPierce;
        this.knockbackAddition = knockbackAddidtion;
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
    public double getKnockback() {
        return this.knockbackAddition;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public boolean canPierce() {
        return this.canPierce;
    }
}
