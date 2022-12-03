package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.helper.ISemiAquatic;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class DinosaurAquatic extends Dinosaur implements ISemiAquatic {

    private static final DataParameter<Boolean> BEACHED = EntityDataManager.createKey(DinosaurAquatic.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DESPAWN_BEACH = EntityDataManager.createKey(DinosaurAquatic.class, DataSerializers.BOOLEAN);
    private static final int MAX_TIME_ON_LAND = 1000;
    private static final int MAX_TIME_IN_WATER = 1000;
    public int timeInWater = 0;
    public int timeOnLand = 0;
    protected boolean isAmphibious = false;
    protected boolean isLandNavigator;
    public float prevBeachedProgress;
    public float beachedProgress;
    private int despawnDelay = 47999;

    public DinosaurAquatic(EntityType<? extends Dinosaur> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
        switchNavigator(true);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return super.canDespawn(distanceToClosestPlayer);
    }

    private boolean canDespawn() {
        return isDespawnBeach();
    }

    private void tryDespawn() {
        if (this.canDespawn()) {
            this.despawnDelay = this.despawnDelay - 1;
            if (this.despawnDelay <= 0) {
                this.clearLeashed(true, false);
                this.remove();
            }
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();
       /* if (this.isOnGround() && !this.isInWaterOrBubbleColumn()) {
            this.setBeached(true);
            this.rotationPitch = 0;
            this.setSleeping(false);
        }
        if (isBeached()) {
            this.setMotion(this.getMotion().mul(0.5, 0.5, 0.5));
            this.despawnDelay = 47999;
            this.setBeached(false);
        }

        prevBeachedProgress = beachedProgress;
        if (isBeached() && this.beachedProgress < 10F) {
            this.beachedProgress++;
        }
        if (!isBeached() && this.beachedProgress > 0F) {
            this.beachedProgress--;
        }
        this.rotationYawHead = this.rotationYaw;
        this.renderYawOffset = this.rotationYaw;

        if (this.isInWater() && !this.areEyesInFluid(FluidTags.WATER) && this.getAir() > 140) {
            this.setMotion(this.getMotion().add(0, -0.06, 0));
        }
        if (!this.world.isRemote) {
            this.tryDespawn();
        }*/
    }

    protected void updateAir(int air) {
        if (!canBreathOnLand()) {
            if (this.isAlive() && !this.isInWaterOrBubbleColumn()) {
                this.setAir(air - 1);
                if (this.getAir() == -40) {
                    this.setAir(0);
                    this.attackEntityFrom(DamageSource.DROWN, 2.0F);
                }
            } else {
                this.setAir(500);
            }
        }
    }

    @Override
    public void baseTick() {
        int i = this.getAir();
        super.baseTick();
        this.updateAir(i);
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveController = new MovementController(this);
            this.navigator = new GroundPathNavigator(this, world);
            this.isLandNavigator = true;
        } else {
            this.moveController = new AquaticMoveController(this, 1F);
            this.navigator = new SwimmerPathNavigator(this, world);
            this.isLandNavigator = false;
        }
    }

    @Override
    public boolean shouldEnterWater() {
        if (!isAmphibious) {
            return true;
        }
        return this.timeInWater == 0 && timeOnLand > MAX_TIME_ON_LAND;
    }

    @Override
    public boolean shouldLeaveWater() {
        return isAmphibious && this.timeInWater > MAX_TIME_IN_WATER && timeOnLand < MAX_TIME_ON_LAND;
    }

    @Override
    public boolean shouldStopMoving() {
        return isMovementDisabled();
    }

    @Override
    public int getWaterSearchRange() {
        return 20;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    //public abstract double swimSpeed();

    @Override
    public boolean isOnLadder() {
        return false;
    }

    public void destroyBoat(Entity sailor) {
        if (sailor.getRidingEntity() != null && sailor.getRidingEntity() instanceof BoatEntity && !world.isRemote) {
            BoatEntity boat = (BoatEntity) sailor.getRidingEntity();
            boat.remove();
            if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                for (int i = 0; i < 3; ++i) {
                    boat.entityDropItem(new ItemStack(boat.getBoatType().asPlank().asItem(), 2), 0);
                }
                for (int j = 0; j < 2; ++j) {
                    boat.entityDropItem(Items.STICK, 1);
                }
            }
        }
    }

    public boolean isPushedByWater() {
        return isBeached();
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(3);
    }

    @Override
    public int getTalkInterval() {
        return 120;
    }

    @Override
    public boolean isNotColliding(IWorldReader worldIn) {
        return worldIn.checkNoEntityCollision(this);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(BEACHED, false);
        this.dataManager.register(DESPAWN_BEACH, Boolean.FALSE);
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putInt("TimeOnLand", this.timeOnLand);
        nbt.putInt("TimeInWater", this.timeInWater);
        nbt.putBoolean("Beached", this.isBeached());
        nbt.putBoolean("BeachedDespawnFlag", this.isDespawnBeach());
        nbt.putInt("DespawnDelay", this.despawnDelay);
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.timeOnLand = nbt.getInt("TimeOnLand");
        this.timeInWater = nbt.getInt("TimeInWater");
        this.setBeached(nbt.getBoolean("Beached"));
        this.setDespawnBeach(nbt.getBoolean("BeachedDespawnFlag"));
        if (nbt.contains("DespawnDelay", 99)) {
            this.despawnDelay = nbt.getInt("DespawnDelay");
        }
    }

    public boolean isBeached() {
        return this.dataManager.get(BEACHED);
    }

    public void setBeached(boolean beached) {
        this.dataManager.set(BEACHED, beached);
    }

    public boolean isDespawnBeach() {
        return this.dataManager.get(DESPAWN_BEACH);
    }

    public void setDespawnBeach(boolean despawn) {
        this.dataManager.set(DESPAWN_BEACH, despawn);
    }

    @Override
    public boolean isInWater() {
        return super.isInWater() || this.areEyesInFluid(FluidTags.WATER);
    }

    @Override
    public boolean canBeSteered() {
        return this.isInWater() && super.canBeSteered();
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

    public boolean canBreathOnLand() {
        return true;
    }

}
