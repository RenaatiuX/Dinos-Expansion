package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class CrocodileMeleeGoal extends DinosaurMeleeAttackGoal {
    public CrocodileMeleeGoal(Dinosaur creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
    }

    @Override
    public boolean shouldExecute() {
        return super.shouldExecute() && dino.getPassengers().isEmpty();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting() && dino.getPassengers().isEmpty();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        double d0 = this.getAttackReachSqr(enemy);
        if (distToEnemySqr <= d0) {
            this.resetSwingCooldown();
            this.attacker.swingArm(Hand.MAIN_HAND);
            this.attacker.attackEntityAsMob(enemy);
        }

    }
}
