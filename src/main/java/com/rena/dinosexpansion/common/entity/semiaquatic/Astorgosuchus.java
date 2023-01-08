package com.rena.dinosexpansion.common.entity.semiaquatic;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.*;
import com.rena.dinosexpansion.common.entity.ia.helper.ISemiAquatic;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import com.rena.dinosexpansion.common.entity.ia.navigator.SemiAquaticPathNavigator;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
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

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Astorgosuchus(EntityType<Astorgosuchus> type, World world) {
        super(type, world, new DinosaurInfo("astorgosuchus", 400, 200, 100, SleepRhythmGoal.SleepRhythm.NONE), generateLevelWithinBounds(20, 100));
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.setPathPriority(PathNodeType.WATER_BORDER, 0.0F);
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
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new OwnerHurtTargetGoal(this));
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
        nbt.putBoolean("HasEgg", this.hasEgg());
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.setHasEgg(nbt.getBoolean("HasEgg"));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HAS_EGG, Boolean.FALSE);
        this.dataManager.register(IS_DIGGING, Boolean.FALSE);
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
    public void updatePassenger(Entity passenger) {

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
    protected Rarity getinitialRarity() {
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
                if (isInDaylight()) {
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
        return this.getAttackTarget() == null && !this.isQueuedToSit() && !shouldLeaveWater();
    }

    @Override
    public boolean shouldLeaveWater() {
        return false;
    }

    @Override
    public boolean shouldStopMoving() {
        return false;
    }

    @Override
    public int getWaterSearchRange() {
        return this.getPassengers().isEmpty() ? 15 : 45;
    }
}
