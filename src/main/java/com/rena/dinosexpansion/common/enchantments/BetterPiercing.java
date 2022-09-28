package com.rena.dinosexpansion.common.enchantments;

import com.rena.dinosexpansion.common.item.TieredBow;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;

public class BetterPiercing extends Enchantment {


    protected BetterPiercing(Rarity p_i46731_1_, EnchantmentType p_i46731_2_, EquipmentSlotType... p_i46731_3_) {
        super(p_i46731_1_, p_i46731_2_, p_i46731_3_);
    }

    public BetterPiercing(){
        this(Rarity.UNCOMMON, EnchantmentType.create("tiered_bows", item -> EnchantmentType.CROSSBOW.canEnchantItem(item) || item instanceof TieredBow), EquipmentSlotType.MAINHAND);
    }

    public int getMinEnchantability(int enchantmentLevel) {
        return 1 + (enchantmentLevel - 1) * 10;
    }

    public int getMaxEnchantability(int enchantmentLevel) {
        return 50;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel() {
        return 4;
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.MULTISHOT;
    }
}
