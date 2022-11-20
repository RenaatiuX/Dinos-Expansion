package com.rena.dinosexpansion.common.entity.flying;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;


public abstract class DinosaurFlying extends Dinosaur implements IFlyingAnimal {

    private static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(DinosaurFlying.class, DataSerializers.BOOLEAN);
    public BlockPos airTarget;
    public float flyProgress;
    private boolean isFlying;
    private int ticksFlying;
    public DinosaurFlying(EntityType<DinosaurFlying> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
        this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathPriority(PathNodeType.COCOA, -1.0F);
        this.setPathPriority(PathNodeType.FENCE, -1.0F);
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
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
    public void travel(Vector3d travelVector) {
        /*if (this.isSitting()) {
            if (this.getNavigator().getPath() != null) {
                this.getNavigator().clearPath();
            }
            travelVector = Vector3d.ZERO;
        }
        super.travel(travelVector);*/
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.IN_WALL || source == DamageSource.FALLING_BLOCK || source == DamageSource.CACTUS || super.isInvulnerableTo(source);
    }

    @Override
    public void livingTick() {
        super.livingTick();
    }
}
