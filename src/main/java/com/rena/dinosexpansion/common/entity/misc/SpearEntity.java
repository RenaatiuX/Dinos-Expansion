package com.rena.dinosexpansion.common.entity.misc;

import com.google.common.collect.Lists;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.EnchantmentInit;
import com.rena.dinosexpansion.core.init.EntityInit;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Arrays;

public class SpearEntity extends AbstractArrowEntity {
    protected static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(SpearEntity.class, DataSerializers.ITEMSTACK);
    protected boolean dealtDamage;
    protected boolean enchanted;
    protected int loyaltyLevel;
    protected int returningTicks;
    protected double damage = 2d;
    protected double knockbackStrength;

    public SpearEntity(EntityType<SpearEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public SpearEntity(World world, LivingEntity thrower, ItemStack item) {
        super(EntityInit.SPEAR.get(), thrower, world);
        setArrowStack(item);
    }

    @OnlyIn(Dist.CLIENT)
    public SpearEntity(World worldIn, double x, double y, double z) {
        super(EntityInit.SPEAR.get(), x, y, z, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ITEM, ItemStack.EMPTY);
    }

    @Override
    public void tick() {
        if (this.timeInGround > 4) {
            this.dealtDamage = true;
        }

        Entity entity = this.getShooter();
        if ((this.dealtDamage || this.getNoClip()) && entity != null) {
            if (loyaltyLevel > 0 && !shouldReturnToThrower()) {
                if (!this.world.isRemote && this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
                    this.entityDropItem(this.getArrowStack(), 0.1F);
                }

                this.remove();
            } else if (loyaltyLevel > 0) {
                this.setNoClip(true);
                Vector3d vector3d = new Vector3d(entity.getPosX() - this.getPosX(), entity.getPosYEye() - this.getPosY(),
                        entity.getPosZ() - this.getPosZ());
                this.setRawPosition(this.getPosX(), this.getPosY() + vector3d.y * 0.015D * loyaltyLevel, this.getPosZ());
                if (this.world.isRemote()) {
                    this.lastTickPosY = this.getPosY();
                }

                double d0 = 0.05D * loyaltyLevel;
                this.setMotion(this.getMotion().scale(0.95D).add(vector3d.normalize().scale(d0)));
                if (this.returningTicks == 0) {
                    this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.returningTicks;
            }
        }

        super.tick();
    }

    protected boolean shouldReturnToThrower() {
        Entity entity = this.getShooter();
        return entity != null && entity.isAlive() && (!(entity instanceof ServerPlayerEntity) || !entity.isSpectator());
    }

    @Override
    public ItemStack getArrowStack() {
        return this.getDataManager().get(ITEM).copy();
    }

    protected void setArrowStack(final ItemStack stack) {
        this.getDataManager().set(ITEM, stack.copy());
        this.loyaltyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.LOYALTY, stack);
        this.enchanted = stack.hasEffect();
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if (key.equals(ITEM)) {
            ItemStack stack = getArrowStack();
            this.loyaltyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.LOYALTY, stack);
            this.enchanted = stack.hasEffect();
        }
    }

