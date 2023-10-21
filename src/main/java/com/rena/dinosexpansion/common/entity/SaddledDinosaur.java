package com.rena.dinosexpansion.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;

public abstract class SaddledDinosaur extends ArmoredDinosaur{
    public SaddledDinosaur(EntityType<? extends TameableEntity> type, World world, DinosaurInfo info, int level, int chestInventorySize) {
        super(type, world, info, level, chestInventorySize);
    }
}
