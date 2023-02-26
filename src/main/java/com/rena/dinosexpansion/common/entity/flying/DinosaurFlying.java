package com.rena.dinosexpansion.common.entity.flying;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.FlightMoveController;
import com.rena.dinosexpansion.common.entity.ia.navigator.FlyingGroundPathNavigator;
import com.rena.dinosexpansion.common.util.enums.MoveOrder;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.EnumSet;


public abstract class DinosaurFlying extends Dinosaur implements IFlyingAnimal {

    private static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(DinosaurFlying.class, DataSerializers.BOOLEAN);

    protected boolean isLandNavigator;
    protected boolean shouldLand = false, startFlying = false;
    /**
     * makes the entity store the sleep order until it lands
     */
    protected boolean shouldSleep = false;

    public DinosaurFlying(EntityType<? extends Dinosaur> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
        this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.WATER_BORDER, 16.0F);
        switchNavigator(false);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public void setSleeping(boolean sleeping) {
        if (isFlying() && sleeping) {
            shouldLand();
            this.shouldSleep = true;
        } else {
            super.setSleeping(sleeping);
        }
    }

    @Override
    protected void setKnockout(boolean knockout) {
        if (knockout && isFlying()) {
            setFlying(false);
        }
        super.setKnockout(knockout);
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
            this.startFlying();
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
                //halfs the damage rounded up
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putBoolean("Flying", this.isFlying());
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.shouldLand = nbt.getBoolean("shouldLand");
        this.startFlying = nbt.getBoolean("startFlying");
        this.shouldSleep = nbt.getBoolean("shouldSleep");
        this.setFlying(nbt.getBoolean("Flying"));
    }

    /**
     * makes the entity land
     * only use this on the serve side, it won´t do anything on the client side
     */
    public void shouldLand() {
        if (!this.world.isRemote) {
            if (this.isFlying() && !this.isOnGround()) {
                this.shouldLand = true;
                this.navigator.clearPath();
            }
        }
    }

    /**
     * makes the entity land
     * only use this on the serve side, it won´t do anything on the client side
     */
    public void startFlying() {
        if (!world.isRemote) {
            if (!this.isFlying() && this.isOnGround()) {
                this.startFlying = true;
                this.navigator.clearPath();
                setFlying(true);
            }
        }
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            if (shouldLand && isOnGround()) {
                shouldLand = false;
                if (shouldSleep) {
                    super.setSleeping(true);
                    shouldSleep = false;
                }
                this.setFlying(false);
            }
            if (startFlying && !isOnGround()) {
                startFlying = false;
            }
        }
        super.tick();
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

    protected void setFlying(boolean flying) {
        if (flying && isChild()) {
            return;
        }
        switchNavigator(!flying);
        setNoGravity(flying);
        this.dataManager.set(FLYING, flying);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(FLYING, false);
    }

    @Override
    public boolean isOnGround() {
        return super.isOnGround() || isInFluid();
    }

