package com.rena.dinosexpansion.common.entity.terrestrial;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;

public abstract class TerrestrialEntity extends AnimalEntity {

    protected TerrestrialEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }
}
