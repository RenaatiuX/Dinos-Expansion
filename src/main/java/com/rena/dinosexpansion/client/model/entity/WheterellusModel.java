package com.rena.dinosexpansion.client.model.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.aquatic.Wheterellus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class WheterellusModel extends AnimatedTickingGeoModel<Wheterellus> {

    @Override
    public ResourceLocation getModelLocation(Wheterellus object) {
        return DinosExpansion.modLoc("geo/wheterellus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Wheterellus object) {
        return DinosExpansion.modLoc("textures/entity/wheterellus.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Wheterellus animatable) {
        return DinosExpansion.modLoc("animations/wheterellus.animation.json");
    }
}
