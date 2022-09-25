package com.rena.dinosexpansion.common.entity.ia.movecontroller;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class FlightMoveController extends MovementController {
    private final Dinosaur dinosaur;
    private float speedGeneral;
    private boolean shouldLookAtTarget;
    private boolean needsYSupport;

    public FlightMoveController(Dinosaur dinosaur, float speedGeneral, boolean shouldLookAtTarget, boolean needsYSupport) {
        super(dinosaur);
        this.dinosaur = dinosaur;
        this.shouldLookAtTarget = shouldLookAtTarget;
        this.speedGeneral = speedGeneral;
        this.needsYSupport = needsYSupport;
    }

    public FlightMoveController(Dinosaur dinosaur, float speedGeneral, boolean shouldLookAtTarget) {
        this(dinosaur, speedGeneral, shouldLookAtTarget, false);
    }

    public FlightMoveController(Dinosaur dinosaur, float speedGeneral) {
        this(dinosaur, speedGeneral, true);
    }

    @Override
    public void tick() {
        if (this.action == MovementController.Action.MOVE_TO) {
            Vector3d vector3d = new Vector3d(this.posX - dinosaur.getPosX(), this.posY - dinosaur.getPosY(), this.posZ - dinosaur.getPosZ());
            double d0 = vector3d.length();
            if (d0 < dinosaur.getBoundingBox().getAverageEdgeLength()) {
                this.action = MovementController.Action.WAIT;
                dinosaur.setMotion(dinosaur.getMotion().scale(0.5D));
            } else {
                dinosaur.setMotion(dinosaur.getMotion().add(vector3d.scale(this.speed * speedGeneral * 0.05D / d0)));
                if(needsYSupport){
                    double d1 = this.posY - dinosaur.getPosY();
                    dinosaur.setMotion(dinosaur.getMotion().add(0.0D, (double)dinosaur.getAIMoveSpeed() * speedGeneral * MathHelper.clamp(d1, -1, 1) * 0.6F, 0.0D));
                }
                if (dinosaur.getAttackTarget() == null || !shouldLookAtTarget) {
                    Vector3d vector3d1 = dinosaur.getMotion();
                    dinosaur.rotationYaw = -((float) MathHelper.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                    dinosaur.renderYawOffset = dinosaur.rotationYaw;
                } else{
                    double d2 = dinosaur.getAttackTarget().getPosX() - dinosaur.getPosX();
                    double d1 = dinosaur.getAttackTarget().getPosZ() - dinosaur.getPosZ();
                    dinosaur.rotationYaw = -((float) MathHelper.atan2(d2, d1)) * (180F / (float) Math.PI);
                    dinosaur.renderYawOffset = dinosaur.rotationYaw;
                }
            }

        }
    }
    
    private boolean func_220673_a(Vector3d vector3d, int p_220673_2_) {
        AxisAlignedBB axisalignedbb = this.dinosaur.getBoundingBox();

        for (int i = 1; i < p_220673_2_; ++i) {
            axisalignedbb = axisalignedbb.offset(vector3d);
            if (!this.dinosaur.world.hasNoCollisions(this.dinosaur, axisalignedbb)) {
                return false;
            }
        }

        return true;
    }
}
