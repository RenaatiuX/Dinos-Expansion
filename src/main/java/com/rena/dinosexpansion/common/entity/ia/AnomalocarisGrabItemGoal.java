package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.aquatic.Anomalocaris;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class AnomalocarisGrabItemGoal extends Goal {
    private final Anomalocaris dinosaur;
    private ItemStack heldItem = ItemStack.EMPTY;

    public AnomalocarisGrabItemGoal(Anomalocaris dinosaur) {
        this.dinosaur = dinosaur;
        this.setMutexFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (!this.dinosaur.isAlive()) {
            return false;
        }
        // Busca un item cercano para recoger
        List<ItemEntity> items = this.dinosaur.world.getEntitiesWithinAABB(ItemEntity.class, this.dinosaur.getBoundingBox().grow(8.0, 4.0, 8.0));
        ItemEntity closestItem = items.stream().min(Comparator.comparingDouble(this.dinosaur::getDistanceSq)).orElse(null);
        if(closestItem != null && !closestItem.getItem().isEmpty()){
            this.dinosaur.setGrabbing(true);
            this.heldItem = closestItem.getItem().copy();
            this.dinosaur.setHeldItem(this.heldItem);
            closestItem.remove();
            return true;
        }
        return false;
    }

    @Override
    public void resetTask() {
        // Suelta el item y establece el Data Parameter
        if (!this.heldItem.isEmpty()) {
            this.dinosaur.setGrabbing(false);
            this.dinosaur.setHeldItem(ItemStack.EMPTY);
            this.dinosaur.world.addEntity(new ItemEntity(this.dinosaur.world, this.dinosaur.getPosX(), this.dinosaur.getPosY(), this.dinosaur.getPosZ(), this.heldItem));
            this.heldItem = ItemStack.EMPTY;
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        // Continúa recogiendo el item mientras no tenga ningún otro item en la mano
        return !this.heldItem.isEmpty() && this.dinosaur.getHeldItemMainhand().isEmpty();
    }

    @Override
    public void tick() {
        // Sostiene el item en la mano y actualiza el Data Parameter
        this.dinosaur.setHeldItem(Hand.MAIN_HAND, this.heldItem);
        this.dinosaur.setHeldItem(this.dinosaur.getHeldItemMainhand());
    }
}
