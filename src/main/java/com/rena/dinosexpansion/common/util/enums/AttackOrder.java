package com.rena.dinosexpansion.common.util.enums;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum AttackOrder {
    PASSIVE(translatable("passive")),
    NEUTRAL(translatable("neutral")),
    PROTECTING(translatable("protecting")),
    HOSTILE(translatable("hostile"));

    private final ITextComponent displayName;

    AttackOrder(ITextComponent displayName) {
        this.displayName = displayName;
    }

    public ITextComponent getDisplayName() {
        return displayName;
    }

    private static ITextComponent translatable(String name){
        return new TranslationTextComponent("order_type." + DinosExpansion.MOD_ID + "." + name);
    }

    public final AttackOrder next() {
        return AttackOrder.values()[(this.ordinal() + 1) % AttackOrder.values().length];
    }
}
