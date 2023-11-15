package com.rena.dinosexpansion.common.tileentity;

import com.rena.dinosexpansion.core.init.BlockEntityInit;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ModSignTileEntity extends SignTileEntity {
    public ModSignTileEntity(){
        super();
    }


    @Override
    public TileEntityType<?> getType() {
        return BlockEntityInit.MOD_SIGN_TE.get();
    }
}
