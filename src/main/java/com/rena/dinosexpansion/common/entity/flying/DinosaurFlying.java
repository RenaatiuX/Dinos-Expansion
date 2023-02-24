package com.rena.dinosexpansion.common.entity.flying;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.FlightMoveController;
import com.rena.dinosexpansion.common.entity.ia.navigator.FlyingGroundPathNavigator;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;


public abstract class DinosaurFlying extends Dinosaur implements IFlyingAnimal {
    private static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(DinosaurFlying.class, DataSerializers.BOOLEAN);
    private boolean isLandNavigator;
    public float prevFlyProgress;
    public float flyProgress;
    private int timeFlying = 0;
    public DinosaurFlying(EntityType<? extends Dinosaur> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
        this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.WATER_BORDER, 16.0F);
        switchNavigator(false);
    }

    @Override
    protected void jump() {
        super.jump();
    }

    @Override
    public boolean canTrample(BlockState state, BlockPos pos, float fallDistance) {
        return false;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            this.setSitting(false);
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putBoolean("Flying", this.isFlying());
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.setFlying(nbt.getBoolean("Flying"));
    }

    @Override
    public void tick() {
        super.tick();
        prevFlyProgress = flyProgress;
        if (isFlying() && flyProgress < 5F) {
            flyProgress++;
        }
        if (!isFlying() && flyProgress > 0F) {
            flyProgress--;
        }
        if (!world.isRemote) {
            if(isFlying() && isSleeping() && isOnGround()){
                setFlying(false);
                setNoGravity(false);
            }
            if (isFlying() && this.isLandNavigator) {
                switchNavigator(false);
            }
            if (!isFlying() && !this.isLandNavigator) {
                switchNavigator(true);
            }
            if (isFlying()) {
                timeFlying++;
                this.setNoGravity(true);
                if (this.isQueuedToSit() || this.isPassenger() || this.isInLove()) {
                    this.setFlying(false);
                }
            } else {
                timeFlying = 0;
                this.setNoGravity(false);
            }
        }
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveController = new MovementController(this);
            this.navigator = new GroundPathNavigator(this, world);
            this.isLandNavigator = true;
        } else {
            this.moveController = new FlightMoveController(this, 0.6F, false);
            this.navigator = new FlyingGroundPathNavigator(this, world);
            this.isLandNavigator = false;
        }
    }

    public boolean isFlying() {
        return this.dataManager.get(FLYING);
    }

    public void setFlying(boolean flying) {
        if(flying && isChild()){
            return;
        }
        this.dataManager.set(FLYING, flying);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(FLYING, false);
    }

    @Override
    public void travel(Vector3d vec3d) {
        if (this.isQueuedToSit()) {
            if (this.getNavigator().getPath() != null) {
                this.getNavigator().clearPath();
            }
            vec3d = Vector3d.ZERO;
        }
        super.travel(vec3d);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.IN_WALL || source == DamageSource.FALLING_BLOCK || source == DamageSource.CACTUS || super.isInvulnerableTo(source);
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
        return getRNG().nextDouble() <= 0.51 ? Gender.MALE : Gender.FEMALE;
    }

    @Override
    public int getTalkInterval() {
        return 120;
    }

    public boolean isDirectPathBetweenPoints(Vector3d target) {
        Vector3d Vector3d = new Vector3d(this.getPosX(), this.getPosYEye(), this.getPosZ());
        return this.world.rayTraceBlocks(new RayTraceContext(Vector3d, target, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this)).getType() != RayTraceResult.Type.MISS;
    }

    public Vector3d getBlockInViewAway(Vector3d fleePos, float radiusAdd) {
        float radius = 0.75F * (0.7F * 6) * -3 - this.getRNG().nextInt(24) - radiusAdd;
        float neg = this.getRNG().nextBoolean() ? 1 : -1;
        float renderYawOffset = this.renderYawOffset;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (this.getRNG().nextFloat() * neg);
        double extraX = radius * MathHelper.sin((float) (Math.PI + angle));
        double extraZ = radius * MathHelper.cos(angle);
        BlockPos radialPos = new BlockPos(fleePos.getX() + extraX, 0, fleePos.getZ() + extraZ);
        BlockPos ground = getDinosaurFlyingGround(radialPos);
        int distFromGround = (int) this.getPosY() - ground.getY();
        int flightHeight = 4 + this.getRNG().nextInt(10);
        BlockPos newPos = ground.up(distFromGround > 8 ? flightHeight : this.getRNG().nextInt(6) + 1);
        if (!this.isDirectPathBetweenPoints(Vector3d.copyCentered(newPos)) && this.getDistanceSq(Vector3d.copyCentered(newPos)) > 1) {
            return Vector3d.copyCentered(newPos);
        }
        return null;
    }

    private BlockPos getDinosaurFlyingGround(BlockPos in){
        BlockPos position = new BlockPos(in.getX(), this.getPosY(), in.getZ());
        while (position.getY() > 2 && world.isAirBlock(position)) {
            position = position.down();
        }
        return position;
    }

    public Vector3d getBlockGrounding(Vector3d fleePos) {
        float radius = 0.75F * (0.7F * 6) * -3 - this.getRNG().nextInt(24);
        float neg = this.getRNG().nextBoolean() ? 1 : -1;
        float renderYawOffset = this.renderYawOffset;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (this.getRNG().nextFloat() * neg);
        double extraX = radius * MathHelper.sin((float) (Math.PI + angle));
        double extraZ = radius * MathHelper.cos(angle);
        BlockPos radialPos = new BlockPos(fleePos.getX() + extraX, getPosY(), fleePos.getZ() + extraZ);
        BlockPos ground = this.getDinosaurFlyingGround(radialPos);
        if (ground.getY() == 0) {
            return this.getPositionVec();
        } else {
            ground = this.getPosition();
            while (ground.getY() > 2 && world.isAirBlock(ground)) {
                ground = ground.down();
            }
        }
        if (!this.isDirectPathBetweenPoints(Vector3d.copyCentered(ground.up()))) {
            return Vector3d.copyCentered(ground);
        }
        return null;
    }

    private boolean isOverWater() {
        BlockPos position = this.getPosition();
        while (position.getY() > 2 && world.isAirBlock(position)) {
            position = position.down();
        }
        return !world.getFluidState(position).isEmpty();
    }

    @Override
    protected void setKnockout(boolean knockout) {
        super.setKnockout(knockout);
       this.setNoGravity(!knockout);
    }

    @Override
    public void setSleeping(boolean sleeping) {
        super.setSleeping(sleeping);
        if (sleeping && isFlying()){
            BlockPos leave = detectTreesInArea(this.world, this.getPosition(), 50);
            if (leave != null){
                this.getMoveHelper().setMoveTo(leave.getX(), leave.getY(), leave.getZ(), 1.1D);
            }
            Vector3d randomPos = null;
            while (randomPos == null){
                randomPos = getBlockGrounding(getPositionVec());
            }
            this.navigator.tryMoveToXYZ(randomPos.x, randomPos.y, randomPos.z, 1.1D);
        }
    }

    public static BlockPos detectTreesInArea(World world, BlockPos center, int radius) {
        for (int x = center.getX() - radius; x <= center.getX() + radius; x++) {
            for (int y = center.getY() - radius; y <= center.getY() + radius; y++) {
                for (int z = center.getZ() - radius; z <= center.getZ() + radius; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState block = world.getBlockState(pos);
                    if (block.isIn(BlockTags.LEAVES) && world.isAirBlock(pos.up())) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    public class DinosaurWalkIdleGoal extends Goal{
        protected final DinosaurFlying dinosaurFlying;
        protected double x;
        protected double y;
        protected double z;
        private boolean flightTarget = false;
        public DinosaurWalkIdleGoal() {
            super();
            this.setMutexFlags(EnumSet.of(Flag.MOVE));
            this.dinosaurFlying = DinosaurFlying.this;
        }
        @Override
        public boolean shouldExecute() {
            if (this.dinosaurFlying.isBeingRidden() || (dinosaurFlying.getAttackTarget() != null && dinosaurFlying.getAttackTarget().isAlive()) || this.dinosaurFlying.isPassenger() || dinosaurFlying.isMovementDisabled()) {
                return false;
            } else {
                if (this.dinosaurFlying.getRNG().nextInt(30) != 0 && !dinosaurFlying.isFlying()) {
                    return false;
                }
                if (this.dinosaurFlying.isOnGround()) {
                    this.flightTarget = rand.nextBoolean();
                } else {
                    this.flightTarget = rand.nextInt(5) > 0 && dinosaurFlying.timeFlying < 200;
                }
                Vector3d lvt_1_1_ = this.getPosition();
                if (lvt_1_1_ == null) {
                    return false;
                } else {
                    this.x = lvt_1_1_.x;
                    this.y = lvt_1_1_.y;
                    this.z = lvt_1_1_.z;
                    return true;
                }
            }
        }

        @Override
        public void tick() {
            if (flightTarget) {
                dinosaurFlying.getMoveHelper().setMoveTo(x, y, z, 1F);
            } else {
                this.dinosaurFlying.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, 0.5F);
            }
            if (!flightTarget && isFlying() && dinosaurFlying.onGround) {
                dinosaurFlying.setFlying(false);
            }
            if (isFlying() && dinosaurFlying.onGround && dinosaurFlying.timeFlying > 10) {
                dinosaurFlying.setFlying(false);
            }
        }
        @Nullable
        protected Vector3d getPosition() {
            Vector3d vector3d = dinosaurFlying.getPositionVec();
            if(dinosaurFlying.isOverWater()){
                flightTarget = true;
            }
            if (flightTarget) {
                if (dinosaurFlying.timeFlying < 50 || dinosaurFlying.isOverWater()) {
                    return dinosaurFlying.getBlockInViewAway(vector3d, 0);
                } else {
                    return dinosaurFlying.getBlockGrounding(vector3d);
                }
            } else {

                return RandomPositionGenerator.findRandomTarget(this.dinosaurFlying, 10, 15);
            }
        }
        @Override
        public boolean shouldContinueExecuting() {
            if (flightTarget) {
                return dinosaurFlying.isFlying() && dinosaurFlying.getDistanceSq(x, y, z) > 2F;
            } else {
                return !this.dinosaurFlying.getNavigator().noPath() && !this.dinosaurFlying.isBeingRidden() && !dinosaurFlying.isMovementDisabled();
            }
        }
        @Override
        public void startExecuting() {
            if (flightTarget) {
                dinosaurFlying.setFlying(true);
                dinosaurFlying.getMoveHelper().setMoveTo(x, y, z, 1F);
            } else {
                this.dinosaurFlying.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, 1F);
            }
        }
        @Override
        public void resetTask() {
            this.dinosaurFlying.getNavigator().clearPath();
            super.resetTask();
        }
    }
}
