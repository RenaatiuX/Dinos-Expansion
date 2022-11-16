package com.rena.dinosexpansion.common.entity.flying;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public abstract class DinosaurFlying extends Dinosaur implements IFlyingAnimal {

    private static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(DinosaurFlying.class, DataSerializers.BOOLEAN);
    public BlockPos airTarget;
    public float flyProgress;
    private boolean isFlying;
    private int ticksFlying;
    public DinosaurFlying(EntityType<? extends DinosaurFlying> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
    }

}
