package com.rena.dinosexpansion.common.entity.flying;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.common.entity.terrestrial.ambient.AmbientDinosaur;
import com.rena.dinosexpansion.core.init.EntityInit;
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
    public Meganeura(EntityType<? extends Dinosaur> type, World world) {
        super(type, world, new DinosaurInfo("meganeura", 30, 10, 5, SleepRhythmGoal.SleepRhythm.NONE), generateLevelWithinBounds(10, 50));
        this.moveController = new MeganeuraMovementController(this);
        this.setPathPriority(PathNodeType.OPEN, 1.0F);
    }

    public Meganeura(World world) {
        this(EntityInit.MEGANEURA.get(), world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RandomFlyGoal());
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8F)
                .createMutableAttribute(Attributes.FLYING_SPEED, 0.25F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0.0F);
    }

    @Override
    public void tick() {
        super.tick();
        this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isServerWorld()) {
            this.moveRelative(this.getAIMoveSpeed(), travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.8D));
        }
        this.func_233629_a_(this, false);
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        FlyingPathNavigator flyingPathNavigation = new FlyingPathNavigator(this, worldIn);
        flyingPathNavigation.setCanSwim(true);
        return flyingPathNavigation;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
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

    static class MeganeuraMovementController extends FlyingMovementController {
        public MeganeuraMovementController(Meganeura meganeura) {
            super(meganeura, 360, true);
        }

        @Override
        public void tick() {
            if (this.action == MovementController.Action.MOVE_TO) {
                this.action = MovementController.Action.WAIT;
                this.mob.setNoGravity(true);
                double d0 = this.posX - this.mob.getPosX();
                double d1 = this.posY - this.mob.getPosY();
                double d2 = this.posZ - this.mob.getPosZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double) 2.5000003E-7F) {
                    this.mob.setMoveVertical(0.0F);
                    this.mob.setMoveForward(0.0F);
                    return;
                }

                float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, f, 360.0F);

                float speed = (float) (this.speed * this.mob.getAttributeValue(Attributes.FLYING_SPEED));

                this.mob.setAIMoveSpeed(speed);
                double d4 = Math.sqrt(d0 * d0 + d2 * d2);
                if (Math.abs(d1) > (double) 1.0E-5F || Math.abs(d4) > (double) 1.0E-5F) {
                    float f2 = (float) (-(MathHelper.atan2(d1, d4) * (double) (180F / (float) Math.PI)));
                    this.mob.rotationPitch = this.limitAngle(this.mob.rotationPitch, f2, 20.0F);
                    this.mob.setMoveVertical(d1 > 0.0D ? speed : -speed);
                }
            } else {
                this.mob.setMoveVertical(0.0F);
                this.mob.setMoveForward(0.0F);
            }
        }
    }

    public class RandomFlyGoal extends Goal {
        private BlockPos targetPosition;

        public RandomFlyGoal() {
            this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            return true;
        }

        @Override
        public void tick() {
            if (this.targetPosition != null && (!Meganeura.this.world.isAirBlock(this.targetPosition) || this.targetPosition.getY() <= 1)) {
                this.targetPosition = null;
            }

            if (this.targetPosition == null || Meganeura.this.rand.nextInt(30) == 0 || this.targetPosition.withinDistance(Meganeura.this.getPositionVec(), 2.0D)) {
                Vector3d randomPos = RandomPositionGenerator.findRandomTarget(Meganeura.this, 10, 7);
                this.targetPosition = randomPos == null ? Meganeura.this.getPosition() : new BlockPos(randomPos);
            }

            double d0 = (double) this.targetPosition.getX() + 0.5D - Meganeura.this.getPosX();
            double d1 = (double) this.targetPosition.getY() + 0.1D - Meganeura.this.getPosY();
            double d2 = (double) this.targetPosition.getZ() + 0.5D - Meganeura.this.getPosZ();

            double speed = Meganeura.this.getAttributeValue(Attributes.FLYING_SPEED);

            Vector3d deltaMovement = Meganeura.this.getMotion();
            Vector3d signumDeltaMovement = deltaMovement.add((Math.signum(d0) * 0.5D - deltaMovement.x) * speed, (Math.signum(d1) * (double) 0.7F - deltaMovement.y) * speed, (Math.signum(d2) * 0.5D - deltaMovement.z) * speed);
            Meganeura.this.setMotion(signumDeltaMovement);

            float angle = (float) (MathHelper.atan2(signumDeltaMovement.z, signumDeltaMovement.x) * (double) (180F / (float) Math.PI)) - 90.0F;
            float wrappedAngle = MathHelper.wrapDegrees(angle - Meganeura.this.rotationYaw);
            Meganeura.this.moveForward = 0.5F;
            Meganeura.this.rotationYaw += wrappedAngle;
        }
    }
}
