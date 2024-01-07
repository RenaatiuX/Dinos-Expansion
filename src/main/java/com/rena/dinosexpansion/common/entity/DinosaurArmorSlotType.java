package com.rena.dinosexpansion.common.entity;

import java.util.UUID;

public enum DinosaurArmorSlotType {

    SADDLE(UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6C")),

    CHEST(UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6D")),
    HEAD(UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6E")),
    CHESTPLATE(UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6F")),
    LEG(UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B70")),
    FEET(UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B71"));

    private final UUID modifierUUID;

    DinosaurArmorSlotType(UUID modifierUUID) {
        this.modifierUUID = modifierUUID;
    }

    public UUID getModifierUUID() {
        return modifierUUID;
    }
}
