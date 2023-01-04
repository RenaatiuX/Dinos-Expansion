package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.renderer.ClientISTERProvider;
import com.rena.dinosexpansion.common.entity.misc.ChakramEntity;
import com.rena.dinosexpansion.common.entity.projectile.CustomArrow;
import com.rena.dinosexpansion.common.item.*;
import com.rena.dinosexpansion.common.item.arrow.DartItem;
import com.rena.dinosexpansion.common.item.arrow.TinyRockItem;
import com.rena.dinosexpansion.common.item.util.BaseNarcoticItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DinosExpansion.MOD_ID);

    public static final RegistryObject<TieredBow> COMPOUND_BOW = ITEMS.register("compound_bow",
            () -> new TieredBow(new Item.Properties().group(ModItemGroups.WEAPONS), BowTiers.COMPOUND_BOW));
    public static final RegistryObject<Item> BLOWGUN = ITEMS.register("blowgun",
            () -> new BlowgunItem(new Item.Properties().group(ModItemGroups.WEAPONS).maxStackSize(1).maxDamage(300)));
    public static final RegistryObject<CustomArrowItem> COMPOUND_ARROW = ITEMS.register("compound_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 4.5d)));
    public static final RegistryObject<CustomArrowItem> TRANQUILLIZER_ARROW = ITEMS.register("tranquillizer_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 0.2, 10)));
    public static final RegistryObject<CustomArrowItem> BONE_ARROW = ITEMS.register("bone_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 2.2d)));
    public static final RegistryObject<CustomArrowItem> DIAMOND_ARROW = ITEMS.register("diamond_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 3.5)));
    public static final RegistryObject<CustomArrowItem> EMERALD_ARROW = ITEMS.register("emerald_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 5d)));
    public static final RegistryObject<CustomArrowItem> GOLD_ARROW = ITEMS.register("golden_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 0.5)));
    public static final RegistryObject<CustomArrowItem> IRON_ARROW = ITEMS.register("iron_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 2.5)));
    public static final RegistryObject<CustomArrowItem> NETHERITE_ARROW = ITEMS.register("netherite_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 4d)));
    public static final RegistryObject<CustomArrowItem> STONE_ARROW = ITEMS.register("stone_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 1)));
    public static final RegistryObject<CustomArrowItem> WOODEN_ARROW = ITEMS.register("wooden_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 0.5d)));
    public static final RegistryObject<Item> DART = ITEMS.register("dart",
            () -> new DartItem(new Item.Properties().group(ModItemGroups.WEAPONS)));
    public static final RegistryObject<Item> TINY_ROCK = ITEMS.register("tiny_rock",
            () -> new TinyRockItem(new Item.Properties().group(ModItemGroups.WEAPONS)));

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

    public static final RegistryObject<Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("wooden_spear")), SpearTiers.WOODEN_SPEAR));
    public static final RegistryObject<Item> STONE_SPEAR = ITEMS.register("stone_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("stone_spear")), SpearTiers.STONE_SPEAR));
    public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("iron_spear")), SpearTiers.IRON_SPEAR));
    public static final RegistryObject<Item> GOLD_SPEAR = ITEMS.register("golden_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("golden_spear")), SpearTiers.GOLD_SPEAR));
    public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("diamond_spear")), SpearTiers.DIAMOND_SPEAR));
    public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("netherite_spear")), SpearTiers.NETHERITE_SPEAR));
    public static final RegistryObject<Item> EMERALD_SPEAR = ITEMS.register("emerald_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("emerald_spear")), SpearTiers.EMERALD_SPEAR));


    public static final RegistryObject<Item> PARAPUZOSIA_SPAWN_EGG = ITEMS.register("parapuzosia_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.PARAPUZOSIA, 0x392829, 0x77554c, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> EOSQUALODON_SPAWN_EGG = ITEMS.register("eosqualodon_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.EOSQUALODON, 2239024, 16777215, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> MEGA_PIRANHA_SPAWN_EGG = ITEMS.register("mega_piranha_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.MEGA_PIRANHA, 799664, 22618693, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> DIMORPHODON_SPAWN_EGG = ITEMS.register("dimorphodon_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.DIMORPHODON, 799664, 22618693, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));

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

    public static final RegistryObject<BaseNarcoticItem> NARCOTICS = ITEMS.register("narcotics", () -> new BaseNarcoticItem(new Item.Properties().group(ModItemGroups.ITEMS), 10));

    public static final RegistryObject<Item> KIBBLE_SIMPLE = ITEMS.register("kibble_simple", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.KIBBLE_SIMPLE)));
    public static final RegistryObject<Item> KIBBLE_BASIC = ITEMS.register("kibble_basic", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.KIBBLE_BASIC)));
    public static final RegistryObject<Item> KIBBLE_REGULAR = ITEMS.register("kibble_regular", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.KIBBLE_REGULAR)));
    public static final RegistryObject<Item> KIBBLE_EXCEPTIONAL = ITEMS.register("kibble_exceptional", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.KIBBLE_EXCEPTIONAL)));
    public static final RegistryObject<Item> KIBBLE_SUPERIOR = ITEMS.register("kibble_superior", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.KIBBLE_SUPERIOR)));
    public static final RegistryObject<Item> KIBBLE_EXTRAORDINARY = ITEMS.register("kibble_extraordinary", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.KIBBLE_EXTRAORDINARY)));
}
