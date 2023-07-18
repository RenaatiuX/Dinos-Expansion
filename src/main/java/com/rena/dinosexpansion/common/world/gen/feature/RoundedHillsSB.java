package com.rena.dinosexpansion.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RoundedHillsSB extends SurfaceBuilder<SurfaceBuilderConfig> {

    protected final Function<Double, Double> continentalnessFunction;

    public RoundedHillsSB(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
        continentalnessFunction = interpolatePoints(new Point2D.Double(-1d, 50d), new Point2D.Double(0.3, 100d), new Point2D.Double(.4d, 150d), new Point2D.Double(1d, 150d));
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        int surfaceHeight = startHeight + (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int roundedHeight = startHeight + 3;


        double totalHeight = continentalnessFunction.apply(MathHelper.clamp(noise / 3d, -1, 1));

        for (int y = startHeight; y >= 0; y--) {
            BlockPos pos = new BlockPos(x, y, z);
            if (y <= seaLevel && y > totalHeight)
                chunkIn.setBlockState(pos, defaultFluid, false);
            else if (y <= totalHeight)
                chunkIn.setBlockState(pos, defaultBlock, false);
            else
                chunkIn.setBlockState(pos, Blocks.AIR.getDefaultState(), false);
        }
    }

    public static Function<Double, Double> interpolatePoints(Point2D... points) {
        LinearFunction[] functions = new LinearFunction[points.length - 1];
        for (int i = 0; i < points.length - 1; i++) {
            functions[i] = new LinearFunction(points[i], points[i + 1]);
        }
        return x -> {
            for (LinearFunction f : functions) {
                if (f.isInside(x))
                    return f.getInterpolationValue(x);
            }
            throw new IllegalArgumentException("x is outside of the Points range for x:" + x);
        };
    }

    public static class LinearFunction {

        private final Point2D start, end;
        private final double m, c;

        public LinearFunction(Point2D start, Point2D end) {
            this.start = start;
            this.end = end;
            m = (start.getY() - end.getY()) / (start.getX() - end.getX());
            c = start.getY() - m * start.getX();
        }

        public boolean isInside(double x) {
            return x >= start.getX() && x <= end.getX();
        }

        public double getInterpolationValue(double x) {
            return m * x + c;
        }
    }

    protected double combinedSin(double x) {
        return Math.sin(x) * 4 + Math.sin(8 * x) / 3;
    }
    //big and deep hills

    /*
     @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        int surfaceHeight = startHeight + (int) (noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int roundedHeight = startHeight + 3;


        double frequency = 0.01D;
        double amplitude = 10D, noiseAmp = 10D;

        double xOffset = combinedSin(x * frequency) * amplitude;
        double zOffset = combinedSin(z * frequency) * amplitude;

        double totalHeight = MathHelper.clamp(seaLevel + zOffset + xOffset + noise * noiseAmp, 20, chunkIn.getHeight());

        for (int y = startHeight; y >= 0; y--) {
            BlockPos pos = new BlockPos(x, y, z);
            if (y <= seaLevel && y > totalHeight)
                chunkIn.setBlockState(pos, defaultFluid, false);
            else if (y <= totalHeight)
                chunkIn.setBlockState(pos, defaultBlock, false);
            else
                chunkIn.setBlockState(pos, Blocks.AIR.getDefaultState(), false);
        }
    }
     */


}
