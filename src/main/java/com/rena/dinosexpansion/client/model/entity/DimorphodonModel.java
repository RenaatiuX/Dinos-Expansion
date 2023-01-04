package com.rena.dinosexpansion.client.model.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.flying.Dimorphodon;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class DimorphodonModel extends AnimatedTickingGeoModel<Dimorphodon> {



    @Override
    public ResourceLocation getModelLocation(Dimorphodon object) {
        return DinosExpansion.modLoc("geo/dimorphodon.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Dimorphodon object) {
        return DinosExpansion.modLoc("textures/entity/dimorphodon.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Dimorphodon animatable) {
        return DinosExpansion.modLoc("animations/dimorphodon.animation.json");
    }
}
