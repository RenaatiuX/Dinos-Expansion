package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.common.entity.ia.DinosaurAISwimBottom;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.client.renderer.entity.FishRenderer;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.passive.fish.PufferfishEntity;
import net.minecraft.entity.passive.fish.SalmonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraftforge.event.world.NoteBlockEvent;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

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
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, GuardianEntity.class, 8.0F, 1.0D, 1.0D));
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new DinosaurAISwimBottom(this, 0.8F, 7));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 0.8F, 3));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(6, new FollowBoatGoal(this));
        this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.2f, true));
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, SalmonEntity.class, true));
    }

    private PlayState predicate(AnimationEvent<Parapuzosia> event){
        if (isInWater()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.Parapuzosia.Swim", true));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    private void doInitialPosing(IWorld world) {
        BlockPos down = this.getPosition();
        while(!world.getFluidState(down).isEmpty() && down.getY() > 1){
            down = down.down();
        }
        this.setPosition(down.getX() + 0.5F, down.getY() + 1, down.getZ() + 0.5F);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (reason == SpawnReason.NATURAL) {
            doInitialPosing(worldIn);
        }
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public boolean isNotColliding(IWorldReader worldIn) {
        return worldIn.checkNoEntityCollision(this);
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(this.getAIMoveSpeed(), travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().mul(0.9D, 0.6D, 0.9D));
            if (this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
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
