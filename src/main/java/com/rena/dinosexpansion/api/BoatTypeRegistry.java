package com.rena.dinosexpansion.api;

import com.google.common.collect.Lists;
import com.rena.dinosexpansion.common.entity.misc.CustomBoatEntity;
import com.rena.dinosexpansion.common.entity.util.BoatType;

import java.util.List;

public class BoatTypeRegistry {

    public static final List<CustomBoatEntity.BoatTypeMaterial> BOAT_TYPES = Lists.newArrayList(BoatType.values());


    public static int register(CustomBoatEntity.BoatTypeMaterial material){
        if (!BOAT_TYPES.contains(material))
            BOAT_TYPES.add(material);
        else
            throw new IllegalArgumentException("material: " + material.toString() + " is already registered");
        return BOAT_TYPES.indexOf(material);
    }

    public static boolean isRegistered(CustomBoatEntity.BoatTypeMaterial material){
        return BOAT_TYPES.contains(material);
    }

    /**
     * when u want to remove existing ones do that after the RegistryEvents
     * never use this method during the game, this will break the boat code
     * @return whether the removal was successful
     */
    public static boolean remove(CustomBoatEntity.BoatTypeMaterial material){
        return BOAT_TYPES.remove(material);
    }
}
