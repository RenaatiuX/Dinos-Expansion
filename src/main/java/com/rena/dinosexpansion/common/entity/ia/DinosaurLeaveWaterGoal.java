package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.helper.ISemiAquatic;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;

public class DinosaurLeaveWaterGoal extends Goal {

    private final Dinosaur dinosaur;
    private BlockPos targetPos;
    private int executionChance = 35;

    public DinosaurLeaveWaterGoal(Dinosaur dinosaur) {
        this.dinosaur = dinosaur;
        this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        if (this.dinosaur.world.getFluidState(this.dinosaur.getPosition()).isTagged(FluidTags.WATER) && (this.dinosaur.getAttackTarget() != null || this.dinosaur.getRNG().nextInt(executionChance) == 0)){
            if(this.dinosaur instanceof ISemiAquatic && ((ISemiAquatic) this.dinosaur).shouldLeaveWater()){
                targetPos = generateTarget();
                return targetPos != null;
            }
        }
        return false;
    }

    @Override
    public void startExecuting() {
        if(targetPos != null){
            this.dinosaur.getNavigator().tryMoveToXYZ(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1D);
        }
    }

    @Override
    public void tick() {
        if(targetPos != null){
            this.dinosaur.getNavigator().tryMoveToXYZ(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1D);
        }
        if(this.dinosaur.collidedHorizontally && this.dinosaur.isInWater()){
            float f1 = dinosaur.rotationYaw * ((float)Math.PI / 180F);
            dinosaur.setMotion(dinosaur.getMotion().add(-MathHelper.sin(f1) * 0.2F, 0.1D, MathHelper.cos(f1) * 0.2F));

        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        if(this.dinosaur instanceof ISemiAquatic && !((ISemiAquatic) this.dinosaur).shouldLeaveWater()){
            this.dinosaur.getNavigator().clearPath();
            return false;
        }
        return !this.dinosaur.getNavigator().noPath() && targetPos != null && !this.dinosaur.world.getFluidState(targetPos).isTagged(FluidTags.WATER);
    }

    public BlockPos generateTarget() {
        Vector3d vector3d = RandomPositionGenerator.getLandPos(this.dinosaur, 23, 7);
        int tries = 0;
        while(vector3d != null && tries < 8){
            boolean waterDetected = false;
            for(BlockPos blockpos1 : BlockPos.getAllInBoxMutable(MathHelper.floor(vector3d.x - 2.0D), MathHelper.floor(vector3d.y - 1.0D), MathHelper.floor(vector3d.z - 2.0D), MathHelper.floor(vector3d.x + 2.0D), MathHelper.floor(vector3d.y), MathHelper.floor(vector3d.z + 2.0D))) {
                if (this.dinosaur.world.getFluidState(blockpos1).isTagged(FluidTags.WATER)) {
                    waterDetected = true;
                    break;
                }
            }
            if(waterDetected){
                vector3d = RandomPositionGenerator.getLandPos(this.dinosaur, 23, 7);
            }else{
                return new BlockPos(vector3d);
            }
            tries++;
        }
        return null;
    }
}
