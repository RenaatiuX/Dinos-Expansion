package com.rena.dinosexpansion.common.enchantments;

import com.rena.dinosexpansion.core.init.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class ElectricEnchantment extends Enchantment {
    public ElectricEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentType.BOW, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    public int getMinEnchantability(int enchantmentLevel) {
        return 20;
    }

    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel() {
        return 1;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != EnchantmentInit.ICE_ENCHANTMENT.get();
    }


}
