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
    public static final RegistryObject<SoundEvent> CAMPANILE_SHELL_OCEAN = create("campanile_shell_ocean");

    //Entities
    public static final RegistryObject<SoundEvent> DIMORPHODON_AMBIENT1 = create("dimorphodon_ambient1");
    public static final RegistryObject<SoundEvent> DIMORPHODON_AMBIENT2 = create("dimorphodon_ambient2");
    public static final RegistryObject<SoundEvent> DIMORPHODON_AMBIENT3 = create("dimorphodon_ambient3");
    public static final ImmutableList<Supplier<SoundEvent>> DIMORPHODON_AMBIENT = ImmutableList.of(
            DIMORPHODON_AMBIENT1,
            DIMORPHODON_AMBIENT2,
            DIMORPHODON_AMBIENT3
    );
    public static final RegistryObject<SoundEvent> DIMORPHODON_HURT1 = create("dimorphodon_hurt1");
    public static final RegistryObject<SoundEvent> DIMORPHODON_HURT2 = create("dimorphodon_hurt2");
    public static final RegistryObject<SoundEvent> DIMORPHODON_HURT3 = create("dimorphodon_hurt3");
    public static final RegistryObject<SoundEvent> DIMORPHODON_HURT4 = create("dimorphodon_hurt4");
    public static final ImmutableList<Supplier<SoundEvent>> DIMORPHODON_HURT = ImmutableList.of(
            DIMORPHODON_HURT1,
            DIMORPHODON_HURT2,
            DIMORPHODON_HURT3,
            DIMORPHODON_HURT4
    );
    public static final RegistryObject<SoundEvent> DIMORPHODON_DEATH1 = create("dimorphodon_death1");
    public static final RegistryObject<SoundEvent> DIMORPHODON_DEATH2 = create("dimorphodon_death2");
    public static final ImmutableList<Supplier<SoundEvent>> DIMORPHODON_DEATH = ImmutableList.of(
            DIMORPHODON_DEATH1,
            DIMORPHODON_DEATH2
    );
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
    public static final RegistryObject<SoundEvent> DRYOSAURUS_HURT2 = create("dryosaurus_hurt2");
    public static final RegistryObject<SoundEvent> DRYOSAURUS_HURT3 = create("dryosaurus_hurt3");
    public static final ImmutableList<Supplier<SoundEvent>> DRYOSAURUS_HURT = ImmutableList.of(
            DRYOSAURUS_HURT1,
            DRYOSAURUS_HURT2,
            DRYOSAURUS_HURT3
    );
    public static final RegistryObject<SoundEvent> DRYOSAURUS_DEATH = create("dryosaurus_death");

    public static final RegistryObject<SoundEvent> BOOMERANG_THROW = create("item.boomerang_throw");
    public static final RegistryObject<SoundEvent> BOOMERANG_LOOP = create("item.boomerang_loop");


    private static RegistryObject<SoundEvent> create(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(DinosExpansion.MOD_ID, name)));
    }
}
