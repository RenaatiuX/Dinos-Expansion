package com.rena.dinosexpansion.common.block.crops;

import com.rena.dinosexpansion.common.item.StakedCropSeedItem;
import jdk.nashorn.internal.ir.ReturnNode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class StakedCropBlock extends Block {

    protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(
            Block.makeCuboidShape(5.499999999999998, 14, 5.499999999999998, 10.499999999999998, 16, 10.499999999999998),
            Block.makeCuboidShape(6.5, 0, 6.499999999999998, 9.499999999999998, 14, 9.499999999999998), IBooleanFunction.OR);

    public StakedCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && stack.getItem() instanceof StakedCropSeedItem) {
            if (world.getBlockState(pos.down()).getBlock().canSustainPlant(world.getBlockState(pos.down()), world, pos.down(), Direction.UP, ((StakedCropSeedItem) stack.getItem()).getCrop())) {
                world.setBlockState(pos, ((StakedCropSeedItem) stack.getItem()).getCropState(), 3);
                if (!player.abilities.isCreativeMode) {
                    player.getHeldItem(hand).shrink(1);
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.CONSUME;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}
