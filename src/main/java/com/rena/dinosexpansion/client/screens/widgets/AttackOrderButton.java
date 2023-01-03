package com.rena.dinosexpansion.client.screens.widgets;

import com.rena.dinosexpansion.common.util.enums.AttackOrder;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;

public class AttackOrderButton extends ExtendedButton {
    private final AttackOrder order;
    public AttackOrderButton(int xPos, int yPos, int width, int height, AttackOrder order, IPressable handler) {
        super(xPos, yPos, width, height, order.getDisplayName(), handler);
        this.order = order;
    }

    public AttackOrder getOrder() {
        return order;
    }
}
