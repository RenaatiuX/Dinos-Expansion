package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.renderer.ClientISTERProvider;
import com.rena.dinosexpansion.common.entity.projectile.CustomArrow;
import com.rena.dinosexpansion.common.entity.util.BoatType;
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
    public static final RegistryObject<Item> COMPOUND_ARROW = ITEMS.register("compound_arrow",
            () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) ->
                    new CustomArrow(shooter, world, arrow, 4.5D)));
    public static final RegistryObject<Item> TRANQUILLIZER_ARROW = ITEMS.register("tranquillizer_arrow",
            () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) ->
                    new CustomArrow(shooter, world, arrow, 0.2D, 10)));
    public static final RegistryObject<Item> BONE_ARROW = ITEMS.register("bone_arrow",
            () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) ->
                    new CustomArrow(shooter, world, arrow, 2.2D)));
    public static final RegistryObject<Item> DIAMOND_ARROW = ITEMS.register("diamond_arrow",
            () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) ->
                    new CustomArrow(shooter, world, arrow, 3.5D)));
    public static final RegistryObject<Item> EMERALD_ARROW = ITEMS.register("emerald_arrow",
            () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) ->
                    new CustomArrow(shooter, world, arrow, 5.0D)));
    public static final RegistryObject<Item> GOLD_ARROW = ITEMS.register("golden_arrow",
            () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) ->
                    new CustomArrow(shooter, world, arrow, 0.5D)));
    public static final RegistryObject<Item> IRON_ARROW = ITEMS.register("iron_arrow",
            () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) ->
                    new CustomArrow(shooter, world, arrow, 2.5D)));
    public static final RegistryObject<Item> NETHERITE_ARROW = ITEMS.register("netherite_arrow",
            () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) ->
                    new CustomArrow(shooter, world, arrow, 4.0D)));
    public static final RegistryObject<Item> STONE_ARROW = ITEMS.register("stone_arrow",
            () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) ->
                    new CustomArrow(shooter, world, arrow, 1.0D)));
    public static final RegistryObject<Item> WOODEN_ARROW = ITEMS.register("wooden_arrow",
            () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) ->
                    new CustomArrow(shooter, world, arrow, 0.5D)));
    public static final RegistryObject<Item> RUBY_ARROW = ITEMS.register("ruby_arrow",
            () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) ->
                    new CustomArrow(shooter, world, arrow, 5.25D)));
    public static final RegistryObject<Item> SAPPHIRE_ARROW = ITEMS.register("sapphire_arrow",
            () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) ->
                    new CustomArrow(shooter, world, arrow, 5.5D)));
    public static final RegistryObject<Item> DART = ITEMS.register("dart",
            () -> new DartItem(new Item.Properties().group(ModItemGroups.WEAPONS)));
    public static final RegistryObject<Item> TINY_ROCK = ITEMS.register("tiny_rock",
            () -> new TinyRockItem(new Item.Properties().group(ModItemGroups.WEAPONS)));
    public static final RegistryObject<Item> SLINGSHOT = ITEMS.register("slingshot",
            () -> new SlingshotItem(new Item.Properties().group(ModItemGroups.WEAPONS).maxStackSize(1).maxDamage(200)));

    public static final RegistryObject<TieredBoomerang> WOOD_BOOMERANG = ITEMS.register("wood_boomerang",
            () -> new TieredBoomerang(new Item.Properties().group(ModItemGroups.WEAPONS), BoomerangTiers.WOOD));
    public static final RegistryObject<TieredBoomerang> STONE_BOOMERANG = ITEMS.register("stone_boomerang",
            () -> new TieredBoomerang(new Item.Properties().group(ModItemGroups.WEAPONS), BoomerangTiers.STONE));
    public static final RegistryObject<TieredBoomerang> IRON_BOOMERANG = ITEMS.register("iron_boomerang",
            () -> new TieredBoomerang(new Item.Properties().group(ModItemGroups.WEAPONS), BoomerangTiers.IRON));
    public static final RegistryObject<TieredBoomerang> GOLDEN_BOOMERANG = ITEMS.register("golden_boomerang",
            () -> new TieredBoomerang(new Item.Properties().group(ModItemGroups.WEAPONS), BoomerangTiers.GOLDEN));
    public static final RegistryObject<TieredBoomerang> DIAMOND_BOOMERANG = ITEMS.register("diamond_boomerang",
            () -> new TieredBoomerang(new Item.Properties().group(ModItemGroups.WEAPONS), BoomerangTiers.DIAMOND));
    public static final RegistryObject<TieredBoomerang> NETHERITE_BOOMERANG = ITEMS.register("netherite_boomerang",
            () -> new TieredBoomerang(new Item.Properties().group(ModItemGroups.WEAPONS), BoomerangTiers.NETHERITE));
    public static final RegistryObject<TieredBoomerang> EMERALD_BOOMERANG = ITEMS.register("emerald_boomerang",
            () -> new TieredBoomerang(new Item.Properties().group(ModItemGroups.WEAPONS), BoomerangTiers.EMERALD));
    public static final RegistryObject<TieredBoomerang> RUBY_BOOMERANG = ITEMS.register("ruby_boomerang",
            () -> new TieredBoomerang(new Item.Properties().group(ModItemGroups.WEAPONS), BoomerangTiers.RUBY));
    public static final RegistryObject<TieredBoomerang> SAPPHIRE_BOOMERANG = ITEMS.register("sapphire_boomerang",
            () -> new TieredBoomerang(new Item.Properties().group(ModItemGroups.WEAPONS), BoomerangTiers.SAPPHIRE));

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
    public static final RegistryObject<Item> RUBY_CHAKRAM = ITEMS.register("ruby_chakram",
            () -> new TieredChakram(new Item.Properties().group(ModItemGroups.WEAPONS), ChakramTiers.RUBY_CHAKRAM));
    public static final RegistryObject<Item> SAPPHIRE_CHAKRAM = ITEMS.register("sapphire_chakram",
            () -> new TieredChakram(new Item.Properties().group(ModItemGroups.WEAPONS), ChakramTiers.SAPPHIRE_CHAKRAM));
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
    public static final RegistryObject<Item> RUBY_HATCHET = ITEMS.register("ruby_hatchet",
            () -> new TieredHatchet(new Item.Properties().group(ModItemGroups.WEAPONS), HatchetTiers.RUBY_HATCHET));
    public static final RegistryObject<Item> SAPPHIRE_HATCHET = ITEMS.register("sapphire_hatchet",
            () -> new TieredHatchet(new Item.Properties().group(ModItemGroups.WEAPONS), HatchetTiers.SAPPHIRE_HATCHET));
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
    public static final RegistryObject<Item> RUBY_SPEAR = ITEMS.register("ruby_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("ruby_spear")), SpearTiers.RUBY_SPEAR));
    public static final RegistryObject<Item> RUBY_SPEAR_INVENTORY = ITEMS.register("ruby_spear_inventory",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SAPPHIRE_SPEAR = ITEMS.register("sapphire_spear",
            () -> new TieredSpear(new Item.Properties().group(ModItemGroups.WEAPONS).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("sapphire_spear")), SpearTiers.SAPPHIRE_SPEAR));
    public static final RegistryObject<Item> SAPPHIRE_SPEAR_INVENTORY = ITEMS.register("sapphire_spear_inventory",
            () -> new Item(new Item.Properties()));
    //Spawn Eggs
    public static final RegistryObject<Item> SQUALODON_SPAWN_EGG = ITEMS.register("squalodon_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.SQUALODON, 13097436, 8431015, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
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
            () -> new ForgeSpawnEggItem(EntityInit.CAMPANILE, 8546118, 14404019, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> DRYOSAURUS_SPAWN_EGG = ITEMS.register("dryosaurus_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.DRYOSAURUS, 9012770, 5987102, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> WETHERELLUS_SPAWN_EGG = ITEMS.register("wetherellus_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.WETHERELLUS, 8161967, 7438131, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> AEGIROCASSIS_SPAWN_EGG = ITEMS.register("aegirocassis_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.AEGIROCASSIS, 3034935, 1214097, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> ANOMALOCARIS_SPAWN_EGG = ITEMS.register("anomalocaris_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.ANOMALOCARIS, 14578253, 14986835, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> BELANTSEA_SPAWN_EGG = ITEMS.register("belantsea_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.BELANTSEA, 12690565, 10637126, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> ACANTHODES_SPAWN_EGG = ITEMS.register("acanthodes_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.ACANTHODES, 10212586, 5737117, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<Item> MEGANEURA_SPAWN_EGG = ITEMS.register("meganeura_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.MEGANEURA, 5257261, 9465189, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
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
    public static final RegistryObject<Item> RAW_ASTORGOSUCHUS_MEAT = ITEMS.register("raw_astorgosuchus_meat",
            () -> new Item(new Item.Properties().food(FoodInit.RAW_ASTORGOSUCHUS_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> COOKED_ASTORGOSUCHUS_MEAT = ITEMS.register("cooked_astorgosuchus_meat",
            () -> new Item(new Item.Properties().food(FoodInit.COOKED_ASTORGOSUCHUS_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> RAW_PARAPUZOSIA_TENTACLE = ITEMS.register("raw_parapuzosia_tentacle",
            () -> new Item(new Item.Properties().food(FoodInit.RAW_PARAPUZOSIA_TENTACLE).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> COOKED_PARAPUZOSIA_TENTACLE = ITEMS.register("cooked_parapuzosia_tentacle",
            () -> new Item(new Item.Properties().food(FoodInit.COOKED_PARAPUZOSIA_TENTACLE).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> RAW_DRYOSAURUS_MEAT = ITEMS.register("raw_dryosaurus_meat",
            () -> new Item(new Item.Properties().food(FoodInit.RAW_DRYOSAURUS_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> COOKED_DRYOSAURUS_MEAT = ITEMS.register("cooked_dryosaurus_meat",
            () -> new Item(new Item.Properties().food(FoodInit.COOKED_DRYOSAURUS_MEAT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> NARCOTIC_BERRIES = ITEMS.register("narcotic_berries",
            () -> new BlockNamedItem(BlockInit.NARCOTIC_BERRY_BUSH.get(), new Item.Properties().food(FoodInit.NARCOTIC_BERRIES).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> ORANGE_BERRIES = ITEMS.register("orange_berry",
            () -> new BlockNamedItem(BlockInit.ORANGE_BERRY_BUSH.get(), new Item.Properties().food(FoodInit.ORANGE_BERRIES).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> YELLOW_BERRIES = ITEMS.register("yellow_berry",
            () -> new BlockNamedItem(BlockInit.YELLOW_BERRY_BUSH.get(), new Item.Properties().food(FoodInit.YELLOW_BERRIES).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> EGGPLANT = ITEMS.register("eggplant",
            ()-> new Item(new Item.Properties().food(FoodInit.EGGPLANT).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> CORN = ITEMS.register("corn",
            ()-> new Item(new Item.Properties().food(FoodInit.CORN).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> ONION = ITEMS.register("onion",
            ()-> new Item(new Item.Properties().food(FoodInit.ONION).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> SPINACH = ITEMS.register("spinach",
            ()-> new Item(new Item.Properties().food(FoodInit.SPINACH).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato",
            ()-> new Item(new Item.Properties().food(FoodInit.TOMATO).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> CUCUMBER = ITEMS.register("cucumber",
            ()-> new Item(new Item.Properties().food(FoodInit.CUCUMBER).group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> LETTUCE = ITEMS.register("lettuce",
            ()-> new Item(new Item.Properties().food(FoodInit.LETTUCE).group(ModItemGroups.ITEMS)));

    public static final RegistryObject<Item> EGGPLANT_SEED = ITEMS.register("eggplant_seed",
            ()-> new BlockNamedItem(BlockInit.EGGPLANT_CROP.get(), new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> CORN_SEED = ITEMS.register("corn_seed",
            ()-> new BlockNamedItem(BlockInit.CORN_CROP.get(), new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> CUCUMBER_SEED = ITEMS.register("cucumber_seed",
            ()-> new BlockNamedItem(BlockInit.CUCUMBER_CROP.get(), new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> LETTUCE_SEED = ITEMS.register("lettuce_seed",
            ()-> new BlockNamedItem(BlockInit.LETTUCE_CROP.get(), new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> SPINACH_SEED = ITEMS.register("spinach_seed",
            ()-> new BlockNamedItem(BlockInit.SPINACH_CROP.get(), new Item.Properties().group(ModItemGroups.ITEMS)));

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
    public static final RegistryObject<Item> RAW_ANOMALOCARIS_TAIL = ITEMS.register("raw_anomalocaris_tail",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.RAW_ANOMALOCARIS_TAIL)));
    public static final RegistryObject<Item> COOKED_ANOMALOCARIS_TAIL = ITEMS.register("cooked_anomalocaris_tail",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.COOKED_ANOMALOCARIS_TAIL)));

    public static final RegistryObject<Item> SPECIAL_FRUIT = ITEMS.register("special_fruit",
            () -> new SpecialFruitItem(new Item.Properties().group(ModItemGroups.ITEMS).food(FoodInit.SPECIAL_FRUIT)));
    //Misc
    public static final RegistryObject<Item> DINOPEDIA = ITEMS.register("dinopedia",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MEGA_PIRANHA_BUCKET = ITEMS.register("mega_piranha_bucket",
            () -> new CustomFishBucket(EntityInit.MEGA_PIRANHA::get, () -> Fluids.WATER, new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> WETHERELLUS_BUCKET = ITEMS.register("wetherellus_bucket",
            () -> new CustomFishBucket(EntityInit.WETHERELLUS::get, () -> Fluids.WATER, new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> BELANTSEA_BUCKET = ITEMS.register("belantsea_bucket",
            () -> new CustomFishBucket(EntityInit.BELANTSEA::get, () -> Fluids.WATER, new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> ACANTHODES_BUCKET = ITEMS.register("acanthodes_bucket",
            () -> new CustomFishBucket(EntityInit.ACANTHODES::get, () -> Fluids.WATER, new Item.Properties().group(ModItemGroups.ITEMS)));
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
    public static final RegistryObject<ZoomItem> SPYGLASS = ITEMS.register("spyglass",
            () -> new ZoomItem(new Item.Properties().group(ModItemGroups.ITEMS), 2, 10));
    public static final RegistryObject<Item> TELEPORT_ITEM = ITEMS.register("teleport_machine",
            () -> new TeleportedItem(new Item.Properties().group(ModItemGroups.ITEMS).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));
    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));

    public static final RegistryObject<Item> CAMPANILE_GOO = ITEMS.register("snail_goo", () -> new Item(new Item.Properties().group(ModItemGroups.ITEMS)));

    //Boats
    public static final RegistryObject<CustomBoatItem> CRATAEGUS_BOAT_ITEM = ITEMS.register("crataegus_boat_item", () -> new CustomBoatItem(BoatType.CRATAEGUS, new Item.Properties().group(ModItemGroups.ITEMS)));
}
