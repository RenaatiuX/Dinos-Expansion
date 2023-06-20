package com.rena.dinosexpansion.client.model.entity;

import com.google.common.collect.Maps;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.aquatic.fish.Acanthodes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

import java.util.Map;

public class AcanthodesModel extends AnimatedTickingGeoModel<Acanthodes> {

    public AcanthodesModel() {
        super();
    }

    private static final Map<Acanthodes.Rarity, ResourceLocation> RARITY = Util.make(Maps.newEnumMap(Acanthodes.Rarity.class),
            (rarity) -> {
                rarity.put(Acanthodes.Rarity.COMMON, DinosExpansion.modLoc("textures/entity/acanthodes/acanthodes_common.png"));
                rarity.put(Acanthodes.Rarity.UNCOMMON, DinosExpansion.modLoc("textures/entity/acanthodes/acanthodes_uncommon.png"));
            });

    @Override
    public ResourceLocation getModelLocation(Acanthodes object) {
        return DinosExpansion.modLoc("geo/acanthodes.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Acanthodes object) {
        return DinosExpansion.modLoc("textures/entity/acanthodes/acanthodes_common.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Acanthodes animatable) {
        return DinosExpansion.modLoc("animations/acanthodes.json");
    }
}
