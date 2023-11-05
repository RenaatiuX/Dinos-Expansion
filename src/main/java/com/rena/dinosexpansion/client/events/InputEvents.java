package com.rena.dinosexpansion.client.events;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.KeybindsInit;
import com.rena.dinosexpansion.core.network.AttackDinosaurPacket;
import com.rena.dinosexpansion.core.network.Network;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DinosExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InputEvents {

    @SubscribeEvent
    public static void onKeyPress(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if(mc.world == null) {
            return;
        }
        onInput(mc, event.getKey(), event.getAction());
    }

    @SubscribeEvent
    public static void onMousePressed(InputEvent.MouseInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if(mc.world == null) {
            return;
        }
        onInput(mc, event.getButton(), event.getAction());
    }

    private static void onInput(Minecraft mc, int key, int action) {
        if(mc.currentScreen == null) {
            if (KeybindsInit.DINOSAUR_ATTACK.isPressed()) {
                Network.CHANNEL1.sendToServer(new AttackDinosaurPacket());
                //System.out.println("work");
            }
        }
    }
}
