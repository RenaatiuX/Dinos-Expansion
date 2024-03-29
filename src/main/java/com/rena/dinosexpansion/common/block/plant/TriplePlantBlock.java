package com.rena.dinosexpansion.common.block.plant;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Random;

public class TriplePlantBlock extends BushBlock {
    public static final EnumProperty<TripleBlockHeight> HEIGHT = EnumProperty.create("height", TripleBlockHeight.class);
    public TriplePlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (state.get(HEIGHT) == TripleBlockHeight.BASE) {
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
        worldIn.setBlockState(pos, this.getDefaultState().with(HEIGHT, TripleBlockHeight.BASE), 2);
        worldIn.setBlockState(pos.up(), this.getDefaultState().with(HEIGHT, TripleBlockHeight.CENTER), 2);
        worldIn.setBlockState(pos.up(2), this.getDefaultState().with(HEIGHT, TripleBlockHeight.CROWN), 2);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.get(HEIGHT) == TripleBlockHeight.BASE) {
            if(!player.isCreative()) {
                worldIn.destroyBlock(pos, true);
                worldIn.destroyBlock(pos.up(), false);
                worldIn.destroyBlock(pos.up(2), false);
            } else {
                worldIn.destroyBlock(pos, false);
                worldIn.destroyBlock(pos.up(), false);
                worldIn.destroyBlock(pos.up(2), false);
            }
        } else if (state.get(HEIGHT) == TripleBlockHeight.CENTER) {
            if(!player.isCreative()) {
                worldIn.destroyBlock(pos.down(), true);
                worldIn.destroyBlock(pos, false);
                worldIn.destroyBlock(pos.up(), false);
            } else {
                worldIn.destroyBlock(pos.down(), false);
                worldIn.destroyBlock(pos, false);
                worldIn.destroyBlock(pos.up(), false);
            }
        } else if (state.get(HEIGHT) == TripleBlockHeight.CROWN) {
            if(!player.isCreative()) {
                worldIn.destroyBlock(pos.down(2), true);
                worldIn.destroyBlock(pos.down(), false);
                worldIn.destroyBlock(pos, false);
            } else {
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
        worldIn.setBlockState(pos, this.getDefaultState().with(HEIGHT, TripleBlockHeight.BASE), 0);
        worldIn.setBlockState(pos.up(1), this.getDefaultState().with(HEIGHT, TripleBlockHeight.CENTER), 1);
        worldIn.setBlockState(pos.up(2), this.getDefaultState().with(HEIGHT, TripleBlockHeight.CROWN), 2);
    }

    public enum TripleBlockHeight implements IStringSerializable{
        BASE,
        CENTER,
        CROWN;

        @Override
        public String getString() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
