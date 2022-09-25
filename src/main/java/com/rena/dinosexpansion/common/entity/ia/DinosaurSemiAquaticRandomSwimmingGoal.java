package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.helper.ISemiAquatic;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public class DinosaurSemiAquaticRandomSwimmingGoal extends RandomWalkingGoal {

    private final Dinosaur dinosaur;

    public DinosaurSemiAquaticRandomSwimmingGoal(Dinosaur dinosaur, double speed, int chance) {
        super(dinosaur, speed, chance, false);
        this.dinosaur = dinosaur;
    }

    @Override
    public boolean shouldExecute() {
        if (this.dinosaur.isBeingRidden() || ((ISemiAquatic) dinosaur).shouldStopMoving() || dinosaur.getAttackTarget() != null || !this.dinosaur.isInWater() && !this.dinosaur.isInLava() && !((ISemiAquatic) this.dinosaur).shouldEnterWater()) {
            return false;
        } else {
            if (!this.mustUpdate) {
                if (this.dinosaur.getRNG().nextInt(this.executionChance) != 0) {
                    return false;
                }
            }
            Vector3d vector3d = this.getPosition();
            if (vector3d == null) {
                return false;
            } else {
                this.x = vector3d.x;
                this.y = vector3d.y;
                this.z = vector3d.z;
                this.mustUpdate = false;
                return true;
            }
        }
    }

    @Nullable
    @Override
    protected Vector3d getPosition() {
        if(this.dinosaur.detachHome() && this.dinosaur.getDistanceSq(Vector3d.copyCentered(this.dinosaur.getHomePosition())) > this.dinosaur.getMaximumHomeDistance() * this.dinosaur.getMaximumHomeDistance()){
            return RandomPositionGenerator.findRandomTargetBlockTowards(this.dinosaur, 7, 3, Vector3d.copyCenteredHorizontally(this.dinosaur.getHomePosition()));
        }
        if(this.dinosaur.getRNG().nextFloat() < 0.3F){
            Vector3d vector3d = findSurfaceTarget(this.dinosaur, 15, 7);
            if(vector3d != null){
                return vector3d;
            }
        }
        Vector3d vector3d = RandomPositionGenerator.findRandomTarget(this.dinosaur, 7, 3);

        for(int i = 0; vector3d != null && !this.dinosaur.world.getFluidState(new BlockPos(vector3d)).isTagged(FluidTags.LAVA) && !this.dinosaur.world.getBlockState(new BlockPos(vector3d)).allowsMovement(this.dinosaur.world, new BlockPos(vector3d), PathType.WATER) && i++ < 15; vector3d = RandomPositionGenerator.findRandomTarget(this.dinosaur, 10, 7)) {
        }

        return vector3d;
    }

    private boolean canJumpTo(BlockPos pos, int dx, int dz, int scale) {
        BlockPos blockpos = pos.add(dx * scale, 0, dz * scale);
        return this.dinosaur.world.getFluidState(blockpos).isTagged(FluidTags.WATER) && !this.dinosaur.world.getBlockState(blockpos).getMaterial().blocksMovement();
    }

    private boolean isAirAbove(BlockPos pos, int dx, int dz, int scale) {
        return this.dinosaur.world.getBlockState(pos.add(dx * scale, 1, dz * scale)).isAir() && this.dinosaur.world.getBlockState(pos.add(dx * scale, 2, dz * scale)).isAir();
    }

    protected Vector3d findSurfaceTarget(Dinosaur dinosaur, int i, int i1) {
        BlockPos upPos = dinosaur.getPosition();
        while(dinosaur.world.getFluidState(upPos).isTagged(FluidTags.WATER) || dinosaur.world.getFluidState(upPos).isTagged(FluidTags.LAVA)){
            upPos = upPos.up();
        }
        if(isAirAbove(upPos.down(), 0, 0, 0) && canJumpTo(upPos.down(), 0, 0, 0)){
            return new Vector3d(upPos.getX() + 0.5F, upPos.getY() - 1F, upPos.getZ() + 0.5F);
        }
        return null;
    }
}
