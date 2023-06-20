package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.aquatic.Anomalocaris;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.List;

public class AnomalocarisGrabItemGoal extends Goal {
    private final Anomalocaris anomalocaris;
    public AnomalocarisGrabItemGoal(Anomalocaris anomalocaris) {
        this.anomalocaris = anomalocaris;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (!anomalocaris.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()) {
            return false;
        } else if (anomalocaris.getAttackTarget() == null && anomalocaris.getRevengeTarget() == null) {
            if (anomalocaris.getRNG().nextInt(10) != 0) {
                return false;
            } else {
                List<ItemEntity> list = anomalocaris.world.getEntitiesWithinAABB(ItemEntity.class, anomalocaris.getBoundingBox().grow(8.0D, 8.0D, 8.0D), Anomalocaris.ITEMS);
                return !list.isEmpty() && anomalocaris.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty();
            }
        } else {
            return false;
        }
    }

    @Override
    public void tick() {
        List<ItemEntity> list = anomalocaris.world.getEntitiesWithinAABB(ItemEntity.class, anomalocaris.getBoundingBox().grow(8.0D, 8.0D, 8.0D), Anomalocaris.ITEMS);
        ItemStack itemstack = anomalocaris.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        if (itemstack.isEmpty() && !list.isEmpty()) {
            anomalocaris.getNavigator().tryMoveToEntityLiving(list.get(0), (double)1.2F);
            anomalocaris.setGrabbing(true);
            anomalocaris.setHeldItem(list.get(0).getItem());
        } else {
            anomalocaris.setHeldItem(ItemStack.EMPTY);
        }
    }

    @Override
    public void startExecuting() {
        List<ItemEntity> list = anomalocaris.world.getEntitiesWithinAABB(ItemEntity.class, anomalocaris.getBoundingBox().grow(8.0D, 8.0D, 8.0D), Anomalocaris.ITEMS);
        if (!list.isEmpty()) {
            anomalocaris.getNavigator().tryMoveToEntityLiving(list.get(0), (double)1.2F);
            anomalocaris.setGrabbing(true);
            anomalocaris.setHeldItem(list.get(0).getItem());
        }
    }
}
