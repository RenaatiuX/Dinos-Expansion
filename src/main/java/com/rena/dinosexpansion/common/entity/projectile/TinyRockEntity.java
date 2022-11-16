package com.rena.dinosexpansion.common.entity.projectile;

import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TinyRockEntity extends CustomArrow{

    public TinyRockEntity(EntityType<CustomArrow> type, World world) {
        super(type, world);
    }

    public TinyRockEntity(EntityType<CustomArrow> type, double x, double y, double z, World worldIn) {
        this(type, worldIn);
        this.setPosition(x, y, z);
    }

    public TinyRockEntity(EntityType<CustomArrow> type, LivingEntity shooter, World worldIn) {
        this(type, shooter.getPosX(), shooter.getPosYEye() - (double)0.1F, shooter.getPosZ(), worldIn);
        this.setShooter(shooter);
        if (shooter instanceof PlayerEntity) {
            this.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
        }

    }

    @Override
    public ItemStack getArrowStack() {
        return new ItemStack(ItemInit.TINY_ROCK.get());
    }

    @Override
    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);
    }

    @Override
    public int getNarcoticValue() {
        return 2;
    }
}
