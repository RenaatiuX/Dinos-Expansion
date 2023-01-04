package com.rena.dinosexpansion.core.network;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.util.enums.AttackOrder;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AttackOrderPacket {

    private final AttackOrder order;
    private final int entityId;

    public AttackOrderPacket(AttackOrder order, int entityId) {
        this.order = order;
        this.entityId = entityId;
    }

    public static void encode(AttackOrderPacket pkt, PacketBuffer buffer){
        buffer.writeEnumValue(pkt.order);
        buffer.writeVarInt(pkt.entityId);
    }

    public static AttackOrderPacket decode(PacketBuffer buffer){
        return new AttackOrderPacket(buffer.readEnumValue(AttackOrder.class), buffer.readVarInt());
    }

    public static void handle(AttackOrderPacket pkt, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            World w = ctx.get().getSender().world;
            Entity entity = w.getEntityByID(pkt.entityId);
            if (entity instanceof Dinosaur){
                Dinosaur dino = (Dinosaur) entity;
                dino.setAttackOrder(pkt.order);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