    @Override
    public void travel(Vector3d vec3d) {
        if (this.getMoveOrder() == MoveOrder.SIT) {
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


    public boolean isInFluid() {
        AxisAlignedBB axisalignedbb = this.getBoundingBox().shrink(0.001D);
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.ceil(axisalignedbb.maxX);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.ceil(axisalignedbb.maxY);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.ceil(axisalignedbb.maxZ);
        if (!this.world.isAreaLoaded(i, k, i1, j, l, j1)) {
            return false;
        } else {
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
            for (int l1 = i; l1 < j; ++l1) {
                for (int i2 = k; i2 < l; ++i2) {
                    for (int j2 = i1; j2 < j1; ++j2) {
                        blockpos$mutable.setPos(l1, i2, j2);
                        FluidState fluidstate = this.world.getFluidState(blockpos$mutable);
                        if (!fluidstate.isEmpty())
                            return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return the minimum distance from the ground the entity can have when random flying
     */
    public int getMaxFlyingHeight() {
        return 16;
    }


    /**
     * @param dino
     * @return this will return the first blockPos under the dino that is not air id there isnt it will return the same pos with y set to 0
     */
    public static BlockPos getGroundDinosaur(DinosaurFlying dino) {
        BlockPos dinoPos = dino.getPosition();
        for (; dinoPos.getY() > 0 && dino.world.isAirBlock(dinoPos); dinoPos = dinoPos.down()) ;
        return dinoPos;
    }

    public static BlockPos getGroundPos(BlockPos pos, World world) {
        BlockPos copy = new BlockPos(pos);
        if (world.isAirBlock(copy.up())) {
            for (; copy.getY() > 0 && world.isAirBlock(copy); copy = copy.down()) ;
            return copy;
        } else if (world.isAirBlock(copy.down())) {
            for (; copy.getY() > 0 && world.isAirBlock(copy); copy = copy.up()) ;
            return copy;
        }
        return copy.down();
    }

    /**
     * this makes the dino land when it should land
     * it also makes dinos preferably land in high trees
     */
    public static class LandWhenOrderedGoal extends Goal {

        protected final DinosaurFlying dino;
        protected final double speed;
        protected final boolean preferTrees;
        protected BlockPos target = null;

        public LandWhenOrderedGoal(DinosaurFlying dino, double speed, boolean preferTrees) {
            this.dino = dino;
            this.speed = speed;
            this.preferTrees = preferTrees;
        }


        @Override
        public boolean shouldExecute() {
            if (!this.dino.shouldLand) {
                return false;
            }
            BlockPos tmp = findPosition();
            if (tmp == null)
                return false;
            this.target = tmp;
            return true;
        }

        @Override
        public void startExecuting() {
            this.dino.getNavigator().tryMoveToXYZ(this.target.getX(), this.target.getY(), this.target.getZ(), this.speed);
        }

        @Override
        public void resetTask() {
            this.target = null;
            this.dino.getNavigator().clearPath();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return !this.dino.getNavigator().noPath() && !this.dino.isMovementDisabled() && !this.dino.isBeingRidden();
        }

        protected BlockPos findPosition() {
            /*
            if (preferTrees) {
                Vector3d treePos = getTreePos();
                if (treePos != null)
                   return new BlockPos(treePos);
            }

             */
            BlockPos ground = getGroundDinosaur(dino);
            BlockPos radialPos = new BlockPos(dino.getPosX() + dino.getRNG().nextInt(10) - 5, ground.getY(), dino.getPosZ() + dino.getRNG().nextInt(10) - 5);
            if (radialPos != null) {
                return radialPos;
            }
            return null;
        }

        @Nullable
        private Vector3d getTreePos() {
            BlockPos blockpos = this.dino.getPosition();
            BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
            BlockPos.Mutable blockpos$mutableblockpos1 = new BlockPos.Mutable();

            for (BlockPos blockpos1 : BlockPos.getAllInBoxMutable(MathHelper.floor(this.dino.getPosX() - 3.0D), MathHelper.floor(this.dino.getPosY() + 6), MathHelper.floor(this.dino.getPosZ() - 3.0D), MathHelper.floor(this.dino.getPosX() + 3.0D), MathHelper.floor(this.dino.getPosY() - 6), MathHelper.floor(this.dino.getPosZ() + 3.0D))) {
                if (!blockpos.equals(blockpos1)) {
                    BlockState blockstate = this.dino.world.getBlockState(blockpos$mutableblockpos1.setAndMove(blockpos1, Direction.DOWN));
                    boolean flag = blockstate.getBlock() instanceof LeavesBlock || blockstate.isIn(BlockTags.LOGS);
                    if (flag && this.dino.world.isAirBlock(blockpos1) && this.dino.world.isAirBlock(blockpos$mutableblockpos.setAndMove(blockpos1, Direction.UP))) {
                        return Vector3d.copyCentered(blockpos1);
                    }
                }
            }

            return null;
        }
    }

    /**
     * makes the dino walk around on the ground
     */
    public static class FlyingRandomWalkingGoal extends RandomWalkingGoal {

        protected DinosaurFlying dino;

        public FlyingRandomWalkingGoal(DinosaurFlying creatureIn, double speedIn) {
            this(creatureIn, speedIn, 120);
        }

        public FlyingRandomWalkingGoal(DinosaurFlying creatureIn, double speedIn, int chance) {
            this(creatureIn, speedIn, chance, true);
        }

        public FlyingRandomWalkingGoal(DinosaurFlying creature, double speed, int chance, boolean stopWhenIdle) {
            super(creature, speed, chance, stopWhenIdle);
            this.dino = creature;
        }

        @Override
        public boolean shouldExecute() {
            return !dino.isMovementDisabled() && !dino.isFlying() && super.shouldExecute();
        }
    }

    /**
     * this goal make the entity fly from time to time and go back to the ground
     */
    public static class ChangeFlyingGoal extends Goal {

        protected final DinosaurFlying dino;
        protected final double chanceStartFlying;
        protected final double chanceGoOnGround;

        public ChangeFlyingGoal(DinosaurFlying dino, double chanceStartFlying, double chanceGoOnGround) {
            this.dino = dino;
            this.chanceStartFlying = chanceStartFlying;
            this.chanceGoOnGround = chanceGoOnGround;
        }

        @Override
        public boolean shouldExecute() {
            return !dino.isMovementDisabled() && !this.dino.isBeingRidden() && dino.isFlying() ? dino.getRNG().nextDouble() < chanceGoOnGround : dino.getRNG().nextDouble() < chanceStartFlying;
        }

        @Override
        public void startExecuting() {
            if (dino.isFlying())
                dino.shouldLand();
            else
                dino.startFlying();
        }
    }


    public static class DinosaurRandomFlyingGoal extends Goal {

        protected final DinosaurFlying dino;
        protected final double chance, speed;
        protected BlockPos target;

        public DinosaurRandomFlyingGoal(DinosaurFlying dino, double chance, double speed) {
            this.dino = dino;
            this.chance = chance;
            this.speed = speed;
        }

        @Override
        public boolean shouldExecute() {
            if (this.dino.getRNG().nextDouble() >= chance)
                return false;
            BlockPos tmp = getBlockInView(this.dino);
            if (tmp == null)
                return false;
            this.target = tmp;
            return !dino.isMovementDisabled() && target != null && !this.dino.isBeingRidden() && (dino.isFlying() || dino.startFlying) && !dino.shouldLand;
        }

        @Override
        public void startExecuting() {
            DinosExpansion.LOGGER.debug("moves to: " + target.toString());
            this.dino.getNavigator().tryMoveToXYZ(target.getX(), target.getY(), target.getZ(), this.speed);
        }

        @Override
        public void resetTask() {
            this.dino.getNavigator().clearPath();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return !dino.getNavigator().noPath() && !dino.isMovementDisabled() && !dino.isBeingRidden();
        }

        public static BlockPos getBlockInView(DinosaurFlying dinosaur) {
            BlockPos radialPos = new BlockPos(dinosaur.getPosX() + dinosaur.getRNG().nextInt(40) - 20, 0, dinosaur.getPosZ() + dinosaur.getRNG().nextInt(40) - 20);
            BlockPos ground = getGroundDinosaur(dinosaur);
            int distFromGround = (int) dinosaur.getPosY() - ground.getY();
            BlockPos newPos = radialPos.up(distFromGround > dinosaur.getMaxFlyingHeight() ? (int) Math.min(dinosaur.getMaxFlyingHeight(), dinosaur.getPosY() + dinosaur.getRNG().nextInt(4) - 2) : (int) dinosaur.getPosY() + dinosaur.getRNG().nextInt(10) + 1);
            if (dinosaur.world.isAirBlock(newPos) && dinosaur.getDistanceSq(Vector3d.copyCentered(newPos)) > 6) {
                return newPos;
            }
            return null;
        }

        public static boolean isTargetBlocked(Entity entity, Vector3d target) {
            if (target != null) {
                AxisAlignedBB entityBox = entity.getBoundingBox().offset(-entity.getPosX(), -entity.getPosY(), -entity.getPosZ()); // Exclude the entity's bounding box from the RayTrace
                RayTraceResult rayTrace = entity.world.rayTraceBlocks(new RayTraceContext(entity.getPositionVec(), target, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entity));
                if (rayTrace != null && rayTrace.getType() != RayTraceResult.Type.MISS) {
                    return entity.world.isAirBlock(new BlockPos(target));
                }
            }
            return false;
        }

    }
}
