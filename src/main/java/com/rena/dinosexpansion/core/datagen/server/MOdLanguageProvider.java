package com.rena.dinosexpansion.core.datagen.server;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.rena.dinosexpansion.core.init.EnchantmentInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class MOdLanguageProvider extends LanguageProvider {
    public MOdLanguageProvider(DataGenerator gen) {
        super(gen, DinosExpansion.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(BlockInit.FUTURISTIC_BLOCK_OFF1.get(), "Unknown Block");
        add(BlockInit.FUTURISTIC_BLOCK_OFF2.get(), "Unknown Block");
        add(BlockInit.FUTURISTIC_BLOCK_ON1.get(), "Unknown Block");
        add(BlockInit.FUTURISTIC_BLOCK_ON2.get(), "Unknown Block");
        add(BlockInit.MOSSY_FUTURISTIC_BLOCK1.get(), "Unknown Block");
        add(BlockInit.MOSSY_FUTURISTIC_BLOCK2.get(), "Unknown Block");
        add(ItemInit.COMPOUND_ARROW.get(), "Compound Arrow");
        add(ItemInit.COMPOUND_BOW.get(), "Compound Bow");
        add(ItemInit.TRANQUILLIZER_ARROW.get(), "Tranquillizer Arrow");
        add(EnchantmentInit.ICE_ENCHANTMENT.get(), "Iced");
        add(EnchantmentInit.ELECTRIC_ENCHANTMENT.get(), "Electrified");
    }
}
