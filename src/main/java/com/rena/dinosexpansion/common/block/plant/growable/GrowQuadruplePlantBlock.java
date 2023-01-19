package com.rena.dinosexpansion.common.block.plant.growable;

import com.rena.dinosexpansion.common.block.plant.QuadruplePlantBlock;
import com.rena.dinosexpansion.common.block.plant.TriplePlantBlock;
import com.rena.dinosexpansion.core.init.BlockInit;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class GrowQuadruplePlantBlock extends BushBlock implements IGrowable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public GrowQuadruplePlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        QuadruplePlantBlock quadruplePlantBlock = (QuadruplePlantBlock) BlockInit.PROTOTAXITES_4.get();
        TriplePlantBlock triplePlantBlock = (TriplePlantBlock) BlockInit.PROTOTAXITES_3.get();
        DoublePlantBlock doublePlantBlock = (DoublePlantBlock) BlockInit.PROTOTAXITES_2.get();
        if (this == BlockInit.PROTOTAXITES.get()) {
            if (doublePlantBlock.getDefaultState().isValidPosition(worldIn, pos) && worldIn.isAirBlock(pos.up())) {
                doublePlantBlock.placeAt(worldIn, pos, 2);
            }
        } else if (this == BlockInit.PROTOTAXITES_2.get()) {
            if (triplePlantBlock.getDefaultState().isValidPosition(worldIn, pos) && worldIn.isAirBlock(pos.up())) {
                triplePlantBlock.placeAt(worldIn, pos);
            }
        } else if (this == BlockInit.PROTOTAXITES_3.get()) {
            if (quadruplePlantBlock.getDefaultState().isValidPosition(worldIn, pos) && worldIn.isAirBlock(pos.up())) {
                quadruplePlantBlock.placeAt(worldIn, pos);
            }
        }
    }
}
