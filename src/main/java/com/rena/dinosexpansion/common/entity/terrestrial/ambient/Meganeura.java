package com.rena.dinosexpansion.common.entity.terrestrial.ambient;

import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.BatMeleeAttackGoal;
import com.rena.dinosexpansion.common.entity.ia.BatRandomFly;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
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
import java.util.*;

public class Meganeura extends AmbientDinosaur implements IAnimatable, IAnimationTickable, IFlyingAnimal {


    public static final DataParameter<Boolean> HANGING = EntityDataManager.createKey(Meganeura.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Integer> HANGING_FACING = EntityDataManager.createKey(Meganeura.class, DataSerializers.VARINT);


    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    protected int hoverTicks, noClipCooldown;
    protected boolean movingToHangingPos = false;

    public float rotationInDegrees = 0, rotationPithInDegrees = 0;

    public Meganeura(EntityType<? extends Dinosaur> type, World world) {
        super(type, world, new DinosaurInfo("meganeura", 30, 10, 5, SleepRhythmGoal.SleepRhythm.NONE), generateLevelWithinBounds(10, 50));
        this.setHoverTicks(this.getMaxHoverTicks());
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
        this.goalSelector.addGoal(4, new FindHangPosAndHang(this, 200, 200, 30));

        this.goalSelector.addGoal(10, new BatRandomFly<>(this, getAvgHeight(), 3, a -> !a.isHanging() && a.getHoverTicks() <= 0 && !movingToHangingPos));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HANGING, false);
        this.dataManager.register(HANGING_FACING, 0);
    }


    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (super.attackEntityAsMob(entityIn)) {
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
        return !this.isHanging();
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
        if (!world.isRemote) {
            if (this.getHoverTicks() > 0)
                this.setHoverTicks(this.getHoverTicks() - 1);
            if (noClipCooldown > 0){
                noClipCooldown--;
                if (noClipCooldown == 0){
                    noClip = false;
                }
            }
        }
    }

    @Override
    public void applyKnockback(float p_233627_1_, double p_233627_2_, double p_233627_4_) {
        if (!this.isHanging())
            super.applyKnockback(p_233627_1_, p_233627_2_, p_233627_4_);
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
        return EntityInit.MEGANEURA.get().create(world);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isHanging())
            event.getController().setAnimation(new AnimationBuilder().loop("idle"));
        else
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
    protected void setHanging(boolean hanging) {
        if (!hanging && noClip)
            noClipCooldown = 40;
        this.dataManager.set(HANGING, hanging);
    }

    /**
     * @return the maximum amount of ticks the entitiy will constantly fly
     */
    public int getMaxHoverTicks() {
        //this says that the entity will always fly at least 30 seconds up to 1 minute and 30 seconds
        return 30;
    }

    /**
     * @return the average height the entity will fly above the ground
     */
    public int getAvgHeight() {
        return 6;
    }

    @Override
    public boolean canBeCollidedWith() {
        return super.canBeCollidedWith();
    }

    @Override
    public boolean func_241845_aY() {
        return !this.isHanging();
    }

    public void setDesiredPitch(int pitchAngle) {
        this.dataManager.set(HANGING_FACING, pitchAngle);
    }

    public int getDesiredPithAngle() {
        return dataManager.get(HANGING_FACING);
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        AxisAlignedBB aabb = super.getBoundingBox();
        return aabb;
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        EntitySize size = super.getSize(poseIn);
        if (this.isHanging()){
            return EntitySize.fixed(size.height, size.width);
        }
        return size;
    }

