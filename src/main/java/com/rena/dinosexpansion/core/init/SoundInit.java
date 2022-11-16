package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DinosExpansion.MOD_ID);

    public static final RegistryObject<SoundEvent> DART = SOUNDS.register("dart",
            () -> new SoundEvent(new ResourceLocation(DinosExpansion.MOD_ID, "dart")));
}
