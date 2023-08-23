package com.rena.dinosexpansion.client.model.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.aquatic.Parapuzosia;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

public class ParapuzosiaModel extends AnimatedTickingGeoModel<Parapuzosia> {
    @Override
    public ResourceLocation getModelLocation(Parapuzosia object) {
        return DinosExpansion.modLoc("geo/parapuzosia.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Parapuzosia object) {
        return DinosExpansion.modLoc("textures/entity/parapuzosia.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Parapuzosia animatable) {
        return DinosExpansion.modLoc("animations/parapuzosia.json");
    }
}
