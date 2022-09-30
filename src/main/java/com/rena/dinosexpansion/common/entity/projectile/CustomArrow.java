package com.rena.dinosexpansion.common.entity.projectile;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CustomArrow extends AbstractArrowEntity implements INarcoticProjectile{

    private final int narcoticValue;
    private final ItemStack arrowStack;

    public CustomArrow(EntityType<CustomArrow> type, World world) {
        super(type, world);
        narcoticValue = 0;
        arrowStack = ItemStack.EMPTY;
    }

    public CustomArrow( double x, double y, double z, World world, ItemStack arrow) {
        this(x, y, z, world, arrow, 0);
    }

    public CustomArrow( double x, double y, double z, World world, ItemStack arrow, int narcoticValue) {
        super(EntityInit.CUSTOM_ARROW.get(), x, y, z, world);
        this.arrowStack = arrow;
        this.narcoticValue = narcoticValue;
    }

    public CustomArrow(LivingEntity shooter, World world, ItemStack arrow) {
        this(shooter, world, arrow, 0);
    }

    public CustomArrow(LivingEntity shooter, World world, ItemStack arrow, int narcoticValue) {
        super(EntityInit.CUSTOM_ARROW.get(), shooter, world);
        this.arrowStack = arrow;
        this.narcoticValue = narcoticValue;
        DinosExpansion.LOGGER.info("entity constructed");
    }

    @Override
    public ItemStack getArrowStack() {
        return this.arrowStack.copy();
    }

    @Override
    public int getNarcoticValue() {
        return this.narcoticValue;
    }
}
