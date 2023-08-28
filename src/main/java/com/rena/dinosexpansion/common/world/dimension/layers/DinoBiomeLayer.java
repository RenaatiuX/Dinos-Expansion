package com.rena.dinosexpansion.common.world.dimension.layers;

import com.rena.dinosexpansion.common.biome.BiomeBase;
import com.rena.dinosexpansion.common.world.dimension.DinoBiomeProvider;
import com.rena.dinosexpansion.common.world.dimension.DinoLayerUtil;
import com.rena.dinosexpansion.core.init.BiomeInit;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;
import net.minecraftforge.common.BiomeManager;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DinoBiomeLayer implements IAreaTransformer1, IDimOffset0Transformer {

    protected List<BiomeBase>[] biomes = new LinkedList[BiomeBase.BiomeType.values().length];

    public DinoBiomeLayer() {
        for (int i = 0; i < biomes.length; i++) {
            biomes[i] = new LinkedList<>();
        }
        for (BiomeBase base : BiomeBase.BIOMES) {
            for (BiomeBase.BiomeType type : base.getBiomeType())
                biomes[type.ordinal()].add(base);
        }
    }

    @Override
    public int apply(IExtendedNoiseRandom<?> context, IArea area, int x, int z) {
        int value = area.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        double noise = DinoLayerUtil.getTemperatureNoise(x, z);
        BiomeBase.BiomeType type = BiomeBase.BiomeType.getTypeWithNoise(noise);
        if (value == 0 && type != BiomeBase.BiomeType.HOT){
            //make ocean
            BiomeBase[] result = BiomeBase.SHALLOW_OCEANS.stream().filter(b -> DinoLayerUtil.contains(b.getBiomeType(), type) && !b.isRiver()).collect(Collectors.toList()).toArray(new BiomeBase[0]);
            if (result.length > 0)
                return DinoLayerUtil.getBiomeId(DinoLayerUtil.weightedRandom(context, result));
            else
                return  DinoLayerUtil.getBiomeId(Biomes.OCEAN);
        }else if (value == 1 || type == BiomeBase.BiomeType.HOT){
            //make land
            BiomeBase[] result = BiomeBase.BIOMES.stream().filter(b -> DinoLayerUtil.contains(b.getBiomeType(), type) && !b.isRiver()).collect(Collectors.toList()).toArray(new BiomeBase[0]);
            if (result.length > 0)
                return DinoLayerUtil.getBiomeId(DinoLayerUtil.weightedRandom(context, result));
            else
                return  DinoLayerUtil.getBiomeId(Biomes.PLAINS);
        }
        return value;
    }
}
