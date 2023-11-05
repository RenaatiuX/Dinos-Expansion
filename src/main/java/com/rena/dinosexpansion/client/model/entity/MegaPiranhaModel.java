package com.rena.dinosexpansion.client.model.entity;

import com.google.common.collect.Maps;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.aquatic.fish.MegaPiranha;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

import java.util.Map;
@OnlyIn(Dist.CLIENT)
public class MegaPiranhaModel extends AnimatedTickingGeoModel<MegaPiranha> {
    private static final Map<Dinosaur.Rarity, ResourceLocation> RARITY = Util.make(Maps.newEnumMap(Dinosaur.Rarity.class),
            (rarity) -> {
                rarity.put(Dinosaur.Rarity.COMMON, DinosExpansion.modLoc("textures/entity/piranha/megapiranha_common.png"));
                rarity.put(Dinosaur.Rarity.UNCOMMON, DinosExpansion.modLoc("textures/entity/piranha/megapiranha_uncommon.png"));
                rarity.put(Dinosaur.Rarity.RARE, DinosExpansion.modLoc("textures/entity/piranha/megapiranha_rare.png"));
                rarity.put(Dinosaur.Rarity.EPIC, DinosExpansion.modLoc("textures/entity/piranha/megapiranha_epic.png"));
                rarity.put(Dinosaur.Rarity.LEGENDARY, DinosExpansion.modLoc("textures/entity/piranha/megapiranha_legendary.png"));
            });
    @Override
    public ResourceLocation getModelLocation(MegaPiranha object) {
        return DinosExpansion.modLoc("geo/megapiranha.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MegaPiranha object) {
        return RARITY.get(object.getRarity());
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MegaPiranha animatable) {
        return DinosExpansion.modLoc("animations/megapiranha.json");
    }
}
