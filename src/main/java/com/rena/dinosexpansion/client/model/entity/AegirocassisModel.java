package com.rena.dinosexpansion.client.model.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.aquatic.Aegirocassis;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class AegirocassisModel extends AnimatedTickingGeoModel<Aegirocassis> {
    @Override
    public ResourceLocation getModelLocation(Aegirocassis object) {
        return DinosExpansion.modLoc("geo/aegirocassis.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Aegirocassis object) {
        return DinosExpansion.modLoc("textures/entity/aegirocassis.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Aegirocassis animatable) {
        return DinosExpansion.modLoc("animations/aegirocassis.json");
    }
}
