package com.rena.dinosexpansion.common.entity.ia.movecontroller;

import com.rena.dinosexpansion.common.entity.ia.helper.ISemiAquatic;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;

public class AquaticMoveController extends MovementController {

    private final CreatureEntity dinosaur;
    private float speedMulti;
    private float yawLimit = 3.0F;

    public AquaticMoveController(CreatureEntity dinosaur, float speedMulti) {
        super(dinosaur);
        this.dinosaur = dinosaur;
        this.speedMulti = speedMulti;
    }

    public AquaticMoveController(CreatureEntity dinosaur, float speedMulti, float yawLimit) {
        super(dinosaur);
        this.dinosaur = dinosaur;
        this.yawLimit = yawLimit;
        this.speedMulti = speedMulti;
    }

    @Override
    public void tick() {
        if (this.dinosaur.isInWater()) {
            this.dinosaur.setMotion(this.dinosaur.getMotion().add(0.0D, 0.005D, 0.0D));
        }
        if(dinosaur instanceof ISemiAquatic && ((ISemiAquatic) dinosaur).shouldStopMoving()){
            this.dinosaur.setAIMoveSpeed(0.0F);
            return;
        }
        if (this.action == Action.MOVE_TO && !this.dinosaur.getNavigator().noPath()) {
            double d0 = this.posX - this.dinosaur.getPosX();
            double d1 = this.posY - this.dinosaur.getPosY();
            double d2 = this.posZ - this.dinosaur.getPosZ();
            double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d1 /= d3;
            float f = (float)(MathHelper.atan2(d2, d0) * 57.2957763671875D) - 90.0F;
            this.dinosaur.rotationYaw = this.limitAngle(this.dinosaur.rotationYaw, f, yawLimit);
            this.dinosaur.renderYawOffset = this.dinosaur.rotationYaw;
            float f1 = (float)(this.speed * this.dinosaur.getAttributeValue(Attributes.MOVEMENT_SPEED) * speedMulti);
            this.dinosaur.setAIMoveSpeed(f1 * 0.4F);
            this.dinosaur.setMotion(this.dinosaur.getMotion().add(0.0D, (double)this.dinosaur.getAIMoveSpeed() * d1 * 0.6D, 0.0D));
        } else {
            this.dinosaur.setAIMoveSpeed(0.0F);
        }
    }
}
