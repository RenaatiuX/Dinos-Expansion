package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum DinoMixOceansLayer implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, IArea area1, IArea area2, int x, int z) {
        int biome1 = area1.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        int biome2 = area2.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        if (!DinoBiomeProvider.isOcean(biome1)) {
            return biome1;
        } else {
            for(int i1 = -8; i1 <= 8; i1 += 4) {
                for(int j1 = -8; j1 <= 8; j1 += 4) {
                    int k1 = area1.getValue(this.getOffsetX(x + i1), this.getOffsetZ(z + j1));
                    if (!DinoBiomeProvider.isOcean(k1)) {
                        if (biome2 == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.WARM_OCEAN))) {
                            return DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.LUKEWARM_OCEAN));
                        }

                        if (biome2 == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.FROZEN_OCEAN))) {
                            return DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.COLD_OCEAN));
                        }
                    }
                }
            }

            if (biome1 == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.DEEP_OCEAN))) {
                if (biome2 == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.LUKEWARM_OCEAN))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.DEEP_LUKEWARM_OCEAN));
                }

                if (biome2 == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.OCEAN))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.DEEP_OCEAN));
                }

                if (biome2 == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.COLD_OCEAN))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.DEEP_COLD_OCEAN));
                }

                if (biome2 == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.FROZEN_OCEAN))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.DEEP_FROZEN_OCEAN));
                }
            }

            return biome2;
        }
    }
}
