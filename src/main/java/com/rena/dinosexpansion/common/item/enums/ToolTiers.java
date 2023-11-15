package com.rena.dinosexpansion.common.item.enums;

import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ToolTiers implements IItemTier {
    RUBY(2231,4, 10, 5.0F, 13.0F, () -> Ingredient.fromItems(ItemInit.RUBY.get())),
    SAPPHIRE(3031,5, 8, 6.0F, 18.0F, () -> Ingredient.fromItems(ItemInit.SAPPHIRE.get()));

    private final int maxUses, harvestLevel, enchantability;
    private final float attackDamage, efficience;
    private final Supplier<Ingredient> repairMaterial;

    ToolTiers(int maxUses, int harvestLevel, int enchantability, float attackDamage, float efficience, Supplier<Ingredient> repairMaterial) {
        this.maxUses = maxUses;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.attackDamage = attackDamage;
        this.efficience = efficience;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getMaxUses() {
        return this.maxUses;
    }

    @Override
    public float getEfficiency() {
        return this.efficience;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.get();
    }
}
