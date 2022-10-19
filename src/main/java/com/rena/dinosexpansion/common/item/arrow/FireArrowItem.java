package com.rena.dinosexpansion.common.item.arrow;

import com.rena.dinosexpansion.common.item.CustomArrowItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FireArrowItem extends CustomArrowItem {

    public FireArrowItem(Properties builder, IArrowEntitySupplier arrowSupplier) {
        super(builder, arrowSupplier);
    }

    @Override
    public AbstractArrowEntity createArrow(World world, ItemStack arrow, LivingEntity shooter) {
        return super.createArrow(world, arrow, shooter);
    }
}
