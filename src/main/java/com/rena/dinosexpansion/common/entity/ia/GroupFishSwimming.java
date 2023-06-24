package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.aquatic.fish.PrehistoricFish;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class GroupFishSwimming extends Goal {
    protected final PrehistoricFish creature;
    protected double x;
    protected double y;
    protected double z;
    protected final double speed;
    protected boolean mustUpdate;
    protected final double distSenseOthers, distSenseBlocks;
    protected final Predicate<PrehistoricFish> selector;

    public GroupFishSwimming(PrehistoricFish creatureIn, double speedIn, double minDist) {
        this(creatureIn, speedIn, minDist, minDist, e -> e.getType() == creatureIn.getType());
    }

    public GroupFishSwimming(PrehistoricFish creature, double speed, double distSenseOthers, double distSenseBlocks, Predicate<PrehistoricFish> selector) {
        this.creature = creature;
        this.speed = speed;
        this.distSenseOthers = distSenseOthers;
        this.distSenseBlocks = distSenseBlocks;
        this.selector = selector;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        if (this.creature.isBeingRidden()) {
            return false;
        } else {
            if (!this.mustUpdate) {
                if (this.creature.getIdleTime() >= 100) {
                    return false;
                }
            }
            if (creature.isMovementDisabled())
                return false;
        }
        return true;
    }

    @Override
    public void tick() {
        //swim with others

        //this will keep the fish swimming
        double swimSPeed = this.creature.getAttributeValue(Attributes.MOVEMENT_SPEED);
        Vector3d motion = this.creature.getMotion();
        if (Double.isNaN(motion.getX()) || Double.isNaN(motion.getY()) || Double.isNaN(motion.getZ()))
            motion = Vector3d.ZERO;

        //add a bit of random nise in the movement
        Vector3d noise = new Vector3d(this.creature.getRNG().nextInt(2) - 1, this.creature.getRNG().nextInt(2) - 1, this.creature.getRNG().nextInt(2) - 1);

        motion = motion.add(getAverageDir());
        motion = motion.add(getAvoidGetToNearOthers());
        motion = motion.add(getAvoidBlocks());
        motion = motion.add(noise);
        motion = motion.add(getCohesion());

        if (motion.lengthSquared() > swimSPeed * swimSPeed){
            motion = motion.normalize().scale(swimSPeed);
        }

        this.creature.setMotion(motion);





    }


    public Vector3d getAvoidGetToNearOthers(){
        List<PrehistoricFish> entities = this.creature.world.getEntitiesWithinAABB(PrehistoricFish.class, this.creature.getBoundingBox().grow(this.distSenseOthers), this.selector);
        Vector3d result = Vector3d.ZERO;
        for (PrehistoricFish fish : entities){
            if (fish != this.creature) {
                double dist = fish.getDistance(this.creature);
                Vector3d diff = this.creature.getPositionVec().subtract(fish.getPositionVec()).normalize().scale(1 / dist);
                result = result.add(diff);
            }
        }

        return result;
    }

    public Vector3d getAverageDir(){
        List<PrehistoricFish> entities = this.creature.world.getEntitiesWithinAABB(PrehistoricFish.class, this.creature.getBoundingBox().grow(this.distSenseOthers), this.selector);
        Vector3d result = Vector3d.ZERO;
        for (PrehistoricFish fish : entities) {
            if (fish != this.creature) {
                double dist = 1 / fish.getDistance(this.creature);
                result = result.add(fish.getMotion().normalize().scale(dist));
            }
        }

        return result;
    }

    public Vector3d getAvoidBlocks() {
        Vector3d result = new Vector3d(0, 0, 0);
        for (int x = (int) Math.floor(-distSenseBlocks); x <= (int) Math.ceil(distSenseBlocks); x++) {
            for (int y = (int) Math.floor(-distSenseBlocks); y <= (int) Math.ceil(distSenseBlocks); y++) {
                for (int z = (int) Math.floor(-distSenseBlocks); z <= (int) Math.ceil(distSenseBlocks); z++) {
                    Vector3d looking = Vector3d.copyCentered( this.creature.getPosition().add(x, y, z));
                    if (!this.creature.world.getFluidState(new BlockPos(looking)).isTagged(FluidTags.WATER)) {
                        double distSquared = this.creature.getPositionVec().distanceTo(looking);
                        Vector3d avoiding = this.creature.getPositionVec().subtract(looking).normalize().scale(1 / distSquared);
                        result = result.add(avoiding);
                    }
                }
            }
        }
        return result;
    }

    public Vector3d getCohesion(){
        List<PrehistoricFish> entities = this.creature.world.getEntitiesWithinAABB(PrehistoricFish.class, this.creature.getBoundingBox().grow(this.distSenseOthers), this.selector);
        Vector3d result = Vector3d.ZERO;
        int count = 0;
        for (PrehistoricFish fish : entities){
            if (fish != this.creature) {
                result = result.add(fish.getPositionVec());
                count++;
            }
        }

        if (count > 0) {
            result = result.scale(1 / (double) count);
            result = result.normalize().scale(0.05);
        }

        return result;

    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return !this.creature.getNavigator().noPath() && !this.creature.isBeingRidden() && !this.creature.isMovementDisabled();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.creature.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.creature.getNavigator().clearPath();
        super.resetTask();
    }
}
