package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.client.renderer.entity.FishRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.passive.fish.PufferfishEntity;
import net.minecraft.entity.passive.fish.SalmonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class Parapuzosia extends WaterMobEntity implements IAnimatable {

    public static final String CONTROLLER_NAME = "controller";

    public static final AttributeModifierMap.MutableAttribute createAttributes(){
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 15f).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3f).createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0f);
    }

    private AnimationFactory factory = new AnimationFactory(this);

    public Parapuzosia(EntityType<Parapuzosia> type, World world) {
        super(type, world);
        this.moveController = new AquaticMoveController(this, 1f);
    }

    public Parapuzosia(World world){
        this(EntityInit.PARAPUZOSIA.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FindWaterGoal(this));
        this.goalSelector.addGoal(0, new AvoidEntityGoal(this, GuardianEntity.class, 8.0F, 1.0D, 1.0D));
        this.goalSelector.addGoal(0, new AvoidEntityGoal(this, PlayerEntity.class, 8.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1f, 10));
        this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.2f, true));
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SalmonEntity.class, true));
    }

    private PlayState predicate(AnimationEvent<Parapuzosia> event){
        if (event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.Parapuzosia.Swim"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public boolean hasNoGravity() {
        return this.isInWater();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, CONTROLLER_NAME, 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
