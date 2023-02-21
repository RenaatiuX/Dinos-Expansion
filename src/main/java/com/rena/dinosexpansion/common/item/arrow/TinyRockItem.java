package com.rena.dinosexpansion.common.item.arrow;

import com.rena.dinosexpansion.common.entity.projectile.TinyRockEntity;
import com.rena.dinosexpansion.core.init.EntityInit;
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
        return new TinyRockEntity(EntityInit.TINY_ROCK.get(), worldIn, shooter, stack);
    }
}
