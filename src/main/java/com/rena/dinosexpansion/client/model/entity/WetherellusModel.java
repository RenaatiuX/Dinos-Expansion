package com.rena.dinosexpansion.client.model.entity;

import com.google.common.collect.Maps;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.aquatic.fish.Acanthodes;
import com.rena.dinosexpansion.common.entity.aquatic.fish.Wetherellus;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

import java.util.Map;
@OnlyIn(Dist.CLIENT)
public class WetherellusModel extends AnimatedTickingGeoModel<Wetherellus> {

    public WetherellusModel() {
        super();
    }

    private static final Map<Acanthodes.Rarity, ResourceLocation> RARITY = Util.make(Maps.newEnumMap(Acanthodes.Rarity.class),
            (rarity) -> {
                rarity.put(Acanthodes.Rarity.COMMON, DinosExpansion.modLoc("textures/entity/wetherellus/wetherellus_common.png"));
                rarity.put(Acanthodes.Rarity.UNCOMMON, DinosExpansion.modLoc("textures/entity/wetherellus/wetherellus_uncommon.png"));
            });

    @Override
    public ResourceLocation getModelLocation(Wetherellus object) {
        return DinosExpansion.modLoc("geo/wetherellus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Wetherellus object) {
        return RARITY.get(object.getRarity());
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Wetherellus animatable) {
        return DinosExpansion.modLoc("animations/wetherellus.json");
    }
}
