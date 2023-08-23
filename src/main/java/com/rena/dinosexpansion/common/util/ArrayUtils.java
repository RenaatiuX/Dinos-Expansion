package com.rena.dinosexpansion.common.util;

import java.util.Random;

public class ArrayUtils {

    /**
     * chooses a random element from the give array
     * @param array the array has to have at least one element otherwise a exception will be thrown
     * @return the random element
     */
    public static <T> T chooseRandom(Random rand, T[] array){
        if (array.length == 0)
            throw new IllegalArgumentException("array has to have at least one Element");
        if (array.length == 1)
            return array[0];
        return array[rand.nextInt(array.length)];
    }

    public static <T> T chooseRandom(T[] array){
        return chooseRandom(new Random(), array);
    }

    /**
     * chooses a random element from the give array
     * @param array the array has to have at least one element otherwise a exception will be thrown
     * @return the random element
     */
    public static int chooseRandom(Random rand, int[] array){
        if (array.length == 0)
            throw new IllegalArgumentException("array has to have at least one Element");
        if (array.length == 1)
            return array[0];
        return array[rand.nextInt(array.length)];
    }

    public static int chooseRandom(int[] array){
        return chooseRandom(new Random(), array);
    }

    /**
     * chooses a random element from the give array
     * @param array the array has to have at least one element otherwise a exception will be thrown
     * @return the random element
     */
    public static double chooseRandom(Random rand, double[] array){
        if (array.length == 0)
            throw new IllegalArgumentException("array has to have at least one Element");
        if (array.length == 1)
            return array[0];
        return array[rand.nextInt(array.length)];
    }

    public static double chooseRandom(double[] array){
        return chooseRandom(new Random(), array);
    }

    /**
     * chooses a random element from the give array
     * @param array the array has to have at least one element otherwise a exception will be thrown
     * @return the random element
     */
    public static float chooseRandom(Random rand, float[] array){
        if (array.length == 0)
            throw new IllegalArgumentException("array has to have at least one Element");
        if (array.length == 1)
            return array[0];
        return array[rand.nextInt(array.length)];
    }

    public static float chooseRandom(float[] array){
        return chooseRandom(new Random(), array);
    }

    /**
     * chooses a random element from the give array
     * @param array the array has to have at least one element otherwise a exception will be thrown
     * @return the random element
     */
    public static long chooseRandom(Random rand, long[] array){
        if (array.length == 0)
            throw new IllegalArgumentException("array has to have at least one Element");
        if (array.length == 1)
            return array[0];
        return array[rand.nextInt(array.length)];
    }

    public static long chooseRandom(long[] array){
        return chooseRandom(new Random(), array);
    }

    /**
     * chooses a random element from the give array
     * @param array the array has to have at least one element otherwise a exception will be thrown
     * @return the random element
     */
    public static char chooseRandom(Random rand, char[] array){
        if (array.length == 0)
            throw new IllegalArgumentException("array has to have at least one Element");
        if (array.length == 1)
            return array[0];
        return array[rand.nextInt(array.length)];
    }

    public static char chooseRandom(char[] array){
        return chooseRandom(new Random(), array);
    }

    /**
     * chooses a random element from the give array
     * @param array the array has to have at least one element otherwise a exception will be thrown
     * @return the random element
     */
    public static boolean chooseRandom(Random rand, boolean[] array){
        if (array.length == 0)
            throw new IllegalArgumentException("array has to have at least one Element");
        if (array.length == 1)
            return array[0];
        return array[rand.nextInt(array.length)];
    }

    public static boolean chooseRandom(boolean[] array){
        return chooseRandom(new Random(), array);
    }

    /**
     * chooses a random element from the give array
     * @param array the array has to have at least one element otherwise a exception will be thrown
     * @return the random element
     */
    public static byte chooseRandom(Random rand, byte[] array){
        if (array.length == 0)
            throw new IllegalArgumentException("array has to have at least one Element");
        if (array.length == 1)
            return array[0];
        return array[rand.nextInt(array.length)];
    }

    public static byte chooseRandom(byte[] array){
        return chooseRandom(new Random(), array);
    }
}
