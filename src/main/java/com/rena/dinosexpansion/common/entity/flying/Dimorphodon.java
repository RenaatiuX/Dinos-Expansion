package com.rena.dinosexpansion.common.entity.flying;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.DinosaurFlyingMeleeAttackGoal;
import com.rena.dinosexpansion.common.entity.ia.DinosaurFollowOwnerGoal;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.common.util.enums.MoveOrder;
import com.rena.dinosexpansion.core.init.EntityInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import com.rena.dinosexpansion.core.init.SoundInit;
import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib3.core.AnimationState;
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
import java.util.UUID;

public class Dimorphodon extends DinosaurFlying implements IAnimatable, IAnimationTickable, IAngerable {
    private static final DataParameter<Integer> ANGER_TIME = EntityDataManager.createKey(Dimorphodon.class, DataSerializers.VARINT);
    private static final RangedInteger ANGER_TIME_RANGE = TickRangeConverter.convertRange(20, 39);
    private UUID persistentAngerTarget;
    public int timeUntilNextEgg = this.rand.nextInt(6000) + 6000;

    public Dimorphodon(EntityType<Dimorphodon> type, World world) {
        super(type, world, new DinosaurInfo("dimorphodon", 50, 50, 25, SleepRhythmGoal.SleepRhythm.DIURNAL), generateLevelWithinBounds(10, 50));
        updateInfo();
    }

    public Dimorphodon(World world) {
        this(EntityInit.DIMORPHODON.get(), world);
    }

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10F)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0F)
                .createMutableAttribute(Attributes.FLYING_SPEED, 0.6F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new DinosaurFlyingMeleeAttackGoal(this));
        this.goalSelector.addGoal(3, new DinosaurWalkIdleGoal());
        this.goalSelector.addGoal(4, new DinosaurFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
        this.targetSelector.addGoal(5, new ResetAngerGoal<>(this, true));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ANGER_TIME, 0);
    }

    public int getAngerTime() {
        return this.dataManager.get(ANGER_TIME);
    }

    public void setAngerTime(int time) {
        this.dataManager.set(ANGER_TIME, time);
    }

    @Nullable
    @Override
    public UUID getAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setAngerTarget(@Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    @Override
    public void func_230258_H__() {
        this.setAngerTime(ANGER_TIME_RANGE.getRandomWithinRange(this.rand));
    }

    @Override
    public List<Item> getFood() {
        return ModTags.Items.DIMORPHODON_FOOD.getAllElements();
    }

    @Override
    protected int reduceNarcotic(int narcoticValue) {
        if (getRNG().nextDouble() <= 0.05)
            return narcoticValue - 1;
        return narcoticValue;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (isSleeping()) {
            if (this.isOnGround() && isFlying()) {
                this.setFlying(false);
            }
        }
        if (!this.world.isRemote && this.isAlive() && !this.isChild() && --this.timeUntilNextEgg <= 0 && this.getRarity() != Rarity.LEGENDARY) {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            //this.entityDropItem(Items.EGG);
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("EggLayTime")) {
            this.timeUntilNextEgg = compound.getInt("EggLayTime");
        }

    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("EggLayTime", this.timeUntilNextEgg);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        if (this.getRarity() != Rarity.LEGENDARY) {
            return EntityInit.DIMORPHODON.get().create(world);
        } else {
            return null;
        }
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 6;
    }

    @Override
    public boolean isMaxGroupSize(int sizeIn) {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        int i = MathHelper.nextInt(rand, 0, 8);
        if (i < SoundInit.DIMORPHODON_AMBIENT.size() && !this.isMovementDisabled()) {
            playSound(SoundInit.DIMORPHODON_AMBIENT.get(i).get(), 1, 1.5F);
        }
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        int i = MathHelper.nextInt(rand, 0, 5);
        if (i < SoundInit.DIMORPHODON_HURT.size() && !this.isMovementDisabled()) {
            playSound(SoundInit.DIMORPHODON_HURT.get(i).get(), 1, 1.5F);
        }
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        int i = MathHelper.nextInt(rand, 0, 2);
        if (i < SoundInit.DIMORPHODON_DEATH.size()) {
            playSound(SoundInit.DIMORPHODON_DEATH.get(i).get(), 1, 1.5F);
        }
        return null;
    }

    @Override
    protected void spawnDrops(DamageSource damageSourceIn) {
        if (this.getRarity() == Rarity.LEGENDARY) {
            float probability = rand.nextFloat();
            if (probability <= 0.2F) {
                float itemCount = rand.nextInt(3) + 1;
                for (int i = 0; i < itemCount; i++) {
                    this.entityDropItem(new ItemStack(ItemInit.ELECTRONICS_PARTS.get()));
                }
            }
            probability = rand.nextFloat();
            if (probability <= 0.3F) {
                float itemCount = rand.nextInt(3) + 1;
                for (int i = 0; i < itemCount; i++) {
                    this.entityDropItem(new ItemStack(ItemInit.OIL.get()));
                }
            }
            probability = rand.nextFloat();
            if (probability <= 0.6F) {
                float itemCount = rand.nextInt(3) + 1;
                for (int i = 0; i < itemCount; i++) {
                    this.entityDropItem(new ItemStack(ItemInit.SCRAP.get()));
                }
            }
        } else if (this.getRarity() != Rarity.LEGENDARY) {
            float probability = rand.nextFloat();
            if (probability <= 0.8F) {
                int itemCount = rand.nextInt(3) + 1;
                for (int i = 0; i < itemCount; i++) {
                    this.entityDropItem(new ItemStack(ItemInit.RAW_DIMORPHODON_MEAT.get()));
                }
            }
        }
    }

    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        this.getInfo().print();
        DinosExpansion.LOGGER.debug("Narcotic: [" + getNarcoticValue() + "/" + getInfo().getMaxNarcotic() + "]");
        DinosExpansion.LOGGER.debug("IsKnockout: " + this.isKnockout());
        if (!world.isRemote() && hand == Hand.MAIN_HAND) {
            if (player.getHeldItem(hand).isEmpty() && isKnockedOutBy(player)) {
                openTamingGui(this, (ServerPlayerEntity) player);
            }
        }
        return super.applyPlayerInteraction(player, vec, hand);
    }

    private PlayState predicate(AnimationEvent<Dimorphodon> event) {
        if (this.isKnockout()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimorphodon.knockout", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.isOnGround()) {
            if (this.isSleeping()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimorphodon.sleep", ILoopType.EDefaultLoopTypes.LOOP));
                return PlayState.CONTINUE;
            }
            if (prevPosX != getPosX() || prevPosY != getPosY() || prevPosZ != getPosZ()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimorphodon.walk", ILoopType.EDefaultLoopTypes.LOOP));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimorphodon.idle", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        }
        if (this.isFlying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimorphodon.fly", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (this.getMoveOrder() == MoveOrder.SIT) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimorphodon.sit", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent<Dimorphodon> event) {
        if (!isKnockout() && this.isSwingInProgress && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimorphodon.attack"));
            this.isSwingInProgress = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(10);
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
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
