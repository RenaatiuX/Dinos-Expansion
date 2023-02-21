package com.rena.dinosexpansion.common.item.enums;

import com.rena.dinosexpansion.common.entity.misc.BoomerangEntity;
import com.rena.dinosexpansion.common.item.TieredBoomerang;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public enum BoomerangTiers implements TieredBoomerang.BoomerangTier {
    WOOD(5, 5, 100, 30);

    private final double damageAddition;
    private final int enchantability, durability, range;

    BoomerangTiers(double damageAddition, int enchantability, int durability, int range) {
        this.damageAddition = damageAddition;
        this.enchantability = enchantability;
        this.durability = durability;
        this.range = range;
    }

    @Override
    public double getDamageAddition() {
        return this.damageAddition;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public int getRange() {
        return this.range;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }
}
