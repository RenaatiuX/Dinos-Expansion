package com.rena.dinosexpansion.common.world.dimension;

import net.minecraft.util.ResourceLocation;

public class DinoBiomeManager {


    public static class PlainLayerRule implements BiomeLayerRule{

        @Override
        public boolean canBeNearBiome(ResourceLocation biome) {
            return true;
        }
    }

    public interface BiomeLayerRule{

        /**
         * use DinoBiomeProvider methods in order to get ids and stuff
         * @param biome
         * @return
         */
        public boolean canBeNearBiome(ResourceLocation biome);

        default boolean canBeSourounded(ResourceLocation north, ResourceLocation northEast, ResourceLocation east, ResourceLocation eastSouth, ResourceLocation south, ResourceLocation southWest, ResourceLocation west, ResourceLocation westNorth){
            return canBeNearBiome(north) && canBeNearBiome(east) && canBeNearBiome(south) && canBeNearBiome(west);
        }
    }
}
