package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.terrestrial.ambient.AmbientDinosaur;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;

public class BatRandomFly<T extends Dinosaur> extends Goal {

    protected BlockPos targetPos;
    protected final T ambient;
    protected Predicate<T> shouldExecute;

    public BatRandomFly(T ambient, Predicate<T> shouldExecute) {
        this(ambient);
        this.shouldExecute = shouldExecute;
    }

    public BatRandomFly(T ambient) {
        this.ambient = ambient;
        this.setMutexFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        return shouldExecute == null ? !ambient.isMovementDisabled() : shouldExecute.test(this.ambient);
    }

    @Override
    public void tick() {
        if (!(this.targetPos == null || ambient.world.isAirBlock(this.targetPos) && this.targetPos.getY() > 1)) {
            this.targetPos = null;
        }

        if (this.targetPos == null || this.targetPos.withinDistance(ambient.getPositionVec(), 2.0)) {
            Random rand = ambient.getRNG();
            this.targetPos = new BlockPos(ambient.getPosX() + (double) rand.nextInt(7) - (double) rand.nextInt(7), ambient.getPosY() + (double) rand.nextInt(6) - 2.0D, ambient.getPosZ() + (double) rand.nextInt(7) - (double) rand.nextInt(7));
        }

        if (this.targetPos != null) {
            double d0 = this.targetPos.getX() + 0.5D - ambient.getPosX();
            double d1 = this.targetPos.getY() + 0.1D - ambient.getPosY();
            double d2 = this.targetPos.getZ() + 0.5D - ambient.getPosZ();

            double speed = ambient.getAttributeValue(Attributes.FLYING_SPEED);

            Vector3d vector3d = ambient.getMotion();
            Vector3d signumVector3d = vector3d.add((Math.signum(d0) * 0.5D - vector3d.x) * speed, (Math.signum(d1) * (double) 0.7F - vector3d.y) * speed, (Math.signum(d2) * 0.5D - vector3d.z) * speed);
            ambient.setMotion(signumVector3d);

            ambient.moveForward = 5.0F;
            float angle = (float) (MathHelper.atan2(signumVector3d.z, signumVector3d.x) * (double) (180F / (float) Math.PI)) - 90.0F;
            float wrappedAngle = MathHelper.wrapDegrees(angle - ambient.rotationYaw);
            ambient.rotationYaw += wrappedAngle;
        }
    }

    @Override
    public void resetTask() {
        ambient.setMotion(Vector3d.ZERO);
    }
}
