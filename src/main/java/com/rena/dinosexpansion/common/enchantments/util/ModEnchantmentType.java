package com.rena.dinosexpansion.common.enchantments.util;

import com.rena.dinosexpansion.common.item.TieredChakram;
import net.minecraft.enchantment.EnchantmentType;

public class ModEnchantmentType{
    public static EnchantmentType TYPE_THROWING_ITEM = EnchantmentType.create("throwing_item",
            (item) -> item instanceof TieredChakram);

}
