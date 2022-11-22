package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public class DinosaurSwimBottomGoal extends RandomWalkingGoal {

    private Dinosaur dinosaur;

    public DinosaurSwimBottomGoal(Dinosaur dinosaur, double speedIn, int chance) {
        super(dinosaur, speedIn, chance);
        this.dinosaur = dinosaur;
    }

    @Override
    public boolean shouldExecute() {
        return !dinosaur.isMovementDisabled() && super.shouldExecute();
    }

    @Nullable
    @Override
    protected Vector3d getPosition() {
        Vector3d vec = RandomPositionGenerator.findRandomTarget(this.dinosaur, 10, 7);

        for(int var2 = 0; vec != null && !this.dinosaur.world.getBlockState(new BlockPos(vec)).allowsMovement(this.dinosaur.world, new BlockPos(vec), PathType.WATER) && var2++ < 10; vec = RandomPositionGenerator.findRandomTarget(this.dinosaur, 10, 7)) {
        }
        int yDrop = 1 + this.dinosaur.getRNG().nextInt(3);
        if(vec != null){
            BlockPos pos = new BlockPos(vec);
            while(this.dinosaur.world.getFluidState(pos).isTagged(FluidTags.WATER) && this.dinosaur.world.getBlockState(pos).allowsMovement(this.dinosaur.world, new BlockPos(vec), PathType.WATER) && pos.getY() > 1){
                pos = pos.down();
            }
            pos = pos.up();
            int yUp = 0;
            while(this.dinosaur.world.getFluidState(pos).isTagged(FluidTags.WATER) && this.dinosaur.world.getBlockState(pos).allowsMovement(this.dinosaur.world, new BlockPos(vec), PathType.WATER) && yUp < yDrop){
                pos = pos.up();
                yUp++;
            }
            return Vector3d.copyCentered(pos);
        }

        return vec;
    }
}
