package com.rena.dinosexpansion.core.network;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Network {

    private static int CHANNEL1_ID = 0;

    public static final SimpleChannel CHANNEL1 = registerSimpleChannel("first_channel", "0.1.0");

    /**
     * this registers all packets, should be called from {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent}
     */
    public static void register(){
        CHANNEL1.registerMessage(CHANNEL1_ID++, MoveOrderPacket.class, MoveOrderPacket::encode, MoveOrderPacket::decode, MoveOrderPacket::handle);
        CHANNEL1.registerMessage(CHANNEL1_ID++, AttackOrderPacket.class, AttackOrderPacket::encode, AttackOrderPacket::decode, AttackOrderPacket::handle);
        CHANNEL1.registerMessage(CHANNEL1_ID++, AttackDinosaurPacket.class, AttackDinosaurPacket::encode, AttackDinosaurPacket::decode, AttackDinosaurPacket::handle);
    }


    protected static SimpleChannel registerSimpleChannel(String id, String networkVersion) {
        return NetworkRegistry.newSimpleChannel(DinosExpansion.modLoc(id), () -> networkVersion, networkVersion::equals, networkVersion::equals);
    }
}
