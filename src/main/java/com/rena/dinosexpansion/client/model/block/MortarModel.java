package com.rena.dinosexpansion.client.model.block;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.tileentity.MortarTileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MortarModel extends AnimatedGeoModel<MortarTileEntity> {
    @Override
    public ResourceLocation getModelLocation(MortarTileEntity object) {
        return DinosExpansion.modLoc("geo/mortar.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MortarTileEntity object) {
        return DinosExpansion.modLoc("textures/block/mortar.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MortarTileEntity animatable) {
        return DinosExpansion.modLoc("animations/mortar.animation.json");
    }
}
