package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.common.entity.ia.DinosaurAINearestTarget;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;

public class MegaPiranha extends WaterMobEntity implements IAnimatable {

    public static final String CONTROLLER_NAME = "controller";
    public static final String ATTACK_CONTROLLER_NAME = "attack_controller";

    public float prevOnLandProgress;
    public float onLandProgress;

    private static final DataParameter<Boolean> ATTACK = EntityDataManager.createKey(Eosqualodon.class, DataSerializers.BOOLEAN);

    protected MegaPiranha(EntityType<MegaPiranha> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new AquaticMoveController(this, 1F);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes(){
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20F)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 0.8F, 3));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(6, new FollowBoatGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(2, new DinosaurAINearestTarget<>(this, AbstractGroupFishEntity.class, 100, false, true, null));
        this.targetSelector.addGoal(3, new DinosaurAINearestTarget<>(this, PlayerEntity.class, 40, false, true, null));

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
            this.setMotion(this.getMotion().scale(0.9D));
            if (this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ATTACK, false);
    }

    public boolean isAttacking() {
        return this.dataManager.get(ATTACK);
    }

    public void setAttacking(boolean running) {
        this.dataManager.set(ATTACK, running);
    }

    @Override
    public void tick() {
        super.tick();
        this.prevOnLandProgress = onLandProgress;
        if (!this.isInWater() && onLandProgress < 5F) {
            onLandProgress++;
        }
        if (this.isInWater() && onLandProgress > 0F) {
            onLandProgress--;
        }
        if (this.isInWater()) {
            this.setMotion(this.getMotion().mul(1.0D, 0.8D, 1.0D));
        }
        if (!world.isRemote && this.getAttackTarget() != null && this.isAttacking()) {
            float f1 = this.rotationYaw * ((float) Math.PI / 180F);
            this.setMotion(this.getMotion().add(-MathHelper.sin(f1) * 0.06F, 0.0D, MathHelper.cos(f1) * 0.06F));
        }
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }

    private class MegaPiranhaAttackMeleeGoal extends Goal {
        public MegaPiranhaAttackMeleeGoal() {
            this.setMutexFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean shouldExecute() {
            return MegaPiranha.this.getAttackTarget() != null && MegaPiranha.this.getAttackTarget().isAlive();
        }

        @Override
        public void tick() {
            LivingEntity target = MegaPiranha.this.getAttackTarget();
            double speed = 1.0F;
            boolean move = true;
            if (MegaPiranha.this.getDistance(target) < 10) {
                if (MegaPiranha.this.getDistance(target) < 1.9D) {
                    MegaPiranha.this.attackEntityAsMob(target);
                    speed = 0.8F;
                } else {
                    speed = 0.6F;
                    MegaPiranha.this.faceEntity(target, 70, 70);
                    if (target instanceof SquidEntity) {
                        Vector3d mouth = MegaPiranha.this.getPositionVec();
                        float squidSpeed = 0.07F;
                        ((SquidEntity) target).setMovementVector((float) (mouth.x - target.getPosX()) * squidSpeed, (float) (mouth.y - target.getPosYEye()) * squidSpeed, (float) (mouth.z - target.getPosZ()) * squidSpeed);
                        MegaPiranha.this.world.setEntityState(MegaPiranha.this, (byte) 68);
                    }
                }
            }
            if (target instanceof PlayerEntity) {
                speed = 1.0F;
            }
            MegaPiranha.this.getNavigator().tryMoveToEntityLiving(target, speed);
        }
    }
}
