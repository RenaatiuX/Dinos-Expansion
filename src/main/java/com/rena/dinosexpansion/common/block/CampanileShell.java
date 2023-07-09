package com.rena.dinosexpansion.common.block;

import com.rena.dinosexpansion.common.tileentity.CampanileShellTileEntity;
import com.rena.dinosexpansion.common.util.WorldUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class CampanileShell extends HorizontalBlock {
    protected static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(7, 17, 9.500000000000002, 9, 22.000000000000004, 11.500000000000002),
            Block.makeCuboidShape(6, 9, 6.500000000000002, 10, 17.000000000000004, 11.500000000000002),
            Block.makeCuboidShape(4.5, -8.881784197001252e-16, 4.500000000000002, 11.5, 9.000000000000004, 11.500000000000002)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    protected static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(9.500000000000002, 17, 7, 11.500000000000002, 22.000000000000004, 9),
            Block.makeCuboidShape(6.500000000000002, 9, 6, 11.500000000000002, 17.000000000000004, 10),
            Block.makeCuboidShape(4.500000000000002, -8.881784197001252e-16, 4.5, 11.500000000000002, 9.000000000000004, 11.5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    protected static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(7, 17, 4.499999999999998, 9, 22.000000000000004, 6.499999999999998),
            Block.makeCuboidShape(6, 9, 4.499999999999998, 10, 17.000000000000004, 9.499999999999998),
            Block.makeCuboidShape(4.5, -8.881784197001252e-16, 4.499999999999998, 11.5, 9.000000000000004, 11.499999999999998)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    protected static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(4.499999999999998, 17, 7, 6.499999999999998, 22.000000000000004, 9),
            Block.makeCuboidShape(4.499999999999998, 9, 6, 9.499999999999998, 17.000000000000004, 10),
            Block.makeCuboidShape(4.499999999999998, -8.881784197001252e-16, 4.5, 11.499999999999998, 9.000000000000004, 11.5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();


    public CampanileShell() {
        super(AbstractBlock.Properties.create(Material.SHULKER).hardnessAndResistance(5f).harvestLevel(1).notSolid());
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
            CampanileShellTileEntity te = WorldUtils.getTileEntity(CampanileShellTileEntity.class, worldIn, pos);
            if (te != null) {
                te.setActivated(!te.isActivated());
                return ActionResultType.SUCCESS;
            }

        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CampanileShellTileEntity();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(HORIZONTAL_FACING)) {
            case NORTH:
                return SHAPE_N;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            case EAST:
                return SHAPE_E;
            default:
                return SHAPE_N;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

}