    /**
     * client synced
     */
    public boolean isHanging() {
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

    public static class FindHangPosAndHang extends Goal {
        protected final Meganeura creature;
        protected volatile BlockPos target;
        protected volatile Direction targetFacing;
        protected boolean wasHanging;
        protected volatile boolean searching;
        protected final int chanceHanging, chanceFlying, maxRange;
        protected int stuckTimer;
        protected double distTarget;

        public FindHangPosAndHang(Meganeura creature, int chanceHanging, int chanceFlying, int maxRange) {
            this.creature = creature;
            this.chanceHanging = chanceHanging;
            this.chanceFlying = chanceFlying;
            this.maxRange = maxRange;
        }

        @Override
        public boolean shouldExecute() {
            if (creature.isHanging() ? creature.rand.nextInt(chanceFlying) == 0 : creature.rand.nextInt(chanceHanging) == 0) {
                if (!creature.isHanging()) {
                    searching = true;
                    Thread searcher = new Thread(this::generateTargetPos);
                    searcher.start();
                    return true;
                }
                return true;
            }
            return false;
        }

        @Override
        public void startExecuting() {
            this.wasHanging = creature.isHanging();
            if (this.creature.isHanging()) {
                this.creature.setHanging(false);
            }
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (this.searching) {
                return true;
            }
            return wasHanging == this.creature.isHanging() && this.stuckTimer < BatRandomFly.MAX_STUCK_TIME;
        }

        @Override
        public void tick() {
            if (this.target != null) {
                if (!creature.movingToHangingPos) {
                    creature.movingToHangingPos = true;
                }
                if (distTarget > 1d && Math.abs(this.distTarget - this.creature.getDistanceSq(Vector3d.copyCentered(this.target))) < .1d) {
                    this.stuckTimer++;
                } else {
                    this.stuckTimer = 0;
                    this.distTarget = this.creature.getDistanceSq(Vector3d.copyCentered(this.target));
                }
                if (this.distTarget > .25d) {
                    Vector3d movementDirection;
                    if (this.distTarget > 2d) {
                        movementDirection = generateMovementVector();
                    } else {
                        movementDirection = Vector3d.copyCentered(this.target).subtract(creature.getPositionVec());
                    }
                    this.creature.setMotion(movementDirection.normalize().scale(creature.getAttributeValue(Attributes.FLYING_SPEED)));
                }

                if (this.distTarget <= .25d && !creature.isHanging()) {
                    creature.movingToHangingPos = false;
                    creature.setHanging(true);
                    this.wasHanging = true;
                    Vector3d pos = Vector3d.copyCentered(this.target);
                    creature.setPosition(pos.getX(), pos.getY(), pos.getZ());
                    creature.setMotion(Vector3d.copy(this.targetFacing.getDirectionVec()).scale(creature.getAttributeValue(Attributes.FLYING_SPEED) * .2d));
                    creature.rotationYaw = 90f;
                    creature.prevRotationYaw = 90f;
                    double x = this.targetFacing.getDirectionVec().getX();
                    double z = this.targetFacing.getDirectionVec().getZ();
                    creature.setDesiredPitch((int) (Math.toDegrees(Math.atan2(x, z)) + 90) % 360);
                }
                if (this.distTarget <= .25d && creature.isHanging()) {
                    creature.noClip = true;
                    creature.setMotion(Vector3d.copy(this.targetFacing.getDirectionVec()).scale(creature.getAttributeValue(Attributes.FLYING_SPEED) * .2d));
                    if (Vector3d.copyCentered(this.target.offset(this.targetFacing)).squareDistanceTo(creature.getPositionVec()) <= .3d) {
                        creature.setMotion(Vector3d.ZERO);
                        this.wasHanging = !this.wasHanging;
                    }
                }

            }
        }

        protected void generateTargetPos() {
            Set<BlockPos> finishedPos = new HashSet<>();
            Queue<Pair<Integer, BlockPos>> workingPoses = new ArrayDeque<>();
            Set<BlockPos> inProgress = new HashSet<>();
            workingPoses.add(Pair.of(0, this.creature.getPosition()));
            while (!workingPoses.isEmpty()) {
                Pair<Integer, BlockPos> currentElement = workingPoses.poll();
                inProgress.remove(currentElement.getSecond());
                if (currentElement.getFirst() > this.maxRange) {
                    continue;
                }
                BlockPos currentPos = currentElement.getSecond();
                for (Direction dir : Direction.values()) {
                    BlockPos offset = currentPos.offset(dir);
                    if (!this.creature.world.isAirBlock(offset)) {
                        continue;
                    }
                    for (Direction facing : Direction.values()) {
                        if (facing == Direction.UP || facing == Direction.DOWN)
                            continue;
                        BlockPos offsetPos = offset.offset(facing);
                        if (this.creature.world.getBlockState(offsetPos).isSolidSide(creature.world, offsetPos, facing.getOpposite())) {
                            this.target = offset;
                            this.targetFacing = facing;
                            this.searching = false;
                            return;
                        }
                    }
                    if (!finishedPos.contains(offset) && !inProgress.contains(offset)) {
                        workingPoses.add(Pair.of(currentElement.getFirst() + 1, offset));
                        inProgress.add(offset);
                    }
                }
                finishedPos.add(currentPos);
                if (!this.creature.isAlive()) {
                    break;
                }
            }

            searching = false;
        }

        protected Vector3d generateMovementVector() {
            Vector3d direction = Vector3d.copyCentered(this.target).subtract(creature.getPositionVec());
            direction = direction.rotatePitch((float) Math.toRadians(MathHelper.nextInt(creature.rand, -20, 20)));
            direction = direction.rotateRoll((float) Math.toRadians(MathHelper.nextInt(creature.rand, -20, 20)));
            direction = direction.rotateYaw((float) Math.toRadians(MathHelper.nextInt(creature.rand, -20, 20)));
            return direction;
        }

        @Override
        public void resetTask() {
            this.target = null;
            this.stuckTimer = 0;
            creature.movingToHangingPos = false;
            this.searching = false;
        }
    }
}
