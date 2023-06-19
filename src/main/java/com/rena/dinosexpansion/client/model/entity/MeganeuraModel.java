package com.rena.dinosexpansion.client.model.entity;

import com.google.common.collect.Maps;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.flying.Meganeura;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

import java.util.Map;

public class MeganeuraModel extends AnimatedTickingGeoModel<Meganeura> {

    private static final Map<Dinosaur.Rarity, ResourceLocation> RARITY = Util.make(Maps.newEnumMap(Dinosaur.Rarity.class),
            (rarity) -> {
                rarity.put(Dinosaur.Rarity.COMMON, DinosExpansion.modLoc("textures/entity/meganeura/meganeura_common.png"));
                rarity.put(Dinosaur.Rarity.UNCOMMON, DinosExpansion.modLoc("textures/entity/meganeura/meganeura_uncommon.png"));
            });
    
    @Override
    public ResourceLocation getModelLocation(Meganeura object) {
        return DinosExpansion.modLoc("geo/meganeura.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Meganeura object) {
        return DinosExpansion.modLoc("textures/entity/meganeura/meganeura_common.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Meganeura animatable) {
        return DinosExpansion.modLoc("animations/meganeura.json");
    }
}
