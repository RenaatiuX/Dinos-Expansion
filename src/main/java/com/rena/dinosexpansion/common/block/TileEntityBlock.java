package com.rena.dinosexpansion.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class TileEntityBlock extends Block {

    private Supplier<TileEntity> teSupplier;
    public TileEntityBlock(Supplier<TileEntity> teSupplier, Properties p_i48440_1_) {
        super(p_i48440_1_);
        this.teSupplier = teSupplier;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return teSupplier != null;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return teSupplier.get();
    }
}
