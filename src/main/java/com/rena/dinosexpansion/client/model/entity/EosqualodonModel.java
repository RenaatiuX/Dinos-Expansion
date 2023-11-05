package com.rena.dinosexpansion.client.model.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.aquatic.Eosqualodon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
@OnlyIn(Dist.CLIENT)
public class EosqualodonModel extends AnimatedTickingGeoModel<Eosqualodon> {
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
        return DinosExpansion.modLoc("animations/eosqualodon.json");
    }
}
