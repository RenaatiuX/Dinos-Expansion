package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import com.rena.dinosexpansion.common.world.dimension.DinoChunkGenerator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum DinoShoreLayer implements ICastleTransformer {
    INSTANCE;
    private static final IntSet COLD_BIOMES = new IntOpenHashSet(new int[]{
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.FROZEN_OCEAN)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.ARCTIC)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.TUNDRA)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.SNOW_BEACH)),
            DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.FROZEN_RIVER))
    });

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {

        if (center == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.JUNGLE))) {
            if (DinoBiomeProvider.isOcean(north) || DinoBiomeProvider.isOcean(west) || DinoBiomeProvider.isOcean(south) || DinoBiomeProvider.isOcean(east)) {
                return DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.BEACH));
            }
        } else if (center != DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.MOUNTAIN))) {
            if (COLD_BIOMES.contains(center)) {
                if (!DinoBiomeProvider.isOcean(center) && (DinoBiomeProvider.isOcean(north) || DinoBiomeProvider.isOcean(west) || DinoBiomeProvider.isOcean(south) || DinoBiomeProvider.isOcean(east))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.SNOW_BEACH));
                }
            } else if (center != DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.BADLANDS))) {
                return DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.BEACH));
            } else if (!DinoBiomeProvider.isOcean(north) && !DinoBiomeProvider.isOcean(west) && !DinoBiomeProvider.isOcean(south) && !DinoBiomeProvider.isOcean(east) && (!this.isMesa(north) || !this.isMesa(west) || !this.isMesa(south) || !this.isMesa(east))) {
                return DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.LOW_DESERT));
            }
        } else if (!DinoBiomeProvider.isOcean(center) && (DinoBiomeProvider.isOcean(north) || DinoBiomeProvider.isOcean(west) || DinoBiomeProvider.isOcean(south) || DinoBiomeProvider.isOcean(east))) {
            return DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.STONE_SHORE));
        }
        return center;
    }

    private static boolean isJungleCompatible(int center) {
        return center == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.JUNGLE)) || center == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.REDWOOD_FOREST)) || DinoBiomeProvider.isOcean(center);
    }

    private boolean isMesa(int center) {
        return center == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.BADLANDS));
    }
}
