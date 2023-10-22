package com.rena.dinosexpansion.client.model.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.terrestrial.Ceratosaurus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CeratorsaurusModel extends AnimatedGeoModel<Ceratosaurus> {
    @Override
    public ResourceLocation getModelLocation(Ceratosaurus object) {
        return DinosExpansion.modLoc("geo/ceratosaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Ceratosaurus object) {
        return DinosExpansion.modLoc("textures/entity/ceratosaurus/ceratosaurus_common.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Ceratosaurus animatable) {
        return DinosExpansion.modLoc("animations/ceratosaurus.json");
    }
}
