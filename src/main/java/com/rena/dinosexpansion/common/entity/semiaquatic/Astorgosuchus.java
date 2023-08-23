package com.rena.dinosexpansion.common.entity.semiaquatic;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.*;
import com.rena.dinosexpansion.common.entity.ia.helper.ISemiAquatic;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import com.rena.dinosexpansion.common.entity.ia.navigator.GroundPathNavigatorWide;
import com.rena.dinosexpansion.common.entity.ia.navigator.SemiAquaticPathNavigator;
import com.rena.dinosexpansion.common.util.enums.AttackOrder;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
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

public class Astorgosuchus extends Dinosaur implements IAnimatable, IAnimationTickable, ISemiAquatic {
    private static final DataParameter<Boolean> HAS_EGG = EntityDataManager.createKey(Astorgosuchus.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_DIGGING = EntityDataManager.createKey(Astorgosuchus.class, DataSerializers.BOOLEAN);
    public float groundProgress = 0;
    public float prevGroundProgress = 0;
    public float swimProgress = 0;
    public float prevSwimProgress = 0;
    public float baskingProgress = 0;
    public float prevBaskingProgress = 0;
    public float grabProgress = 0;
    public float prevGrabProgress = 0;
    public int baskingType = 0;
    private int baskingTimer = 0;
    private int swimTimer = -1000;
    private int ticksSinceInWater = 0;
    private int passengerTimer = 0;
    private boolean isLandNavigator;
    private boolean hasSpedUp = false;
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Astorgosuchus(EntityType<Astorgosuchus> type, World world) {
        super(type, world, new DinosaurInfo("astorgosuchus", 400, 200, 100, SleepRhythmGoal.SleepRhythm.NONE), generateLevelWithinBounds(20, 100));
        DinosExpansion.LOGGER.debug("why the hell ist this then just Client side");
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.setPathPriority(PathNodeType.WATER_BORDER, 0.0F);
        switchNavigator(false);
        this.baskingType = rand.nextInt(1);
    }

    public Astorgosuchus(World world){
        this(EntityInit.ASTORGOSUCHUS.get(), world);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 50.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 20)
                .createMutableAttribute(Attributes.ARMOR, 12.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 10.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.4F)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SitGoal(this));
        this.goalSelector.addGoal(2, new BreatheAirGoal(this));
        this.goalSelector.addGoal(2, new DinosaurFindWaterGoal(this));
        this.goalSelector.addGoal(2, new DinosaurLeaveWaterGoal(this));
        this.goalSelector.addGoal(4, new CrocodileMeleeGoal(this, 1, true));
        this.goalSelector.addGoal(5, new CrocodileRandomSwimmingGoal(this, 1.0D, 7));
        this.goalSelector.addGoal(6, new DinosaurLookRandomGoal(this));
        this.goalSelector.addGoal(7, new DinosaurLookAtGoal(this, PlayerEntity.class, 6.0F));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, PlayerEntity.class, 80, false, true, null) {
            public boolean shouldExecute() {
                return !isMovementDisabled() && !isChild() && (isTamed() || getAttackOrder() == AttackOrder.HOSTILE) && world.getDifficulty() != Difficulty.PEACEFUL && super.shouldExecute();
            }
        });
    }
    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }
    @Override
    public boolean isMaxGroupSize(int sizeIn) {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            this.setSitting(false);
            if (entity != null && this.isTamed() && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 3.0F;
            }
            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putInt("BaskingStyle", this.baskingType);
        nbt.putInt("BaskingTimer", this.baskingTimer);
        nbt.putInt("SwimTimer", this.swimTimer);
        nbt.putBoolean("HasEgg", this.hasEgg());
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.baskingType = nbt.getInt("BaskingStyle");
        this.baskingTimer = nbt.getInt("BaskingTimer");
        this.swimTimer = nbt.getInt("SwimTimer");
        this.setHasEgg(nbt.getBoolean("HasEgg"));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HAS_EGG, Boolean.FALSE);
        this.dataManager.register(IS_DIGGING, Boolean.FALSE);
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveController = new MovementController(this);
            this.navigator = new GroundPathNavigatorWide(this, world);
            this.isLandNavigator = true;
        } else {
            this.moveController = new AquaticMoveController(this, 1F);
            this.navigator = new SemiAquaticPathNavigator(this, world);
            this.isLandNavigator = false;
        }
    }

    @Override
    public boolean canRiderInteract() {
        return true;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public boolean isOnSameTeam(Entity entityIn) {
        if (this.isTamed()) {
            LivingEntity livingentity = this.getOwner();
            if (entityIn == livingentity) {
                return true;
            }
            if (entityIn instanceof TameableEntity) {
                return ((TameableEntity) entityIn).isOwner(livingentity);
            }
            if (livingentity != null) {
                return livingentity.isOnSameTeam(entityIn);
            }
        }
        return super.isOnSameTeam(entityIn);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (!this.getPassengers().isEmpty()) {
            this.renderYawOffset = MathHelper.wrapDegrees(this.rotationYaw - 180F);
        }
        if (this.isPassenger(passenger)) {
            float radius = 2F;
            float angle = (0.01745329251F * this.renderYawOffset);
            double extraX = radius * MathHelper.sin((float) (Math.PI + angle));
            double extraZ = radius * MathHelper.cos(angle);
            passenger.setPosition(this.getPosX() + extraX, this.getPosY() + 0.1F, this.getPosZ() + extraZ);
            passengerTimer++;
            if (this.isAlive() && passengerTimer > 0 && passengerTimer % 40 == 0) {
                passenger.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
            }
        }
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public boolean isNotColliding(IWorldReader worldIn) {
        return worldIn.checkNoEntityCollision(this);
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
    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.DROWN || source == DamageSource.IN_WALL || source == DamageSource.FALLING_BLOCK || super.isInvulnerableTo(source);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        this.prevGroundProgress = groundProgress;
        this.prevSwimProgress = swimProgress;
        this.prevBaskingProgress = baskingProgress;
        this.prevGrabProgress = grabProgress;
        boolean ground = !this.isInWater();
        boolean groundAnimate = !this.isInWater();
        boolean basking = groundAnimate && this.isQueuedToSit();
        boolean grabbing = this.isInWater() && !this.getPassengers().isEmpty();
        if (!ground && this.isLandNavigator) {
            switchNavigator(false);
        }
        if (ground && !this.isLandNavigator) {
            switchNavigator(true);
        }
        if (groundAnimate && this.groundProgress < 10F) {
            this.groundProgress++;
        }
        if (!groundAnimate && this.groundProgress > 0F) {
            this.groundProgress--;
        }
        if (!groundAnimate && this.swimProgress < 10F) {
            this.swimProgress++;
        }
        if (groundAnimate && this.swimProgress > 0F) {
            this.swimProgress--;
        }
        if (basking && this.baskingProgress < 10F) {
            this.baskingProgress++;
        }
        if (!basking && this.baskingProgress > 0F) {
            this.baskingProgress--;
        }
        if (grabbing && this.grabProgress < 10F) {
            this.grabProgress++;
        }
        if (!grabbing && this.grabProgress > 0F) {
            this.grabProgress--;
        }
        if (this.getAttackTarget() != null && !hasSpedUp) {
            hasSpedUp = true;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3F);
        }
        if (this.getAttackTarget() == null && hasSpedUp) {
            hasSpedUp = false;
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25F);
        }
        if (baskingTimer < 0) {
            baskingTimer++;
        }
        if (passengerTimer > 0 && this.getPassengers().isEmpty()) {
            passengerTimer = 0;
        }
        if (!world.isRemote) {
            if (isInWater()) {
                swimTimer++;
                ticksSinceInWater = 0;
            } else {
                ticksSinceInWater++;
                swimTimer--;
            }
        }
        if (!world.isRemote && !this.isInWater() && this.isOnGround()) {
            if (!this.isTamed()) {
                if (!this.isQueuedToSit() && baskingTimer == 0 && this.getAttackTarget() == null && this.getNavigator().noPath()) {
                    this.setSitting(true);
                    this.baskingTimer = 1000 + rand.nextInt(750);
                }
                if (this.isQueuedToSit() && (baskingTimer <= 0 || this.getAttackTarget() != null || swimTimer < -1000)) {
                    this.setSitting(false);
                    this.baskingTimer = -2000 - rand.nextInt(750);
                }
                if (this.isQueuedToSit() && baskingTimer > 0) {
                    baskingTimer--;
                }
            }
        }
        if (this.isInLove() && this.getAttackTarget() != null) {
            this.setAttackTarget(null);
        }
    }

    public boolean hasEgg() {
        return this.dataManager.get(HAS_EGG);
    }

    private void setHasEgg(boolean hasEgg) {
        this.dataManager.set(HAS_EGG, hasEgg);
    }

    public boolean isDigging() {
        return this.dataManager.get(IS_DIGGING);
    }

    private void setDigging(boolean isDigging) {
        this.dataManager.set(IS_DIGGING, isDigging);
    }


    @Override
    public List<Item> getFood() {
        return null;
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
        return 0;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    private PlayState predicate(AnimationEvent<Astorgosuchus> event) {
        if (isKnockout()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Astorgosuchus_KnockedOut", ILoopType.EDefaultLoopTypes.LOOP));
        } else if (this.isOnGround()) {
            if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Astorgosuchus_Walk", ILoopType.EDefaultLoopTypes.LOOP));
                event.getController().setAnimationSpeed(1.2);
            } else {
                if (baskingType == 0) {
                    event.getController().setAnimation(new AnimationBuilder().addAnimation("Astorgosuchus_SunbathingSleeping", ILoopType.EDefaultLoopTypes.LOOP));
                }
            }
        } else if (this.isInWater()) {
            if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Astorgosuchus_Swim", ILoopType.EDefaultLoopTypes.LOOP));
            } else {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("Astorgosuchus_SwimIdle", ILoopType.EDefaultLoopTypes.LOOP));
            }
        } else if (this.isQueuedToSit()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("Astorgosuchus_Sit", ILoopType.EDefaultLoopTypes.LOOP));
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

    @Override
    public boolean shouldEnterWater() {
        if (!this.getPassengers().isEmpty()) {
            return true;
        }
        return this.getAttackTarget() == null && !this.isQueuedToSit() && this.baskingTimer <= 0 && !shouldLeaveWater() && swimTimer <= -1000;
    }

    @Override
    public boolean shouldLeaveWater() {
        if (!this.getPassengers().isEmpty()) {
            return false;
        }
        if (this.getAttackTarget() != null && !this.getAttackTarget().isInWater()) {
            return true;
        }
        return swimTimer > 600;
    }

    @Override
    public boolean shouldStopMoving() {
        return false;
    }

    @Override
    public int getWaterSearchRange() {
        return this.getPassengers().isEmpty() ? 15 : 45;
    }

    @Override
    public void setAttackTarget(@Nullable LivingEntity entitylivingbaseIn) {
        if (!this.isChild()) {
            super.setAttackTarget(entitylivingbaseIn);
        }
    }
}
