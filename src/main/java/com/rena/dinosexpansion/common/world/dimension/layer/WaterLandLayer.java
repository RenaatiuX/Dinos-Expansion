package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum WaterLandLayer implements IAreaTransformer0 {
    INSTANCE;

    /**
     * this will randomly add 0 or 1 to the area in order to determine whether the are should be land or ocean
     * 1 = land
     * 0 = ocean
     */
    @Override
    public int apply(INoiseRandom random, int x, int z) {
        //this ensures (0,0) is not water(spawn stuff)
        if (x == 0 && z == 0)
            return 1;
        return random.random(10) < 4 ? 1 : 0;
    }
}
