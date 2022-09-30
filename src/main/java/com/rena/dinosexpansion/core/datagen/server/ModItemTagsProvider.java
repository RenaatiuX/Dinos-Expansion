package com.rena.dinosexpansion.core.datagen.server;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.ItemInit;
import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator p_i244817_1_, BlockTagsProvider p_i244817_2_, @Nullable ExistingFileHelper p_i244817_4_) {
        super(p_i244817_1_, p_i244817_2_, DinosExpansion.MOD_ID, p_i244817_4_);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(ItemTags.ARROWS).add(ItemInit.TRANQUILLIZER_ARROW.get());
        getOrCreateBuilder(ModTags.Items.COMPOUND_ARROWS).add(ItemInit.COMPOUND_ARROW.get());
    }
}
