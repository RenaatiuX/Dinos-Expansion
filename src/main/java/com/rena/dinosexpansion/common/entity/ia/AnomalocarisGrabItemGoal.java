package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.aquatic.Anomalocaris;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.List;

public class AnomalocarisGrabItemGoal extends Goal {
    protected final Anomalocaris anomalocaris;
    protected final double speedAddition;

    protected int idleCounter = 0, animationCooldown = 0;

    protected ItemEntity target;

    public AnomalocarisGrabItemGoal(Anomalocaris anomalocaris, double speedAddition) {
        this.anomalocaris = anomalocaris;
        this.speedAddition = speedAddition;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (anomalocaris.hasHeldItem()) {
            return false;
        } else if (anomalocaris.getAttackTarget() == null && anomalocaris.getRevengeTarget() == null) {
            if (anomalocaris.getRNG().nextInt(10) == 0) {
                List<ItemEntity> list = anomalocaris.world.getEntitiesWithinAABB(ItemEntity.class, anomalocaris.getBoundingBox().grow(8.0D, 8.0D, 8.0D), Anomalocaris.ITEMS);
                if (list.size() > 0) {
                    this.target = list.get(this.anomalocaris.getRNG().nextInt(list.size()));
                    return target != null;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public void resetTask() {
        this.anomalocaris.getNavigator().clearPath();
        this.animationCooldown = 0;
        this.idleCounter = 0;
        this.target = null;
    }

    @Override
    public void tick() {
        if (animationCooldown > 0){
            animationCooldown--;
            if (animationCooldown == 0){
                grabItem();
            }
        }
        else if (this.anomalocaris.getDistanceSq(this.target) <= 4){
            this.anomalocaris.setGrabbing(true);
            this.animationCooldown = 14;//cause 0.625 * 20 + 1 = 13,5 = 14
        } else if (this.anomalocaris.getNavigator().noPath()){
            idleCounter++;
            this.anomalocaris.getNavigator().tryMoveToEntityLiving(this.target, speedAddition);
        }

    }

    protected void grabItem(){
        this.anomalocaris.setGrabbing(false);
        this.anomalocaris.setHeldItem(this.target.getItem().copy());
        this.target.remove();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !anomalocaris.hasHeldItem() && idleCounter < 200 && this.target != null && !this.target.cannotPickup() && this.target.isAlive();
    }

    @Override
    public void startExecuting() {
        this.anomalocaris.getNavigator().tryMoveToEntityLiving(this.target, speedAddition);
        this.idleCounter = 0;
    }
}
