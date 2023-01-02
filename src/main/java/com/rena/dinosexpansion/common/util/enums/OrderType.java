package com.rena.dinosexpansion.common.util.enums;

public enum OrderType {
    WANDER, SIT, FOLLOW;

    public final OrderType next() {
        return OrderType.values()[(this.ordinal() + 1) % OrderType.values().length];
    }
}
