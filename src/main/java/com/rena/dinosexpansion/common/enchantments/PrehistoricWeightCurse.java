package com.rena.dinosexpansion.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class PrehistoricWeightCurse extends Enchantment {
    public PrehistoricWeightCurse() {
        super(Rarity.VERY_RARE, EnchantmentType.ARMOR, new EquipmentSlotType[] {EquipmentSlotType.CHEST, EquipmentSlotType.FEET, EquipmentSlotType.HEAD, EquipmentSlotType.LEGS});
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 25;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }
}
