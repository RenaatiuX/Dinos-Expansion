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

public abstract class DinosaurAquatic extends Dinosaur{

    public DinosaurAquatic(EntityType<? extends Dinosaur> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    @Override
    public void livingTick() {
        super.livingTick();
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

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

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
        return false;
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
