package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.awt.event.KeyEvent;


public class KeybindsInit {
    public static KeyBinding DINOSAUR_ATTACK;

    public static void init() {
        DINOSAUR_ATTACK = register("dinosaur_attack", KeyEvent.VK_V);
    }

    public static KeyBinding register(String name, int key) {
        KeyBinding binding = create(name, key);
        ClientRegistry.registerKeyBinding(binding);
        return binding;
    }


    protected static KeyBinding create(String name, int key) {
        return new KeyBinding("key." + DinosExpansion.MOD_ID + "." + name, key, "key.categories." + DinosExpansion.MOD_ID);
    }
}
