package com.rena.dinosexpansion.common.enchantments;

import com.rena.dinosexpansion.common.item.TieredBow;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class BetterQuickCharge extends Enchantment {
    private BetterQuickCharge(Rarity p_i46731_1_, EnchantmentType p_i46731_2_, EquipmentSlotType... p_i46731_3_) {
        super(p_i46731_1_, p_i46731_2_, p_i46731_3_);
    }
    public BetterQuickCharge() {
        this(Enchantment.Rarity.UNCOMMON, EnchantmentType.create("tiered_bows", item -> EnchantmentType.CROSSBOW.canEnchantItem(item) || item instanceof TieredBow), EquipmentSlotType.MAINHAND);
    }


    public int getMinEnchantability(int enchantmentLevel) {
        return 12 + (enchantmentLevel - 1) * 20;
    }

    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel() {
        return 3;
    }
}
