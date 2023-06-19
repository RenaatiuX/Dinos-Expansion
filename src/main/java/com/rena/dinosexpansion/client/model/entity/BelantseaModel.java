package com.rena.dinosexpansion.client.model.entity;

import com.google.common.collect.Maps;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.aquatic.fish.Belantsea;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

import java.util.Map;

public class BelantseaModel extends AnimatedTickingGeoModel<Belantsea> {

    public BelantseaModel() {
        super();
    }

    private static final Map<Belantsea.Rarity, ResourceLocation> RARITY = Util.make(Maps.newEnumMap(Belantsea.Rarity.class),
            (rarity) -> {
                rarity.put(Belantsea.Rarity.COMMON, DinosExpansion.modLoc("textures/entity/belantsea/belantsea_common.png"));
                rarity.put(Belantsea.Rarity.UNCOMMON, DinosExpansion.modLoc("textures/entity/belantsea/belantsea_uncommon.png"));
            });

    @Override
    public ResourceLocation getModelLocation(Belantsea object) {
        return DinosExpansion.modLoc("geo/belantsea.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Belantsea object) {
        return DinosExpansion.modLoc("textures/entity/belantsea/belantsea_common.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Belantsea animatable) {
        return DinosExpansion.modLoc("animations/belantsea.json");
    }
}
