package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum DinoIslandLayer implements IAreaTransformer0 {
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, int x, int z) {
        if (x == 0 && z == 0) {
            return DinoBiomeProvider.getId(DinoBiomeProvider.GRASSLAND);
        } else {
            return random.random(10) == 0 ? DinoBiomeProvider.getId(DinoBiomeProvider.GRASSLAND) : DinoBiomeProvider.getId(DinoBiomeProvider.OCEAN);
        }
    }
}
