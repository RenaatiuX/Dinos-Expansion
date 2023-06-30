package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.*;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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


    public static final String CONTROLLER_NAME = "controller", ATTACK_CONTROLLER = "attack_controller";


    public static AttributeModifierMap.MutableAttribute createAttributes(){
        return Dinosaur.registerAttributes()
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3f)
                .createMutableAttribute(Attributes.MAX_HEALTH, 35f)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 5f)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 20f);
    }

    protected AnimationFactory factory = GeckoLibUtil.createFactory(this);


    public Squalodon(World world) {
        super(EntityInit.SQUALODON.get(), world, new DinosaurInfo("esqualodon", 400, 200, 50, SleepRhythmGoal.SleepRhythm.NONE), Dinosaur.generateLevelWithinBounds(20, 40));
    }

    public Squalodon(EntityType<? extends Squalodon> type, World world) {
        this(world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new DinosaurMeleeAttackGoal(this, 0.6, true));
        this.goalSelector.addGoal(2, new DinosaurHurByTargetGoal(this));
        this.goalSelector.addGoal(3, new DinosaurNearestTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.addGoal(4, new DinosaurNearestTargetGoal<>(this, AbstractGroupFishEntity.class, true));
        this.goalSelector.addGoal(5, new DinosaurLookRandomGoal(this));
        this.goalSelector.addGoal(6, new DinosaurRandomSwimmingGoal(this, 0.5, 30, 5));

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
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.squalodon.knockout", ILoopType.EDefaultLoopTypes.LOOP));
        }else if (event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.squalodon.swim", ILoopType.EDefaultLoopTypes.LOOP));
        }else
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.squalodon.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent<Squalodon> event) {
        if (this.isSwingInProgress && event.getController().getAnimationState() == AnimationState.Stopped) {
            event.getController().setAnimation(new AnimationBuilder().playOnce("animation.squalodon.attack"));
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
        data.addAnimationController(new AnimationController(this, CONTROLLER_NAME, 20, this::predicate));
        data.addAnimationController(new AnimationController(this, ATTACK_CONTROLLER, 0, this::attackPredicate));
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
    protected Rarity getinitialRarity() {
        if(getRNG().nextDouble() <= 0.3)
            return Rarity.UNCOMMON;
        return Rarity.COMMON;
    }
}
