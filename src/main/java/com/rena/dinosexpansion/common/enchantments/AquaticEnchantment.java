package com.rena.dinosexpansion.common.enchantments;

import com.rena.dinosexpansion.common.enchantments.util.ModEnchantmentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class AquaticEnchantment extends Enchantment {

    public AquaticEnchantment() {
        super(Rarity.RARE, ModEnchantmentType.TYPE_THROWING_ITEM, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 20;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench);
    }
}
