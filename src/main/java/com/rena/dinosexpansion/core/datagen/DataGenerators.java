package com.rena.dinosexpansion.core.datagen;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.datagen.client.ModBlockSTatesProvider;
import com.rena.dinosexpansion.core.datagen.server.MOdLanguageProvider;
import com.rena.dinosexpansion.core.datagen.server.ModEntityTypeTagsProvider;
import com.rena.dinosexpansion.core.datagen.server.ModLootTableProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = DinosExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeClient())
            gatherClientData(gen, helper);
        if (event.includeServer())
            gatherServerData(gen, helper);
        try {
            gen.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void gatherClientData(DataGenerator gen, ExistingFileHelper helper) {
        gen.addProvider(new ModBlockSTatesProvider(gen, helper));
    }

    private static void gatherServerData(DataGenerator gen, ExistingFileHelper helper) {
            gen.addProvider(new ModEntityTypeTagsProvider(gen, helper));
            gen.addProvider(new ModLootTableProvider(gen));
            gen.addProvider(new MOdLanguageProvider(gen));
    }
}
