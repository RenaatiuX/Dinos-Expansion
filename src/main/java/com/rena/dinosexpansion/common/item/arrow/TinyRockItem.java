package com.rena.dinosexpansion.common.item.arrow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TinyRockItem extends ArrowItem {
    public TinyRockItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        return super.createArrow(worldIn, stack, shooter);
    }
}
