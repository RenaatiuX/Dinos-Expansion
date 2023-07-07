package com.rena.dinosexpansion.common.entity.misc;

import com.rena.dinosexpansion.common.entity.util.BoatType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class CustomBoatEntity extends BoatEntity {
    private static final DataParameter<Integer> BOAT_TYPE = EntityDataManager.createKey(CustomBoatEntity.class, DataSerializers.VARINT);
    private BoatEntity.Status status;
    public CustomBoatEntity(EntityType<? extends CustomBoatEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(BOAT_TYPE, BoatType.CRATAEGUS.ordinal());
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putString("Type", this.getBoat().getName());
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        if (compound.contains("Type", 8)) {
            this.setBoat(BoatType.getTypeFromString(compound.getString("Type")));
        }
    }

    public void setBoat(BoatType boatType) {
        this.dataManager.set(BOAT_TYPE, boatType.ordinal());
    }

    public BoatType getBoat() {
        return BoatType.byId(this.dataManager.get(BOAT_TYPE));
    }
}
