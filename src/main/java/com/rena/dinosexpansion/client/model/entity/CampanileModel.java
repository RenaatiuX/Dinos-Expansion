package com.rena.dinosexpansion.client.model.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.terrestrial.ambient.Campanile;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

import java.util.Locale;

public class CampanileModel extends AnimatedTickingGeoModel<Campanile> {
    @Override
    public ResourceLocation getModelLocation(Campanile object) {
        return DinosExpansion.modLoc("geo/campanile.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Campanile object) {
        String textureName = "campanile";
        textureName += "_" +  object.getRarity().name().toLowerCase(Locale.ROOT);
        if (!object.hasShell())
            textureName += "_no_shell";
        textureName += ".png";
        return campanile(textureName);
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Campanile animatable) {
        return DinosExpansion.modLoc("animations/campanile.animation.json");
    }

    public static ResourceLocation campanile(String textureName){
        return DinosExpansion.modLoc("textures/entity/campanile/" + textureName);
    }
}
