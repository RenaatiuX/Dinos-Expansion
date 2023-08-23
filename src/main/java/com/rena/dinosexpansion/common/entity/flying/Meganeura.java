package com.rena.dinosexpansion.common.entity.flying;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.BatMeleeAttackGoal;
import com.rena.dinosexpansion.common.entity.ia.BatRandomFly;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.common.entity.terrestrial.ambient.AmbientDinosaur;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class Meganeura extends AmbientDinosaur implements IAnimatable, IAnimationTickable, IFlyingAnimal {


    public static final DataParameter<Boolean> HANGING = EntityDataManager.createKey(Meganeura.class, DataSerializers.BOOLEAN);


    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    @Nullable
    private BlockPos targetPosition, treePos;
    private int hoverTicks;

    public Meganeura(EntityType<? extends Dinosaur> type, World world) {
        super(type, world, new DinosaurInfo("meganeura", 30, 10, 5, SleepRhythmGoal.SleepRhythm.NONE), generateLevelWithinBounds(10, 50));
        this.setHoverTicks(30);
        this.setNoGravity(true);

    }

    public Meganeura(World world) {
        this(EntityInit.MEGANEURA.get(), world);
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8F)
                .createMutableAttribute(Attributes.FLYING_SPEED, 0.1F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0.1F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new BatMeleeAttackGoal(this, 0.6D));
        this.goalSelector.addGoal(3, new HurtByTargetGoal(this).setCallsForHelp(Meganeura.class));

        this.goalSelector.addGoal(10, new BatRandomFly<>(this, a -> !a.isHanging() && a.getHoverTicks() <= 0));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HANGING, false);
    }


    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (super.attackEntityAsMob(entityIn)){
            if (entityIn instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entityIn;
                livingEntity.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 100, 2));
            }
            return true;

        }
        return super.attackEntityAsMob(entityIn);
    }

    public int getHoverTicks() {
        return hoverTicks;
    }

    public void setHoverTicks(int hoverTicks) {
        this.hoverTicks = hoverTicks;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
    }

    @Override
    protected void collideWithNearbyEntities() {}

    @Override
    public void tick() {
        super.tick();
        this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
        if (!world.isRemote){
            if (this.getHoverTicks() > 0)
                this.setHoverTicks(this.getHoverTicks() - 1);
        }
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public float getBlockPathWeight(BlockPos pos) {
        if (this.world.getBlockState(pos).isAir() && this.world.hasWater(pos.down(2))) {
            return 10.0F;
        }
        return 0.0F;
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
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().loop("fly"));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return this.ticksExisted;
    }

    /**
     * client synced
     * this will make the entity try to find a tree and hangs on it
     */
    protected void setHanging(boolean hanging){
        this.dataManager.set(HANGING, hanging);
    }

    /**
     * client synced
     */
    public boolean isHanging(){
        return this.dataManager.get(HANGING);
    }

    //TODO do this for all other entities
    @Override
    protected Rarity getInitialRarity() {
        //in here we have a 20% chance that the entity is uncommon and a 80% chance that the entity is uncommon
        //as u see this method can only return Common or Uncommon so the entity can never have anything else as the rarity never changes throughout the game
        double random = this.getRNG().nextDouble();
        if (random < 0.2)
            return Rarity.UNCOMMON;
        return Rarity.COMMON;
    }
}
