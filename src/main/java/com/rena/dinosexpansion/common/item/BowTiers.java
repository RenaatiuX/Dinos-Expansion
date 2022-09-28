package com.rena.dinosexpansion.common.item;

import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public enum BowTiers implements TieredBow.BowTier {
    VANILLA(0, 0, 1);

    private final double damageAddition, speedAddition;
    private final int enchantability;
    private final Predicate<ItemStack> ammo;

    BowTiers(double damageAddition, double speedAddition, int enchantability, Predicate<ItemStack> ammo) {
        this.damageAddition = damageAddition;
        this.speedAddition = speedAddition;
        this.enchantability = enchantability;
        this.ammo = ammo;
    }
    BowTiers(double damageAddition, double speedAddition, int enchantability) {
        this(damageAddition, speedAddition, enchantability, null);
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
    public Predicate<ItemStack> getAmmo() {
        return this.ammo == null ? TieredBow.BowTier.super.getAmmo() : ammo;
    }
}
