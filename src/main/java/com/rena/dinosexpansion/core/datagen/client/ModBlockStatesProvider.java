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
    }

    private void block(Block block){
        simpleBlock(block);
        simpleBlockItem(block, cubeAll(block));
    }
}
