package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.enchantments.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.QuickChargeEnchantment;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentInit {

    public static final DeferredRegister<Enchantment> VANILLA = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "minecraft");
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DinosExpansion.MOD_ID);

    //Vanilla
    public static final RegistryObject<BetterQuickCharge> BETTER_QUICK_CHARGE = VANILLA.register("quick_charge", BetterQuickCharge::new);
    public static final RegistryObject<BetterPiercing> BETTER_PIERCING = VANILLA.register("piercing", BetterPiercing::new);

    //Custom
    public static final RegistryObject<ElectricEnchantment> ELECTRIC_ENCHANTMENT = ENCHANTMENTS.register("electric", ElectricEnchantment::new);
    public static final RegistryObject<IceEnchantment> ICE_ENCHANTMENT = ENCHANTMENTS.register("iced_arrow", IceEnchantment::new);
    public static final RegistryObject<AquaticEnchantment> AQUATIC_ENCHANT = ENCHANTMENTS.register("aquatic_enchant", AquaticEnchantment::new);
    public static final RegistryObject<BlessingUnknownEnchantment> BLESSING_UNKNOWN = ENCHANTMENTS.register("blessing_unknown", BlessingUnknownEnchantment::new);
    public static final RegistryObject<BetterJump> BETTER_JUMP = ENCHANTMENTS.register("better_jump", BetterJump::new);
    public static final RegistryObject<AmmoReservationEnchantment> AMMO_RESERVATION = ENCHANTMENTS.register("ammo_reservation", AmmoReservationEnchantment::new);
}
