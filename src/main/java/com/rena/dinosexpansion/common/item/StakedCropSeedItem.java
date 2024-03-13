package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.common.block.crops.StakedGrowCropBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;

import java.util.function.Supplier;

public class StakedCropSeedItem extends Item {

    private Supplier<StakedGrowCropBlock> cropBlock;

    public StakedCropSeedItem(Properties pProperties, Supplier<StakedGrowCropBlock> cropBlock) {
        super(pProperties);
        this.cropBlock = cropBlock;
    }

    public BlockState getCropState() {
        return cropBlock.get().getDefaultState();
    }

    public StakedGrowCropBlock getCrop() {
        return cropBlock.get();
    }
}
