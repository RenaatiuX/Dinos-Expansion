package com.rena.dinosexpansion.common.entity.terrestrial.ambient;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
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

import javax.annotation.Nullable;
import java.util.List;

public class Campanile extends AmbientDinosaur implements IAnimatable, IAnimationTickable {
    private static final DataParameter<Boolean> IS_IN_SHELL = EntityDataManager.createKey(Campanile.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public int ticksToShell;
    public float shellProgress;

    public Campanile(EntityType<Campanile> type, World world) {
        super(type, world, new DinosaurInfo("campanile", 20, 20, 10, SleepRhythmGoal.SleepRhythm.NONE), generateLevelWithinBounds(20, 50));
        updateInfo();
    }

    public Campanile(World world) {
        this(EntityInit.CAMPANILE.get(), world);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.8D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.5D) {
            @Override
            public boolean shouldExecute() {
                return !Campanile.this.isInShell() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F) {
            @Override
            public boolean shouldExecute() {
                return !Campanile.this.isInShell() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this) {
            @Override
            public boolean shouldExecute() {
                return !Campanile.this.isInShell() && super.shouldExecute();
            }
        });
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.1D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0.1D);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_IN_SHELL, false);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("InShell", this.isInShell());
        compound.putInt("ShellTick", this.ticksToShell);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setInShell(compound.getBoolean("InShell"));
        this.ticksToShell = compound.getInt("ShellTick");
    }

    public boolean isInShell() {
        return this.dataManager.get(IS_IN_SHELL);
    }

    public void setInShell(boolean inShell) {
        this.dataManager.set(IS_IN_SHELL, inShell);
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

    @Override
    public List<Item> getFood() {
        return null;
    }

    @Override
    protected int reduceNarcotic(int narcoticValue) {
        return 0;
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
        return entity.getWidth() >= 1.5;
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
            event.getController().setAnimation(new AnimationBuilder().addAnimation("campanile.hiding", ILoopType.EDefaultLoopTypes.LOOP));
        } else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("campanile.move", ILoopType.EDefaultLoopTypes.LOOP));
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("campanile.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(10);
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
