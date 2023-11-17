package com.rena.dinosexpansion.common.entity.projectile;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ThrowEggEntity extends ProjectileItemEntity {

    protected double chanceEntity = .01d, chanceTamedEntity = .3d;
    protected int maxEntitiesSpawn = 1, minEntitiesSpawn = 0;

    protected Supplier<EntityType<?>> spawningEntity = () -> EntityType.CHICKEN;

    protected boolean allTamedOrNot = false;

    public ThrowEggEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
        super(p_i50155_1_, p_i50155_2_);
    }

    public ThrowEggEntity(double x, double y, double z, World world) {
        super(EntityInit.THROWN_EGG.get(), x, y, z, world);
    }

    public ThrowEggEntity(LivingEntity shooter, World world) {
        super(EntityInit.THROWN_EGG.get(), shooter, world);
    }

    /**
     * @param chanceEntity defines how high the chance is that an entity will spawn at impact, this will always be clamped inside [0, 1]
     */
    public void setChanceEntity(double chanceEntity) {
        this.chanceEntity = MathHelper.clamp(chanceEntity, 0, 1);
    }

    /**
     * @param maxEntitiesSpawn this defines how many entities can spawn, only when the first entity spawns, then the same chance will be applied to maybe spawn the second and so on and so forth
     *                         this will be clamped above or equal to {@link ThrowEggEntity#minEntitiesSpawn}
     */
    public void setMaxEntitiesSpawn(int maxEntitiesSpawn) {
        this.maxEntitiesSpawn = Math.max(this.minEntitiesSpawn, maxEntitiesSpawn);
    }

    /**
     * @param chanceTamedEntity this defines that when an entity is spawned if it spawns already tamed, this will always be clamped to [0, 1]
     *                          this of course only works on {@link net.minecraft.entity.passive.TameableEntity}
     */
    public void setChanceTamedEntity(double chanceTamedEntity) {
        this.chanceTamedEntity = MathHelper.clamp(chanceTamedEntity, 0d, 1d);
    }

    /**
     * @param minEntitiesSpawn this defines how many entities at least spawn, not that this will be clamped below the {@link ThrowEggEntity#maxEntitiesSpawn}
     */
    public void setMinEntitiesSpawn(int minEntitiesSpawn) {
        this.minEntitiesSpawn = MathHelper.clamp(minEntitiesSpawn, 0, this.maxEntitiesSpawn);
    }

    /**
     * @param spawningEntity this is the entity that will be spawned
     */
    public void setSpawningEntity(Supplier<EntityType<?>> spawningEntity) {
        this.spawningEntity = spawningEntity;
    }

    /**
     *
     * @param allTamedOrNot this defines when there can spawn multiple entities if either all will be tamed or not or if it can deffer between all spawning entities
     */
    public void setAllTamedOrNot(boolean allTamedOrNot) {
        this.allTamedOrNot = allTamedOrNot;
    }

    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        result.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.getShooter()), 0.0F);
    }

    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {
            int amountEntitiesSpawn = minEntitiesSpawn;
            if (minEntitiesSpawn < maxEntitiesSpawn) {
                for (int i = 0; i < maxEntitiesSpawn - minEntitiesSpawn; i++) {
                    if (this.rand.nextDouble() < this.chanceEntity){
                        amountEntitiesSpawn++;
                    }else
                        break;
                }
            }

            boolean tamed = this.rand.nextDouble() < this.chanceTamedEntity;
            for (int j = 0; j < amountEntitiesSpawn; ++j) {
                Entity e = this.spawningEntity.get().create(this.world);
                e.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
                if (e instanceof AgeableEntity)
                    ((AgeableEntity) e).setGrowingAge(-24000);
                if (tamed) {
                    if (e instanceof Dinosaur) {
                        Dinosaur dinosaur = (Dinosaur) e;
                        dinosaur.setKnockedOutBy((LivingEntity) this.getShooter());
                        dinosaur.onKnockoutTaming();
                    } else if (e instanceof TameableEntity) {
                        if (getShooter() instanceof PlayerEntity)
                            ((TameableEntity) e).setTamedBy((PlayerEntity) getShooter());
                        else
                            ((TameableEntity) e).setTamed(true);
                    }
                }
                if (!allTamedOrNot)
                    tamed = this.rand.nextDouble() < this.chanceTamedEntity;
                this.world.addEntity(e);
            }

            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }

    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.chanceEntity = nbt.getDouble("chanceEntity");
        this.chanceTamedEntity = nbt.getDouble("chanceTamedEntity");
        this.minEntitiesSpawn = nbt.getInt("minEntities");
        this.maxEntitiesSpawn = nbt.getInt("maxEntities");
        this.allTamedOrNot = nbt.getBoolean("allTamedOrNot");
        EntityType<?> spawn = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(nbt.getString("spawningEntity")));
        this.spawningEntity = () -> spawn;
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putDouble("chanceEntity", this.chanceEntity);
        nbt.putDouble("chanceTamedEntity", this.chanceTamedEntity);
        nbt.putInt("minEntities", this.minEntitiesSpawn);
        nbt.putInt("maxEntities", this.maxEntitiesSpawn);
        nbt.putBoolean("allTamedOrNot", this.allTamedOrNot);
        nbt.putString("spawningEntity", ForgeRegistries.ENTITIES.getKey(this.spawningEntity.get()).toString());
    }

    @Override
    protected Item getDefaultItem() {
        return Items.EGG;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    /**
     * spawns the particle
     *
     * @param id
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getPosX(), this.getPosY(), this.getPosZ(), ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }
}
