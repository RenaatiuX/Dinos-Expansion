package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.ia.*;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import com.rena.dinosexpansion.core.init.EntityInit;
import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
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

public class Eosqualodon extends DinosaurAquatic implements IAnimatable, IAnimationTickable {

    public static final String CONTROLLER_NAME = "controller";
    public static final String ATTACK_CONTROLLER_NAME = "attack_controller";

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 30F)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0F)
                .createMutableAttribute(ForgeMod.SWIM_SPEED.get(), 1.2f);
    }

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Eosqualodon(EntityType<Eosqualodon> type, World world) {
        super(type, world, new DinosaurInfo("eosqualodon", 200, 100, 50, SleepRhythmGoal.SleepRhythm.NONE), generateLevelWithinBounds(20, 100));
        this.moveController = new AquaticMoveController(this, 1F);
        updateInfo();
    }

    public Eosqualodon(World world) {
        this(EntityInit.EOSQUALODON.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FindWaterGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(3, new DinosaurFollowOwnerGoal(this, 0.5, 10f, 2f, false));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 0.8F, 10));
        this.goalSelector.addGoal(5, new DinosaurLookRandomGoal(this));
        this.goalSelector.addGoal(6, new FollowBoatGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(2, new DinosaurNearestTargetGoal<>(this, SquidEntity.class, 40, false, true, null));
        this.targetSelector.addGoal(3, new DinosaurNearestTargetGoal<>(this, AbstractGroupFishEntity.class, 100, false, true, null));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }
            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }
        return flag;
    }

    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        this.getInfo().print();
        DinosExpansion.LOGGER.debug("Narcotic: [" + getNarcoticValue() + "/" + getInfo().getMaxNarcotic() + "]");
        DinosExpansion.LOGGER.debug("IsKnockout: " + this.isKnockout());
        if (!world.isRemote() && hand == Hand.MAIN_HAND){
            if (player.getHeldItem(hand).isEmpty() && isKnockedOutBy(player)){
                openTamingGui(this, (ServerPlayerEntity) player);
            }
        }
        return super.applyPlayerInteraction(player, vec, hand);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return EntityInit.EOSQUALODON.get().create(world);
    }

    @Override
    public List<Item> getFood() {
        return ModTags.Items.EOSQUALODON_FOOD.getAllElements();
    }

    @Override
    protected int reduceNarcotic(int narcoticValue) {
        if (getRNG().nextDouble() <= 0.05)
            return narcoticValue - 1;
        return narcoticValue;
    }

    private PlayState predicate(AnimationEvent<Eosqualodon> event) {
        if (event.getController().getCurrentAnimation() != null){
            //DinosExpansion.LOGGER.debug(event.getController().getCurrentAnimation().animationName);
        }
        if (isKnockout()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("eosqualodon_knockout", ILoopType.EDefaultLoopTypes.LOOP));
        }
        if (this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("eosqualodon_swim", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent<Eosqualodon> event) {
        if (!isKnockout() && this.isSwingInProgress && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("eosqualodon_bite"));
            this.isSwingInProgress = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, CONTROLLER_NAME, 30, this::predicate));
        data.addAnimationController(new AnimationController<>(this, ATTACK_CONTROLLER_NAME, 0, this::attackPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }
}
