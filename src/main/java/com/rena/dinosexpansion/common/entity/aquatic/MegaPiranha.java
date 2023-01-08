package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.common.entity.ia.DinosaurAINearestTarget;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
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

public class MegaPiranha extends DinosaurAquatic implements IAnimatable, IAnimationTickable {

    public static final String CONTROLLER_NAME = "controller";
    public static final String ATTACK_CONTROLLER_NAME = "attack_controller";

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public float prevOnLandProgress;
    public float onLandProgress;

    private static final DataParameter<Boolean> ATTACK = EntityDataManager.createKey(MegaPiranha.class, DataSerializers.BOOLEAN);

    public MegaPiranha(EntityType<MegaPiranha> type, World worldIn) {
        super(type, worldIn, new DinosaurInfo("mega_piranha", 30, 10, 5, SleepRhythmGoal.SleepRhythm.NONE), generateLevelWithinBounds(10, 50));
        this.moveController = new AquaticMoveController(this, 1F);
    }

    public MegaPiranha(World world) {
        this(EntityInit.MEGA_PIRANHA.get(), world);
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
        this.goalSelector.addGoal(2, new MegaPiranhaAttackMeleeGoal(this, 1.5D, false));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 0.8F, 3));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(6, new FollowBoatGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractGroupFishEntity.class, 100, false, true, null));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 40, false, true, null));

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

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ATTACK, false);
    }

    @Override
    public List<Item> getFood() {
        return null;
    }

    @Override
    protected Rarity getinitialRarity() {
        return null;
    }

    @Override
    protected Gender getInitialGender() {
        return null;
    }

    @Override
    protected int reduceNarcotic(int narcoticValue) {
        return 0;
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
    }

    @Override
    public int tickTimer() {
        return this.ticksExisted;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, CONTROLLER_NAME, 0, this::predicate));
        data.addAnimationController(new AnimationController<>(this, ATTACK_CONTROLLER_NAME, 0, this::attackPredicate));
    }

    private PlayState predicate(AnimationEvent<MegaPiranha> event){
        if (isInWater()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("de.megapiranha.swim", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("de.megapiranha.outofwater", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent<MegaPiranha> event){
        if(isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("de.megapiranha.bite", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private class MegaPiranhaAttackMeleeGoal extends MeleeAttackGoal {

        private final MegaPiranha piranha;
        private int attackStep;
        public MegaPiranhaAttackMeleeGoal(MegaPiranha piranha, double speedIn, boolean useLongMemory) {
            super(piranha, speedIn, useLongMemory);
            this.piranha = piranha;
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if(distToEnemySqr <= d0 && this.isSwingOnCooldown())
            {
                this.resetSwingCooldown();
                this.piranha.attackEntityAsMob(enemy);
                MegaPiranha.this.setAttacking(false);
            }
            else if(distToEnemySqr <= d0 * 2.0D)
            {
                if(this.isSwingOnCooldown())
                {
                    MegaPiranha.this.setAttacking(false);
                    this.resetSwingCooldown();
                }

                if(this.getSwingCooldown() <= 10)
                {
                    MegaPiranha.this.setAttacking(true);
                }
            }
            else
            {
                this.resetSwingCooldown();
                MegaPiranha.this.setAttacking(false);
            }
        }

        @Override
        public boolean shouldExecute() {
            LivingEntity livingentity = this.piranha.getAttackTarget();
            return livingentity != null && livingentity.isAlive() && this.piranha.canAttack(livingentity);
        }

        @Override
        public void startExecuting() {
            super.startExecuting();
            this.attackStep = 0;
        }

        @Override
        public void resetTask() {
            super.resetTask();
            this.piranha.setAttacking(false);
        }
    }

}
