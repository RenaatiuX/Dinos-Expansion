package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.common.entity.aquatic.Anomalocaris;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.gen.Heightmap;

public class ModEntityPlacement {

    public static void entityPlacement() {
        EntitySpawnPlacementRegistry.register(EntityInit.ANOMALOCARIS.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Anomalocaris::anomalocarisSpawn);
    }

}
