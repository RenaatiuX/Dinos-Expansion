package com.rena.dinosexpansion.common.entity.terrestrial;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class Triceratops extends Dinosaur implements IAnimatable, IAnimationTickable {
    private static final DataParameter<Integer> CHARGE_COOLDOWN = EntityDataManager.createKey(Triceratops.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> HAS_TARGET = EntityDataManager.createKey(Triceratops.class, DataSerializers.BOOLEAN);
    private int stunnedTick;
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public Triceratops(EntityType<Dinosaur> type, World world, DinosaurInfo info, int minLevel, int maxLevel) {
        super(type, world, info, minLevel, maxLevel);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 80.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(CHARGE_COOLDOWN, 0);
        this.dataManager.register(HAS_TARGET, false);
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.stunnedTick = nbt.getInt("StunTick");
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putInt("StunTick", this.stunnedTick);
    }

    public void setChargeCooldownTicks(int ticks) {
        this.dataManager.set(CHARGE_COOLDOWN, ticks);
    }

    public int getChargeCooldownTicks() {
        return this.dataManager.get(CHARGE_COOLDOWN);
    }

    public boolean hasChargeCooldown() {
        return this.dataManager.get(CHARGE_COOLDOWN) > 0;
    }

    public void resetChargeCooldownTicks() {
        this.dataManager.set(CHARGE_COOLDOWN, 50);
    }

    public void setHasTarget(boolean hasTarget) {
        this.dataManager.set(HAS_TARGET, hasTarget);
    }

    public boolean hasTarget() {
        return this.dataManager.get(HAS_TARGET);
    }

    @Override
    public int getHorizontalFaceSpeed() {
        return this.isSprinting() ? 1 : 50;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.isAlive()) {
            return;
        }
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.isMovementBlocked() ? 0.0 : 0.2);
        if (this.stunnedTick > 0) {
            --this.stunnedTick;
            this.func_213682_eh();
        }
    }

    private void func_213682_eh() {
        if (this.rand.nextInt(6) == 0) {
            double d0 = this.getPosX() - (double)this.getWidth() * Math.sin(this.renderYawOffset * ((float)Math.PI / 180F)) + (this.rand.nextDouble() * 0.6D - 0.3D);
            double d1 = this.getPosY() + (double)this.getHeight() - 0.3D;
            double d2 = this.getPosZ() + (double)this.getWidth() * Math.cos(this.renderYawOffset * ((float)Math.PI / 180F)) + (this.rand.nextDouble() * 0.6D - 0.3D);
            this.world.addParticle(ParticleTypes.ENTITY_EFFECT, d0, d1, d2, 0.4980392156862745D, 0.5137254901960784D, 0.5725490196078431D);
        }
    }

    @Override
    protected void constructKnockBackVector(LivingEntity entityIn) {
        this.stunnedTick = 60;
        this.resetChargeCooldownTicks();
        this.getNavigator().clearPath();
        this.playSound(SoundEvents.ENTITY_RAVAGER_STUNNED, 1.0f, 1.0f);
        this.world.setEntityState(this, (byte)39);
        entityIn.applyEntityCollision(this);
        entityIn.velocityChanged = true;
    }

    @Override
    protected boolean isMovementBlocked() {
        return super.isMovementBlocked() || this.stunnedTick > 0;
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 39) {
            this.stunnedTick = 60;
        }
        super.handleStatusUpdate(id);
    }

    @Override
    protected void updateAITasks() {
        if (this.getMoveHelper().isUpdating()) {
            this.setSprinting(this.getMoveHelper().getSpeed() >= 1.5D);
        } else {
            this.setSprinting(false);
        }
        super.updateAITasks();
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public List<Item> getFood() {
        return null;
    }

    @Override
    protected Rarity getinitialRarity() {
        return null;
    }

    @Override
    protected Gender getInitialGender() {
        return null;
    }

    @Override
    protected int reduceNarcotic(int narcoticValue) {
        return 0;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return this.ticksExisted;
    }
}
