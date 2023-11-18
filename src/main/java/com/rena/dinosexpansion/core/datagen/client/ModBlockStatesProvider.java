package com.rena.dinosexpansion.core.datagen.client;

import com.mojang.datafixers.types.Func;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.block.plant.TriplePlantBlock;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.rena.dinosexpansion.core.init.ModItemGroups;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.EnumProperty;
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
        slabBlock(BlockInit.FUTURISTIC_BLOCK_OFF1_SLAB.get(), BlockInit.FUTURISTIC_BLOCK_OFF1.get());
        stairsBlock(BlockInit.FUTURISTIC_BLOCK_OFF1_STAIRS.get(), BlockInit.FUTURISTIC_BLOCK_OFF1.get());
        fenceBlock(BlockInit.FUTURISTIC_BLOCK_OFF1_FENCE.get(), BlockInit.FUTURISTIC_BLOCK_OFF1.get());
        wallBlock(BlockInit.FUTURISTIC_BLOCK_OFF1_WALL.get(), BlockInit.FUTURISTIC_BLOCK_OFF1.get());

        slabBlock(BlockInit.FUTURISTIC_BLOCK_OFF2_SLAB.get(), BlockInit.FUTURISTIC_BLOCK_OFF2.get());
        stairsBlock(BlockInit.FUTURISTIC_BLOCK_OFF2_STAIRS.get(), BlockInit.FUTURISTIC_BLOCK_OFF2.get());
        fenceBlock(BlockInit.FUTURISTIC_BLOCK_OFF2_FENCE.get(), BlockInit.FUTURISTIC_BLOCK_OFF2.get());
        wallBlock(BlockInit.FUTURISTIC_BLOCK_OFF2_WALL.get(), BlockInit.FUTURISTIC_BLOCK_OFF2.get());

        slabBlock(BlockInit.FUTURISTIC_BLOCK_ON1_SLAB.get(), BlockInit.FUTURISTIC_BLOCK_ON1.get());
        stairsBlock(BlockInit.FUTURISTIC_BLOCK_ON1_STAIRS.get(), BlockInit.FUTURISTIC_BLOCK_ON1.get());
        fenceBlock(BlockInit.FUTURISTIC_BLOCK_ON1_FENCE.get(), BlockInit.FUTURISTIC_BLOCK_ON1.get());
        wallBlock(BlockInit.FUTURISTIC_BLOCK_ON1_WALL.get(), BlockInit.FUTURISTIC_BLOCK_ON1.get());

        slabBlock(BlockInit.FUTURISTIC_BLOCK_ON2_SLAB.get(), BlockInit.FUTURISTIC_BLOCK_ON2.get());
        stairsBlock(BlockInit.FUTURISTIC_BLOCK_ON2_STAIRS.get(), BlockInit.FUTURISTIC_BLOCK_ON2.get());
        fenceBlock(BlockInit.FUTURISTIC_BLOCK_ON2_FENCE.get(), BlockInit.FUTURISTIC_BLOCK_ON2.get());
        wallBlock(BlockInit.FUTURISTIC_BLOCK_ON2_WALL.get(), BlockInit.FUTURISTIC_BLOCK_ON2.get());

        slabBlock(BlockInit.MOSSY_FUTURISTIC_BLOCK1_SLAB.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK1.get());
        stairsBlock(BlockInit.MOSSY_FUTURISTIC_BLOCK1_STAIRS.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK1.get());
        fenceBlock(BlockInit.MOSSY_FUTURISTIC_BLOCK1_FENCE.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK1.get());
        wallBlock(BlockInit.MOSSY_FUTURISTIC_BLOCK1_WALL.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK1.get());

        slabBlock(BlockInit.MOSSY_FUTURISTIC_BLOCK2_SLAB.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK2.get());
        stairsBlock(BlockInit.MOSSY_FUTURISTIC_BLOCK2_STAIRS.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK2.get());
        fenceBlock(BlockInit.MOSSY_FUTURISTIC_BLOCK2_FENCE.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK2.get());
        wallBlock(BlockInit.MOSSY_FUTURISTIC_BLOCK2_WALL.get(), BlockInit.MOSSY_FUTURISTIC_BLOCK2.get());

        //blockWithoutBlockItem(BlockInit.CAMPANILE_SHELL_COMMON.get(), BlockInit.CAMPANILE_SHELL_UNCOMMON.get());
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
        agingDoublePlant(BlockInit.EGGPLANT_CROP.get(), BlockStateProperties.AGE_0_7, 3, age -> {
            if (age < 1)
                return 0;
            if (age < 3)
                return 1;
            if (age < 5)
                return 2;
            if (age < 6)
                return 3;
            return 4;
        });
        doublePlant(BlockInit.CRATAEGUS.get());
        simpleDoorBlock(BlockInit.CRATAEGUS_DOOR, false);
        simpleItem(BlockInit.CRATAEGUS_DOOR.get());
        leaves(BlockInit.CRATAEGUS_LEAVES.get());
        logBlock(BlockInit.CRATAEGUS_LOG.get());
        blockItem(BlockInit.CRATAEGUS_LOG.get());
        block(BlockInit.CRATAEGUS_PLANKS.get());
        block(BlockInit.MUD.get());
        simplePlant(BlockInit.DILLHOFFIA.get());
        doublePlant(BlockInit.DUISBERGIA.get());
        simplePlant(BlockInit.EPHEDRA.get());
        simplePlant(BlockInit.FLORISSANTIA.get());
        //doublePlant(BlockInit.FLORISSANTIA.get());
        doublePlant(BlockInit.FOOZIA.get());
        block(BlockInit.GEYSER.get(), existing(BlockInit.GEYSER.get()));
        simplePlant(BlockInit.HORSETAIL.get());
        simplePlant(BlockInit.LAVENDER.get());
        simplePlant(BlockInit.LEMON_VERBENA.get());
        //horizontalBlock(BlockInit.MORTAR.get(), existing(BlockInit.MORTAR.get()));
        agingPlant(BlockInit.NARCOTIC_BERRY_BUSH.get(), BlockStateProperties.AGE_0_3);
        agingPlant(BlockInit.ORANGE_BERRY_BUSH.get(), BlockStateProperties.AGE_0_3);
        simplePlant(BlockInit.OSMUNDA);
        simplePlant(BlockInit.PACHYPODA);
        triplePlant(BlockInit.PROTOTAXITES.get());
        block(BlockInit.QUICKSAND.get());
        leaves(BlockInit.REDWOOD_LEAVES.get());
        logBlock(BlockInit.REDWOOD_LOG.get());
        blockItem(BlockInit.REDWOOD_LOG.get());
        simplePlant(BlockInit.REDWOOD_SAPLING);
        block(BlockInit.RUBY_BLOCK.get(), BlockInit.RUBY_ORE.get(), BlockInit.SAPPHIRE_BLOCK.get(), BlockInit.SAPPHIRE_ORE.get());
        simplePlant(BlockInit.SARRACENIA.get());
        logBlock(BlockInit.STRIPPED_CRATAEGUS_LOG.get());
        blockItem(BlockInit.STRIPPED_CRATAEGUS_LOG.get());
        triplePlant(BlockInit.TEMPSKYA.get());
        simplePlant(BlockInit.VACCINIUM);
        simplePlant(BlockInit.WELWITSCHIA);
        agingPlant(BlockInit.YELLOW_BERRY_BUSH.get(), BlockStateProperties.AGE_0_3);
        simplePlant(BlockInit.ZAMITES.get());
        makeSignFiles(BlockInit.CRATEAGUS_SIGN.get(), BlockInit.CRATEAGUS_WALL_SIGN.get(), BlockInit.CRATAEGUS_PLANKS.get());


    }

    protected void makeSignFiles(Block standing, Block wall, Block planks) {
        ModelFile file = models().getBuilder(name(standing)).texture("particle", blockTexture(planks));
        this.getVariantBuilder(standing).partialState().setModels(new ConfiguredModel(file));
        file = models().getBuilder(name(wall)).texture("particle", blockTexture(planks));
        this.getVariantBuilder(wall).partialState().setModels(new ConfiguredModel(file));
    }

    protected void slabBlock(Block block, Block textureFrom) {
        ResourceLocation texture = blockTexture(textureFrom);
        slabBlock(block, models().getExistingFile(textureFrom.getRegistryName()), texture, texture, texture);
    }

    protected void simpleDoorBlock(Supplier<DoorBlock> block) {
        simpleDoorBlock(block.get(), true);
    }

    protected void simpleDoorBlock(DoorBlock block) {
        simpleDoorBlock(block, true);
    }

    public void blockItem(Block block) {
        simpleBlockItem(block, existing(block));
    }

    protected void simpleDoorBlock(Supplier<DoorBlock> block, boolean generateItem) {
        simpleDoorBlock(block.get(), generateItem);
    }

    protected void simpleDoorBlock(DoorBlock block, boolean generateItem) {
        ResourceLocation texture = blockTexture(block);
        doorBlock(block, extend(texture, "_bottom"), extend(texture, "_top"));
        if (generateItem)
            simpleBlockTextureItem(block);
    }

    protected void slabBlock(Block block, ModelFile existingDouble, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        getVariantBuilder(block).forAllStates(state -> {
            String name = name(state.getBlock());
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            if (state.get(BlockStateProperties.SLAB_TYPE) == SlabType.TOP) {
                name += "_top";
                builder.modelFile(models().slabTop(name, side, bottom, top));
                return builder.build();
            } else if (state.get(BlockStateProperties.SLAB_TYPE) == SlabType.BOTTOM) {
                ModelFile model = models().slab(name, side, bottom, top);
                builder.modelFile(model);
                simpleBlockItem(state.getBlock(), model);
                return builder.build();
            }
            builder.modelFile(existingDouble);
            return builder.build();
        });

    }

   public void stairsBlock(StairsBlock stair, Block textureFrom){
        stairsBlock(stair, blockTexture(textureFrom));
        simpleBlockItem(stair, existing(stair));
   }

    protected void leaves(Block block) {
        String name = name(block);
        ModelFile leaves = models().withExistingParent(name, mcLoc("block/leaves")).texture("all", blockTexture(block));
        simpleBlock(block, leaves);
        simpleBlockItem(block, leaves);
    }

    protected void doublePlant(Supplier<Block> block) {
        doublePlant(block.get());
    }

    /**
     * this will also generate an item model, basing of the upper texture of the plant
     */
    protected void doublePlant(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            String name = name(state.getBlock());
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            ResourceLocation texture = blockTexture(block);
            if (state.get(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
                name += "_top";
                texture = extend(texture, "_top");
                itemModels().getBuilder(name(block)).parent(generated).texture("layer0", texture);
            } else {
                name += "_bottom";
                texture = extend(texture, "_bottom");
            }
            builder.modelFile(models().cross(name, texture));
            return builder.build();
        });
    }

    protected void simplePlant(Supplier<Block> block) {
        simplePlant(block.get(), true);
    }

    protected void simplePlant(Block block) {
        simplePlant(block, true);
    }

    protected void simplePlant(Supplier<Block> block, boolean generateSimpleItem) {
        simplePlant(block.get(), generateSimpleItem);
    }

    protected void simplePlant(Block block, boolean generateSimpleItem) {
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(models().cross(name(block), blockTexture(block))));
        if (generateSimpleItem) {
            itemModels().getBuilder(name(block)).parent(generated).texture("layer0", blockTexture(block));
        }
    }

    protected void block(Block block, ModelFile model) {
        simpleBlock(block, model);
        simpleBlockItem(block, model);
    }

    protected void block(Block... blocks) {
        for (Block b : blocks) {
            simpleBlock(b);
            simpleBlockItem(b, cubeAll(b));
        }
    }

    protected void blockWithoutBlockItem(Block... blocks) {
        for (Block b : blocks) {
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

    protected void triplePlant(Block block) {
        triplePlant(block, true);
    }

    protected void triplePlant(Block block, boolean generateBlockItem) {
        getVariantBuilder(block).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            TriplePlantBlock.TripleBlockHeight height = state.get(TriplePlantBlock.HEIGHT);
            String name = name(state.getBlock());
            name += "_" + height.getString();
            ModelFile files = models().cross(name, blockTextureLoc(name));
            builder.modelFile(files);
            if (height == TriplePlantBlock.TripleBlockHeight.CROWN && generateBlockItem) {
                itemModels().getBuilder(name(state.getBlock())).parent(generated).texture("layer0", blockTextureLoc(name));
            }

            return builder.build();

        });
    }


    protected void agingDoublePlant(Block block, IntegerProperty ageProperty, int fromWhichAgeDouble) {
        agingDoublePlant(block, ageProperty, fromWhichAgeDouble, Function.identity());
    }

    protected void agingDoublePlant(Block block, IntegerProperty ageProperty, int fromWhichAgeDouble, Function<Integer, Integer> ageFunction) {
        if (!ageProperty.getAllowedValues().contains(fromWhichAgeDouble)) {
            int min = ageProperty.getAllowedValues().stream().min(Comparator.comparing(Function.identity())).get();
            int max = ageProperty.getAllowedValues().stream().max(Comparator.comparing(Function.identity())).get();
            throw new IllegalArgumentException(String.format("the integer property %s ony cal have values between [%s, %s] and not %s", ageProperty.getName(), min, max, fromWhichAgeDouble));
        }
        getVariantBuilder(block).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            int age = state.get(ageProperty);
            String name = name(state.getBlock()) + "_stage" + ageFunction.apply(age);
            if (ageFunction.apply(age) >= fromWhichAgeDouble && state.get(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
                String tmpName = name + "_top";
                builder.modelFile(models().crop(tmpName, blockTextureLoc(tmpName)));
            } else {
                builder.modelFile(models().crop(name, blockTextureLoc(name)));
            }
            return builder.build();

        });
    }

    protected void agingPlant(Block block, IntegerProperty ageProperty) {
        agingPlant(block, ageProperty, Function.identity());
    }

    protected void agingPlant(Block block, IntegerProperty ageProperty, Function<Integer, Integer> ageFunction) {
        getVariantBuilder(block).forAllStates(state -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder();
            int age = state.get(ageProperty);
            String name = name(state.getBlock()) + "_stage" + ageFunction.apply(age);
            builder.modelFile(models().crop(name, blockTextureLoc(name)));
            return builder.build();

        });
    }

    protected void fenceBlock(FenceBlock fence, Block textureFrom){
        fenceBlock(fence, blockTexture(textureFrom));
        simpleBlockItem(fence, models().fenceInventory(name(fence), blockTexture(textureFrom)));
    }

    protected void wallBlock(WallBlock fence, Block textureFrom){
        wallBlock(fence, blockTexture(textureFrom));
        simpleBlockItem(fence, models().wallInventory(name(fence), blockTexture(textureFrom)));
    }

    public ResourceLocation blockTextureLoc(String name) {
        return new ResourceLocation(DinosExpansion.MOD_ID, ModelProvider.BLOCK_FOLDER + "/" + name);
    }

    public ModelFile existing(Block block) {
        return models().getExistingFile(block.getRegistryName());
    }
}
