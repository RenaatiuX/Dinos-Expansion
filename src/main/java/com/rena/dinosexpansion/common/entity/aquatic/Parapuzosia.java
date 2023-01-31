package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.common.entity.ia.DinosaurSwimBottomGoal;
import com.rena.dinosexpansion.common.entity.ia.DinosaurSwimMoveControllerSinkGoal;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
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

public class Parapuzosia extends DinosaurAquatic implements IAnimatable, IAnimationTickable {

    public static final String CONTROLLER_NAME = "controller";
    public static final String ATTACK_CONTROLLER_NAME = "attack_controller";
    private static final DataParameter<Boolean> GRABBING = EntityDataManager.createKey(Parapuzosia.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> GRAB_ENTITY = EntityDataManager.createKey(Parapuzosia.class, DataSerializers.VARINT);

    public float grabProgress;
    public float prevGrabProgress;
    private int holdTime;

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 50F)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0F)
                .createMutableAttribute(Attributes.ARMOR, 15F)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 15F);
    }

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Parapuzosia(EntityType<Parapuzosia> type, World world) {
        super(type, world, new DinosaurInfo("parapuzosia", 100, 100, 50, SleepRhythmGoal.SleepRhythm.NONE), generateLevelWithinBounds(20, 100));
        this.moveController = new AquaticMoveController(this, 1.2F, 5);
        updateInfo();
    }

    public Parapuzosia(World world) {
        this(EntityInit.PARAPUZOSIA.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FindWaterGoal(this));
        this.goalSelector.addGoal(2, new ParapuzosiaAiGrab());
        this.goalSelector.addGoal(3, new DinosaurSwimBottomGoal(this, 0.8F, 7));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 0.8F, 3));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, DrownedEntity.class,  20, true, true, null));
    }

    private void doInitialPosing(IWorld world) {
        BlockPos down = this.getPosition();
        while (!world.getFluidState(down).isEmpty() && down.getY() > 1) {
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

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(GRABBING, false);
        this.dataManager.register(GRAB_ENTITY, -1);
    }

    @Override
    public List<Item> getFood() {
        return null;
    }

    @Override
    protected int reduceNarcotic(int narcoticValue) {
        return 0;
    }

    @Nullable
    public Entity getGrabbedEntity() {
        if (!this.world.isRemote || this.dataManager.get(GRAB_ENTITY) == -1) {
            return this.getAttackTarget();
        } else {
            return this.world.getEntityByID(this.dataManager.get(GRAB_ENTITY));
        }
    }

    @Override
    public void tick() {
        super.tick();
        prevGrabProgress = grabProgress;
        if (this.ticksExisted % 100 == 0) {
            this.heal(2);
        }
        if (this.isGrabbing() && grabProgress < 5F) {
            grabProgress += 0.25F;
        }
        if (!this.isGrabbing() && grabProgress > 0F) {
            grabProgress -= 0.25F;
        }
        if (this.isGrabbing()) {
            Entity target = getGrabbedEntity();
            if (!world.isRemote && target != null) {
                this.dataManager.set(GRAB_ENTITY, target.getEntityId());
                if (holdTime % 20 == 0 && holdTime > 30) {
                    target.attackEntityFrom(DamageSource.causeMobDamage(this), 3 + rand.nextInt(5));
                }
            }
            if (target != null && target.isAlive()) {
                float invert = 1F - grabProgress * 0.2F;
                Vector3d extraVec = new Vector3d(0, 0, 2F + invert * 7F).rotatePitch(-this.rotationPitch * ((float) Math.PI / 180F)).rotateYaw(-this.renderYawOffset * ((float) Math.PI / 180F));
                Vector3d minus = new Vector3d(this.getPosX() + extraVec.x - target.getPosX(), this.getPosY() + extraVec.y - target.getPosY(), this.getPosZ() + extraVec.z - target.getPosZ());
                target.setMotion(minus);
            }
            holdTime++;
            if (holdTime > 1000) {
                holdTime = 0;
                this.setGrabbing(false);
            }
        } else {
            holdTime = 0;
        }
    }

    @Override
    public int tickTimer() {
        return this.ticksExisted;
    }

    public boolean isGrabbing() {
        return this.dataManager.get(GRABBING);
    }

    public void setGrabbing(boolean running) {
        this.dataManager.set(GRABBING, running);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(10);
        data.addAnimationController(new AnimationController<>(this, CONTROLLER_NAME, 0, this::predicate));
        //data.addAnimationController(new AnimationController<>(this, ATTACK_CONTROLLER_NAME, 0, this::attackPredicate));
    }

    private PlayState predicate(AnimationEvent<Parapuzosia> event) {
        if (isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.Parapuzosia.Swim", ILoopType.EDefaultLoopTypes.LOOP));
        }
        if (isGrabbing()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.Parapuzosia.Catch", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
        }
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent<Parapuzosia> event) {
        if (isGrabbing()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.Parapuzosia.Catch", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private class ParapuzosiaAiGrab extends Goal {

        @Override
        public boolean shouldExecute() {
            return Parapuzosia.this.isInWaterOrBubbleColumn() && Parapuzosia.this.getAttackTarget() != null && Parapuzosia.this.getAttackTarget().isAlive();
        }

        @Override
        public void tick() {
            super.tick();
            Parapuzosia parapuzosia = Parapuzosia.this;
            LivingEntity target = Parapuzosia.this.getAttackTarget();
            double dist = parapuzosia.getDistanceSq(target);
            if (parapuzosia.canEntityBeSeen(target) && dist < 2.0F) {
                parapuzosia.setGrabbing(true);
            } else {
                Vector3d moveBodyTo = target.getPositionVec();
                parapuzosia.getNavigator().tryMoveToXYZ(moveBodyTo.x, moveBodyTo.y, moveBodyTo.z, 1.0F);
            }
        }

        @Override
        public void resetTask() {
            super.resetTask();
            Parapuzosia.this.setGrabbing(false);
        }
    }
}
