package com.rena.dinosexpansion.common.util.enums;

public enum PoopEffect {

    SMALL(1),
    MEDIUM(2),
    LARGE(4);

    final int effectArea;

    PoopEffect(int effectArea) {
        this.effectArea = effectArea;
    }

    public int getEffectArea() {
        return effectArea;
    }
}
