package com.rena.dinosexpansion.common.entity.util;

import com.rena.dinosexpansion.core.init.BlockInit;
import net.minecraft.block.Block;

public enum BoatType {
    CRATAEGUS("crataegus", BlockInit.CRATAEGUS_PLANKS.get());

    private final String name;
    private final Block block;
    BoatType(String name, Block block) {
        this.name = name;
        this.block = block;
    }

    public String getName() {
        return name;
    }

    public Block getBlock() {
        return block;
    }

    public static BoatType byId(int id) {
        BoatType[] aboatentity$type = values();
        if (id < 0 || id >= aboatentity$type.length) {
            id = 0;
        }

        return aboatentity$type[id];
    }

    public static BoatType getTypeFromString(String nameIn) {
        BoatType[] aboatentity$type = values();

        for(int i = 0; i < aboatentity$type.length; ++i) {
            if (aboatentity$type[i].getName().equals(nameIn)) {
                return aboatentity$type[i];
            }
        }
        return aboatentity$type[0];
    }
}
