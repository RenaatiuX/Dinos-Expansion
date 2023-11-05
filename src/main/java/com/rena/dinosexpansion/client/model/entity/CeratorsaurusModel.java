package com.rena.dinosexpansion.client.model.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.terrestrial.Ceratosaurus;
import com.rena.dinosexpansion.common.entity.terrestrial.Dryosaurus;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.List;
@OnlyIn(Dist.CLIENT)
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

    @Override
    public void setLivingAnimations(Ceratosaurus entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone neck = this.getAnimationProcessor().getBone("chests");
        neck.setHidden(!entity.hasChest());
    }
}
