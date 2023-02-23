package com.rena.dinosexpansion.common.entity.terrestrial;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.common.entity.semiaquatic.Astorgosuchus;
import com.rena.dinosexpansion.core.init.EntityInit;
import com.rena.dinosexpansion.core.init.SoundInit;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.GeckoLib;
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

public class Dryosaurus extends Dinosaur implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private int panicTicks = 0;
    private int eatAnimationTick;
    private EatGrassGoal eatGrassGoal;

    public Dryosaurus(EntityType<Dryosaurus> type, World world) {
        super(type, world, new DinosaurInfo("dryosaurus", 70, 35, 10, SleepRhythmGoal.SleepRhythm.DIURNAL), generateLevelWithinBounds(10, 50));
        updateInfo();
    }

    public Dryosaurus(World world){
        this(EntityInit.DRYOSAURUS.get(), world);
    }

    @Override
    protected void registerGoals() {
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.addGoal(5, this.eatGrassGoal);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 16F)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0.7F);
    }

    @Override
    public void livingTick() {
        if (this.world.isRemote) {
            this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        }
        super.livingTick();
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.eatAnimationTick = 40;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    public boolean isEating() {
        return this.eatAnimationTick > 0;
    }

    @Override
    public void eatGrassBonus() {
        if (this.isChild()) {
            this.addGrowth(60);
        }
    }

    @Override
    public List<Item> getFood() {
        return null;
    }

    @Override
    protected Rarity getinitialRarity() {
        double rand = this.getRNG().nextDouble();
        if (rand <= 0.05)
            return Rarity.LEGENDARY;
        /*if (rand <= 0.1)
            return Rarity.EPIC;
        if (rand < 0.2)
            return Rarity.RARE;
        if (rand <= 0.5)
            return Rarity.UNCOMMON;*/
        return Rarity.COMMON;
    }

    @Override
    protected Gender getInitialGender() {
        return getRNG().nextDouble() <= 0.51 ? Gender.MALE : Gender.FEMALE;
    }

    @Override
    protected int reduceNarcotic(int narcoticValue) {
        if (getRNG().nextDouble() <= 0.05)
            return narcoticValue - 1;
        return narcoticValue;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return EntityInit.DRYOSAURUS.get().create(world);
    }

    @Override
    protected void updateAITasks() {
        this.eatAnimationTick = this.eatGrassGoal.getEatingGrassTimer();
        if (this.getMoveHelper().isUpdating()) {
            this.setSprinting(this.getMoveHelper().getSpeed() >= 1.5D);
        } else {
            this.setSprinting(false);
        }
        super.updateAITasks();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean lastHurt = super.attackEntityFrom(source, amount);
        if (lastHurt) {
            int ticks = 100 + this.rand.nextInt(100);
            this.panicTicks = ticks;
            List<? extends Dryosaurus> dryosauruses = this.world.getEntitiesWithinAABB(Dryosaurus.class, this.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
            for (Dryosaurus dryosaurus : dryosauruses) {
                dryosaurus.panicTicks = ticks;
            }
        }
        return lastHurt;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            if (panicTicks >= 0) {
                panicTicks--;
            }
            if (panicTicks == 0 && this.getRevengeTarget() != null) {
                this.setRevengeTarget(null);
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

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        int i = MathHelper.nextInt(rand, 0, 8);
        if (i < SoundInit.DRYOSAURUS_AMBIENT.size()) {
            playSound(SoundInit.DRYOSAURUS_AMBIENT.get(i).get(), 1, 1.5F);
        }
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        int i = MathHelper.nextInt(rand, 0, 5);
        if (i < SoundInit.DRYOSAURUS_HURT.size()) {
            playSound(SoundInit.DRYOSAURUS_HURT.get(i).get(), 1, 1.5F);
        }
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.DRYOSAURUS_DEATH.get();
    }

    private PlayState predicate(AnimationEvent<Dryosaurus> event) {
        if (this.isKnockout()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("dryosaurus.knockout", ILoopType.EDefaultLoopTypes.LOOP));
        }
        if (event.isMoving()) {
            if (this.isSprinting()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("dryosaurus.run", ILoopType.EDefaultLoopTypes.LOOP));
                event.getController().setAnimationSpeed(1.5D);
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("dryosaurus.walk", ILoopType.EDefaultLoopTypes.LOOP));
            }
            return PlayState.CONTINUE;
        } else if (this.isEating()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("dryosaurus.eat", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            return PlayState.CONTINUE;
        } else if (this.isSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("dryosaurus.sleep", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else if (this.isQueuedToSit()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("dryosaurus.sit", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("dryosaurus.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent<Dryosaurus> event) {
        if (!isKnockout() && this.isSwingInProgress && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("dryosaurus.attack"));
            this.isSwingInProgress = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
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
