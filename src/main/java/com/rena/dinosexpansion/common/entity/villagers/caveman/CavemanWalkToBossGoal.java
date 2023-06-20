package com.rena.dinosexpansion.common.entity.villagers.caveman;

import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public class CavemanWalkToBossGoal extends Goal {

    protected final Caveman caveman;
    protected final double maxDist, speed;
    protected Vector3d wanderTo;

    public CavemanWalkToBossGoal(Caveman caveman, double maxDist, double speed) {
        this.caveman = caveman;
        this.maxDist = maxDist;
        this.speed = speed;
    }

    @Override
    public boolean shouldExecute() {
        if (caveman.getTribe() != null && caveman.getTribe().hasBoss() && !caveman.tribe.isBossFight() && !caveman.isBoss() && caveman.getRNG().nextInt(20) == 0) {
            Vector3d wanderTo = getPosition();
            if (wanderTo != null && wanderTo.isWithinDistanceOf(caveman.getTribe().getBoss().getPositionVec(), this.maxDist)) {
                this.wanderTo = wanderTo;
                return true;
            }
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.caveman.getNavigator().tryMoveToXYZ(this.wanderTo.x, this.wanderTo.y, this.wanderTo.z, this.speed);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return caveman.getTribe() != null && caveman.getTribe().hasBoss() && !this.caveman.getNavigator().noPath() && !this.caveman.isBeingRidden();
    }

    @Nullable
    protected Vector3d getPosition() {
        return RandomPositionGenerator.findRandomTarget(this.caveman.getTribe().getBoss(), (int) Math.round(this.maxDist), 7);
    }

    @Override
    public void resetTask() {
        this.caveman.getNavigator().clearPath();
        super.resetTask();
    }


}
