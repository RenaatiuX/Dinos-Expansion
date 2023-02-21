package com.rena.dinosexpansion.common.enchantments;

import com.rena.dinosexpansion.common.item.TieredBoomerang;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class GuidanceEnchantment extends Enchantment {
    public GuidanceEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentType.create("boomerang", i -> i instanceof TieredBoomerang), new EquipmentSlotType[]{EquipmentSlotType.MAINHAND, EquipmentSlotType.OFFHAND});
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    public int getMinEnchantability(int enchantmentLevel) {
        return 15 + (enchantmentLevel - 1) * 9;
    }

    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }
}
