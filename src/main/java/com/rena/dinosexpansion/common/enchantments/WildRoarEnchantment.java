package com.rena.dinosexpansion.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class WildRoarEnchantment extends Enchantment {

    public WildRoarEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 15 + (enchantmentLevel - 1) * 9;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public void onEntityDamaged(LivingEntity user, Entity target, int level) {
        if (!(user instanceof PlayerEntity) || !(target instanceof LivingEntity))
            return;

        PlayerEntity player = (PlayerEntity) user;
        double probability = 0.1 * level;

        if (Math.random() < probability) {
            List<CreatureEntity> nearbyEntities = player.world.getEntitiesWithinAABB(CreatureEntity.class, player.getBoundingBox().grow(16, 8, 16));
            for (CreatureEntity entity : nearbyEntities) {
                entity.setAttackTarget(null);
                entity.setRevengeTarget(null);
                if (Math.random() < 0.4) {
                    Vector3d vec = RandomPositionGenerator.findRandomTargetBlockAwayFrom(entity, 20, 7, player.getPositionVec());
                    if(vec != null){
                        entity.getNavigator().tryMoveToXYZ(vec.x, vec.y, vec.z, 1.5D);
                    }
                }
            }
        }
    }
}
