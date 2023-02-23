package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.util.enums.AttackOrder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class DinosaurNearestTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    protected final Dinosaur dino;
    protected boolean attackAsNonTamed;
    public DinosaurNearestTargetGoal(Dinosaur goalOwnerIn, Class<T> targetClassIn, boolean checkSight) {
        this(goalOwnerIn, targetClassIn, checkSight, false, true);
    }

    public DinosaurNearestTargetGoal(Dinosaur goalOwnerIn, Class<T> targetClassIn, boolean checkSight, boolean nearbyOnlyIn, boolean attackAsNonTamed) {
        this(goalOwnerIn, targetClassIn,10, checkSight, nearbyOnlyIn, attackAsNonTamed, null);
    }

    public DinosaurNearestTargetGoal(Dinosaur goalOwnerIn, Class<T> targetClassIn, int targetChanceIn, boolean checkSight, boolean nearbyOnlyIn,boolean attackAsNonTamed, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(goalOwnerIn, targetClassIn, targetChanceIn, checkSight, nearbyOnlyIn, targetPredicate);
        this.dino = goalOwnerIn;
        this.attackAsNonTamed = attackAsNonTamed;
    }

    @Override
    public boolean shouldExecute() {
        if (dino.isTamed()){
            return !dino.isMovementDisabled() && this.dino.getAttackOrder() == AttackOrder.HOSTILE && super.shouldExecute();
        }
        return attackAsNonTamed && !this.dino.isSleeping() && !this.dino.isKnockout() && super.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting();
    }

    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        return this.goalOwner.getBoundingBox().grow(targetDistance, targetDistance, targetDistance);
    }

}
