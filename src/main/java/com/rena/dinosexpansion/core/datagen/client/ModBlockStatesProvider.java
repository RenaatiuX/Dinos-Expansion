package com.rena.dinosexpansion.core.datagen.client;

import com.mojang.datafixers.types.Func;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlockStatesProvider extends BlockStateProvider {

    public final ModelFile generated = itemModels().getExistingFile(mcLoc("item/generated"));
    public final ModelFile spawnEgg = itemModels().getExistingFile(mcLoc("item/template_spawn_egg"));

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
        doublePlant(BlockInit.AMORPHOPHALLUS);
        doublePlant(BlockInit.BENNETTITALES);
        simplePlant(BlockInit.ARCHAEOSIGILLARIA);
        simplePlant(BlockInit.CEPHALOTAXUS);
        simplePlant(BlockInit.COOKSONIA);
        //agingDoublePlant(BlockInit.CORN_CROP.get(), BlockStateProperties.AGE_0_7, 5);
        agingPlant(BlockInit.SPINACH_CROP.get(), BlockStateProperties.AGE_0_7, age -> {
            if (age < 2)
                return 0;
            if (age < 4)
                return 1;
            if (age < 6)
                return 2;
            if (age < 7)
                return 3;
            return 4;
        });
        doublePlant(BlockInit.CRATAEGUS.get());
        simpleDoorBlock(BlockInit.CRATAEGUS_DOOR);
    }

    protected void slabBlock(Supplier<Block> block, Supplier<Block> textureFrom){
        ResourceLocation texture = blockTexture(textureFrom.get());
        slabBlock(block.get(), models().getExistingFile(textureFrom.get().getRegistryName()), texture, texture, texture);
    }

    protected void simpleDoorBlock(Supplier<DoorBlock> block){
        simpleDoorBlock(block.get(), true);
    }

    protected void simpleDoorBlock(DoorBlock block){
        simpleDoorBlock(block, true);
    }

    protected void simpleDoorBlock(Supplier<DoorBlock> block, boolean generateItem){
        simpleDoorBlock(block.get(), generateItem);
    }

    protected void simpleDoorBlock(DoorBlock block, boolean generateItem){
        ResourceLocation texture = blockTexture(block);
        doorBlock(block, extend(texture, "_bottom"), extend(texture, "_top"));
        if (generateItem)
            simpleBlockTextureItem(block);
    }

    protected void slabBlock(Block block, ModelFile existingDouble, ResourceLocation side, ResourceLocation bottom, ResourceLocation top){
        getVariantBuilder(block).forAllStates(state -> {
            String name = name(state.getBlock());
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            if (state.get(BlockStateProperties.SLAB_TYPE) == SlabType.TOP) {
                name += "_top";
                builder.modelFile(models().slab(name, side, bottom, top));
                return builder.build();
            }else if (state.get(BlockStateProperties.SLAB_TYPE) == SlabType.BOTTOM){
                ModelFile model = models().slab(name, side, bottom, top);
                builder.modelFile(model);
                simpleBlockItem(state.getBlock(), model);
                return builder.build();
            }
            builder.modelFile(existingDouble);
            return builder.build();
        });

    }

    protected void doublePlant(Supplier<Block> block){
        doublePlant(block.get());
    }

    protected void doublePlant(Block block){
        getVariantBuilder(block).forAllStates(state -> {
            String name = name(state.getBlock());
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            ResourceLocation texture = blockTexture(block);
            if (state.get(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER){
                name += "_top";
                texture = extend(texture, "_top");
                itemModels().getBuilder(name(block)).parent(generated).texture("layer0", texture);
            }else {
                name += "_bottom";
                texture = extend(texture, "_bottom");
            }
            builder.modelFile(models().cross(name, texture));
            return builder.build();
        });
    }

    protected void simplePlant(Supplier<Block> block){
        simplePlant(block.get(), true);
    }

    protected void simplePlant(Block block){
        simplePlant(block, true);
    }

    protected void simplePlant(Supplier<Block> block, boolean generateSimpleItem){
        simplePlant(block.get(), generateSimpleItem);
    }

    protected void simplePlant(Block block, boolean generateSimpleItem){
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(models().cross(name(block), blockTexture(block))));
        if (generateSimpleItem){
            itemModels().getBuilder(name(block)).parent(generated).texture("layer0", blockTexture(block));
        }
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

    protected String name(Block block) {
        return block.getRegistryName().getPath();
    }

    protected ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }

    private void simpleItem(Item... items) {
        for (Item item : items) {
            String name = item.getRegistryName().getPath();
            itemModels().getBuilder(name).parent(generated).texture("layer0", "item/" + name);
        }
    }

    private void simpleItem(IItemProvider... items) {
        for (IItemProvider itemProvider : items) {
            simpleItem(itemProvider.asItem());
        }
    }

    private void simpleBlockTextureItem(Block... blocks) {
        for (Block block : blocks) {
            String name = block.getRegistryName().getPath();
            itemModels().getBuilder(name).parent(generated).texture("layer0", blockTexture(block));
        }
    }

    private void simpleBlockTextureItem(Supplier<Block>... blocks) {
        for (Supplier<Block> itemProvider : blocks) {
            simpleItem(itemProvider.get());
        }
    }

    protected void agingDoublePlant(Block block, IntegerProperty ageProperty, int fromWhichAgeDouble){
        if ( !ageProperty.getAllowedValues().contains(fromWhichAgeDouble)){
            int min = ageProperty.getAllowedValues().stream().min(Comparator.comparing(Function.identity())).get();
            int max = ageProperty.getAllowedValues().stream().max(Comparator.comparing(Function.identity())).get();
            throw new IllegalArgumentException(String.format("the integer property %s ony cal have values between [%s, %s] and not %s", ageProperty.getName(), min, max, fromWhichAgeDouble));
        }
        getVariantBuilder(block).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            int age = state.get(ageProperty);
            String name = name(state.getBlock()) + "_stage" + age;
            if (age >= fromWhichAgeDouble && state.get(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER){
                String tmpName = name + "_top";
                builder.modelFile(models().crop(tmpName, blockTextureLoc(tmpName)));
            }else{
                builder.modelFile(models().crop(name, blockTextureLoc(name)));
            }
            return builder.build();

        });
    }

    protected void agingPlant(Block block, IntegerProperty ageProperty, Function<Integer, Integer> ageFunction){
        getVariantBuilder(block).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            int age = state.get(ageProperty);
            String name = name(state.getBlock()) + "_stage" + ageFunction.apply(age);
            builder.modelFile(models().crop(name, blockTextureLoc(name)));
            return builder.build();

        });
    }

    public ResourceLocation blockTextureLoc(String name){
        return new ResourceLocation(DinosExpansion.MOD_ID, ModelProvider.BLOCK_FOLDER + "/" + name);
    }
}
