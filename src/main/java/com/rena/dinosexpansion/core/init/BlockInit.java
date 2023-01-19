package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.block.BaseSaplingBlock;
import com.rena.dinosexpansion.common.block.GeyserBlock;
import com.rena.dinosexpansion.common.block.plant.QuadruplePlantBlock;
import com.rena.dinosexpansion.common.block.plant.SinglePlantBlock;
import com.rena.dinosexpansion.common.block.plant.TriplePlantBlock;
import com.rena.dinosexpansion.common.block.plant.growable.GrowDoublePlantBlock;
import com.rena.dinosexpansion.common.block.plant.growable.GrowQuadruplePlantBlock;
import com.rena.dinosexpansion.common.block.plant.growable.GrowTriplePlantBlock;
import com.rena.dinosexpansion.common.world.gen.trees.ModTreeSpawners;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.OakTree;
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

    //Plants
    public static final RegistryObject<Block> LAVENDER = register("lavender",
            ()-> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> LEMON_VERBENA = register("lemon_verbena",
            ()-> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> ARCHAEOSIGILLARIA = register("archaeosigillaria",
            ()-> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> CEPHALOTAXUS = register("cephalotaxus",
            ()-> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> DILLHOFFIA = register("dillhoffia",
            ()-> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> EPHEDRA = register("ephedra",
            ()-> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> OSMUNDA = register("osmunda",
            ()-> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> SARRACENIA = register("sarracenia",
            ()-> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> VACCINIUM = register("vaccinium",
            ()-> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> ZAMITES = register("zamites",
            ()-> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> WELWITSCHIA = register("welwitschia",
            ()-> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> PACHYPODA = register("pachypoda",
            ()-> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);

    //Double Tall Flower
    public static final RegistryObject<Block> HORSETAIL = register("horsetail",
            ()-> new DoublePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> FOOZIA = register("foozia",
            ()-> new DoublePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> DUISBERGIA = register("duisbergia",
            ()-> new DoublePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> BENNETTITALES = register("bennettitales",
            ()-> new DoublePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> CRATAEGUS = register("crataegus",
            ()-> new DoublePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> FLORISSANTIA = register("florissantia",
            ()-> new DoublePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> AMORPHOPHALLUS = register("amorphophallus",
            ()-> new DoublePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);

    //Triple Tall Flower
    public static final RegistryObject<Block> TEMPSKYA = register("tempskya",
            ()-> new TriplePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);

    //Quadruple Tall Flower
    public static final RegistryObject<Block> PROTOTAXITES = register("prototaxites_1",
            ()-> new GrowQuadruplePlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)),  ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> PROTOTAXITES_2 = register("prototaxites_2",
            ()-> new GrowQuadruplePlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)),  ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> PROTOTAXITES_3 = register("prototaxites_3",
            ()-> new GrowQuadruplePlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)),  ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> PROTOTAXITES_4 = register("prototaxites_4",
            ()-> new GrowQuadruplePlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)),  ModItemGroups.BLOCKS);

    //Tree
    public static final RegistryObject<Block> REDWOOD_LEAVES = register("redwood_leaves",
            ()-> new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()
                    .setBlocksVision((state, world, pos) -> false).setSuffocates((state, world, pos) -> false).harvestTool(ToolType.HOE)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> REDWOOD_LOG = register("redwood_log",
            ()-> new RotatedPillarBlock((AbstractBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F))), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> REDWOOD_SAPLING = register("redwood_sapling",
            ()-> new BaseSaplingBlock(ModTreeSpawners.REDWOOD, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);

    //Blocks
    public static final RegistryObject<Block> GEYSER = register("geyser",
            ()-> new GeyserBlock(AbstractBlock.Properties.create(Material.ROCK).zeroHardnessAndResistance().sound(SoundType.GROUND).tickRandomly().notSolid()), ModItemGroups.BLOCKS);
    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, ItemGroup tab){
        return register(name, blockSupplier, () -> new Item.Properties().group(tab));
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Supplier<Item.Properties> properties){
        return register(name, blockSupplier, b -> new BlockItem(b, properties.get()));
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Function<Block, Item> blockItemFunction){
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ItemInit.ITEMS.register(name, () -> blockItemFunction.apply(block.get()));
        return block;
    }
}
