package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.common.entity.misc.BoomerangEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BoomerangTiers implements TieredBoomerang.BoomerangSupplier {
    @Override
    public BoomerangEntity createBoomerang(World world, PlayerEntity player, ItemStack stack, Hand hand) {
        return null;
    }

    @Override
    public double getDamageAddition() {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public int getDurability() {
        return 0;
    }
}
