package com.rena.dinosexpansion.common.block;

import com.rena.dinosexpansion.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class UnknownLockBlock extends Block {
    public UnknownLockBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(handIn);
        if (stack.getItem() == Items.STICK) {
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            deleteNearbyBlock(worldIn, pos, pos);
        }
        return ActionResultType.SUCCESS;
    }

    private void deleteNearbyBlock(World worldIn, BlockPos pos, BlockPos startPos) {
        if (pos.distanceSq(startPos) < 32) {
            if (worldIn.getBlockState(pos).getBlock() == BlockInit.FUTURISTIC_LOCK_BLOCK.get() || worldIn.getBlockState(pos).getBlock() == BlockInit.FUTURISTIC_BLOCK_OFF1.get()) {
                worldIn.destroyBlock(pos, false);
                for (Direction facing : Direction.values()) {
                    deleteNearbyBlock(worldIn, pos.offset(facing), startPos);
                }
            }
        }
    }
}
