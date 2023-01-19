package com.rena.dinosexpansion.common.block.plant;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class QuadruplePlantBlock extends BushBlock {
    public static final IntegerProperty HEIGHT = IntegerProperty.create("height", 0, 3);
    public QuadruplePlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (state.get(HEIGHT) == 0) {
            return super.isValidPosition(state, worldIn, pos);
        } else {
            BlockState blockstate = worldIn.getBlockState(pos.down());
            if (state.getBlock() != this) return super.isValidGround(state, worldIn, pos);
            return blockstate.getBlock() == this;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        return blockpos.getY() < context.getWorld().getDimensionType().getLogicalHeight() - 1 && context.getWorld().getBlockState(blockpos.up()).isReplaceable(context) && context.getWorld().getBlockState(blockpos.up(2)).isReplaceable(context) ? super.getStateForPlacement(context) : null;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos, this.getDefaultState().with(HEIGHT, 0), 3);
        worldIn.setBlockState(pos.up(), this.getDefaultState().with(HEIGHT, 1), 2);
        worldIn.setBlockState(pos.up(2), this.getDefaultState().with(HEIGHT, 2), 3);
        worldIn.setBlockState(pos.up(3), this.getDefaultState().with(HEIGHT, 3), 3);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.get(HEIGHT) == 0) {
            if(!player.isCreative()) {
                worldIn.destroyBlock(pos, true);
                worldIn.destroyBlock(pos.up(), false);
                worldIn.destroyBlock(pos.up(2), false);
                worldIn.destroyBlock(pos.up(3), false);
            } else {
                worldIn.destroyBlock(pos, false);
                worldIn.destroyBlock(pos.up(), false);
                worldIn.destroyBlock(pos.up(2), false);
                worldIn.destroyBlock(pos.up(3), false);
            }
        } else if (state.get(HEIGHT) == 1) {
            if(!player.isCreative()) {
                worldIn.destroyBlock(pos.down(), true);
                worldIn.destroyBlock(pos, false);
                worldIn.destroyBlock(pos.up(), false);
                worldIn.destroyBlock(pos.up(2), false);
            } else {
                worldIn.destroyBlock(pos.down(), false);
                worldIn.destroyBlock(pos, false);
                worldIn.destroyBlock(pos.up(), false);
                worldIn.destroyBlock(pos.up(2), false);
            }
        } else if (state.get(HEIGHT) == 2) {
            if(!player.isCreative()) {
                worldIn.destroyBlock(pos.down(2), true);
                worldIn.destroyBlock(pos.down(), false);
                worldIn.destroyBlock(pos, false);
                worldIn.destroyBlock(pos.up(), false);
            } else {
                worldIn.destroyBlock(pos.down(2), false);
                worldIn.destroyBlock(pos.down(), false);
                worldIn.destroyBlock(pos, false);
                worldIn.destroyBlock(pos.up(), false);
            }
        }else if (state.get(HEIGHT) == 3) {
            if(!player.isCreative()) {
                worldIn.destroyBlock(pos.down(3), true);
                worldIn.destroyBlock(pos.down(2), false);
                worldIn.destroyBlock(pos.down(), false);
                worldIn.destroyBlock(pos, false);
            } else {
                worldIn.destroyBlock(pos.down(3), false);
                worldIn.destroyBlock(pos.down(2), false);
                worldIn.destroyBlock(pos.down(), false);
                worldIn.destroyBlock(pos, false);
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }


    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HEIGHT);
    }

    public void placeAt(IWorld worldIn, BlockPos pos) {
        worldIn.setBlockState(pos, this.getDefaultState().with(HEIGHT, 0), 0);
        worldIn.setBlockState(pos.up(1), this.getDefaultState().with(HEIGHT, 1), 1);
        worldIn.setBlockState(pos.up(2), this.getDefaultState().with(HEIGHT, 2), 2);
        worldIn.setBlockState(pos.up(3), this.getDefaultState().with(HEIGHT, 3), 3);
    }
}
