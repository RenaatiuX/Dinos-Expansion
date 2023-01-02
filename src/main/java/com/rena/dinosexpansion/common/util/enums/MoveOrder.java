package com.rena.dinosexpansion.common.util.enums;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum MoveOrder {
    WANDER(translatable("wander")),
    SIT(translatable("sit")),
    FOLLOW(translatable("follow"));

    private final ITextComponent displayName;

    MoveOrder(ITextComponent displayName) {
        this.displayName = displayName;
    }

    public ITextComponent getDisplayName() {
        return displayName;
    }

    private static ITextComponent translatable(String name){
        return new TranslationTextComponent("order_type." + DinosExpansion.MOD_ID + "." + name);
    }

    public final MoveOrder next() {
        return MoveOrder.values()[(this.ordinal() + 1) % MoveOrder.values().length];
    }
}

