package com.rena.dinosexpansion.client.model.entity;

import com.google.common.collect.Maps;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.aquatic.Anomalocaris;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

import java.util.Map;

public class AnomalocarisModel extends AnimatedTickingGeoModel<Anomalocaris> {
    public AnomalocarisModel() {
        super();
    }

    public static final Map<Dinosaur.Rarity, ResourceLocation> RARITY = Util.make(Maps.newEnumMap(Dinosaur.Rarity.class),
            (rarity) -> {
                rarity.put(Dinosaur.Rarity.COMMON, DinosExpansion.modLoc("textures/entity/anomalocaris/anomalocaris_common.png"));
                rarity.put(Dinosaur.Rarity.UNCOMMON, DinosExpansion.modLoc("textures/entity/anomalocaris/anomalocaris_uncommon.png"));
                rarity.put(Dinosaur.Rarity.RARE, DinosExpansion.modLoc("textures/entity/anomalocaris/anomalocaris_rare.png"));
                rarity.put(Dinosaur.Rarity.EPIC, DinosExpansion.modLoc("textures/entity/anomalocaris/anomalocaris_epic.png"));
                rarity.put(Dinosaur.Rarity.LEGENDARY, DinosExpansion.modLoc("textures/entity/anomalocaris/anomalocaris_legendary.png"));
            });

    @Override
    public ResourceLocation getModelLocation(Anomalocaris object) {
        return DinosExpansion.modLoc("geo/anomalocaris.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Anomalocaris object) {
        return RARITY.get(object.getRarity());
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Anomalocaris animatable) {
        return DinosExpansion.modLoc("animations/anomalocaris.json");
    }

}
