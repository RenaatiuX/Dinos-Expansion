package com.rena.dinosexpansion.core.datagen.server;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.BlockInit;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator p_i244820_1_, @Nullable ExistingFileHelper p_i244820_3_) {
        super(p_i244820_1_, DinosExpansion.MOD_ID, p_i244820_3_);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(BlockTags.FENCES).add(BlockInit.FUTURISTIC_BLOCK_OFF1_FENCE.get(), BlockInit.FUTURISTIC_BLOCK_OFF2_FENCE.get(), BlockInit.FUTURISTIC_BLOCK_ON1_FENCE.get(), BlockInit.FUTURISTIC_BLOCK_ON2_FENCE.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK1_FENCE.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK2_FENCE.get());
        getOrCreateBuilder(BlockTags.WALLS).add(BlockInit.FUTURISTIC_BLOCK_OFF1_WALL.get(), BlockInit.FUTURISTIC_BLOCK_OFF2_WALL.get(), BlockInit.FUTURISTIC_BLOCK_ON1_WALL.get(), BlockInit.FUTURISTIC_BLOCK_ON2_WALL.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK1_WALL.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK2_WALL.get());
    }
}
