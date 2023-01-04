package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum DinoOceanLayer implements IAreaTransformer0 {
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, int x, int y) {
        ImprovedNoiseGenerator improvednoisegenerator = random.getNoiseGenerator();
        double temperature = improvednoisegenerator.func_215456_a((double) x / 8.0D, (double) y / 8.0D, 0.0D, 0.0D, 0.0D);
        if (temperature > 0.4D) {
            return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getOrDefault(DinoBiomeProvider.WARM_OCEAN));
        } else if (temperature > 0.2D) {
            return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getOrDefault(DinoBiomeProvider.LUKEWARM_OCEAN));
        } else if (temperature < -0.4D) {
            return DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getOrDefault(DinoBiomeProvider.FROZEN_OCEAN));
        } else {
            return temperature < -0.2D ? DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getOrDefault(DinoBiomeProvider.COLD_OCEAN)) : DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                    .getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY
                            .getOrDefault(DinoBiomeProvider.OCEAN));
        }
    }
}
