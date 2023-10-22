package com.rena.dinosexpansion.common.util;

import com.mojang.datafixers.types.Func;
import net.minecraft.nbt.CompoundNBT;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class NbtUtils {

    /**
     * this will  get the value from the nbt and set it to something but only if that value exists to avoid setting values to 0
     * @param nbt the nbt we want it to read from
     * @param name the name of the value
     * @param valueGetter the value getter normally a lambda like <i>CompoundNBT::getInt</i>
     * @param valueSetter this is the setter which will be given the value from the getter and then should set some value
     * @param <T> the type of the value
     */
    public static<T> void setIfExists(CompoundNBT nbt, String name, BiFunction<CompoundNBT, String, T> valueGetter, Consumer<T> valueSetter){
        if (nbt.contains(name)){
            T value = valueGetter.apply(nbt, name);
            valueSetter.accept(value);
        }
    }
}
