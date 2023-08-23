package com.rena.dinosexpansion.core.datagen.client;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, DinosExpansion.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        block(BlockInit.FUTURISTIC_BLOCK_OFF1.get());
        block(BlockInit.FUTURISTIC_BLOCK_OFF2.get());
        block(BlockInit.FUTURISTIC_BLOCK_ON1.get());
        block(BlockInit.FUTURISTIC_BLOCK_ON2.get());
        block(BlockInit.MOSSY_FUTURISTIC_BLOCK1.get());
        block(BlockInit.MOSSY_FUTURISTIC_BLOCK2.get());
        blockWithoutBlockItem(BlockInit.CAMPANILE_SHELL_COMMON.get(), BlockInit.CAMPANILE_SHELL_UNCOMMON.get());

        /*block(BlockInit.LAVENDER.get());
        block(BlockInit.LEMON_VERBENA.get());
        block(BlockInit.ARCHAEOSIGILLARIA.get());
        block(BlockInit.CEPHALOTAXUS.get());
        block(BlockInit.DILLHOFFIA.get());
        block(BlockInit.EPHEDRA.get());
        block(BlockInit.OSMUNDA.get());
        block(BlockInit.SARRACENIA.get());
        block(BlockInit.VACCINIUM.get());
        block(BlockInit.ZAMITES.get());
        block(BlockInit.WELWITSCHIA.get());
        block(BlockInit.PACHYPODA.get());
        block(BlockInit.HORSETAIL.get());
        block(BlockInit.FOOZIA.get());
        block(BlockInit.DUISBERGIA.get());
        block(BlockInit.BENNETTITALES.get());
        block(BlockInit.CRATAEGUS.get());
        block(BlockInit.FLORISSANTIA.get());
        block(BlockInit.AMORPHOPHALLUS.get());
        block(BlockInit.TEMPSKYA.get());
        block(BlockInit.REDWOOD_LEAVES.get());
        block(BlockInit.REDWOOD_LOG.get());
        block(BlockInit.REDWOOD_SAPLING.get());
        block(BlockInit.GEYSER.get());*/
    }

    protected void block(Block... blocks){
        for (Block b : blocks){
            simpleBlock(b);
            simpleBlockItem(b, cubeAll(b));
        }
    }

    protected void blockWithoutBlockItem(Block... blocks){
        for (Block b : blocks){
            simpleBlock(b);
        }
    }
}
