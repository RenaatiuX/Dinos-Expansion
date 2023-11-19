package com.rena.dinosexpansion.common.block;

import com.rena.dinosexpansion.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

import java.util.Random;
import java.util.function.Function;

public class TypeOreBlock extends Block {



    protected final Function<Random, Integer> xpFunction;

    public TypeOreBlock(Properties properties) {
        this(null, properties);
    }

    public TypeOreBlock(Function<Random, Integer> xpFunction, Properties properties) {
        super(properties);
        this.xpFunction = xpFunction;
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader world, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 && xpFunction != null ? xpFunction.apply(RANDOM) : 0;
    }
}
