package com.rena.dinosexpansion.common.entity.skeleton;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class DinosaurSkeleton extends Entity {
    private static final DataParameter<Integer> MODEL = EntityDataManager.createKey(DinosaurSkeleton.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> DARK = EntityDataManager.createKey(DinosaurSkeleton.class, DataSerializers.BOOLEAN);
    public DinosaurSkeleton(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return null;
    }
}
