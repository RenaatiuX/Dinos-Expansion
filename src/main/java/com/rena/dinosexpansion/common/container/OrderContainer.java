package com.rena.dinosexpansion.common.container;

import com.rena.dinosexpansion.common.container.util.DinosaurContainer;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class OrderContainer extends DinosaurContainer<Dinosaur> {
    public OrderContainer(int id, PlayerInventory inv, Dinosaur dinosaur) {
        super(ContainerInit.ORDER_CONTAINER.get(), id, inv, dinosaur);
    }

    public OrderContainer(int id, PlayerInventory inv, PacketBuffer buffer) {
        super(ContainerInit.ORDER_CONTAINER.get(), id, inv, buffer);
    }

    @Override
    protected void init() {

    }
}
