package com.rena.dinosexpansion.common.entity.villagers.caveman;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.system.CallbackI;

public class StartBossfightGoal extends Goal {

    protected final Caveman caveman;
    protected final double radius;
    protected int pathfindCooldown = 0;
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
        pathfindCooldown = 0;
        BlockPos center = caveman.getTribe().getBossFightCenter();
        if (caveman.isFighterBossFight()) {
            if (!caveman.getRivalBoss().takingPlaceInBossfight) {
                this.viewerPos = new BlockPos(center.getX() + radius - 2d, 250, center.getZ());
            } else {
                this.viewerPos = new BlockPos(center.getX() - radius + 2d, 250, center.getZ());
            }
            while (caveman.world.isAirBlock(viewerPos)){
                viewerPos = viewerPos.down();
            }
            viewerPos = viewerPos.up();
            caveman.getNavigator().tryMoveToXYZ(viewerPos.getX(), viewerPos.getY(), viewerPos.getZ(), .6d);
        } else {
            double around = (2d * Math.PI) / (double) (caveman.getTribe().getSize() - caveman.getTribe().countBosses());
            double getCavemanPosition = caveman.getTribe().getBossfightCounterCircle();
            double x = center.getX() + Math.cos(around * getCavemanPosition) * radius;
            double z = center.getZ() + Math.sin(around * getCavemanPosition) * radius;
            this.viewerPos = new BlockPos(x, caveman.getPosY() + 3, z);
            while (caveman.world.isAirBlock(viewerPos)){
                viewerPos = viewerPos.down();
            }
            viewerPos = viewerPos.up();
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
                if (!caveman.getNavigator().tryMoveToXYZ(viewerPos.getX(), viewerPos.getY(), viewerPos.getZ(), .7d))
                    pathfindCooldown++;
            } else {
                caveman.getLookController().setLookPosition(caveman.rivalBoss.getPosX(), caveman.getPosYEye(), caveman.rivalBoss.getPosZ());
                if (!caveman.getNavigator().tryMoveToXYZ(viewerPos.getX(), viewerPos.getY(), viewerPos.getZ(), .6d))
                    pathfindCooldown++;
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (pathfindCooldown >= 100 || caveman.getPositionVec().squareDistanceTo(Vector3d.copy(viewerPos)) <= 1.5d) {
            caveman.takingPlaceInBossfight = false;
            caveman.tookPlaceInBossfight = true;
            return false;
        }
        return caveman.getTribe() != null && caveman.getTribe().isBossFight() && caveman.getTribe().countBosses() > 1;
    }

    @Override
    public void resetTask() {
        this.caveman.getNavigator().clearPath();
        pathfindCooldown = 0;
    }
}
