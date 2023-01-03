package com.rena.dinosexpansion.core.network;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.util.enums.MoveOrder;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoveOrderPacket {

    private final MoveOrder order;
    private final int entityId;

    public MoveOrderPacket(MoveOrder order, int entityId) {
        this.order = order;
        this.entityId = entityId;
    }

    public static void encode(MoveOrderPacket pkt, PacketBuffer buffer){
        buffer.writeEnumValue(pkt.order);
        buffer.writeVarInt(pkt.entityId);
    }

    public static MoveOrderPacket decode(PacketBuffer buffer){
        return new MoveOrderPacket(buffer.readEnumValue(MoveOrder.class), buffer.readVarInt());
    }

    public static void handle(MoveOrderPacket pkt, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            World w = ctx.get().getSender().world;
            Entity entity = w.getEntityByID(pkt.entityId);
            if (entity instanceof Dinosaur){
                Dinosaur dino = (Dinosaur) entity;
                dino.setMoveOrder(pkt.order);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
