package com.rena.dinosexpansion.client.model.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.aquatic.fish.Wetherellus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class WetherellusModel extends AnimatedTickingGeoModel<Wetherellus> {

    @Override
    public ResourceLocation getModelLocation(Wetherellus object) {
        return DinosExpansion.modLoc("geo/wetherellus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Wetherellus object) {
        return DinosExpansion.modLoc("textures/entity/wetherellus.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Wetherellus animatable) {
        return DinosExpansion.modLoc("animations/wetherellus.json");
    }
}
