package com.rena.dinosexpansion.client.model.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.aquatic.MegaPiranha;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MegaPiranhaModel extends AnimatedGeoModel<MegaPiranha> {
    @Override
    public ResourceLocation getModelLocation(MegaPiranha object) {
        return DinosExpansion.modLoc("geo/megapiranha.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MegaPiranha object) {
        return DinosExpansion.modLoc("textures/entity/megapiranha.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MegaPiranha animatable) {
        return DinosExpansion.modLoc("animations/piranha.animation.json");
    }
}
