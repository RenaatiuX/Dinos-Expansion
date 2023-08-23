package com.rena.dinosexpansion.common.block;

import com.rena.dinosexpansion.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

import java.util.Random;

public class TypeOreBlock extends Block {

    public TypeOreBlock(Properties properties) {
        super(properties);
    }

    protected int getExperience(Random rand) {
        if (this == BlockInit.SAPPHIRE_ORE.get()) {
            return MathHelper.nextInt(rand, 3, 7);
        } else {
            return this == BlockInit.RUBY_ORE.get() ? MathHelper.nextInt(rand, 3, 7) : 0;
        }
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }
}
