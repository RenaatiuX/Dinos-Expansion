package com.rena.dinosexpansion.common.entity.terrestrial.ambient;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

public class Campanile extends CreatureEntity implements IAnimatable, IAnimationTickable {
    private static final DataParameter<Boolean> IS_IN_SHELL = EntityDataManager.createKey(Campanile.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public boolean isInShell;
    public int ticksToShell;
    public float shellProgress;

    public Campanile(EntityType<Campanile> type, World world) {
        super(type, world);
    }

    public Campanile(World world) {
        this(EntityInit.CAMPANILE.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D){
            @Override
            public boolean shouldExecute() {
                return !Campanile.this.isInShell() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F){
            @Override
            public boolean shouldExecute() {
                return !Campanile.this.isInShell() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this){
            @Override
            public boolean shouldExecute() {
                return !Campanile.this.isInShell() && super.shouldExecute();
            }
        });
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.1D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_IN_SHELL, false);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("InShell", this.isInShell);
        compound.putInt("ShellTick", this.ticksToShell);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setInShell(compound.getBoolean("InShell"));
        this.ticksToShell = compound.getInt("ShellTick");
    }

    public boolean isInShell() {
        if (world.isRemote) {
            boolean isSleeping = this.dataManager.get(IS_IN_SHELL);
            this.isInShell = isSleeping;
            return isSleeping;
        }
        return isInShell;
    }

    public void setInShell(boolean inShell) {
        this.dataManager.set(IS_IN_SHELL, inShell);
        if (!world.isRemote) {
            this.isInShell = inShell;
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();
        boolean inShell = isInShell();
        if (inShell && shellProgress < 20.0F) {
            shellProgress += 0.5F;
        } else if (!inShell && shellProgress > 0.0F) {
            shellProgress -= 0.5F;
        }
        if (ticksToShell > 0) {
            ticksToShell--;
        }
        if (!this.world.isRemote) {
            if (isThereNearbyMobs() && this.ticksToShell == 0 || this.onGround && this.ticksToShell == 0) {
                if (!this.isInShell()) {
                    this.setInShell(true);
                }
            } else {
                if (this.isInShell()) {
                    this.setInShell(false);
                }
            }
        }
    }

    public boolean isThereNearbyMobs() {
        List<LivingEntity> list = world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(2.0D, 2.0D, 2.0D), null);
        return list.stream().anyMatch(this::isAScaryAnimal);
    }

    public boolean isAScaryAnimal(Entity entity) {
        if (entity instanceof PlayerEntity && !((PlayerEntity) entity).isCreative()) {
            return true;
        }
        if (entity instanceof Dinosaur) {
            return true;
        }
        return entity.getWidth() >= 1.2;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (amount > 0 && this.isInShell() && source.getTrueSource() != null) {
            this.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, this.getRNG().nextFloat() + 0.8F);
            if (this.getRidingEntity() != null) {
                return super.attackEntityFrom(source, amount);
            }
            return false;
        }
        if (!this.isInShell()) {
            this.setInShell(true);
        }
        return super.attackEntityFrom(source, amount);
    }

    private PlayState predicate(AnimationEvent<Campanile> event) {
        if (this.isInShell()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("campanile.hidein", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("campanile.move", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("campanile.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 20, this::predicate));
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
