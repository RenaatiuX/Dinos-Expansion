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
        int deepOceanInputs = area1.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        int oceanInputs = area2.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        if (!DinoBiomeProvider.isOcean(deepOceanInputs)) {
            return deepOceanInputs;
        } else {
            for(int biomesInputs1 = -8; biomesInputs1 <= 8; biomesInputs1 += 4) {
                for(int oceanInputs1 = -8; oceanInputs1 <= 8; oceanInputs1 += 4) {
                    int biomeInputs = area1.getValue(this.getOffsetX(x + biomesInputs1), this.getOffsetZ(z + oceanInputs1));
                    if (!DinoBiomeProvider.isOcean(biomeInputs)) {
                        if (oceanInputs == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                        .getOrDefault(DinoBiomeProvider.WARM_OCEAN))) {
                            return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                            .getOrDefault(DinoBiomeProvider.LUKEWARM_OCEAN));
                        }

                        if (oceanInputs == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                        .getOrDefault(DinoBiomeProvider.FROZEN_OCEAN))) {
                            return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                            .getOrDefault(DinoBiomeProvider.COLD_OCEAN));
                        }
                    }
                }
            }

            if (deepOceanInputs == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getOrDefault(DinoBiomeProvider.DEEP_OCEAN))) {
                if (oceanInputs == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.LUKEWARM_OCEAN))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.DEEP_LUKEWARM_OCEAN));
                }

                if (oceanInputs == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.OCEAN))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.DEEP_OCEAN));
                }

                if (oceanInputs == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.COLD_OCEAN))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.DEEP_COLD_OCEAN));
                }

                if (oceanInputs == DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                        .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                .getOrDefault(DinoBiomeProvider.FROZEN_OCEAN))) {
                    return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                                    .getOrDefault(DinoBiomeProvider.DEEP_FROZEN_OCEAN));
                }
            }
            return oceanInputs;
        }
    }
}
