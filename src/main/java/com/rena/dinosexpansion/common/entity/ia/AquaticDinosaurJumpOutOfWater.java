package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.aquatic.DinosaurAquatic;
import com.rena.dinosexpansion.common.util.enums.MoveOrder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;

public class AquaticDinosaurJumpOutOfWater extends Goal {
    protected final DinosaurAquatic aquatic;
    protected final int chance;
    protected final double speedAddition;

    protected double distWeight = 1;
    protected BlockPos jumpPos;
    protected boolean wasInWater = false;

    public AquaticDinosaurJumpOutOfWater(DinosaurAquatic aquatic, int chance, double speedAddition) {
        this.aquatic = aquatic;
        this.chance = chance;
        this.speedAddition = speedAddition;

        setMutexFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        if (!this.aquatic.isTamed()) {
            if (!this.aquatic.isMovementDisabled() && this.aquatic.getRNG().nextInt(chance) == 0) {
                this.jumpPos = generateWaterTarget();
                return this.jumpPos != null;
            }
        } else {
            if (!this.aquatic.isMovementDisabled() && this.aquatic.getMoveOrder() == MoveOrder.WANDER && this.aquatic.getRNG().nextInt(this.chance) == 0) {
                this.jumpPos = generateWaterTarget();
                return this.jumpPos != null;
            }
        }
        return false;
    }

    @Override
    public void startExecuting() {
        this.aquatic.getNavigator().clearPath();
        this.distWeight = 1d;
    }

    @Override
    public void tick() {
        boolean wasWater = this.wasInWater;
        this.wasInWater = this.aquatic.world.getFluidState(this.aquatic.getPosition()).isTagged(FluidTags.WATER);
        if (wasWater && !wasInWater) {
            this.aquatic.playSound(SoundEvents.ENTITY_DOLPHIN_JUMP, 1.0F, 1.0F);
        }
        Vector3d oldMotion = Vector3d.ZERO;
        if (wasWater && this.wasInWater) {
            BlockPos entityPos = this.aquatic.getPosition();
            BlockPos airAbove = entityPos;
            while (this.aquatic.world.getFluidState(airAbove).isTagged(FluidTags.WATER) && airAbove.getY() < aquatic.world.getHeight()) {
                airAbove = airAbove.up();
            }

            double disSq = entityPos.distanceSq(airAbove);
            if (disSq >= 25)
                distWeight = 0;
            Vector3d down = new Vector3d(0, entityPos.getY() - airAbove.getY(), 0).scale(disSq * 0.1 * distWeight);


            double distSq = Math.pow(entityPos.getX() - this.jumpPos.getX(), 2) + Math.pow(entityPos.getZ() - this.jumpPos.getZ(), 2);
            Vector3d up = new Vector3d(0, jumpPos.getY() - entityPos.getY(), 0).scale(100 / distSq);
            Vector3d front = new Vector3d(jumpPos.getX() - entityPos.getX(), 0, jumpPos.getZ() - entityPos.getZ());
            oldMotion = oldMotion.add(down);
            oldMotion = oldMotion.add(up);
            oldMotion = oldMotion.add(front).scale(this.speedAddition * this.aquatic.getAttributeValue(Attributes.MOVEMENT_SPEED));
        } else if (!wasWater && !this.wasInWater) {
            oldMotion = this.aquatic.getMotion();
            oldMotion = oldMotion.add(new Vector3d(0, -0.01, 0)).scale(this.speedAddition * this.aquatic.getAttributeValue(Attributes.MOVEMENT_SPEED));
        } else if (!wasWater && this.wasInWater) {
            this.jumpPos = null;
        }

        float yaw = (float) Math.toDegrees(Math.atan2(-oldMotion.getX(), oldMotion.getZ()));
        double d0 = Math.sqrt(Entity.horizontalMag(oldMotion));
        double pitch = Math.signum(-oldMotion.y) * Math.acos(d0 / oldMotion.length()) * (double)(180F / (float)Math.PI);


        this.aquatic.renderYawOffset = yaw;
        this.aquatic.rotationYaw = yaw;

        this.aquatic.rotationPitch = (float) pitch;

        this.aquatic.setMotion(oldMotion);

    }

    @Override
    public void resetTask() {
        this.distWeight = 1;
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (this.aquatic.isTamed()) {
            if (!this.aquatic.isMovementDisabled()) {
                return this.jumpPos != null;
            }
        } else {
            if (!this.aquatic.isMovementDisabled() && this.aquatic.getMoveOrder() == MoveOrder.WANDER) {
                return this.jumpPos != null;
            }
        }
        return false;
    }

    protected BlockPos generateWaterTarget() {
        double x = aquatic.getRNG().nextInt(10) - 5;
        double z = aquatic.getRNG().nextInt(10) - 5;
        BlockPos pos = new BlockPos(this.aquatic.getPositionVec().add(x, 0, z));
        if (!aquatic.world.getFluidState(pos).isTagged(FluidTags.WATER))
            return null;
        while (aquatic.world.getFluidState(pos).isTagged(FluidTags.WATER) && pos.getY() < aquatic.world.getHeight()) {
            pos = pos.up();
        }
        if (pos.getY() >= aquatic.world.getHeight())
            return null;
        pos = pos.down();
        return pos;
    }
}
