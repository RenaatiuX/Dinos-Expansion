package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.projectile.CustomArrow;
import com.rena.dinosexpansion.common.item.BowTiers;
import com.rena.dinosexpansion.common.item.CustomArrowItem;
import com.rena.dinosexpansion.common.item.TieredBow;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DinosExpansion.MOD_ID);

    public static final RegistryObject<TieredBow> COMPOUND_BOW = ITEMS.register("compound_bow", () -> new TieredBow(new Item.Properties().group(ModItemGroups.WEAPONS), BowTiers.COMPOUND_BOW));
    public static final RegistryObject<CustomArrowItem> COMPOUND_ARROW = ITEMS.register("compound_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 4.5d)));
    public static final RegistryObject<CustomArrowItem> TRANQUILLIZER_ARROW = ITEMS.register("tranquillizer_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow,0.2, 10)));
    public static final RegistryObject<CustomArrowItem> BONE_ARROW = ITEMS.register("bone_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow,2.2d)));
    public static final RegistryObject<CustomArrowItem> DIAMOND_ARROW = ITEMS.register("diamond_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow,3.5)));
    public static final RegistryObject<CustomArrowItem> EMERALD_ARROW = ITEMS.register("emerald_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow,5d)));
    public static final RegistryObject<CustomArrowItem> GOLD_ARROW = ITEMS.register("golden_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow,0.5)));
    public static final RegistryObject<CustomArrowItem> IORN_ARROW = ITEMS.register("iron_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow,2.5)));
    public static final RegistryObject<CustomArrowItem> NETHERITE_ARROW = ITEMS.register("netherite_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow,4d)));
    public static final RegistryObject<CustomArrowItem> STONE_ARROW = ITEMS.register("stone_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow,1)));
    public static final RegistryObject<CustomArrowItem> WOODEN_ARROW = ITEMS.register("wooden_arrow", () -> new CustomArrowItem(new Item.Properties().group(ModItemGroups.WEAPONS), (world, arrow, shooter) -> new CustomArrow(shooter, world, arrow, 0.5d)));

    public static final RegistryObject<ForgeSpawnEggItem> PARAPUZOSIA_SPAWN_EGG = ITEMS.register("parapuzosia_spawn_egg", () -> new ForgeSpawnEggItem(() -> EntityInit.PARAPUZOSIA.get(), 0x392829,0x77554c, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
    public static final RegistryObject<ForgeSpawnEggItem> EOSQUALODON_SPAWN_EGG = ITEMS.register("eosqualodon_spawn_egg", () -> new ForgeSpawnEggItem(() -> EntityInit.EOSQUALODON.get(), 2239024, 16777215, new Item.Properties().group(ModItemGroups.SPAWN_EGGS)));
}
