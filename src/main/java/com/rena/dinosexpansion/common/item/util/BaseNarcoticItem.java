package com.rena.dinosexpansion.common.item.util;

import net.minecraft.item.Item;

public class BaseNarcoticItem extends Item implements INarcotic{
    protected final int narcoticValue;
    public BaseNarcoticItem(Properties properties, int narcoticValue) {
        super(properties);
        this.narcoticValue = narcoticValue;
    }

    @Override
    public int getNarcoticValue() {
        return this.narcoticValue;
    }
}
