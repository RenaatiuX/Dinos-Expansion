package com.rena.dinosexpansion.common.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;

public class TieredSword extends SwordItem {

    public TieredSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }
}
