package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum DinoRiverMixLayer implements IAreaTransformer2, IDimOffset0Transformer {

    INSTANCE;

    @Override
    public int apply(INoiseRandom random, IArea area1, IArea area2, int x, int y) {
        int biomesInputs = area1.getValue(this.getOffsetX(x), this.getOffsetZ(y));
        int riverInputs = area2.getValue(this.getOffsetX(x), this.getOffsetZ(y));

        if (DinoBiomeProvider.isOcean(biomesInputs)) {
            return biomesInputs;
        } else if (riverInputs == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.RIVER))) {
            if (biomesInputs == DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.TUNDRA))) {
                return DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getId(DinoBiomeProvider.LAYERS_BIOME_REGISTRY.getOrDefault(DinoBiomeProvider.FROZEN_RIVER));
            } else {
                return riverInputs;
            }
        } else {
            return biomesInputs;
        }
    }
}
