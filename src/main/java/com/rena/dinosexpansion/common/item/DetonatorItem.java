package com.rena.dinosexpansion.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

import java.util.UUID;

public class DetonatorItem extends Item {
    private static final double DETONATION_RANGE = 50.0;
    private UUID linkedC4;

    public DetonatorItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return super.onItemUse(context);
    }

    public void linkC4(UUID c4Id) {
        this.linkedC4 = c4Id;
    }

    public UUID getLinkedC4() {
        return this.linkedC4;
    }
}
