package com.rena.dinosexpansion.common.util.enums;

public enum PoopSize {

    SMALL(1),
    MEDIUM(2),
    LARGE(4);

    final int effectArea;

    PoopSize(int effectArea) {
        this.effectArea = effectArea;
    }

    public int getEffectArea() {
        return effectArea;
    }
}
