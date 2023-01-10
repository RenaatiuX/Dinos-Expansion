package com.rena.dinosexpansion.common.block;

import com.rena.dinosexpansion.common.world.gen.trees.util.TreeSpawner;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class BaseSaplingBlock extends SaplingBlock {
    private final TreeSpawner treeSpawner;
    public BaseSaplingBlock(TreeSpawner treeSpawner, Properties properties) {
        super(null, properties);
        this.treeSpawner = treeSpawner;
    }

    @Override
    public void placeTree(ServerWorld world, BlockPos pos, BlockState state, Random rand) {
        if (state.get(STAGE) == 0) {
            world.setBlockState(pos, state.cycleValue(STAGE), 4);
        } else {
            this.treeSpawner.spawn(world, world.getChunkProvider().getChunkGenerator(), pos, state, rand);
        }
    }
}
