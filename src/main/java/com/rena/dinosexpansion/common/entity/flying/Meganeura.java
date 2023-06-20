package com.rena.dinosexpansion.common.entity.flying;

import com.rena.dinosexpansion.common.entity.Dinosaur;
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
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
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
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    @Nullable
    private BlockPos targetPosition;
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
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0.0F);
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
    protected void collideWithNearbyEntities() {
    }

    @Override
    public void tick() {
        super.tick();
        this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if (!(this.targetPosition == null || this.world.isAirBlock(this.targetPosition) && this.targetPosition.getY() > 1)) {
            this.targetPosition = null;
        }
        if (this.getHoverTicks() > 0)  {
            this.setHoverTicks(Math.max(0, this.getHoverTicks() - 1));
        } else if (this.targetPosition == null || this.targetPosition.withinDistance(this.getPositionVec(), 2.0)) {
            this.targetPosition = new BlockPos(this.getPosX() + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7), this.getPosY() + (double)this.rand.nextInt(6) - 2.0D, this.getPosZ() + (double)this.rand.nextInt(7) - (double)this.rand.nextInt(7));
        }

        if (this.targetPosition != null && this.getHoverTicks() <= 0) {
            double d0 = this.targetPosition.getX() + 0.5D - this.getPosX();
            double d1 = this.targetPosition.getY() + 0.1D - this.getPosY();
            double d2 = this.targetPosition.getZ() + 0.5D - this.getPosZ();

            double speed = this.getAttributeValue(Attributes.FLYING_SPEED);

            Vector3d vector3d = this.getMotion();
            Vector3d signumVector3d = vector3d.add((Math.signum(d0) * 0.5D - vector3d.x) * speed, (Math.signum(d1) * (double) 0.7F - vector3d.y) * speed, (Math.signum(d2) * 0.5D - vector3d.z) * speed);
            this.setMotion(signumVector3d);

            this.moveForward = 5.0F;
            float angle = (float) (MathHelper.atan2(signumVector3d.z, signumVector3d.x) * (double) (180F / (float) Math.PI)) - 90.0F;
            float wrappedAngle = MathHelper.wrapDegrees(angle - this.rotationYaw);
            this.rotationYaw += wrappedAngle;
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

    //TODO do this for all other entities
    @Override
    protected Rarity getinitialRarity() {
        //in here we have a 20% chance that the entity is uncommon and a 80% chance that the entity is uncommon
        //as u see this method can only return Common or Uncommon so the entity can never have anything else as the rarity never changes throughout the game
        double random = this.getRNG().nextDouble();
        if (random < 0.2)
            return Rarity.UNCOMMON;
        return Rarity.COMMON;
    }
}
