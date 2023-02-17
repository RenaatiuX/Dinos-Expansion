package com.rena.dinosexpansion.common.item.enums;

import com.rena.dinosexpansion.common.entity.misc.BoomerangEntity;
import com.rena.dinosexpansion.common.item.TieredBoomerang;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public enum BoomerangTiers implements TieredBoomerang.BoomerangTier {
    ;

    private final BoomerangEntity boomerang;
    private final double damageAddition;
    private final int enchantability, durability;

    BoomerangTiers(BoomerangEntity boomerang, double damageAddition, int enchantability, int durability) {
        this.boomerang = boomerang;
        this.damageAddition = damageAddition;
        this.enchantability = enchantability;
        this.durability = durability;
    }

    @Override
    public BoomerangEntity createBoomerang(World world, PlayerEntity player, ItemStack stack, Hand hand) {
        return this.boomerang;
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
    public int getDurability() {
        return this.durability;
    }
}
