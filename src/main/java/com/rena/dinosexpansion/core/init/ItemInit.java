package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.renderer.ClientISTERProvider;
import com.rena.dinosexpansion.common.entity.projectile.CustomArrow;
import com.rena.dinosexpansion.common.item.*;
import com.rena.dinosexpansion.common.item.arrow.DartItem;
import com.rena.dinosexpansion.common.item.arrow.TinyRockItem;
import com.rena.dinosexpansion.common.item.enums.*;
import com.rena.dinosexpansion.common.item.util.BaseNarcoticItem;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DinosExpansion.MOD_ID);

    public static final RegistryObject<Item> COMPOUND_BOW = ITEMS.register("compound_bow",
            () -> new TieredBow(new Item.Properties().group(ModItemGroups.WEAPONS), BowTiers.COMPOUND_BOW));
    public static final RegistryObject<Item> BLOWGUN = ITEMS.register("blowgun",
            () -> new BlowgunItem(new Item.Properties().group(ModItemGroups.WEAPONS).maxStackSize(1).maxDamage(300)));
    public static final RegistryObject<Item> COMPOUND_ARROW = ITEMS.register("compound_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 4.5d)));
    public static final RegistryObject<Item> TRANQUILLIZER_ARROW = ITEMS.register("tranquillizer_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 0.2, 10)));
    public static final RegistryObject<Item> BONE_ARROW = ITEMS.register("bone_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 2.2d)));
    public static final RegistryObject<Item> DIAMOND_ARROW = ITEMS.register("diamond_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 3.5)));
    public static final RegistryObject<Item> EMERALD_ARROW = ITEMS.register("emerald_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 5d)));
    public static final RegistryObject<Item> GOLD_ARROW = ITEMS.register("golden_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 0.5)));
    public static final RegistryObject<Item> IRON_ARROW = ITEMS.register("iron_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 2.5)));
    public static final RegistryObject<Item> NETHERITE_ARROW = ITEMS.register("netherite_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 4d)));
    public static final RegistryObject<Item> STONE_ARROW = ITEMS.register("stone_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 1)));
    public static final RegistryObject<Item> WOODEN_ARROW = ITEMS.register("wooden_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 0.5d)));
    public static final RegistryObject<Item> DART = ITEMS.register("dart",
            () -> new DartItem(new Item.Properties().group(ModItemGroups.WEAPONS)));
    public static final RegistryObject<Item> TINY_ROCK = ITEMS.register("tiny_rock",
            () -> new TinyRockItem(new Item.Properties().group(ModItemGroups.WEAPONS)));
    public static final RegistryObject<Item> SLINGSHOT = ITEMS.register("slingshot",
            () -> new SlingshotItem(new Item.Properties().group(ModItemGroups.WEAPONS).maxStackSize(1).maxDamage(200)));

    public static final RegistryObject<TieredBoomerang> WOOD_BOOMERANG = ITEMS.register("wood_boomerang",
            () -> new TieredBoomerang(new Item.Properties().group(ModItemGroups.WEAPONS), BoomerangTiers.WOOD));
    public static final RegistryObject<TieredBoomerang> IRON_BOOMERANG = ITEMS.register("iron_boomerang",
            () -> new TieredBoomerang(new Item.Properties().group(ModItemGroups.WEAPONS), BoomerangTiers.IRON));
    public static final RegistryObject<TieredBoomerang> DIAMOND_BOOMERANG = ITEMS.register("diamond_boomerang",
            () -> new TieredBoomerang(new Item.Properties().group(ModItemGroups.WEAPONS), BoomerangTiers.DIAMOND));

    public static final RegistryObject<Item> WOODEN_CHAKRAM = ITEMS.register("wooden_chakram",
            () -> new TieredChakram(new Item.Properties().group(ModItemGroups.WEAPONS), ChakramTiers.WOODEN_CHAKRAM));
    public static final RegistryObject<Item> STONE_CHAKRAM = ITEMS.register("stone_chakram",
            () -> new TieredChakram(new Item.Properties().group(ModItemGroups.WEAPONS), ChakramTiers.STONE_CHRAKRAM));
    public static final RegistryObject<Item> IRON_CHAKRAM = ITEMS.register("iron_chakram",
            () -> new TieredChakram(new Item.Properties().group(ModItemGroups.WEAPONS), ChakramTiers.IRON_CHAKRAM));
    public static final RegistryObject<Item> GOLD_CHAKRAM = ITEMS.register("golden_chakram",
            () -> new TieredChakram(new Item.Properties().group(ModItemGroups.WEAPONS), ChakramTiers.GOLD_CHAKRAM));
    public static final RegistryObject<Item> DIAMOND_CHAKRAM = ITEMS.register("diamond_chakram",
            () -> new TieredChakram(new Item.Properties().group(ModItemGroups.WEAPONS), ChakramTiers.DIAMOND_CHAKRAM));
    public static final RegistryObject<Item> EMERALD_CHAKRAM = ITEMS.register("emerald_chakram",
            () -> new TieredChakram(new Item.Properties().group(ModItemGroups.WEAPONS), ChakramTiers.EMERALD_CHAKRAM));
    public static final RegistryObject<Item> NETHERITE_CHAKRAM = ITEMS.register("netherite_chakram",
            () -> new TieredChakram(new Item.Properties().group(ModItemGroups.WEAPONS), ChakramTiers.NETHERITE_CHAKRAM));
    public static final RegistryObject<Item> WOODEN_HATCHET = ITEMS.register("wooden_hatchet",
            () -> new TieredHatchet(new Item.Properties().group(ModItemGroups.WEAPONS), HatchetTiers.WOODEN_HATCHET));
    public static final RegistryObject<Item> STONE_HATCHET = ITEMS.register("stone_hatchet",
            () -> new TieredHatchet(new Item.Properties().group(ModItemGroups.WEAPONS), HatchetTiers.STONE_HATCHET));
    public static final RegistryObject<Item> IRON_HATCHET = ITEMS.register("iron_hatchet",
            () -> new TieredHatchet(new Item.Properties().group(ModItemGroups.WEAPONS), HatchetTiers.IRON_HATCHET));
    public static final RegistryObject<Item> GOLD_HATCHET = ITEMS.register("golden_hatchet",
            () -> new TieredHatchet(new Item.Properties().group(ModItemGroups.WEAPONS), HatchetTiers.GOLD_HATCHET));
    public static final RegistryObject<Item> DIAMOND_HATCHET = ITEMS.register("diamond_hatchet",
            () -> new TieredHatchet(new Item.Properties().group(ModItemGroups.WEAPONS), HatchetTiers.DIAMOND_HATCHET));
    public static final RegistryObject<Item> NETHERITE_HATCHET = ITEMS.register("netherite_hatchet",
            () -> new TieredHatchet(new Item.Properties().group(ModItemGroups.WEAPONS), HatchetTiers.NETHERITE_HATCHET));
    public static final RegistryObject<Item> EMERALD_HATCHET = ITEMS.register("emerald_hatchet",
            () -> new TieredHatchet(new Item.Properties().group(ModItemGroups.WEAPONS), HatchetTiers.EMERALD_HATCHET));
    public static final RegistryObject<Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("wooden_spear")), SpearTiers.WOODEN_SPEAR));
    public static final RegistryObject<Item> WOODEN_SPEAR_INVENTORY = ITEMS.register("wooden_spear_inventory",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STONE_SPEAR = ITEMS.register("stone_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("stone_spear")), SpearTiers.STONE_SPEAR));
    public static final RegistryObject<Item> STONE_SPEAR_INVENTORY = ITEMS.register("stone_spear_inventory",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("iron_spear")), SpearTiers.IRON_SPEAR));
    public static final RegistryObject<Item> IRON_SPEAR_INVENTORY = ITEMS.register("iron_spear_inventory",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_SPEAR = ITEMS.register("golden_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("golden_spear")), SpearTiers.GOLD_SPEAR));
    public static final RegistryObject<Item> GOLD_SPEAR_INVENTORY = ITEMS.register("golden_spear_inventory",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_SPEAR_INVENTORY = ITEMS.register("diamond_spear_inventory",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("diamond_spear")), SpearTiers.DIAMOND_SPEAR));
    public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("netherite_spear")), SpearTiers.NETHERITE_SPEAR));
    public static final RegistryObject<Item> NETHERITE_SPEAR_INVENTORY = ITEMS.register("netherite_spear_inventory",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EMERALD_SPEAR = ITEMS.register("emerald_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("emerald_spear")), SpearTiers.EMERALD_SPEAR));
    public static final RegistryObject<Item> EMERALD_SPEAR_INVENTORY = ITEMS.register("emerald_spear_inventory",
            () -> new Item(new Item.Properties()));

    //Spawn Eggs
    public static final RegistryObject<Item> PARAPUZOSIA_SPAWN_EGG = ITEMS.register("parapuzosia_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.PARAPUZOSIA, 0x392829, 0x77554c, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> EOSQUALODON_SPAWN_EGG = ITEMS.register("eosqualodon_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.EOSQUALODON, 2239024, 16777215, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> MEGA_PIRANHA_SPAWN_EGG = ITEMS.register("mega_piranha_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.MEGA_PIRANHA, 5201984, 14858845, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> DIMORPHODON_SPAWN_EGG = ITEMS.register("dimorphodon_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.DIMORPHODON, 5592428, 10785674, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> ASTORGOSUCHUS_SPAWN_EGG = ITEMS.register("astorgosuchus_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.ASTORGOSUCHUS, 8098406, 12561789, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> CAMPANILE_SPAEN_EGG = ITEMS.register("campanile_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.CAMPANILE, 5592428, 12561789, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> DRYOSAURUS_SPAWN_EGG = ITEMS.register("dryosaurus_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.DRYOSAURUS, 9012770, 5987102, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    //Food
    public static final RegistryObject<Item> RAW_CARNOTAURUS_MEAT = ITEMS.register("raw_carnotaurus_meat",
            () -> new Item(new Item.Properties().food(FoodInit.RAW_CARNOTAURUS_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> COOKED_CARNOTAURUS_MEAT = ITEMS.register("cooked_carnotaurus_meat",
            () -> new Item(new Item.Properties().food(FoodInit.COOKED_CARNOTAURUS_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> RAW_ANKYLOSAURUS_MEAT = ITEMS.register("raw_ankylosaurus_meat",
            () -> new Item(new Item.Properties().food(FoodInit.RAW_ANKYLOSAURUS_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> COOKED_ANKYLOSAURUS_MEAT = ITEMS.register("cooked_ankylosaurus_meat",
            () -> new Item(new Item.Properties().food(FoodInit.COOKED_ANKYLOSAURUS_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> RAW_TRICERATOPS_MEAT = ITEMS.register("raw_triceratops_meat",
            () -> new Item(new Item.Properties().food(FoodInit.RAW_TRICERATOPS_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> COOKED_TRICERATOPS_MEAT = ITEMS.register("cooked_triceratops_meat",
            () -> new Item(new Item.Properties().food(FoodInit.COOKED_TRICERATOPS_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> RAW_GALLIMIMUS_MEAT = ITEMS.register("raw_gallimimus_meat",
            () -> new Item(new Item.Properties().food(FoodInit.RAW_GALLIMIMUS_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> COOKED_GALLIMIMUS_MEAT = ITEMS.register("cooked_gallimimus_meat",
            () -> new Item(new Item.Properties().food(FoodInit.COOKED_GALLIMIMUS_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> RAW_DIMORPHODON_MEAT = ITEMS.register("raw_dimorphodon_meat",
            () -> new Item(new Item.Properties().food(FoodInit.RAW_DIMORPHODON_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> COOKED_DIMORPHODON_MEAT = ITEMS.register("cooked_dimorphodon_meat",
            () -> new Item(new Item.Properties().food(FoodInit.COOKED_DIMORPHODON_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> NARCOTIC_BERRIES = ITEMS.register("narcotic_berries",
            () -> new BlockNamedItem(BlockInit.NARCOTIC_BERRY_BUSH.get(), new Item.Properties().food(FoodInit.NARCOTIC_BERRIES).group(ModItemGroups.ITEMS)));

    public static final RegistryObject<Item> NARCOTICS = ITEMS.register("narcotics",
            () -> new BaseNarcoticItem(new Item.Properties().group(ModItemGroups.ITEMS), 10));

    public static final RegistryObject<Item> KIBBLE_SIMPLE = ITEMS.register("kibble_simple",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.KIBBLE_SIMPLE)));
    public static final RegistryObject<Item> KIBBLE_BASIC = ITEMS.register("kibble_basic",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.KIBBLE_BASIC)));
    public static final RegistryObject<Item> KIBBLE_REGULAR = ITEMS.register("kibble_regular",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.KIBBLE_REGULAR)));
    public static final RegistryObject<Item> KIBBLE_EXCEPTIONAL = ITEMS.register("kibble_exceptional",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.KIBBLE_EXCEPTIONAL)));
    public static final RegistryObject<Item> KIBBLE_SUPERIOR = ITEMS.register("kibble_superior",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.KIBBLE_SUPERIOR)));
    public static final RegistryObject<Item> KIBBLE_EXTRAORDINARY = ITEMS.register("kibble_extraordinary",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.KIBBLE_EXTRAORDINARY)));

    public static final RegistryObject<Item> SPECIAL_FRUIT = ITEMS.register("special_fruit",
            () -> new SpecialFruitItem(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.SPECIAL_FRUIT)));
    //Misc
    public static final RegistryObject<Item> DINOPEDIA = ITEMS.register("dinopedia",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MEGA_PIRANHA_BUCKET = ITEMS.register("mega_piranha_bucket",
            () -> new CustomFishBucket(EntityInit.MEGA_PIRANHA::get, () -> Fluids.WATER, new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> ELECTRONICS_PARTS = ITEMS.register("electronics_parts",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> OIL = ITEMS.register("oil",
            () -> new TestItem(new Item.Properties().group(ModItemGroups.ITEMS).maxDamage(100)));
    public static final RegistryObject<Item> SCRAP = ITEMS.register("scrap",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> EXPLORER_JOURNAL = ITEMS.register("explorer_journal",
            () -> new ExplorerJournalItem(new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> EXPLORER_JOURNAL_PAGE = ITEMS.register("explorer_journal_page",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> QUICKSAND_BUCKET = ITEMS.register("quicksand_bucket",
            () -> new CustomBlockBucket(new Item.Properties().group(ModItemGroups.ITEMS).maxStackSize(1), BlockInit.QUICKSAND));
    public static final RegistryObject<Item> GLOW_STICK = ITEMS.register("glowstick",
            () -> new GlowStickItem(new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<ZoomItem> ZOOM = ITEMS.register("zooooooooom", () -> new ZoomItem(new Item.Properties().group(ModItemGroups.ITEMS), 2, 10));
}
