package com.rena.dinosexpansion.common.block.crops;

import net.minecraft.block.CropsBlock;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;

import java.util.function.Supplier;

public class CropBaseBlock extends CropsBlock {


    private final Supplier<Item> seedItemSupplier;

    public CropBaseBlock(Properties builder, Supplier<Item> seedItemSupplier) {
        super(builder);
        this.seedItemSupplier = seedItemSupplier;
    }

    @Override
    public IItemProvider getSeedsItem() {
        return seedItemSupplier.get();
    }
}
