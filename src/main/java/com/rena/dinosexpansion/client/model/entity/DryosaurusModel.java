package com.rena.dinosexpansion.client.model.entity;

import com.google.common.collect.Maps;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.terrestrial.Dryosaurus;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class DryosaurusModel extends AnimatedTickingGeoModel<Dryosaurus> {

    private static final Map<Dinosaur.Rarity, ResourceLocation> RARITY = Util.make(Maps.newEnumMap(Dinosaur.Rarity.class),
            (rarity) -> {
                rarity.put(Dinosaur.Rarity.COMMON, DinosExpansion.modLoc("textures/entity/dryosaurus/dryosaurus_common.png"));
                //rarity.put(Dinosaur.Rarity.UNCOMMON, DinosExpansion.modLoc("textures/entity/dimorphodon/dimorphodon_uncommon.png"));
                //rarity.put(Dinosaur.Rarity.RARE, DinosExpansion.modLoc("textures/entity/dimorphodon/dimorphodon_rare.png"));
                //rarity.put(Dinosaur.Rarity.EPIC, DinosExpansion.modLoc("textures/entity/dimorphodon/dimorphodon_epic.png"));
                rarity.put(Dinosaur.Rarity.LEGENDARY, DinosExpansion.modLoc("textures/entity/dryosaurus/dryosaurus_legendary.png"));
            });
    @Override
    public ResourceLocation getModelLocation(Dryosaurus object) {
        return DinosExpansion.modLoc("geo/dryosaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Dryosaurus object) {
        return RARITY.get(object.getRarity());
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Dryosaurus animatable) {
        return DinosExpansion.modLoc("animations/dryosaurus.animation.json");
    }

    @Override
    public void setLivingAnimations(Dryosaurus entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        if (customPredicate == null) return;

        List<EntityModelData> extraDataOfType = customPredicate.getExtraDataOfType(EntityModelData.class);
        IBone neck = this.getAnimationProcessor().getBone("neck");

        if (entity.isChild()) {
            neck.setScaleX(1.45F);
            neck.setScaleY(1.45F);
            neck.setScaleZ(1.45F);
        }

        if (entity.isEating()) {
            neck.setRotationX(extraDataOfType.get(0).headPitch * 0.017453292F);
            neck.setRotationY(extraDataOfType.get(0).netHeadYaw * 0.017453292F);
        }
    }
}
