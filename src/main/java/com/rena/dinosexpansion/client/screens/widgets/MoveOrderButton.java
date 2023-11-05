package com.rena.dinosexpansion.client.screens.widgets;

import com.rena.dinosexpansion.common.util.enums.MoveOrder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;
@OnlyIn(Dist.CLIENT)
public class MoveOrderButton extends ExtendedButton {
    private final MoveOrder order;
    public MoveOrderButton(int xPos, int yPos, int width, int height, MoveOrder order, IPressable handler) {
        super(xPos, yPos, width, height, order.getDisplayName(), handler);
        this.order = order;
    }

    public MoveOrder getOrder() {
        return order;
    }
}
