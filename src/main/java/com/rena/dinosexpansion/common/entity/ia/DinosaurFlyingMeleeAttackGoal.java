package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.flying.DinosaurFlying;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class DinosaurFlyingMeleeAttackGoal extends Goal {

    private DinosaurFlying dinosaurFlying;
    float circlingTime = 0;
    float circleDistance = 1;
    float yLevel = 2;
    boolean clockwise = false;
    private int maxCircleTime;

    public DinosaurFlyingMeleeAttackGoal(DinosaurFlying dinosaurFlying) {
        this.dinosaurFlying = dinosaurFlying;
    }
    @Override
    public boolean shouldExecute(){
        if (this.dinosaurFlying.isChild()){
            return false;
        }
        return dinosaurFlying.getAttackTarget() != null;
    }

    @Override
    public void startExecuting() {
        clockwise = dinosaurFlying.getRNG().nextBoolean();
        yLevel = dinosaurFlying.getRNG().nextInt(8);
        circlingTime = 0;
        maxCircleTime = 20 + dinosaurFlying.getRNG().nextInt(100);
        circleDistance = 8F + dinosaurFlying.getRNG().nextFloat() * 3F;
    }
    @Override
    public void resetTask() {
        clockwise = dinosaurFlying.getRNG().nextBoolean();
        yLevel = dinosaurFlying.getRNG().nextInt(8);
        circlingTime = 0;
        maxCircleTime = 20 + dinosaurFlying.getRNG().nextInt(100);
        circleDistance = 8F + dinosaurFlying.getRNG().nextFloat() * 3F;
        if(dinosaurFlying.isOnGround()){
            dinosaurFlying.setFlying(false);
        }
    }
    @Override
    public void tick() {
        if (this.dinosaurFlying.isFlying()) {
            circlingTime++;
        }
        LivingEntity target = dinosaurFlying.getAttackTarget();
        if(circlingTime > maxCircleTime){
            dinosaurFlying.getMoveHelper().setMoveTo(target.getPosX(), target.getPosY() + target.getEyeHeight() / 2F, target.getPosZ(), 1.3F);
            if(dinosaurFlying.getDistance(target) < 2){
                if(target.getCreatureAttribute() == CreatureAttribute.UNDEAD){
                    target.attackEntityFrom(DamageSource.MAGIC, 4);
                }else{
                    target.attackEntityFrom(DamageSource.GENERIC, 1);
                }

                resetTask();
            }
        }else{
            Vector3d circlePos = getCirclePos(target.getPositionVec());
            if (circlePos == null) {
                circlePos = target.getPositionVec();
            }
            dinosaurFlying.setFlying(true);
            dinosaurFlying.getMoveHelper().setMoveTo(circlePos.getX(), circlePos.getY() + target.getEyeHeight() + 0.2F, circlePos.getZ(), 1F);

        }
    }

    public Vector3d getCirclePos(Vector3d target) {
        float angle = (0.01745329251F * 8 * (clockwise ? -circlingTime : circlingTime));
        double extraX = circleDistance * MathHelper.sin((angle));
        double extraZ = circleDistance * MathHelper.cos(angle);
        Vector3d pos = new Vector3d(target.getX() + extraX, target.getY() + yLevel, target.getZ() + extraZ);
        if (dinosaurFlying.world.isAirBlock(new BlockPos(pos))) {
            return pos;
        }
        return null;
    }
    
}
