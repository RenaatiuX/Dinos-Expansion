package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.helper.ISemiAquatic;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;

public class DinosaurSwimMoveControllerSinkGoal extends MovementController {
    private final Dinosaur dinosaur;
    private float speedMulti;
    private float ySpeedMod = 1;
    private float yawLimit = 10.0F;

    public DinosaurSwimMoveControllerSinkGoal(Dinosaur dinosaur, float speedMulti, float ySpeedMod) {
        super(dinosaur);
        this.dinosaur = dinosaur;
        this.speedMulti = speedMulti;
        this.ySpeedMod = ySpeedMod;
    }

    public DinosaurSwimMoveControllerSinkGoal(Dinosaur dinosaur, float speedMulti, float ySpeedMod, float yawLimit) {
        super(dinosaur);
        this.dinosaur = dinosaur;
        this.speedMulti = speedMulti;
        this.ySpeedMod = ySpeedMod;
        this.yawLimit = yawLimit;
    }

    @Override
    public void tick() {
        if (dinosaur instanceof ISemiAquatic && ((ISemiAquatic) dinosaur).shouldStopMoving()) {
            this.dinosaur.setAIMoveSpeed(0.0F);
            return;
        }
        if (this.action == Action.MOVE_TO && !this.dinosaur.getNavigator().noPath()) {
            double lvt_1_1_ = this.posX - this.dinosaur.getPosX();
            double lvt_3_1_ = this.posY - this.dinosaur.getPosY();
            double lvt_5_1_ = this.posZ - this.dinosaur.getPosZ();
            double lvt_7_1_ = lvt_1_1_ * lvt_1_1_ + lvt_3_1_ * lvt_3_1_ + lvt_5_1_ * lvt_5_1_;
            if (lvt_7_1_ < 2.500000277905201E-7D) {
                this.mob.setMoveForward(0.0F);
            } else {
                float lvt_9_1_ = (float) (MathHelper.atan2(lvt_5_1_, lvt_1_1_) * 57.2957763671875D) - 90.0F;
                this.dinosaur.rotationYaw = this.limitAngle(this.dinosaur.rotationYaw, lvt_9_1_, yawLimit);
                this.dinosaur.renderYawOffset = this.dinosaur.rotationYaw;
                this.dinosaur.rotationYawHead = this.dinosaur.rotationYaw;
                float lvt_10_1_ = (float) (this.speed * speedMulti * 3 * this.dinosaur.getAttributeValue(Attributes.MOVEMENT_SPEED));
                if (this.dinosaur.isInWater()) {
                    if(lvt_3_1_ > 0 && dinosaur.collidedHorizontally){
                        this.dinosaur.setMotion(this.dinosaur.getMotion().add(0.0D, 0.08F, 0.0D));
                    }else{
                        this.dinosaur.setMotion(this.dinosaur.getMotion().add(0.0D, (double) this.dinosaur.getAIMoveSpeed() * lvt_3_1_ * 0.6D * ySpeedMod, 0.0D));
                    }
                    this.dinosaur.setAIMoveSpeed(lvt_10_1_ * 0.02F);
                    float lvt_11_1_ = -((float) (MathHelper.atan2(lvt_3_1_, MathHelper.sqrt(lvt_1_1_ * lvt_1_1_ + lvt_5_1_ * lvt_5_1_)) * 57.2957763671875D));
                    lvt_11_1_ = MathHelper.clamp(MathHelper.wrapDegrees(lvt_11_1_), -85.0F, 85.0F);
                    this.dinosaur.rotationPitch = this.limitAngle(this.dinosaur.rotationPitch, lvt_11_1_, 5.0F);
                    float lvt_12_1_ = MathHelper.cos(this.dinosaur.rotationPitch * 0.017453292F);
                    float lvt_13_1_ = MathHelper.sin(this.dinosaur.rotationPitch * 0.017453292F);
                    this.dinosaur.moveForward = lvt_12_1_ * lvt_10_1_;
                    this.dinosaur.moveVertical = -lvt_13_1_ * lvt_10_1_;
                } else {
                    this.dinosaur.setAIMoveSpeed(lvt_10_1_ * 0.1F);
                }

            }
        }
    }
}
