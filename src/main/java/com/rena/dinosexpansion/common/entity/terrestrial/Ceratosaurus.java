package com.rena.dinosexpansion.common.entity.terrestrial;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.ChestedDinosaur;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.DinosaurArmorSlotType;
import com.rena.dinosexpansion.common.entity.ia.*;
import com.rena.dinosexpansion.common.util.enums.MoveOrder;
import com.rena.dinosexpansion.core.init.EntityInit;
import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
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

public class Ceratosaurus extends ChestedDinosaur implements IAnimatable, IAnimationTickable {

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 50.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 20)
                .createMutableAttribute(Attributes.ARMOR, 12.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.4F)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Ceratosaurus(EntityType<Ceratosaurus> type, World world) {
        //TODO max narcotic back to 300
        super(type, world, new DinosaurInfo("ceratosaurus", 400, 100, 50, SleepRhythmGoal.SleepRhythm.DIURNAL), generateLevelWithinBounds(10, 100));
        updateInfo();
        this.stepHeight = 1.0f;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new DinosaurMeleeAttackGoal(this, 1f, false));
        this.goalSelector.addGoal(2, new DinosaurFollowOwnerGoal(this, .7f, 10, 20, false));
        this.goalSelector.addGoal(3, new DinosaurLookAtGoal(this, PlayerEntity.class, 10));

        this.targetSelector.addGoal(5, new DinosaurNearestTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(6, new DinosaurHurByTargetGoal(this));

        this.goalSelector.addGoal(7, new DinosaurWanderGoal(this, .6f));

    }

    @Override
    public List<Item> getFood() {
        return ModTags.Items.CERATOSAURUS_FOOD.getAllElements();
    }

    @Override
    protected Rarity getInitialRarity() {
        return Rarity.COMMON;
    }

    @Override
    protected Gender getInitialGender() {
        return getRNG().nextDouble() <= 0.51 ? Gender.MALE : Gender.FEMALE;
    }

    @Override
    protected int reduceNarcotic(int narcoticValue) {
        if (getRNG().nextDouble() <= 0.001)
            return narcoticValue - 1;
        return narcoticValue;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return EntityInit.CERATOSAURUS.get().create(world);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 20, this::predicate));
        data.addAnimationController(new AnimationController<>(this, "attack_controller", 0, this::attackPredicate));
    }

    private PlayState predicate(AnimationEvent<Ceratosaurus> event) {
        if (this.isKnockout()) {
            event.getController().setAnimation(new AnimationBuilder().loop("knockedout"));
        } else if (this.isSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().loop("sleep"));
        } else if (this.getMoveOrder() == MoveOrder.SIT) {
            event.getController().setAnimation(new AnimationBuilder().loop("sit"));
        } else if (event.isMoving()) {
            if (this.isRunning()) {
                event.getController().setAnimation(new AnimationBuilder().loop("run"));
            } else
                event.getController().setAnimation(new AnimationBuilder().loop("walk"));
        } else {
            event.getController().setAnimation(new AnimationBuilder().loop("idle"));
        }

        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent<Ceratosaurus> event) {
        if (event.getController().getAnimationState() == AnimationState.Stopped && this.isSwingInProgress) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        //this.getInfo().print();
        //DinosExpansion.LOGGER.debug("Narcotic: [" + getNarcoticValue() + "/" + this.getMaxNarcotic() + "]");
        //DinosExpansion.LOGGER.debug("IsKnockout: " + this.isKnockout());
        if (!world.isRemote() && hand == Hand.MAIN_HAND && isKnockedOutBy(player) && player.getHeldItem(hand).isEmpty()) {
            openTamingGui(this, (ServerPlayerEntity) player);
            return ActionResultType.SUCCESS;
        }
        //System.out.println(String.format("%s|%s|%s|%s|%s|%s", !this.world.isRemote, this.isTamed(), isOwner(player), hand == Hand.MAIN_HAND, player.getHeldItem(hand).getItem() == Items.SADDLE, !hasSaddle()));
        if (!this.world.isRemote && this.isTamed() && isOwner(player) && hand == Hand.MAIN_HAND && player.getHeldItem(hand).getItem() == Items.SADDLE && !hasSaddle()) {
            this.setArmor(DinosaurArmorSlotType.SADDLE, player.getHeldItem(hand).copy());
            player.getHeldItem(hand).shrink(1);
            return ActionResultType.SUCCESS;
        }
        if (!this.world.isRemote && this.isTamed() && isOwner(player) && this.canFitPassenger(player) && hasSaddle() && !player.isSneaking()) {
            player.rotationYaw = this.rotationYaw;
            player.rotationPitch = this.rotationPitch;
            player.startRiding(this);
            return ActionResultType.SUCCESS;
        }
        if (!this.world.isRemote && this.isTamed() && isOwner(player) && player.getHeldItem(hand).isEmpty() && player.isSneaking()) {
            Dinosaur.openMediumDinoGui(this, (ServerPlayerEntity) player);
            return ActionResultType.SUCCESS;
        }
        return super.applyPlayerInteraction(player, vec, hand);
    }

    @Override
    public void travel(Vector3d pos) {
        if (this.isAlive()) {
            if (this.isBeingRidden()) {
                LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
                this.rotationYaw = livingentity.rotationYaw;
                this.prevRotationYaw = this.rotationYaw;
                this.rotationPitch = livingentity.rotationPitch * 0.5F;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.renderYawOffset = this.rotationYaw;
                this.rotationYawHead = this.renderYawOffset;
                float f = livingentity.moveStrafing * 0.5F;
                float f1 = livingentity.moveForward;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }
                this.setAIMoveSpeed(0.2f);
                super.travel(new Vector3d(f, pos.y, f1));
            } else
                super.travel(pos);
        }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        Vector3d look = getLookVec().scale(-0.2);
        passenger.setPosition(this.getPosX() - look.getX(), this.getPosY() + 1.5F, this.getPosZ() - look.getZ());
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
    protected EntitySize getKnockoutSize(Pose pose) {
        return EntitySize.fixed(1.5f, 1f);
    }

    @Override
    protected EntitySize getSleepSize(Pose pose) {
        return getKnockoutSize(pose);
    }
}
