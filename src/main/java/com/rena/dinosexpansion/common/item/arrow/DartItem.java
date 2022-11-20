package com.rena.dinosexpansion.common.item.arrow;

import com.rena.dinosexpansion.common.entity.projectile.DartEntity;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DartItem extends ArrowItem {
    public DartItem(Properties builder) {
        super(builder);
    }

    @Override
    public AbstractArrowEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new DartEntity(EntityInit.DART.get(), world, shooter, stack);
    }
}
