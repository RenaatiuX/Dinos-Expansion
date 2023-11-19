package com.rena.dinosexpansion.common.block;

import com.rena.dinosexpansion.common.tileentity.UnknownLockTileEntity;
import com.rena.dinosexpansion.common.util.WorldUtils;
import com.rena.dinosexpansion.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnknownLockBlock extends Block {
    public UnknownLockBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new UnknownLockTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(handIn);
        if (stack.getItem() == Items.STICK) {
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            if (!worldIn.isRemote)
                deleteNearbyBlock(worldIn, pos, 32);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }


    private void deleteNearbyBlock(World worldIn, BlockPos start, int maxDepth) {
        int ticksBeforeDestruction = 5;
        int ticksDelay = 5;
        List<BlockPos> finishedPos = new ArrayList<>();
        ArrayDeque<BlockPos> currentPoses = new ArrayDeque<>();
        currentPoses.add(start);

        //this is BFS with a maxDepth if u dont know what BFS is google it
        while (finishedPos.size() < maxDepth && !currentPoses.isEmpty()) {
            BlockPos current = currentPoses.pop();
            UnknownLockTileEntity te = WorldUtils.getTileEntity(UnknownLockTileEntity.class, worldIn, current);
            for (Direction dir : Direction.values()) {
                BlockPos offset = current.offset(dir);
                if (!finishedPos.contains(offset) && !currentPoses.contains(offset)) {
                    UnknownLockTileEntity offsetTe = WorldUtils.getTileEntity(UnknownLockTileEntity.class, worldIn, offset);
                    if (offsetTe != null) {
                        currentPoses.add(offset);
                    }
                }
            }
            te.setShouldDestroy(ticksBeforeDestruction);
            ticksBeforeDestruction += ticksDelay;
            finishedPos.add(current);
        }

    }
}
