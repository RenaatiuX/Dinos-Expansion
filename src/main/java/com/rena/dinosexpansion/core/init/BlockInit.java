package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.renderer.item.MortarItemRenderer;
import com.rena.dinosexpansion.common.block.*;
import com.rena.dinosexpansion.common.block.bush.BushBlockBase;
import com.rena.dinosexpansion.common.block.crops.CropBaseBlock;
import com.rena.dinosexpansion.common.block.crops.DoubleCropBaseBlock;
import com.rena.dinosexpansion.common.block.plant.SinglePlantBlock;
import com.rena.dinosexpansion.common.block.plant.TriplePlantBlock;
import com.rena.dinosexpansion.common.item.util.AnimatedBlockItem;
import com.rena.dinosexpansion.common.world.gen.trees.ModTreeSpawners;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import software.bernie.shadowed.eliotlash.mclib.math.functions.classic.Mod;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DinosExpansion.MOD_ID);

    public static final RegistryObject<Block> FUTURISTIC_BLOCK_OFF1 = register("futuristic_block_off_1",
            () -> new OnlyFromSapphireBreakable(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(100f, 3600000.0F)
                    .harvestTool(ToolType.PICKAXE).harvestLevel(5).setRequiresTool()), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> FUTURISTIC_BLOCK_OFF2 = register("futuristic_block_off_2",
            () -> new OnlyFromSapphireBreakable(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(100f, 3600000.0F)
                    .harvestTool(ToolType.PICKAXE).harvestLevel(5).setRequiresTool()), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> FUTURISTIC_BLOCK_ON1 = register("futuristic_block_on_1",
            () -> new OnlyFromSapphireBreakable(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(30f, 3600000.0F)
                    .harvestTool(ToolType.PICKAXE).harvestLevel(5).setRequiresTool().setLightLevel(state -> 11)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> FUTURISTIC_BLOCK_ON2 = register("futuristic_block_on_2",
            () -> new OnlyFromSapphireBreakable(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(30f, 3600000.0F)
                    .harvestTool(ToolType.PICKAXE).harvestLevel(5).setRequiresTool().setLightLevel(state -> 10)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> MOSSY_FUTURISTIC_BLOCK1 = register("mossy_futuristic_block_1",
            () -> new OnlyFromSapphireBreakable(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(30f, 3600000.0F)
                    .harvestTool(ToolType.PICKAXE).harvestLevel(5).setRequiresTool()), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> MOSSY_FUTURISTIC_BLOCK2 = register("mossy_futuristic_block_2",
            () -> new OnlyFromSapphireBreakable(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(30f, 3600000.0F)
                    .harvestTool(ToolType.PICKAXE).harvestLevel(5).setRequiresTool()), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> FUTURISTIC_LOCK_BLOCK = register("futuristic_lock_block",
            () -> new UnknownLockBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 1000000F)), ModItemGroups.BLOCKS);

    //futuristic slabs
    public static final RegistryObject<SlabBlock> FUTURISTIC_BLOCK_OFF1_SLAB = register("futuristic_block_off_1_slab", () -> new OnlySapphireBreakableSlab(AbstractBlock.Properties.from(FUTURISTIC_BLOCK_OFF1.get())), ModItemGroups.BLOCKS);
    public static final RegistryObject<StairsBlock> FUTURISTIC_BLOCK_OFF1_STAIRS = register("futuristic_block_off_1_stair", () -> new OnlySapphireBreakableStair(() -> FUTURISTIC_BLOCK_OFF1.get().getDefaultState()), ModItemGroups.BLOCKS);
    public static final RegistryObject<WallBlock> FUTURISTIC_BLOCK_OFF1_WALL = register("futuristic_block_off_1_wall", () -> new OnlySapphireBreakableWall(AbstractBlock.Properties.from(FUTURISTIC_BLOCK_OFF1.get())), ModItemGroups.BLOCKS);
    public static final RegistryObject<FenceBlock> FUTURISTIC_BLOCK_OFF1_FENCE = register("futuristic_block_off_1_fence", () -> new OnlySapphireBreakableFence(AbstractBlock.Properties.from(FUTURISTIC_BLOCK_OFF1.get())), ModItemGroups.BLOCKS);

    public static final RegistryObject<SlabBlock> FUTURISTIC_BLOCK_OFF2_SLAB = register("futuristic_block_off_2_slab", () -> new OnlySapphireBreakableSlab(AbstractBlock.Properties.from(FUTURISTIC_BLOCK_OFF2.get())), ModItemGroups.BLOCKS);
    public static final RegistryObject<StairsBlock> FUTURISTIC_BLOCK_OFF2_STAIRS = register("futuristic_block_off_2_stair", () -> new OnlySapphireBreakableStair(() -> FUTURISTIC_BLOCK_OFF2.get().getDefaultState()), ModItemGroups.BLOCKS);
    public static final RegistryObject<WallBlock> FUTURISTIC_BLOCK_OFF2_WALL = register("futuristic_block_off_2_wall", () -> new OnlySapphireBreakableWall(AbstractBlock.Properties.from(FUTURISTIC_BLOCK_OFF2.get())), ModItemGroups.BLOCKS);
    public static final RegistryObject<FenceBlock> FUTURISTIC_BLOCK_OFF2_FENCE = register("futuristic_block_off_2_fence", () -> new OnlySapphireBreakableFence(AbstractBlock.Properties.from(FUTURISTIC_BLOCK_OFF2.get())), ModItemGroups.BLOCKS);

    public static final RegistryObject<SlabBlock> FUTURISTIC_BLOCK_ON1_SLAB = register("futuristic_block_on_1_slab", () -> new OnlySapphireBreakableSlab(AbstractBlock.Properties.from(FUTURISTIC_BLOCK_ON1.get())), ModItemGroups.BLOCKS);
    public static final RegistryObject<StairsBlock> FUTURISTIC_BLOCK_ON1_STAIRS = register("futuristic_block_on_1_stair", () -> new OnlySapphireBreakableStair(() -> FUTURISTIC_BLOCK_ON1.get().getDefaultState()), ModItemGroups.BLOCKS);
    public static final RegistryObject<WallBlock> FUTURISTIC_BLOCK_ON1_WALL = register("futuristic_block_on_1_wall", () -> new OnlySapphireBreakableWall(AbstractBlock.Properties.from(FUTURISTIC_BLOCK_ON1.get())), ModItemGroups.BLOCKS);
    public static final RegistryObject<FenceBlock> FUTURISTIC_BLOCK_ON1_FENCE = register("futuristic_block_on_1_fence", () -> new OnlySapphireBreakableFence(AbstractBlock.Properties.from(FUTURISTIC_BLOCK_ON1.get())), ModItemGroups.BLOCKS);

    public static final RegistryObject<SlabBlock> FUTURISTIC_BLOCK_ON2_SLAB = register("futuristic_block_on_2_slab", () -> new OnlySapphireBreakableSlab(AbstractBlock.Properties.from(FUTURISTIC_BLOCK_ON2.get())), ModItemGroups.BLOCKS);
    public static final RegistryObject<StairsBlock> FUTURISTIC_BLOCK_ON2_STAIRS = register("futuristic_block_on_2_stair", () -> new OnlySapphireBreakableStair(() -> FUTURISTIC_BLOCK_ON2.get().getDefaultState()), ModItemGroups.BLOCKS);
    public static final RegistryObject<WallBlock> FUTURISTIC_BLOCK_ON2_WALL = register("futuristic_block_on_2_wall", () -> new OnlySapphireBreakableWall(AbstractBlock.Properties.from(FUTURISTIC_BLOCK_ON2.get())), ModItemGroups.BLOCKS);
    public static final RegistryObject<FenceBlock> FUTURISTIC_BLOCK_ON2_FENCE = register("futuristic_block_on_2_fence", () -> new OnlySapphireBreakableFence(AbstractBlock.Properties.from(FUTURISTIC_BLOCK_ON2.get())), ModItemGroups.BLOCKS);

    public static final RegistryObject<SlabBlock>  MOSSY_FUTURISTIC_BLOCK1_SLAB = register("mossy_futuristic_block_1_slab", () -> new OnlySapphireBreakableSlab(AbstractBlock.Properties.from(MOSSY_FUTURISTIC_BLOCK1.get())), ModItemGroups.BLOCKS);
    public static final RegistryObject<StairsBlock>  MOSSY_FUTURISTIC_BLOCK1_STAIRS = register("mossy_futuristic_block_1_stair", () -> new OnlySapphireBreakableStair(() -> MOSSY_FUTURISTIC_BLOCK1.get().getDefaultState()), ModItemGroups.BLOCKS);
    public static final RegistryObject<WallBlock>  MOSSY_FUTURISTIC_BLOCK1_WALL = register("mossy_futuristic_block_1_wall", () -> new OnlySapphireBreakableWall(AbstractBlock.Properties.from(MOSSY_FUTURISTIC_BLOCK1.get())), ModItemGroups.BLOCKS);
    public static final RegistryObject<FenceBlock>  MOSSY_FUTURISTIC_BLOCK1_FENCE = register("mossy_futuristic_block_1_fence", () -> new OnlySapphireBreakableFence(AbstractBlock.Properties.from(MOSSY_FUTURISTIC_BLOCK1.get())), ModItemGroups.BLOCKS);

    public static final RegistryObject<SlabBlock>  MOSSY_FUTURISTIC_BLOCK2_SLAB = register("mossy_futuristic_block_2_slab", () -> new OnlySapphireBreakableSlab(AbstractBlock.Properties.from(MOSSY_FUTURISTIC_BLOCK2.get())), ModItemGroups.BLOCKS);
    public static final RegistryObject<StairsBlock>  MOSSY_FUTURISTIC_BLOCK2_STAIRS = register("mossy_futuristic_block_2_stair", () -> new OnlySapphireBreakableStair(() -> MOSSY_FUTURISTIC_BLOCK2.get().getDefaultState()), ModItemGroups.BLOCKS);

    public static final RegistryObject<WallBlock>  MOSSY_FUTURISTIC_BLOCK2_WALL = register("mossy_futuristic_block_2_wall", () -> new OnlySapphireBreakableWall(AbstractBlock.Properties.from(MOSSY_FUTURISTIC_BLOCK2.get())), ModItemGroups.BLOCKS);
    public static final RegistryObject<FenceBlock>  MOSSY_FUTURISTIC_BLOCK2_FENCE = register("mossy_futuristic_block_2_fence", () -> new OnlySapphireBreakableFence(AbstractBlock.Properties.from(MOSSY_FUTURISTIC_BLOCK2.get())), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> QUICKSAND = register("quicksand",
            () -> new QuicksandBlock(AbstractBlock.Properties.create(Material.SAND, MaterialColor.SAND).hardnessAndResistance(0.5F)
                    .sound(SoundType.SAND).doesNotBlockMovement().harvestTool(ToolType.SHOVEL)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> GLOW_STICK = BLOCKS.register("glowstick",
            () -> (new GlowStickBlock(AbstractBlock.Properties.create(Material.CARPET).doesNotBlockMovement().setLightLevel((state) -> 12)
                    .zeroHardnessAndResistance())));
    public static final RegistryObject<MortarBlock> MORTAR = register("mortar",
            MortarBlock::new, ModItemGroups.BLOCKS, () -> MortarItemRenderer::new);

    public static final RegistryObject<CampanileShell> CAMPANILE_SHELL_COMMON = register("campanile_common_shell", CampanileShell::new, ModItemGroups.BLOCKS);
    public static final RegistryObject<CampanileShell> CAMPANILE_SHELL_UNCOMMON = register("campanile_uncommon_shell", CampanileShell::new, ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> RUBY_ORE = register("ruby_ore",
            () -> new TypeOreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool()
                    .harvestTool(ToolType.PICKAXE).harvestLevel(2)
                    .hardnessAndResistance(3.0F, 3.0F)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> RUBY_BLOCK = register("ruby_block",
            () -> new Block(AbstractBlock.Properties.create(Material.IRON).setRequiresTool()
                    .harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F)
                    .sound(SoundType.METAL)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> SAPPHIRE_ORE = register("sapphire_ore",
            () -> new TypeOreBlock(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool()
                    .harvestTool(ToolType.PICKAXE).harvestLevel(2)
                    .hardnessAndResistance(3.0F, 3.0F)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> SAPPHIRE_BLOCK = register("sapphire_block",
            () -> new Block(AbstractBlock.Properties.create(Material.IRON).setRequiresTool()
                    .harvestTool(ToolType.PICKAXE).harvestLevel(2).hardnessAndResistance(5.0F, 6.0F)
                    .sound(SoundType.METAL)), ModItemGroups.BLOCKS);
    //Bush
    public static final RegistryObject<Block> NARCOTIC_BERRY_BUSH = BLOCKS.register("narcotic_berry_bush",
            () -> new BushBlockBase(AbstractBlock.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH), ItemInit.NARCOTIC_BERRIES));
    public static final RegistryObject<Block> ORANGE_BERRY_BUSH = BLOCKS.register("orange_berry_bush",
            () -> new BushBlockBase(AbstractBlock.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH), ItemInit.ORANGE_BERRIES));
    public static final RegistryObject<Block> YELLOW_BERRY_BUSH = BLOCKS.register("yellow_berry_bush",
            () -> new BushBlockBase(AbstractBlock.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().sound(SoundType.SWEET_BERRY_BUSH), ItemInit.YELLOW_BERRIES));

    //Plants
    public static final RegistryObject<Block> LAVENDER = register("lavender",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> LEMON_VERBENA = register("lemon_verbena",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> ARCHAEOSIGILLARIA = register("archaeosigillaria",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> CEPHALOTAXUS = register("cephalotaxus",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> DILLHOFFIA = register("dillhoffia",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> EPHEDRA = register("ephedra",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> OSMUNDA = register("osmunda",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> SARRACENIA = register("sarracenia",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> VACCINIUM = register("vaccinium",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> ZAMITES = register("zamites",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> WELWITSCHIA = register("welwitschia",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> PACHYPODA = register("pachypoda",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> FLORISSANTIA = register("florissantia",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> HORSETAIL = register("horsetail",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> COOKSONIA = register("cooksonia",
            () -> new SinglePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);

    //Double Tall Flower
    public static final RegistryObject<Block> FOOZIA = register("foozia",
            () -> new DoublePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> DUISBERGIA = register("duisbergia",
            () -> new DoublePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> BENNETTITALES = register("bennettitales",
            () -> new DoublePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> CRATAEGUS = register("crataegus",
            () -> new DoublePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> AMORPHOPHALLUS = register("amorphophallus",
            () -> new DoublePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);

    //Triple Tall Flower
    public static final RegistryObject<Block> TEMPSKYA = register("tempskya",
            () -> new TriplePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> PROTOTAXITES = register("prototaxites",
            () -> new TriplePlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    //Tree
    public static final RegistryObject<Block> REDWOOD_LEAVES = register("redwood_leaves",
            () -> new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()
                    .setBlocksVision((state, world, pos) -> false).setSuffocates((state, world, pos) -> false).harvestTool(ToolType.HOE)), ModItemGroups.BLOCKS);
    public static final RegistryObject<RotatedPillarBlock> REDWOOD_LOG = register("redwood_log",
            () -> new RotatedPillarBlock((AbstractBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F))), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> REDWOOD_SAPLING = register("redwood_sapling",
            () -> new BaseSaplingBlock(ModTreeSpawners.REDWOOD, AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> CRATAEGUS_PLANKS = register("crataegus_planks",
            () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0F, 3.0F)
                    .sound(SoundType.WOOD)), ModItemGroups.BLOCKS);
    public static final RegistryObject<RotatedPillarBlock> CRATAEGUS_LOG = register("crataegus_log",
            () -> new RotatedPillarBlock((AbstractBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F))), ModItemGroups.BLOCKS);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_CRATAEGUS_LOG = register("stripped_crataegus_log",
            () -> new RotatedPillarBlock((AbstractBlock.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F))), ModItemGroups.BLOCKS);
    public static final RegistryObject<DoorBlock> CRATAEGUS_DOOR = register("crataegus_door",
            () -> new DoorBlock(AbstractBlock.Properties.create(Material.WOOD, CRATAEGUS_PLANKS.get().getMaterialColor())
                    .hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid()), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> CRATAEGUS_LEAVES = register("crataegus_leaves",
            () -> new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()
                    .setBlocksVision((state, world, pos) -> false).setSuffocates((state, world, pos) -> false).harvestTool(ToolType.HOE)), ModItemGroups.BLOCKS);
    //Crops
    public static final RegistryObject<Block> EGGPLANT_CROP = BLOCKS.register("eggplant_crop",
            () -> new DoubleCropBaseBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0).sound(SoundType.CROP), ItemInit.EGGPLANT_SEED));
    public static final RegistryObject<Block> CORN_CROP = BLOCKS.register("corn_crop",
            () -> new DoubleCropBaseBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0).sound(SoundType.CROP), ItemInit.CORN_SEED));
    public static final RegistryObject<Block> CUCUMBER_CROP = BLOCKS.register("cucumber_crop",
            () -> new CropBaseBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0).sound(SoundType.CROP), ItemInit.CUCUMBER_SEED));
    public static final RegistryObject<Block> LETTUCE_CROP = BLOCKS.register("lettuce_crop",
            () -> new CropBaseBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0).sound(SoundType.CROP), ItemInit.LETTUCE_SEED));
    public static final RegistryObject<Block> SPINACH_CROP = BLOCKS.register("spinach_crop",
            () -> new CropBaseBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0).sound(SoundType.CROP), ItemInit.SPINACH_SEED));

    //Blocks
    public static final RegistryObject<Block> GEYSER = register("geyser", ()-> new GeyserBlock(AbstractBlock.Properties.create(Material.ROCK).zeroHardnessAndResistance().sound(SoundType.GROUND).tickRandomly().notSolid()), ModItemGroups.BLOCKS);
    public static final RegistryObject<Block> MUD = register("mud",
            ()-> new Block(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND)), ModItemGroups.BLOCKS);

    //Signs
    public static final RegistryObject<ModStandingSignBlock> CRATEAGUS_SIGN = BLOCKS.register("crateagus_sign", () -> new ModStandingSignBlock(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement(), WoodTypeInit.CRATEAGUS_WOOD));
    public static final RegistryObject<ModWallSignBlock> CRATEAGUS_WALL_SIGN = BLOCKS.register("crateagus_wall_sign", () -> new ModWallSignBlock(AbstractBlock.Properties.create(Material.WOOD).doesNotBlockMovement().lootFrom(CRATEAGUS_SIGN.get()), WoodTypeInit.CRATEAGUS_WOOD));


    private static Boolean onlyWithSapphire(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {

        return false;
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, ItemGroup tab) {
        return register(name, blockSupplier, () -> new Item.Properties().group(tab));
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Supplier<Item.Properties> properties) {
        return register(name, blockSupplier, b -> new BlockItem(b, properties.get()));
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Function<Block, Item> blockItemFunction) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ItemInit.ITEMS.register(name, () -> blockItemFunction.apply(block.get()));
        return block;
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, ItemGroup tab, Supplier<Callable<ItemStackTileEntityRenderer>> renderer) {
        return register(name, blockSupplier, b -> new AnimatedBlockItem(b, new Item.Properties().setISTER(renderer).group(tab)));
    }
}
