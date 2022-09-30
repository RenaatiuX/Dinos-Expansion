package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public enum BowTiers implements TieredBow.BowTier {
    VANILLA(0, 0, 1, 384),
    COMPOUND_BOW(4, 0.3f, 1, 1200, stack -> ModTags.Items.COMPOUND_ARROWS.contains(stack.getItem()));

    private final double damageAddition, speedAddition;
    private final int enchantability, durability;
    private final Predicate<ItemStack> ammo;

    BowTiers(double damageAddition, double speedAddition, int enchantability, int durability, Predicate<ItemStack> ammo) {
        this.damageAddition = damageAddition;
        this.speedAddition = speedAddition;
        this.enchantability = enchantability;
        this.ammo = ammo;
        this.durability = durability;
    }
    BowTiers(double damageAddition, double speedAddition, int enchantability, int durability) {
        this(damageAddition, speedAddition, enchantability,durability, null);
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
        return durability;
    }

    @Override
    public Predicate<ItemStack> getAmmo() {
        return this.ammo == null ? TieredBow.BowTier.super.getAmmo() : ammo;
    }
}
