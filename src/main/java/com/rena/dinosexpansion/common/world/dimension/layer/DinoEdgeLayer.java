package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public class DinoEdgeLayer {
    public enum CoolWarm implements ICastleTransformer {
        INSTANCE;

        public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
            return center != DinoBiomeProvider.getId(DinoBiomeProvider.GRASSLAND) || north != DinoBiomeProvider.getId(DinoBiomeProvider.MOUNTAIN) && west != DinoBiomeProvider.getId(DinoBiomeProvider.MOUNTAIN) && east != DinoBiomeProvider.getId(DinoBiomeProvider.MOUNTAIN) && south != DinoBiomeProvider.getId(DinoBiomeProvider.MOUNTAIN) && north != DinoBiomeProvider.getId(DinoBiomeProvider.REDWOOD_FOREST) && west != DinoBiomeProvider.getId(DinoBiomeProvider.REDWOOD_FOREST) && east != DinoBiomeProvider.getId(DinoBiomeProvider.REDWOOD_FOREST) && south != DinoBiomeProvider.getId(DinoBiomeProvider.REDWOOD_FOREST) ? center : DinoBiomeProvider.getId(DinoBiomeProvider.LOW_DESERT);
        }
    }

    public enum HeatIce implements ICastleTransformer {
        INSTANCE;

        public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
            return center != DinoBiomeProvider.getId(DinoBiomeProvider.REDWOOD_FOREST) || north != DinoBiomeProvider.getId(DinoBiomeProvider.GRASSLAND) && west != DinoBiomeProvider.getId(DinoBiomeProvider.GRASSLAND) && east != DinoBiomeProvider.getId(DinoBiomeProvider.GRASSLAND) && south != DinoBiomeProvider.getId(DinoBiomeProvider.GRASSLAND) && north != DinoBiomeProvider.getId(DinoBiomeProvider.LOW_DESERT) && west != DinoBiomeProvider.getId(DinoBiomeProvider.LOW_DESERT) && east != DinoBiomeProvider.getId(DinoBiomeProvider.LOW_DESERT) && south != DinoBiomeProvider.getId(DinoBiomeProvider.LOW_DESERT) ? center : DinoBiomeProvider.getId(DinoBiomeProvider.MOUNTAIN);
        }
    }
}
