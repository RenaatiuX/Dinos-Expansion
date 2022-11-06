package com.rena.dinosexpansion.common.entity.misc;

import com.rena.dinosexpansion.core.init.EntityInit;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ChakramEntity extends AbstractArrowEntity {

    protected static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(ChakramEntity.class, DataSerializers.ITEMSTACK);
    protected static final DataParameter<Float> PREV_ROTATION = EntityDataManager.createKey(ChakramEntity.class, DataSerializers.FLOAT);
    protected static final DataParameter<Float> ROTATION = EntityDataManager.createKey(ChakramEntity.class, DataSerializers.FLOAT);

    protected boolean dealtDamage;
    protected boolean enchanted;
    protected int loyaltyLevel;
    protected int returningTicks;

    public ChakramEntity(EntityType<? extends ChakramEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ChakramEntity(World worldIn, LivingEntity thrower, ItemStack thrownStackIn) {
        super(EntityInit.CHAKRAM.get(), thrower, worldIn);
        setArrowStack(thrownStackIn);
    }

    @OnlyIn(Dist.CLIENT)
    public ChakramEntity(World worldIn, double x, double y, double z) {
        super(EntityInit.CHAKRAM.get(), x, y, z, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ITEM, ItemStack.EMPTY);
        this.dataManager.register(ROTATION, 0.0F);
        this.dataManager.register(PREV_ROTATION, 0.0F);
    }

    @Override
    public void tick() {
        this.dataManager.set(PREV_ROTATION, this.getRotation());
        if (this.timeInGround > 4) {
            this.dealtDamage = true;
        } else {
            this.dataManager.set(ROTATION, this.getRotation() + 45.0F);

            if (!this.hasNoGravity() && !this.getNoClip()) {
                Vector3d motion = this.getMotion();
                this.setMotion(motion.x, motion.y + 0.025, motion.z);
            }
        }

        Entity entity = this.getShooter();
        Vector3d vector3d;

        if ((this.dealtDamage || this.getNoClip()) && entity != null) {
            if (loyaltyLevel > 0 && !this.shouldReturnToThrower()) {
                if (!this.world.isRemote && this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
                    this.entityDropItem(this.getArrowStack(), 0.1F);
                }

                this.remove();
            } else if (loyaltyLevel > 0) {
                this.setNoClip(true);
                vector3d = new Vector3d(entity.getPosX() - this.getPosX(), entity.getPosYEye() - this.getPosY(), entity.getPosZ() - this.getPosZ());
                this.setRawPosition(this.getPosX(), this.getPosY() + vector3d.y * 0.015D * loyaltyLevel, this.getPosZ());
                if (this.world.isRemote) {
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

    private boolean shouldReturnToThrower() {
        Entity entity = this.getShooter();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        } else {
            return false;
        }
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

    @Nullable
    @Override
    protected EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
        return this.dealtDamage ? null : super.rayTraceEntities(startVec, endVec);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        Entity entity = result.getEntity();
        float f = 8.0F;
        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) entity;
            f += EnchantmentHelper.getModifierForCreature(this.getArrowStack(), livingentity.getCreatureAttribute());
        }

        Entity entity1 = this.getShooter();
        DamageSource damagesource = DamageSource.causeTridentDamage(this, entity1 == null ? this : entity1);
        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.ITEM_TRIDENT_HIT;
        if (entity.attackEntityFrom(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity) entity;
                if (entity1 instanceof LivingEntity) {
                    EnchantmentHelper.applyThornEnchantments(livingentity1, entity1);
                    EnchantmentHelper.applyArthropodEnchantments((LivingEntity) entity1, livingentity1);
                }

                this.arrowHit(livingentity1);
            }
        }
        this.setMotion(this.getMotion().mul(-0.01D, -0.1D, -0.01D));
        this.playSound(soundevent, 1.0F, 1.0F);
    }

    @Override
    protected SoundEvent getHitEntitySound() {
        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
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
        if (compound.contains("Chakram", 10)) {
            setArrowStack(ItemStack.read(compound.getCompound("Chakram")));
        }

        this.dealtDamage = compound.getBoolean("DealtDamage");
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("Chakram", getArrowStack().write(new CompoundNBT()));
        compound.putBoolean("DealtDamage", this.dealtDamage);
    }

    public float getRotation() {
        return this.dataManager.get(ROTATION);
    }

    public float getPrevRotation() {
        return this.dataManager.get(PREV_ROTATION);
    }

    @Override
    public void func_225516_i_() {
        int i = loyaltyLevel;
        if (this.pickupStatus != AbstractArrowEntity.PickupStatus.ALLOWED || i <= 0) {
            super.func_225516_i_();
        }
    }


    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isInRangeToRender3d(double x, double y, double z) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean canRenderOnFire() {
        return false;
    }
}
