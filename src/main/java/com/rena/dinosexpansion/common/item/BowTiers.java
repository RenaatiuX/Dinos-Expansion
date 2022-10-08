package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.core.init.ItemInit;
import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.function.Predicate;
import java.util.function.Supplier;

public enum BowTiers implements TieredBow.BowTier {
    VANILLA(0, 0, 1, 384),
    COMPOUND_BOW(4, 0.3f, 1, 1200, stack -> ModTags.Items.COMPOUND_ARROWS.contains(stack.getItem()), () -> ItemInit.COMPOUND_ARROW.get());

    private final double damageAddition, speedAddition;
    private final int enchantability, durability;
    private final Predicate<ItemStack> ammo;
    private final Supplier<Item> creativeDefault;

    BowTiers(double damageAddition, double speedAddition, int enchantability, int durability, Predicate<ItemStack> ammo, Supplier<Item> creativeDefault) {
        this.damageAddition = damageAddition;
        this.speedAddition = speedAddition;
        this.enchantability = enchantability;
        this.ammo = ammo;
        this.durability = durability;
        this.creativeDefault = creativeDefault;
    }

    BowTiers(double damageAddition, double speedAddition, int enchantability, int durability, Predicate<ItemStack> ammo) {
       this(damageAddition, speedAddition, enchantability, durability, ammo, () -> Items.ARROW);
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

    @Override
    public Item creativeDefault() {
        return this.creativeDefault.get();
    }
}
