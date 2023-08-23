package com.rena.dinosexpansion.common.enchantments;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;

public class ResilientSkin extends Enchantment {
    public ResilientSkin() {
        super(Rarity.RARE, EnchantmentType.ARMOR, new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET});
    }


    @Override
    public int calcModifierDamage(int level, DamageSource source) {
        if (source.getTrueSource() instanceof Dinosaur){
            return level * 2;
        }
        return super.calcModifierDamage(level, source);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 1 + (enchantmentLevel - 1) * 11;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return getMinEnchantability(enchantmentLevel) + 11;
    }
}
