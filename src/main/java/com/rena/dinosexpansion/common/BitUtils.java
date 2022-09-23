package com.rena.dinosexpansion.common;

public class BitUtils {

    public static final int getBit(int position, int number){
            return (number >> position) & 1;
    }

    public static final int setBit(int position, int number, boolean value){
        if (value)
            number |= 1 << position;
        else {
            number &= ~(1 << position);
        }
        return number;
    }
}
