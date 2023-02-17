package com.rena.dinosexpansion.common.enchantments;

import com.rena.dinosexpansion.common.enchantments.util.ModEnchantmentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
public class AmmoReservationEnchantment extends Enchantment {

    public AmmoReservationEnchantment() {
        super(Rarity.UNCOMMON, ModEnchantmentType.TYPE_THROWING_ITEM, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 20;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
