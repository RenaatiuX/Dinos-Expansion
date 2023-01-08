package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum DinoRiverLayer implements ICastleTransformer {

    INSTANCE;
    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        int i = riverFilter(center);
        return i == riverFilter(east) && i == riverFilter(north) && i == riverFilter(west) && i == riverFilter(south) ? -1 :
                DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.RIVER));
    }

    private static int riverFilter(int i) {
        return i >= 2 ? 2 + (i & 1) : i;
    }

}
