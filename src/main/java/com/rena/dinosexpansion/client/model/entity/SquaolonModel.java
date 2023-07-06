package com.rena.dinosexpansion.client.model.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.aquatic.Squalodon;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.raw.pojo.Bone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import java.util.List;

public class SquaolonModel extends AnimatedTickingGeoModel<Squalodon> {
    @Override
    public ResourceLocation getModelLocation(Squalodon object) {
        return DinosExpansion.modLoc("geo/squalodon.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Squalodon object) {
        if (object.getRarity() == Dinosaur.Rarity.COMMON)
            return DinosExpansion.modLoc("textures/entity/squalodon/squalodon_common.png");
        return DinosExpansion.modLoc("textures/entity/squalodon/squalodon_uncommon.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Squalodon animatable) {
        return DinosExpansion.modLoc("animations/squalodon.json");
    }
}
