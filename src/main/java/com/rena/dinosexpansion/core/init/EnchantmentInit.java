package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.enchantments.BetterPiercing;
import com.rena.dinosexpansion.common.enchantments.BetterQuickCharge;
import com.rena.dinosexpansion.common.enchantments.ElectricEnchantment;
import com.rena.dinosexpansion.common.enchantments.IceEnchantment;
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
}
