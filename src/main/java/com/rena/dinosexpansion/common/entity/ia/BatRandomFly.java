package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.flying.DinosaurFlying;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;

public class BatRandomFly<T extends Dinosaur> extends Goal {


    public static final int MAX_STUCK_TIME = 100;
    protected BlockPos targetPos;
    protected final T ambient;
    protected Predicate<T> shouldExecute;
    protected double distTarget;
    protected int avgHeight, noise, stuckTimer;

    public BatRandomFly(T ambient, int avgHeight, int noise, Predicate<T> shouldExecute) {
        this.ambient = ambient;
        this.shouldExecute = shouldExecute;
        this.noise = noise;
        this.avgHeight = avgHeight;
        this.setMutexFlags(EnumSet.of(Flag.MOVE));
    }

    public BatRandomFly(T ambient, int avgHeight, int noise) {
        this(ambient, avgHeight, noise, null);
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
            generateNewPos();
        }

        if (this.targetPos != null) {
            if (Math.abs(distTarget - this.targetPos.distanceSq(this.ambient.getPosition())) >= .1d) {
                this.stuckTimer++;
            } else {
                this.distTarget = this.targetPos.distanceSq(this.ambient.getPosition());
            }
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
        if (stuckTimer >= MAX_STUCK_TIME){
            this.generateNewPos();
        }
    }

    protected void generateNewPos() {
        Random rand = ambient.getRNG();
        int currentHeight = (int) Math.abs(this.ambient.getPosY() - DinosaurFlying.getGroundDinosaur(this.ambient).getY());
        int range = avgHeight - currentHeight;
        double y = generateRange(rand, MathHelper.clamp(range, -3, 0) - noise, MathHelper.clamp(range, 0, 3) + noise);
        this.targetPos = this.ambient.getPosition().add(rand.nextInt(7) - rand.nextInt(7), y, rand.nextInt(7) - rand.nextInt(7));
    }

    /**
     * generates a number with a range [min, max]
     */
    public static double generateRange(Random random, int min, int max) {
        int range = Math.abs(max - min);
        return random.nextInt(range + 1) + min;
    }

    @Override
    public void resetTask() {
        ambient.setMotion(Vector3d.ZERO);
        this.targetPos = null;
        this.stuckTimer = 0;
    }
}
