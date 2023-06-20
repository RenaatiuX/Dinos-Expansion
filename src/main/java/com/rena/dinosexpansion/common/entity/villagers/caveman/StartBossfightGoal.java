package com.rena.dinosexpansion.common.entity.villagers.caveman;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.system.CallbackI;

public class StartBossfightGoal extends Goal {

    protected final Caveman caveman;
    protected final double radius;
    protected BlockPos viewerPos = null;

    public StartBossfightGoal(Caveman caveman, double radius) {
        this.caveman = caveman;
        this.radius = radius;
    }


    @Override
    public boolean shouldExecute() {
        return caveman.getTribe() != null && caveman.getTribe().isBossFight() && !caveman.tookPlaceInBossfight && !caveman.takingPlaceInBossfight;
    }

    @Override
    public void startExecuting() {
        BlockPos center = caveman.getTribe().getBossFightCenter();
        if (caveman.isFighterBossFight()) {
            if (!caveman.getRivalBoss().takingPlaceInBossfight) {
                this.viewerPos = new BlockPos(center.getX() + radius - 2d, 250, center.getZ());
            } else {
                this.viewerPos = new BlockPos(center.getX() - radius + 2d, 250, center.getZ());
            }
            while (!caveman.world.getBlockState(viewerPos).isSolid() && caveman.getWorld().getBlockState(viewerPos).allowsMovement(caveman.world, viewerPos, PathType.LAND)){
                viewerPos = viewerPos.down();
            }
            viewerPos = viewerPos.up();
            caveman.getNavigator().tryMoveToXYZ(viewerPos.getX(), viewerPos.getY(), viewerPos.getZ(), .6d);
        } else {
            double around = (2d * Math.PI) / (double) (caveman.getTribe().getSize() - caveman.getTribe().countBosses());
            double getCavemanPosition = caveman.getTribe().getBossfightCounterCircle();
            double x = center.getX() + Math.cos(around * getCavemanPosition) * radius;
            double z = center.getZ() + Math.sin(around * getCavemanPosition) * radius;
            this.viewerPos = new BlockPos(x, 250, z);
            while (!caveman.world.getBlockState(viewerPos).isSolid() && caveman.getWorld().getBlockState(viewerPos).allowsMovement(caveman.world, viewerPos, PathType.LAND)){
                viewerPos = viewerPos.down();
            }
            caveman.getNavigator().tryMoveToXYZ(viewerPos.getX(), viewerPos.getY(), viewerPos.getZ(), .7d);
            caveman.getTribe().setBossfightCounterCircle((int) (getCavemanPosition + 1d));
        }
        caveman.takingPlaceInBossfight = true;
    }


    @Override
    public void tick() {
        if (!caveman.world.isRemote()) {
            if (!caveman.isFighterBossFight()) {
                caveman.getLookController().setLookPosition(caveman.tribe.getBossFightCenter().getX(), caveman.getPosYEye(), caveman.tribe.getBossFightCenter().getZ());
                caveman.getNavigator().tryMoveToXYZ(viewerPos.getX(), viewerPos.getY(), viewerPos.getZ(), .7d);
            } else {
                caveman.getLookController().setLookPosition(caveman.rivalBoss.getPosX(), caveman.getPosYEye(), caveman.rivalBoss.getPosZ());
                caveman.getNavigator().tryMoveToXYZ(viewerPos.getX(), viewerPos.getY(), viewerPos.getZ(), .6d);
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        System.out.println(caveman.getPositionVec().squareDistanceTo(Vector3d.copy(viewerPos)));
        if (caveman.getNavigator().getPath() == null || caveman.getPositionVec().squareDistanceTo(Vector3d.copy(viewerPos)) < 1d) {
            caveman.takingPlaceInBossfight = false;
            caveman.tookPlaceInBossfight = true;
            return false;
        }
        return caveman.getTribe() != null && caveman.getTribe().isBossFight() && caveman.getTribe().countBosses() > 1;
    }

    @Override
    public void resetTask() {
        this.caveman.getNavigator().clearPath();
    }
}
