package com.rena.dinosexpansion.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;

public class TrexFangKnifeItem extends SwordItem {
    public TrexFangKnifeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            Vector3d attackerLookVec = attacker.getLookVec();
            Vector3d attackerToTargetVec = target.getPositionVec().subtract(attacker.getPositionVec()).normalize();
            double dotProduct = attackerLookVec.dotProduct(attackerToTargetVec);
            if (dotProduct < 0) {
                float extraDamage = ((SwordItem)stack.getItem()).getAttackDamage() + 1;
                target.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) attacker), extraDamage);
            }
        }
        return super.hitEntity(stack, target, attacker);
    }
}