    //if u leave that as default it will apply piercing, but when u did override this method and didn´t call the super the piercing basically didn´t got applied
    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        Entity entity = result.getEntity();
        float f = (float) this.getMotion().length();
        int i = MathHelper.ceil(MathHelper.clamp((double) f * damage, 0.0D, 2.147483647E9D));
        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(10);
            }

            if (this.hitEntities == null) {
                this.hitEntities = Lists.newArrayListWithCapacity(10);
            }

            if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
                this.setMotion(this.getMotion().mul(-0.01D, -0.1D, -0.01D));
                return;
            }

            this.piercedEntities.add(entity.getEntityId());
        }

        if (this.getIsCritical()) {
            long j = this.rand.nextInt(i / 2 + 2);
            i = (int) Math.min(j + (long) i, 2147483647L);
        }

        Entity entity1 = this.getShooter();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.causeArrowDamage(this, this);
        } else {
            damagesource = DamageSource.causeArrowDamage(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity) entity1).setLastAttackedEntity(entity);
            }
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        if (this.isBurning() && !flag) {
            entity.setFire(5);
        }

        if (entity.attackEntityFrom(damagesource, (float) i)) {
            if (flag) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;
                if (!this.world.isRemote && this.getPierceLevel() <= 0) {
                    livingentity.setArrowCountInEntity(livingentity.getArrowCountInEntity() + 1);
                }

                if (this.knockbackStrength > 0) {
                    Vector3d vector3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale(this.knockbackStrength * 0.6D);
                    if (vector3d.lengthSquared() > 0.0D) {
                        livingentity.addVelocity(vector3d.x, 0.1D, vector3d.z);
                    }
                }

                if (!this.world.isRemote && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.applyThornEnchantments(livingentity, entity1);
                    EnchantmentHelper.applyArthropodEnchantments((LivingEntity) entity1, livingentity);
                }

                this.arrowHit(livingentity);
                if (entity1 != null && livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity) entity1).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.HIT_PLAYER_ARROW, 0.0F));
                }

                if (!entity.isAlive() && this.hitEntities != null) {
                    this.hitEntities.add(livingentity);
                }

                if (!this.world.isRemote && entity1 instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) entity1;
                    //TODO maybe add our own criteria Triggers here
                    /*
                    if (this.hitEntities != null && this.getShotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.test(serverplayerentity, this.hitEntities);
                    } else if (!entity.isAlive() && this.getShotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.test(serverplayerentity, Arrays.asList(entity));
                    }

                     */
                }
            }

            this.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.setMotion(this.getMotion().mul(-0.01D, -0.1D, -0.01D));
            }
        } else {
            this.setMotion(this.getMotion().mul(-0.01D, -0.1D, -0.01D));
        }
    }

    @Nullable
    @Override
    protected EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
        return this.dealtDamage ? null : super.rayTraceEntities(startVec, endVec);
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn) {
        Entity entity = this.getShooter();
        if (entity == null || entity.getUniqueID() == entityIn.getUniqueID()) {
            super.onCollideWithPlayer(entityIn);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Spear", 10)) {
            setArrowStack(ItemStack.read(compound.getCompound("Spear")));
        }
        dealtDamage = compound.getBoolean("damage");
        this.damage = compound.getDouble("SpearDamage");
        this.knockbackStrength = compound.getDouble("knockbackStrength");
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("Spear", getArrowStack().write(new CompoundNBT()));
        compound.putBoolean("damage", this.dealtDamage);
        compound.putDouble("SpearDamage", this.damage);
        compound.putDouble("knockbackStrength", this.knockbackStrength);
    }

    @Override
    protected float getWaterDrag() {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.AQUATIC_ENCHANT.get(), getArrowStack()) > 0 ? 1.0f : super.getWaterDrag();
    }

    @Override
    protected float getSpeedFactor() {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.AQUATIC_ENCHANT.get(), getArrowStack()) > 0 ? 1.0f : super.getSpeedFactor();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected SoundEvent getHitEntitySound() {
        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
    }

    @Override
    public void setDamage(double damage) {
        this.damage = damage;
    }

    @Override
    public double getDamage() {
        return damage;
    }


    public void setKnockbackStrengthD(double knockbackStrength) {
        this.knockbackStrength = knockbackStrength;
    }

    public double getKnockbackStrengthD() {
        return knockbackStrength;
    }

    @Override
    public void func_225516_i_() {
        int i = loyaltyLevel;
        if (this.pickupStatus != AbstractArrowEntity.PickupStatus.ALLOWED || i <= 0) {
            super.func_225516_i_();
        }
    }

    @Override
    public boolean isInRangeToRender3d(double x, double y, double z) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isEnchanted() {
        return enchanted;
    }
}
