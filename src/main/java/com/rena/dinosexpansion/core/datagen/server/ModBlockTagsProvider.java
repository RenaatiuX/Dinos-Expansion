package com.rena.dinosexpansion.core.datagen.server;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator p_i244820_1_, @Nullable ExistingFileHelper p_i244820_3_) {
        super(p_i244820_1_, DinosExpansion.MOD_ID, p_i244820_3_);
    }

    @Override
    protected void registerTags() {
    }
}
