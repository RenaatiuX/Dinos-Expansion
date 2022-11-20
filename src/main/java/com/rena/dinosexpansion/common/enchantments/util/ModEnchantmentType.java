package com.rena.dinosexpansion.common.enchantments.util;

import com.rena.dinosexpansion.common.item.TieredChakram;
import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.enchantment.EnchantmentType;

public class ModEnchantmentType{
    public static final EnchantmentType TYPE_THROWING_ITEM = EnchantmentType.create("throwing_item", ModTags.Items.AQUATIC_ENCHANT::contains);

}
