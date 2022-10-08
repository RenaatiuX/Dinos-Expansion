package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.common.entity.projectile.CustomArrow;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CustomArrowItem extends ArrowItem {
    private final IArrowEntitySupplier arrowSupplier;
    public CustomArrowItem(Properties p_i48531_1_, IArrowEntitySupplier arrowSupplier) {
        super(p_i48531_1_);
        this.arrowSupplier = arrowSupplier;
    }

    @Override
    public AbstractArrowEntity createArrow(World world, ItemStack arrow, LivingEntity shooter) {
        return arrowSupplier.getArrow(world, arrow, shooter);
    }

    public interface IArrowEntitySupplier{
        CustomArrow getArrow(World worldIn, ItemStack stack, LivingEntity shooter);
    }
}
