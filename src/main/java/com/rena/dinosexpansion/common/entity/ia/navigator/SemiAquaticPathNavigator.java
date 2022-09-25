package com.rena.dinosexpansion.common.entity.ia.navigator;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.pathfinding.WalkAndSwimNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SemiAquaticPathNavigator extends SwimmerPathNavigator {

    private final Dinosaur dinosaur;

    public SemiAquaticPathNavigator(Dinosaur dinosaur, World worldIn) {
        super(dinosaur, worldIn);
        this.dinosaur = dinosaur;
    }

    protected PathFinder getPathFinder(int p_179679_1_) {
        this.nodeProcessor = new WalkAndSwimNodeProcessor();
        return new PathFinder(this.nodeProcessor, p_179679_1_);
    }

    protected boolean canNavigate() {
        return true;
    }

    protected Vector3d getEntityPosition() {
        return new Vector3d(this.dinosaur.getPosX(), this.dinosaur.getPosYHeight(0.5D), this.dinosaur.getPosZ());
    }

    protected boolean isDirectPathBetweenPoints(Vector3d posVec31, Vector3d posVec32, int sizeX, int sizeY, int sizeZ) {
        Vector3d vector3d = new Vector3d(posVec32.x, posVec32.y + (double)this.dinosaur.getHeight() * 0.5D, posVec32.z);
        return this.world.rayTraceBlocks(new RayTraceContext(posVec31, vector3d, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this.dinosaur)).getType() == RayTraceResult.Type.MISS;
    }

    public boolean canEntityStandOnPos(BlockPos pos) {
        return  !this.world.getBlockState(pos.down()).isAir();
    }

    public void setCanSwim(boolean canSwim) {
    }

}
