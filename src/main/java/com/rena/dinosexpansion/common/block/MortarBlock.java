package com.rena.dinosexpansion.common.block;

import com.rena.dinosexpansion.common.tileentity.MortarTileEntity;
import com.rena.dinosexpansion.common.util.WorldUtils;
import com.rena.dinosexpansion.core.init.BlockEntityInit;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MortarBlock extends Block {
    public MortarBlock() {
        super(AbstractBlock.Properties.from(Blocks.STONE).notSolid());
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote){
            MortarTileEntity te = WorldUtils.getTileEntity(MortarTileEntity.class, worldIn, pos);
            if (te != null){
                NetworkHooks.openGui((ServerPlayerEntity) player, te, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(!worldIn.isRemote && !state.matchesBlock(newState.getBlock())) {
            TileEntity entity = worldIn.getTileEntity(pos);
            if(entity instanceof MortarTileEntity) {
                MortarTileEntity tileEntity = (MortarTileEntity) entity;
                InventoryHelper.dropInventoryItems(worldIn, pos, tileEntity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.POWERED);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return BlockEntityInit.MORTAR.get().create();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}
