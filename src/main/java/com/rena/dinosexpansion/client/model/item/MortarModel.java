package com.rena.dinosexpansion.client.model.item;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.item.util.AnimatedBlockItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedGeoModel;
@OnlyIn(Dist.CLIENT)
public class MortarModel extends AnimatedGeoModel<AnimatedBlockItem> {
    @Override
    public ResourceLocation getModelLocation(AnimatedBlockItem object) {
        return DinosExpansion.modLoc("geo/mortar.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AnimatedBlockItem object) {
        return DinosExpansion.modLoc("textures/block/mortar.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AnimatedBlockItem animatable) {
        return DinosExpansion.modLoc("animations/mortar.animation.json");
    }
}
