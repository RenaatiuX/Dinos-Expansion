package com.rena.dinosexpansion.common.world.dimension.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum DinoRiverGrow implements ICastleTransformer {
    INSTANCE;

    public static final int chanceTurn = 10;
    public static final int CHANCE_EXTEND_TWO_INTO_ONE = 5;

    // 7 = start
    //8 = north
    // 9 = west
    //10 = south
    // 11 = east
    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        //river sources start
        if (!isOcean(center)) {
            //do nothing if we have already a river here
            if (center > 7)
                return center;
            //basicaly removing sources when they have already been grown
            if (isRiverSource(center)) {
                if (north > 7)
                    return 8;
                if (west > 7)
                    return 9;
                if (south > 7)
                    return 10;
                if (east > 7)
                    return 11;
            }
            //this is that start at sources
            if (isRiverSource(north) && context.random(4) == 0)
                return 10;
            else if (isRiverSource(west) && context.random(3) == 0)
                return 11;
            else if (isRiverSource(south) && context.random(2) == 0)
                return 8;
            else if (isRiverSource(east))
                return 9;

            int riverCount = 0;
            if (north >= 7)
                riverCount++;
            if (south >= 7)
                riverCount++;
            if (east >= 7)
                riverCount++;
            if (west >= 7)
                riverCount++;
            if (riverCount == 4)
                return 10;
            //checking for the missing side and reutrning it so it can add with a chance of 1/5
            if (riverCount == 3) {
                if (north < 7)
                    return context.random(CHANCE_EXTEND_TWO_INTO_ONE) == 0 ? 8 : 9;
                if (west < 7)
                    return context.random(CHANCE_EXTEND_TWO_INTO_ONE) == 0 ? 9 : 10;
                if (south < 7)
                    return context.random(CHANCE_EXTEND_TWO_INTO_ONE) == 0 ? 10 : 11;
                if (east < 7)
                    return context.random(CHANCE_EXTEND_TWO_INTO_ONE) == 0 ? 11 : 8;
            }
            if (riverCount == 2) {
                if (north < 7 && east < 7) {
                    return context.random(CHANCE_EXTEND_TWO_INTO_ONE) == 0 ? context.random(2) == 0 ? 8 : 11 : 9;
                }

                if (south < 7 && east < 7) {
                    return context.random(CHANCE_EXTEND_TWO_INTO_ONE) == 0 ? context.random(2) == 0 ? 10 : 11 : 9;
                }
                if (south < 7 && west < 7) {
                    return context.random(CHANCE_EXTEND_TWO_INTO_ONE) == 0 ? context.random(2) == 0 ? 10 : 9 : 11;
                }
                if (north < 7 && west < 7) {
                    return context.random(CHANCE_EXTEND_TWO_INTO_ONE) == 0 ? context.random(2) == 0 ? 8 : 9 : 11;
                }
                if (north < 7 && south < 7) {
                    return context.random(CHANCE_EXTEND_TWO_INTO_ONE) == 0 ? context.random(2) == 0 ? 8 : 10 : 9;
                }
                if (west < 7 && east < 7) {
                    return context.random(CHANCE_EXTEND_TWO_INTO_ONE) == 0 ? context.random(2) == 0 ? 9 : 10 : 11;
                }
            }
            //adding a tree from them with random turns
            if (north == 10) {
                if (context.random(chanceTurn) == 0)
                    return 8 + context.random(4);
                return 10;

            }
            if (west == 11) {
                if (context.random(chanceTurn) == 0)
                    return 8 + context.random(4);
                return 11;
            }
            if (south == 8) {
                if (context.random(chanceTurn) == 0)
                    return 8 + context.random(4);
                return 8;
            }
            if (east == 9) {
                if (context.random(chanceTurn) == 0)
                    return 8 + context.random(4);
                return 9;
            }
        }


        return center;
    }


    private static boolean isRiverSource(int position) {
        return position == 7;
    }

    private static boolean isOcean(int position) {
        return position == 0;
    }
}
