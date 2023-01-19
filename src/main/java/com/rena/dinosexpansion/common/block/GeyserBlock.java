package com.rena.dinosexpansion.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class GeyserBlock extends Block {
    private int ticks = 40;
    public GeyserBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (worldIn.isRemote) {
            return;
        }
        if (rand.nextInt(2) == 0) {
            for (int i = 1; i <= 10; i++) {
                BlockPos waterPos = new BlockPos(pos.getX(), pos.getY() + i, pos.getZ());
                worldIn.setBlockState(waterPos, Blocks.WATER.getDefaultState());
                worldIn.addParticle(ParticleTypes.SPLASH, pos.getX() + rand.nextFloat(), pos.getY() + i + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 0, 0.1, 0);
            }
        }
        ticks--;
        if (ticks == 0){
            for (int i = 1; i <= 10; i++) {
                BlockPos waterPos = new BlockPos(pos.getX(), pos.getY() + i, pos.getZ());
                worldIn.setBlockState(waterPos, Blocks.AIR.getDefaultState());
                worldIn.addParticle(ParticleTypes.CLOUD, pos.getX(), pos.getY() + i, pos.getZ(), 0, 0.1, 0);
            }
            ticks = 40;
        }
    }
}