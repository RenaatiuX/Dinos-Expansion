package com.rena.dinosexpansion.common.entity.projectile;

import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class TinyRockEntity extends CustomArrow{

    public TinyRockEntity(EntityType<CustomArrow> type, World world) {
        super(type, world);
    }

    public TinyRockEntity(EntityType<CustomArrow> type, double x, double y, double z, World worldIn) {
        this(type, worldIn);
        this.setPosition(x, y, z);
    }

    public TinyRockEntity(LivingEntity shooter, World worldIn, ItemStack rock) {
        super(shooter.getPosX(), shooter.getPosYEye() - (double)0.1F, shooter.getPosZ(), worldIn, rock, 0, 2);
        this.setShooter(shooter);
        if (shooter instanceof PlayerEntity) {
            this.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
        }

    }

    @Override
    public ItemStack getArrowStack() {
        return super.getArrowStack().isEmpty() ? new ItemStack(ItemInit.TINY_ROCK.get()) : super.getArrowStack();
    }

    @Override
    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);
    }
}
