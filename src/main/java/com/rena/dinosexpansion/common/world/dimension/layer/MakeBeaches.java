package com.rena.dinosexpansion.common.world.dimension.layer;

import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;
import net.minecraftforge.common.BiomeManager;

import static com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider.*;

public enum MakeBeaches implements ICastleTransformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (DinoBiomeProvider.isLand(center)){
            if (isOcean(north) || isOcean(west) || isOcean(south) || isOcean(east)) {
                if (((isBeach(north) || isBeach(south) || isBeach(west) || isBeach(east)) && context.random(2) == 0) || context.random(3) == 0) {
                    //check if we have an icy biome  then applying the snow beach
                    for (int id : DinoBiomeLayerMixer.NORMAL_LANDBIOMES.get(BiomeManager.BiomeType.ICY).stream().mapToInt(DinoBiomeProvider::getId).toArray()) {
                        if (id == center)
                            return getId(SNOW_BEACH);
                    }
                    //check if we have an cool biome then applying the snow beach
                    for (int id : DinoBiomeLayerMixer.NORMAL_LANDBIOMES.get(BiomeManager.BiomeType.COOL).stream().mapToInt(DinoBiomeProvider::getId).toArray()) {
                        if (id == center)
                            return getId(SNOW_BEACH);
                    }
                    return getId(BEACH);
                }
            }
        }
        return center;
    }
}
