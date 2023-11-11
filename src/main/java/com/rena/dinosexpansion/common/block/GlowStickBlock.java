package com.rena.dinosexpansion.common.block;

import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class GlowStickBlock extends FallingBlock {

    public static final VoxelShape SHAPE = Block.makeCuboidShape(7.499999999999998, 0, 4, 8.499999999999998, 1, 11);
    public static final VoxelShape SHAPE_FLIPPED = Block.makeCuboidShape(4, 0, 7.499999999999998, 11, 1, 8.499999999999998);
    public static final BooleanProperty FLIPPED = BooleanProperty.create("flipped");
    public GlowStickBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FLIPPED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(FLIPPED) ? SHAPE_FLIPPED : SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.get(FLIPPED) ? SHAPE_FLIPPED : SHAPE;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.get(FLIPPED) ? SHAPE_FLIPPED : SHAPE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FLIPPED);
    }

    @Override
    public Item asItem() {
        return ItemInit.GLOW_STICK.get();
    }
}
