package com.rena.dinosexpansion.core.datagen.server;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.SoundInit;
import net.minecraft.client.audio.Sound;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class ModSoundsProvider extends SoundDefinitionsProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param generator The data generator instance provided by the event you are initializing this provider in.
     * @param helper    The existing file helper provided by the event you are initializing this provider in.
     */
    public ModSoundsProvider(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, DinosExpansion.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(SoundInit.BOOMERANG_THROW.get(), SoundDefinition.definition().subtitle("sound." + SoundInit.BOOMERANG_THROW.getId().getPath()).with(
                SoundDefinition.Sound.sound(DinosExpansion.modLoc("item/boomerang_loop"), SoundDefinition.SoundType.SOUND)
                        .stream()
        ));
        add(SoundInit.BOOMERANG_LOOP.get(), SoundDefinition.definition().subtitle("sound." + SoundInit.BOOMERANG_LOOP.getId().getPath()).with(
                SoundDefinition.Sound.sound(DinosExpansion.modLoc("item/boomerang_loop"), SoundDefinition.SoundType.SOUND)
                        .stream()
        ));
    }
}
