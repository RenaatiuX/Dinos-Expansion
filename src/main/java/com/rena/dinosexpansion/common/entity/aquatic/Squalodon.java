package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.*;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.w3c.dom.Attr;
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

public class Squalodon extends DinosaurAquatic implements IAnimatable, IAnimationTickable {


    public static final String CONTROLLER_NAME = "controller";
    public static final String ATTACK_CONTROLLER = "attack_controller";


    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F)
                .createMutableAttribute(Attributes.MAX_HEALTH, 35F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 5F);
    }

    protected AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Squalodon(EntityType<Squalodon> type, World world) {
        super(type, world, new DinosaurInfo("squalodon", 400, 200, 50, SleepRhythmGoal.SleepRhythm.NONE), generateLevelWithinBounds(20, 100));
        this.moveController = new AquaticMoveController(this, 1F);
        this.lookController = new DolphinLookController(this,10);
        updateInfo();
    }

    public Squalodon(World world) {
        this(EntityInit.SQUALODON.get(), world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new DinosaurJumLikeDolphin(this, 30));
        this.goalSelector.addGoal(1, new DinosaurMeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new DinosaurFollowOwnerGoal(this, 0.5D, 10F, 2F, false));
        this.goalSelector.addGoal(3, new DinosaurRandomSwimmingGoal(this, 0.8D, 10, 24, true) {
            @Override
            public boolean shouldExecute() {
                return !Squalodon.this.isMovementDisabled() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(5, new DinosaurLookRandomGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(2, new DinosaurNearestTargetGoal<>(this, SquidEntity.class, 40, false, true, true, null));
        this.targetSelector.addGoal(3, new DinosaurNearestTargetGoal<>(this, AbstractGroupFishEntity.class, 100, false, true, true, null));
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

    @Override
    public List<Item> getFood() {
        return null;
    }

    @Override
    protected int reduceNarcotic(int narcoticValue) {
        if (this.getRNG().nextDouble() <= 0.01)
            return narcoticValue - 1;
        return narcoticValue;
    }


    private PlayState predicate(AnimationEvent<Squalodon> event) {
        if (isKnockout()) {
            event.getController().setAnimation(new AnimationBuilder().loop("knockout"));
        } else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().loop("swim"));
        } else if (this.isOnGround()) {
            event.getController().setAnimation(new AnimationBuilder().loop("beached"));
        } else {
            event.getController().setAnimation(new AnimationBuilder().loop("idle"));
        }
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent<Squalodon> event) {
        if (!isKnockout() && this.isSwingInProgress && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().playOnce("attack"));
            this.isSwingInProgress = false;
        }
        return PlayState.CONTINUE;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return EntityInit.SQUALODON.get().create(world);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(10);
        data.addAnimationController(new AnimationController<>(this, CONTROLLER_NAME, 10, this::predicate));
        data.addAnimationController(new AnimationController<>(this, ATTACK_CONTROLLER, 0, this::attackPredicate));
    }


    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return this.ticksExisted;
    }

    @Override
    protected Rarity getInitialRarity() {
        if (getRNG().nextDouble() <= 0.3)
            return Rarity.UNCOMMON;
        return Rarity.COMMON;
    }
}
