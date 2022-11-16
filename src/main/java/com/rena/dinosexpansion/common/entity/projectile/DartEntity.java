package com.rena.dinosexpansion.common.entity.projectile;

import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DartEntity extends ArrowEntity {
    public DartEntity(EntityType<DartEntity> type, World world) {
        super(type, world);
    }

    public DartEntity(EntityType<DartEntity> type, World worldIn, double x, double y, double z) {
        this(type, worldIn);
        this.setPosition(x, y, z);
        setDamage(1.0D);
    }

    public DartEntity(EntityType<DartEntity> type, World world, LivingEntity shooter) {
        this(type, world, shooter.getPosX(),shooter.getPosY() + (double)shooter.getEyeHeight() - (double)0.1F, shooter.getPosZ());
        this.setShooter(shooter);
        if (shooter instanceof PlayerEntity) {
            this.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemInit.DART.get());
    }

    @Override
    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);
        if (getShooter() instanceof PlayerEntity) living.addPotionEffect(new EffectInstance(Effects.POISON, 40, 3, false, true));
        else living.addPotionEffect(new EffectInstance(Effects.POISON, 30, 1, false, true));
        living.setArrowCountInEntity(living.getArrowCountInEntity() - 1);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult raytraceResultIn) {
        if (raytraceResultIn.getType() == RayTraceResult.Type.ENTITY) {
            Entity hit = raytraceResultIn.getEntity();
            Entity shooter = getShooter();
            if (hit instanceof LivingEntity) {
                if (world.isRemote || (shooter == hit))
                    return;
            }
        }
        super.onEntityHit(raytraceResultIn);
    }
}
