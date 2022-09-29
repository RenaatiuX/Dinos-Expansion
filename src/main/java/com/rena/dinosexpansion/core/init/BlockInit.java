package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DinosExpansion.MOD_ID);

    public static final RegistryObject<Block> FUTURISTIC_BLOCK_OFF1 = register("futuristic_block_off_1", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(100f, 2400f).harvestTool(ToolType.PICKAXE).harvestLevel(3).setRequiresTool()), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> FUTURISTIC_BLOCK_OFF2 = register("futuristic_block_off_2", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(100f, 2400f).harvestTool(ToolType.PICKAXE).harvestLevel(3).setRequiresTool()), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> FUTURISTIC_BLOCK_ON1 = register("futuristic_block_on_1", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(100f, 2400f).harvestTool(ToolType.PICKAXE).harvestLevel(3).setRequiresTool().setLightLevel(state -> 11)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> FUTURISTIC_BLOCK_ON2 = register("futuristic_block_on_2", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(100f, 2400f).harvestTool(ToolType.PICKAXE).harvestLevel(3).setRequiresTool().setLightLevel(state -> 10)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> MOSSY_FUTURISTIC_BLOCK1 = register("mossy_futuristic_block_1", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(100f, 2400f).harvestTool(ToolType.PICKAXE).harvestLevel(3).setRequiresTool()), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> MOSSY_FUTURISTIC_BLOCK2 = register("mossy_futuristic_block_2", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(100f, 2400f).harvestTool(ToolType.PICKAXE).harvestLevel(3).setRequiresTool()), ModItemGroups.BLOCKS);



    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, ItemGroup tab){
        return register(name, blockSupplier, () -> new Item.Properties().group(tab));
    }

    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Supplier<Item.Properties> properties){
        return register(name, blockSupplier, b -> new BlockItem(b, properties.get()));
    }

    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Function<Block, Item> blockItemFunction){
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ItemInit.ITEMS.register(name, () -> blockItemFunction.apply(block.get()));
        return block;
    }
}
