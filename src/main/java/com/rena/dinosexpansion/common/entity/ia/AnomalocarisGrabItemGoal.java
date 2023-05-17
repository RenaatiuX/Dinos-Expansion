package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.aquatic.Anomalocaris;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.List;

public class AnomalocarisGrabItemGoal extends Goal {
    private final Anomalocaris dinosaur;
    private int eatTimer;

    public AnomalocarisGrabItemGoal(Anomalocaris dinosaur) {
        this.dinosaur = dinosaur;
        this.setMutexFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (!dinosaur.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()) {
            return false;
        } else if (dinosaur.getAttackTarget() == null && dinosaur.getRevengeTarget() == null) {
            if (dinosaur.getRNG().nextInt(10) != 0) {
                return false;
            } else {
                List<ItemEntity> items = this.dinosaur.world.getEntitiesWithinAABB(ItemEntity.class, this.dinosaur.getBoundingBox().grow(8.0, 4.0, 8.0), Anomalocaris.ITEMS);
                return !items.isEmpty() && dinosaur.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty();
            }
        } else {
            return false;
        }
    }

    @Override
    public void startExecuting() {
        List<ItemEntity> items = dinosaur.world.getEntitiesWithinAABB(ItemEntity.class, dinosaur.getBoundingBox().grow(8.0D, 4.0D, 8.0D), Anomalocaris.ITEMS);
        if (!items.isEmpty()) {
            dinosaur.getNavigator().tryMoveToEntityLiving(items.get(0), (double) 1.2F);
            dinosaur.setGrabbing(true);
            dinosaur.setHeldItem(items.get(0).getItem());
        }
    }

    @Override
    public void tick() {
        List<ItemEntity> items = dinosaur.world.getEntitiesWithinAABB(ItemEntity.class, dinosaur.getBoundingBox().grow(8.0D, 4.0D, 8.0D), Anomalocaris.ITEMS);
        ItemStack itemstack = dinosaur.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        if (itemstack.isEmpty() && !items.isEmpty()) {
            dinosaur.getNavigator().tryMoveToEntityLiving(items.get(0), (double) 1.2F);
            dinosaur.setGrabbing(true);
            dinosaur.setHeldItem(items.get(0).getItem());
        } else {
            dinosaur.setHeldItem(ItemStack.EMPTY);
        }
    }
}
