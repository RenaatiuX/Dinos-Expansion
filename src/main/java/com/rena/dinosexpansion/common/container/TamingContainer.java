package com.rena.dinosexpansion.common.container;

import com.rena.dinosexpansion.common.container.util.DinosaurContainer;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;

public class TamingContainer extends DinosaurContainer<Dinosaur> {
    public TamingContainer(int id, PlayerInventory inv, Dinosaur dinosaur) {
        super(ContainerInit.TAMING_CONTAINER.get(), id, inv, dinosaur);
    }

    public TamingContainer(int id, PlayerInventory inv, PacketBuffer buffer) {
        super(ContainerInit.TAMING_CONTAINER.get(), id, inv, buffer);
    }

    @Override
    protected void init() {
        addPlayerInventory(7, 119);
    }
}
