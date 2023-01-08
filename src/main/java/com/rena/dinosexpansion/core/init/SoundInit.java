package com.rena.dinosexpansion.core.init;

import com.google.common.collect.ImmutableList;
import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SoundInit {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DinosExpansion.MOD_ID);

    //Item
    public static final RegistryObject<SoundEvent> DART = create("dart");

    //Entities
    public static final RegistryObject<SoundEvent> DRYOSAURUS_AMBIENT1 = create("dryosaurus_ambient1");
    public static final RegistryObject<SoundEvent> DRYOSAURUS_AMBIENT2 = create("dryosaurus_ambient2");
    public static final RegistryObject<SoundEvent> DRYOSAURUS_AMBIENT3 = create("dryosaurus_ambient3");
    public static final RegistryObject<SoundEvent> DRYOSAURUS_AMBIENT4 = create("dryosaurus_ambient4");
    public static final ImmutableList<Supplier<SoundEvent>> DRYOSAURUS_AMBIENT = ImmutableList.of(
            DRYOSAURUS_AMBIENT1,
            DRYOSAURUS_AMBIENT2,
            DRYOSAURUS_AMBIENT3,
            DRYOSAURUS_AMBIENT4
    );
    public static final RegistryObject<SoundEvent> DRYOSAURUS_HURT1 = create("dryosaurus_hurt1");
    public static final RegistryObject<SoundEvent> DRYOSAURUS_HURT2 = create("dryosaurus_hurt1");
    public static final RegistryObject<SoundEvent> DRYOSAURUS_HURT3 = create("dryosaurus_hurt1");
    public static final ImmutableList<Supplier<SoundEvent>> DRYOSAURUS_HURT = ImmutableList.of(
            DRYOSAURUS_HURT1,
            DRYOSAURUS_HURT2,
            DRYOSAURUS_HURT3
    );
    public static final RegistryObject<SoundEvent> DRYOSAURUS_DEATH = create("dryosaurus_death");


    private static RegistryObject<SoundEvent> create(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(DinosExpansion.MOD_ID, name)));
    }
}
