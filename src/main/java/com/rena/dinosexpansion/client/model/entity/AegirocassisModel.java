package com.rena.dinosexpansion.client.model.entity;

import com.google.common.collect.Maps;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.aquatic.Aegirocassis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

import java.util.Map;

public class AegirocassisModel extends AnimatedTickingGeoModel<Aegirocassis> {
    private static final Map<Dinosaur.Rarity, ResourceLocation> RARITY = Util.make(Maps.newEnumMap(Dinosaur.Rarity.class),
            (rarity) -> {
                rarity.put(Dinosaur.Rarity.COMMON, DinosExpansion.modLoc("textures/entity/aegirocassis/aegirocassis_common.png"));
                rarity.put(Dinosaur.Rarity.UNCOMMON, DinosExpansion.modLoc("textures/entity/aegirocassis/aegirocassis_uncommon.png"));
            });
    @Override
    public ResourceLocation getModelLocation(Aegirocassis object) {
        return DinosExpansion.modLoc("geo/aegirocassis.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Aegirocassis object) {
        return RARITY.get(object.getRarity());
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Aegirocassis animatable) {
        return DinosExpansion.modLoc("animations/aegirocassis.json");
    }
}
