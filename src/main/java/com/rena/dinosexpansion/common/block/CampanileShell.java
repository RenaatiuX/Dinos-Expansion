package com.rena.dinosexpansion.common.block;

import com.rena.dinosexpansion.common.tileentity.CampanileShellTileEntity;
import com.rena.dinosexpansion.common.util.WorldUtils;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CampanileShell extends Block {
    public CampanileShell() {
        super(AbstractBlock.Properties.create(Material.SHULKER).hardnessAndResistance(5f).harvestLevel(1).notSolid());
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote && handIn == Hand.MAIN_HAND){
            CampanileShellTileEntity te = WorldUtils.getTileEntity(CampanileShellTileEntity.class, worldIn, pos);
            if (te != null){
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
}
