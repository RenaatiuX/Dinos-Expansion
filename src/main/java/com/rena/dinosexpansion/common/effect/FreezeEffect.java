package com.rena.dinosexpansion.common.effect;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class FreezeEffect extends Effect {
    public FreezeEffect() {
        super(EffectType.HARMFUL, 0x5F8DCE);
        addAttributesModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160891", -0.15d, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
