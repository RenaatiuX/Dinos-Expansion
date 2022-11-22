package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class DinosaurAINearestTarget<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    protected final Dinosaur dino;
    public DinosaurAINearestTarget(Dinosaur goalOwnerIn, Class<T> targetClassIn, boolean checkSight) {
        super(goalOwnerIn, targetClassIn, checkSight);
        this.dino = goalOwnerIn;
    }

    public DinosaurAINearestTarget(Dinosaur goalOwnerIn, Class<T> targetClassIn, boolean checkSight, boolean nearbyOnlyIn) {
        super(goalOwnerIn, targetClassIn, checkSight, nearbyOnlyIn);
        this.dino = goalOwnerIn;
    }

    public DinosaurAINearestTarget(Dinosaur goalOwnerIn, Class<T> targetClassIn, int targetChanceIn, boolean checkSight, boolean nearbyOnlyIn, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(goalOwnerIn, targetClassIn, targetChanceIn, checkSight, nearbyOnlyIn, targetPredicate);
        this.dino = goalOwnerIn;
    }

    @Override
    public boolean shouldExecute() {
        return !this.dino.isSleeping() && !this.dino.isKnockout() && super.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting();
    }

    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        return this.goalOwner.getBoundingBox().grow(targetDistance, targetDistance, targetDistance);
    }

}
