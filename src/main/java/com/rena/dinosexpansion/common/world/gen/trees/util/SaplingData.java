package com.rena.dinosexpansion.common.world.gen.trees.util;

import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class SaplingData {

    private final Set<BlockPos> trunkOffsetCoordinates;
    private final int searchRange;

    public SaplingData(Set<BlockPos> trunkOffsetCoordinates, int searchRange) {
        this.trunkOffsetCoordinates = trunkOffsetCoordinates;
        this.searchRange = searchRange;
    }


    public int getSearchRange() {
        return searchRange;
    }

    public Set<BlockPos> getTrunkOffsetCoordinates() {
        return trunkOffsetCoordinates;
    }

}
