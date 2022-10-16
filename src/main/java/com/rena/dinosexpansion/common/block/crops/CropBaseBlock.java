package com.rena.dinosexpansion.common.block.crops;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import java.util.function.Supplier;

public class CropBaseBlock extends CropsBlock {

    private final Supplier<Item> seedItemSupplier;

    public CropBaseBlock(Properties builder, Supplier<Item> seedItemSupplier) {
        super(builder);
        this.seedItemSupplier = seedItemSupplier;
    }

    @Override
    public BlockState withAge(int age) {
        return this.getDefaultState().with(this.getAgeProperty(), age);
    }

    @Override
    public IItemProvider getSeedsItem() {
        return seedItemSupplier.get();
    }
}
