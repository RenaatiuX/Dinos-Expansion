package com.rena.dinosexpansion.common.world.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

public class CompoundNbtUtil {


    public static CompoundNBT saveBlockPos(BlockPos pos){
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("posX", pos.getX());
        nbt.putInt("posY", pos.getY());
        nbt.putInt("posZ", pos.getZ());
        return nbt;
    }

    public static BlockPos readBlockPos(CompoundNBT nbt){
        if (nbt.contains("posX") && nbt.contains("posY") && nbt.contains("posZ")){
            return new BlockPos(nbt.getInt("posX"), nbt.getInt("posY"), nbt.getInt("posZ"));
        }
        throw new IllegalArgumentException("nbt doesnt contain a block pos");
    }
}
