package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.enchantments.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentInit {

    public static final DeferredRegister<Enchantment> VANILLA = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "minecraft");
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DinosExpansion.MOD_ID);

    //Vanilla
   /**overriding vanilla enchants is a big nono, use {@link net.minecraftforge.common.extensions.IForgeItem#canApplyAtEnchantingTable(ItemStack, Enchantment)}*/

    //Custom
    public static final RegistryObject<ElectricEnchantment> ELECTRIC_ENCHANTMENT = ENCHANTMENTS.register("electric", ElectricEnchantment::new);
    public static final RegistryObject<IceEnchantment> ICE_ENCHANTMENT = ENCHANTMENTS.register("iced_arrow", IceEnchantment::new);
    public static final RegistryObject<AquaticEnchantment> AQUATIC_ENCHANT = ENCHANTMENTS.register("aquatic_enchant", AquaticEnchantment::new);
    public static final RegistryObject<BlessingUnknownEnchantment> BLESSING_UNKNOWN = ENCHANTMENTS.register("blessing_unknown", BlessingUnknownEnchantment::new);
    public static final RegistryObject<BetterJumpEnchantment> BETTER_JUMP = ENCHANTMENTS.register("better_jump", BetterJumpEnchantment::new);
    public static final RegistryObject<AmmoReservationEnchantment> AMMO_RESERVATION = ENCHANTMENTS.register("ammo_reservation", AmmoReservationEnchantment::new);
    public static final RegistryObject<GuidanceEnchantment> GUIDANCE_ENCHANTMENT = ENCHANTMENTS.register("guidance_enchantment", GuidanceEnchantment::new);
}
