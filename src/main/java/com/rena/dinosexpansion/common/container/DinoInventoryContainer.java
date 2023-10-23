package com.rena.dinosexpansion.common.container;

import com.rena.dinosexpansion.common.container.util.DinosaurContainer;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;

public class DinoInventoryContainer extends DinosaurContainer<Dinosaur> {
    public DinoInventoryContainer(ContainerType<?> type, int id, PlayerInventory inv, Dinosaur dinosaur) {
        super(type, id, inv, dinosaur);
    }

    public DinoInventoryContainer(ContainerType<?> type, int id, PlayerInventory inv, PacketBuffer buffer) {
        super(type, id, inv, buffer);
    }

    @Override
    protected void init() {

    }
}
