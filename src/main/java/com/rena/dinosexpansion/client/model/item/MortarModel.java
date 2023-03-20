package com.rena.dinosexpansion.client.model.item;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.block.AnimatedBlockItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

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
