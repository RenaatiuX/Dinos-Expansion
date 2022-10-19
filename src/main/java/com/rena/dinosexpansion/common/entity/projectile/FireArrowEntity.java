package com.rena.dinosexpansion.common.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FireArrowEntity extends CustomArrow{

    float damage = 1.0F;

    public FireArrowEntity(EntityType<CustomArrow> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();
    }

}
