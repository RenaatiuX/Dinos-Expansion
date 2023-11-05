package com.rena.dinosexpansion.client.model.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.semiaquatic.Astorgosuchus;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
@OnlyIn(Dist.CLIENT)
public class AstorgosuchusModel extends AnimatedTickingGeoModel<Astorgosuchus> {
    @Override
    public ResourceLocation getModelLocation(Astorgosuchus object) {
        return DinosExpansion.modLoc("geo/astorgosuchus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Astorgosuchus object) {
        return DinosExpansion.modLoc("textures/entity/astorgosuchus.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Astorgosuchus animatable) {
        return DinosExpansion.modLoc("animations/astorgosuchus.animation.json");
    }
}
