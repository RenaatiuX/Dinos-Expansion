package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.common.entity.ia.DinosaurAINearestTarget;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import com.rena.dinosexpansion.core.init.EntityInit;
import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class Eosqualodon extends DinosaurAquatic implements IAnimatable {

    public static final String CONTROLLER_NAME = "controller";
    public static final String ATTACK_CONTROLLER_NAME = "attack_controller";

    private static final DataParameter<Boolean> ATTACK = EntityDataManager.createKey(Eosqualodon.class, DataSerializers.BOOLEAN);

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 30F)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0F);
    }

    @Override
    public double swimSpeed() {
        return 0;
    }

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Eosqualodon(EntityType<Eosqualodon> type, World world) {
        super(type, world, new DinosaurInfo(200, 400, 50, SleepRhythmGoal.SleepRhythm.DIURNAL), generateLevelWithinBounds(20, 100));
        this.moveController = new AquaticMoveController(this, 1F);
        switchNavigator(false);
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
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 0.8F, 3));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(6, new FollowBoatGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(2, new DinosaurAINearestTarget<>(this, SquidEntity.class, 40, false, true, null));
        this.targetSelector.addGoal(3, new DinosaurAINearestTarget<>(this, AbstractGroupFishEntity.class, 100, false, true, null));
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
        return super.applyPlayerInteraction(player, vec, hand);
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
        return ModTags.Items.EOSQUALODON_FOOD.getAllElements();
    }

    @Override
    protected Rarity getinitialRarity() {
        double rand = this.getRNG().nextDouble();
        if (rand <= 0.05)
            return Rarity.LEGENDARY;
        if (rand <= 0.1)
            return Rarity.EPIC;
        if (rand < 0.2)
            return Rarity.RARE;
        if (rand <= 0.5)
            return Rarity.UNCOMMON;
        return Rarity.COMMON;
    }

    @Override
    protected Gender getInitialGender() {
        //bring a bit real life in the random distribution :-)
        return getRNG().nextDouble() <= 0.51 ? Gender.MALE : Gender.FEMALE;
    }

    public boolean isAttacking() {
        return this.dataManager.get(ATTACK);
    }

    public void setAttacking(boolean running) {
        this.dataManager.set(ATTACK, running);
    }

    private PlayState predicate(AnimationEvent<Eosqualodon> event) {
        if (this.isSwingInProgress) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("eosqualodon_bite"));
            this.isSwingInProgress = false;
        }
        else if (event.isMoving() && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("eosqualodon_swim", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }

    private PlayState attackPredicate(AnimationEvent<Eosqualodon> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, CONTROLLER_NAME, 0, this::predicate));
        data.addAnimationController(new AnimationController<>(this, ATTACK_CONTROLLER_NAME, 0, this::attackPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private class EosqualodonAttackGoal extends MeleeAttackGoal {

        private Eosqualodon eosqualodon;
        private int animCounter = 0;
        private int animTickLength = 20;

        public EosqualodonAttackGoal(CreatureEntity creature, double speedIn, boolean useLongMemory) {
            super(creature, speedIn, useLongMemory);
            eosqualodon = ((Eosqualodon) creature);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            if (distToEnemySqr <= this.getAttackReachSqr(enemy) && this.getSwingCooldown() <= 0) {
                if (eosqualodon != null) {
                    eosqualodon.setAttacking(true);
                    animCounter = 0;
                }
            }

            super.checkAndPerformAttack(enemy, distToEnemySqr);
        }

        @Override
        public void tick() {
            super.tick();
            if (eosqualodon.isAttacking()) {
                animCounter++;

                if (animCounter >= animTickLength) {
                    animCounter = 0;
                    eosqualodon.setAttacking(false);
                }
            }
        }

        @Override
        public void resetTask() {
            animCounter = 0;
            eosqualodon.setAttacking(false);
            super.resetTask();
        }
    }

}
