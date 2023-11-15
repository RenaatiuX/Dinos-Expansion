package com.rena.dinosexpansion.common.block;

import com.rena.dinosexpansion.common.tileentity.ModSignTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ModStandingSignBlock extends StandingSignBlock {
    public ModStandingSignBlock(Properties p_i225766_1_, WoodType p_i225766_2_) {
        super(p_i225766_1_, p_i225766_2_);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ModSignTileEntity();
    }
}
