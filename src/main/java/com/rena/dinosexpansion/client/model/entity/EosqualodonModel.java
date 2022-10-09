package com.rena.dinosexpansion.client.model.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.aquatic.Eosqualodon;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EosqualodonModel extends AnimatedGeoModel<Eosqualodon> {
    @Override
    public ResourceLocation getModelLocation(Eosqualodon object) {
        return DinosExpansion.modLoc("geo/eosqualodon.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Eosqualodon object) {
        return DinosExpansion.modLoc("textures/entity/eosqualodon.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Eosqualodon animatable) {
        return DinosExpansion.modLoc("animations/eosqualodon.animation.json");
    }
}
