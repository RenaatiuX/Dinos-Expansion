package com.rena.dinosexpansion.core.datagen.server;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.SoundInit;
import net.minecraft.client.audio.Sound;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.fml.RegistryObject;

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
        add(SoundInit.CAMPANILE_SHELL_OCEAN.get(), SoundDefinition.definition().subtitle(subtitle(SoundInit.CAMPANILE_SHELL_OCEAN)).with(SoundDefinition.Sound.sound(DinosExpansion.modLoc("item/sea_song"), SoundDefinition.SoundType.SOUND).stream()));

        add(SoundInit.DIMORPHODON_AMBIENT1.get(), dinosaur() + "dimorphodon/dimorphodon_idle1");
        add(SoundInit.DIMORPHODON_AMBIENT2.get(), dinosaur() + "dimorphodon/dimorphodon_idle2");
        add(SoundInit.DIMORPHODON_AMBIENT3.get(), dinosaur() + "dimorphodon/dimorphodon_idle3");


        add(SoundInit.DIMORPHODON_HURT1.get(), dinosaur() + "dimorphodon/dimorphodon_hurt1");
        add(SoundInit.DIMORPHODON_HURT2.get(), dinosaur() + "dimorphodon/dimorphodon_hurt2");
        add(SoundInit.DIMORPHODON_HURT3.get(), dinosaur() + "dimorphodon/dimorphodon_hurt3");
        add(SoundInit.DIMORPHODON_HURT4.get(), dinosaur() + "dimorphodon/dimorphodon_hurt4");

        add(SoundInit.DIMORPHODON_DEATH1.get(), dinosaur() + "dimorphodon/dimorphodon_dead1");
        add(SoundInit.DIMORPHODON_DEATH2.get(), dinosaur() + "dimorphodon/dimorphodon_dead2");
    }

    protected String dinosaur(){
        return "entity/dinosaur/";
    }

    protected void add(SoundEvent event, String path){
        add(event, SoundDefinition.definition().subtitle(subtitle(event.getRegistryName())).with(SoundDefinition.Sound.sound(DinosExpansion.modLoc(path), SoundDefinition.SoundType.SOUND).stream()));
    }

    protected String subtitle(RegistryObject<SoundEvent> event){
        return subtitle(event.getId());
    }
    protected String subtitle(ResourceLocation event){
        return "sound." + event.getPath();
    }
}
