package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.helper.ISemiAquatic;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.Random;

public class DinosaurFindWaterGoal extends Goal {

    private final Dinosaur dinosaur;
    private BlockPos targetPos;
    private int executionChance = 35;

    public DinosaurFindWaterGoal(Dinosaur dinosaur) {
        this.dinosaur = dinosaur;
        this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        if (this.dinosaur.isOnGround() && !this.dinosaur.world.getFluidState(this.dinosaur.getPosition()).isTagged(FluidTags.WATER)){
            if(this.dinosaur instanceof ISemiAquatic && ((ISemiAquatic) this.dinosaur).shouldEnterWater() && (this.dinosaur.getAttackTarget() != null || this.dinosaur.getRNG().nextInt(executionChance) == 0)){
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
    }

    @Override
    public boolean shouldContinueExecuting() {
        if(this.dinosaur instanceof ISemiAquatic && !((ISemiAquatic) this.dinosaur).shouldEnterWater()){
            this.dinosaur.getNavigator().clearPath();
            return false;
        }
        return !this.dinosaur.getNavigator().noPath() && targetPos != null && !this.dinosaur.world.getFluidState(this.dinosaur.getPosition()).isTagged(FluidTags.WATER);
    }

    public BlockPos generateTarget() {
        BlockPos blockpos = null;
        Random random = new Random();
        int range = this.dinosaur instanceof ISemiAquatic ? ((ISemiAquatic) this.dinosaur).getWaterSearchRange() : 14;
        for(int i = 0; i < 15; i++){
            BlockPos blockpos1 = this.dinosaur.getPosition().add(random.nextInt(range) - range/2, 3, random.nextInt(range) - range/2);
            while(this.dinosaur.world.isAirBlock(blockpos1) && blockpos1.getY() > 1){
                blockpos1 = blockpos1.down();
            }
            if(this.dinosaur.world.getFluidState(blockpos1).isTagged(FluidTags.WATER)){
                blockpos = blockpos1;
            }
        }
        return blockpos;
    }
}
