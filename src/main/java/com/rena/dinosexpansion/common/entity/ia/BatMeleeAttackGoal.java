package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.util.ArrayUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

public class BatMeleeAttackGoal extends Goal {

    protected final Dinosaur dino;
    protected final double speed;
    protected int pathfindIdlTimer = 0, attackingIdleTimer = 0;
    protected boolean attacking = false;

    public BatMeleeAttackGoal(Dinosaur dino, double speed) {
        this.dino = dino;
        this.speed = speed;
    }

    @Override
    public boolean shouldExecute() {
        return dino.getAttackTarget() != null && !dino.isMovementDisabled();
    }

    @Override
    public void tick() {
        LivingEntity target = dino.getAttackTarget();
        if (target != null) {
            if (target.getDistanceSq(dino) <= 4d) {
                this.pathfindIdlTimer = 0;
                this.dino.getNavigator().clearPath();
                if ((dino.getRNG().nextInt(100) == 0 || this.attacking) && attackingIdleTimer <= 200) {
                    this.attacking = true;
                    System.out.println("attacking");
                    if (!this.dino.getNavigator().tryMoveToEntityLiving(target, this.speed)) {
                        attackingIdleTimer++;
                    }
                    if (this.dino.getDistanceSq(target) <= getAttackReachSqr(target)) {
                        this.dino.swingArm(Hand.MAIN_HAND);
                        this.dino.attackEntityAsMob(target);
                        this.attacking = false;
                        this.dino.getNavigator().clearPath();
                    }

                } else if (this.dino.getNavigator().noPath() && (dino.getRNG().nextInt(60) == 0 || targetLooksOnIt(target))) {
                    this.attackingIdleTimer = 0;
                    Direction randomDir = ArrayUtils.chooseRandom(dino.getRNG(), Direction.values());
                    Vector3d newPos = dino.getPositionVec().add(Vector3d.copy(randomDir.getDirectionVec()).mul(2, 2, 2));
                    this.dino.getNavigator().tryMoveToXYZ(newPos.getX(), newPos.getZ(), newPos.getY(), this.speed);
                }

            } else {
                if (!this.dino.getNavigator().tryMoveToEntityLiving(target, this.speed)) {
                    this.pathfindIdlTimer++;
                }
            }
        }
    }

    protected boolean targetLooksOnIt(LivingEntity target) {
        return ProjectileHelper.rayTraceEntities(dino.world, target, target.getPositionVec(), target.getPositionVec().add(target.getLookVec().normalize().mul(5d, 5d, 5d)), target.getBoundingBox(), e -> e.getEntityId() == dino.getEntityId()) != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting() && this.pathfindIdlTimer <= 200;
    }

    @Override
    public void resetTask() {
        this.pathfindIdlTimer = 0;
        this.dino.getNavigator().clearPath();
    }

    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return (double)(this.dino.getWidth() * 2.0F * this.dino.getWidth() * 2.0F + attackTarget.getWidth());
    }
}
