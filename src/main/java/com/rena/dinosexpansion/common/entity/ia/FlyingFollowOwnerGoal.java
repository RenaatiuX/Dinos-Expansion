package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.helper.IFollower;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import java.util.EnumSet;

public class FlyingFollowOwnerGoal extends Goal {

    private final Dinosaur dinosaur;
    private LivingEntity owner;
    private final IWorldReader world;
    private final double followSpeed;
    private final PathNavigator navigator;
    private int timeToRecalcPath;
    private final float maxDist;
    private final float minDist;
    private float oldWaterCost;
    private final boolean teleportToLeaves;
    private IFollower follower;

    public FlyingFollowOwnerGoal(Dinosaur dinosaur, double speed, float minDist, float maxDist, boolean teleportToLeaves) {
        this.dinosaur = dinosaur;
        this.world = dinosaur.world;
        this.followSpeed = speed;
        this.navigator = dinosaur.getNavigator();
        this.minDist = minDist;
        this.maxDist = maxDist;
        this.teleportToLeaves = teleportToLeaves;
        follower = (IFollower)dinosaur;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }


    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    @Override
    public boolean shouldExecute() {
        /*LivingEntity livingentity = this.dinosaur.getOwner();
        if (livingentity == null) {
            return false;
        } else if (livingentity.isSpectator()) {
            return false;
        } else if (this.dinosaur.isQueuedToSit()) {
            return false;
        } else if (this.dinosaur.getDistanceSq(livingentity) < (double)(this.minDist * this.minDist)) {
            return false;
        } else {
            this.owner = livingentity;
            return true;
        }*/
        return false; //delete this
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        /*if (this.dinosaur.isQueuedToSit()) {
            return false;
        } else {
            return this.dinosaur.getDistanceSq(this.owner) > (double)(this.maxDist * this.maxDist);
        }*/

        return this.dinosaur.getDistanceSq(this.owner) > (double)(this.maxDist * this.maxDist); // Delete this
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.dinosaur.getPathPriority(PathNodeType.WATER);
        this.dinosaur.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
    public void resetTask() {
        this.owner = null;
        this.navigator.clearPath();
        this.dinosaur.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        this.dinosaur.getLookController().setLookPositionWithEntity(this.owner, 10.0F, (float)this.dinosaur.getVerticalFaceSpeed());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            if (!this.dinosaur.getLeashed() && !this.dinosaur.isPassenger()) {
                if (this.dinosaur.getDistanceSq(this.owner) >= 144.0D) {
                    this.tryToTeleportNearEntity();
                }
                follower.followEntity(dinosaur, owner, followSpeed);
            }
        }
    }

    private void tryToTeleportNearEntity() {
        BlockPos blockpos = this.owner.getPosition();

        for(int i = 0; i < 10; ++i) {
            int j = this.getRandomNumber(-3, 3);
            int k = this.getRandomNumber(-1, 1);
            int l = this.getRandomNumber(-3, 3);
            boolean flag = this.tryToTeleportToLocation(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
            if (flag) {
                return;
            }
        }

    }

    private boolean tryToTeleportToLocation(int x, int y, int z) {
        if (Math.abs((double)x - this.owner.getPosX()) < 2.0D && Math.abs((double)z - this.owner.getPosZ()) < 2.0D) {
            return false;
        } else if (!this.isTeleportFriendlyBlock(new BlockPos(x, y, z))) {
            return false;
        } else {
            this.dinosaur.setLocationAndAngles((double)x + 0.5D, (double)y, (double)z + 0.5D, this.dinosaur.rotationYaw, this.dinosaur.rotationPitch);
            this.navigator.clearPath();
            return true;
        }
    }

    private boolean isTeleportFriendlyBlock(BlockPos pos) {
        PathNodeType pathnodetype = WalkNodeProcessor.getFloorNodeType(this.world, pos.toMutable());
        if (pathnodetype != PathNodeType.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = this.world.getBlockState(pos.down());
            if (!this.teleportToLeaves && blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = pos.subtract(this.dinosaur.getPosition());
                return this.world.hasNoCollisions(this.dinosaur, this.dinosaur.getBoundingBox().offset(blockpos));
            }
        }
    }

    private int getRandomNumber(int min, int max) {
        return this.dinosaur.getRNG().nextInt(max - min + 1) + min;
    }
}
