package com.rena.dinosexpansion.common.entity.misc;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Supplier;

public class CustomEggEntity extends ProjectileItemEntity {
    private final Supplier<Item> itemSupplier;
    public CustomEggEntity(EntityType<? extends CustomEggEntity> type, World worldIn, Supplier<Item> itemSupplier) {
        super(type, worldIn);
        this.itemSupplier = itemSupplier;
    }

    public CustomEggEntity(World worldIn, LivingEntity throwerIn, Supplier<Item> itemSupplier) {
        super(EntityType.EGG, throwerIn, worldIn);
        this.itemSupplier = itemSupplier;
    }

    public CustomEggEntity(World worldIn, double x, double y, double z, Supplier<Item> itemSupplier) {
        super(EntityType.EGG, x, y, z, worldIn);
        this.itemSupplier = itemSupplier;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getPosX(), this.getPosY(), this.getPosZ(), ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    /**
     * Called when the arrow hits an entity
     */
    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        result.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.getShooter()), 0.0F);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {
            if (this.rand.nextInt(8) == 0) {
                int i = 1;
                if (this.rand.nextInt(32) == 0) {
                    i = 4;
                }
                for(int j = 0; j < i; ++j) {
                    ChickenEntity chickenentity = EntityType.CHICKEN.create(this.world);
                    chickenentity.setGrowingAge(-24000);
                    chickenentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
                    this.world.addEntity(chickenentity);
                }
            }
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }

    }

    @Override
    protected Item getDefaultItem() {
        return this.itemSupplier.get();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
