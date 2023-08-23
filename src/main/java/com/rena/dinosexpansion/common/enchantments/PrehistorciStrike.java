package com.rena.dinosexpansion.common.enchantments;

import com.rena.dinosexpansion.core.init.EnchantmentInit;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class PrehistorciStrike extends Enchantment {
    public PrehistorciStrike() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 15 + 20 * (enchantmentLevel - 1);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if (!(target instanceof LivingEntity))
            return;
        LivingEntity targetLiving = (LivingEntity) target;
        targetLiving.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20 + 4 * level, level < 4 ? 0 : level - 3, false, false, false));
    }
}
