package com.rena.dinosexpansion.common.world.dimension.layer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;
import net.minecraftforge.common.BiomeManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public enum DinoBiomeLayerMixer implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    public static final Map<BiomeManager.BiomeType, List<ResourceLocation>> NORMAL_LANDBIOMES = Util.make(Maps.newHashMap(), map -> {
        map.put(BiomeManager.BiomeType.ICY, Lists.newArrayList(DinoBiomeProvider.ARCTIC));
        map.put(BiomeManager.BiomeType.WARM, Lists.newArrayList(DinoBiomeProvider.BADLANDS, DinoBiomeProvider.GRASSLAND, DinoBiomeProvider.JUNGLE, DinoBiomeProvider.MOUNTAIN, DinoBiomeProvider.REDWOOD_FOREST, DinoBiomeProvider.STONE_SHORE, DinoBiomeProvider.SWAMP));
        map.put(BiomeManager.BiomeType.DESERT, Lists.newArrayList(DinoBiomeProvider.DUNES, DinoBiomeProvider.HIGH_DESERT, DinoBiomeProvider.LOW_DESERT, DinoBiomeProvider.CANYON));
         map.put(BiomeManager.BiomeType.COOL, Lists.newArrayList(DinoBiomeProvider.TUNDRA));
    });

    @Override
    public int apply(INoiseRandom random, IArea area, IArea temperature, int x, int z) {
        int areaValue = area.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        int temperatureValue = temperature.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        if (areaValue == 1 || areaValue == 2) {
            BiomeManager.BiomeType type = BiomeManager.BiomeType.values()[temperatureValue - 100];
            List<ResourceLocation> possibleBiomes = NORMAL_LANDBIOMES.get(type);
            return DinoBiomeProvider.getId(possibleBiomes.get(random.random(possibleBiomes.size())));
        }
        return areaValue;
    }
}
