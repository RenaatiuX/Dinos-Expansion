package com.rena.dinosexpansion.common.events;


import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.api.ArmorSlotItemApi;
import com.rena.dinosexpansion.common.commands.TameCommand;
import com.rena.dinosexpansion.common.entity.aquatic.*;
import com.rena.dinosexpansion.common.entity.aquatic.fish.Acanthodes;
import com.rena.dinosexpansion.common.entity.aquatic.fish.Belantsea;
import com.rena.dinosexpansion.common.entity.aquatic.fish.MegaPiranha;
import com.rena.dinosexpansion.common.entity.aquatic.fish.Wetherellus;
import com.rena.dinosexpansion.common.entity.flying.Dimorphodon;
import com.rena.dinosexpansion.common.entity.semiaquatic.Astorgosuchus;
import com.rena.dinosexpansion.common.entity.terrestrial.Ceratosaurus;
import com.rena.dinosexpansion.common.entity.terrestrial.Dryosaurus;
import com.rena.dinosexpansion.common.entity.terrestrial.ambient.Campanile;
import com.rena.dinosexpansion.common.entity.terrestrial.ambient.Meganeura;
import com.rena.dinosexpansion.common.entity.villagers.Hermit;
import com.rena.dinosexpansion.common.entity.villagers.caveman.Caveman;
import com.rena.dinosexpansion.common.entity.villagers.caveman.TribeTypeSerializer;
import com.rena.dinosexpansion.core.init.*;
import com.rena.dinosexpansion.core.network.Network;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.WoodType;
import net.minecraft.item.Items;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = DinosExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() ->  {
            BiomeInit.addBiomeEntries();
            BiomeInit.fillBiomeDictionary();
            CriteriaTriggerInit.REGISTRY.forEach(CriteriaTriggers::register);
            Network.register();
            //FeatureInit.registerConfiguredFeatures();
            DimensionInit.registerDimension();
            StructureInit.setupStructures();
            ModVillagerTrades.registerTrades();
            TribeTypeSerializer.serializeTribeTypes();

            WoodType.register(WoodTypeInit.CRATEAGUS_WOOD);
        });
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.PARAPUZOSIA.get(), Parapuzosia.createAttributes().create());
        event.put(EntityInit.EOSQUALODON.get(), Eosqualodon.createAttributes().create());
        event.put(EntityInit.MEGA_PIRANHA.get(), MegaPiranha.createAttributes().create());
        event.put(EntityInit.DIMORPHODON.get(), Dimorphodon.createAttributes().create());
        event.put(EntityInit.ASTORGOSUCHUS.get(), Astorgosuchus.createAttributes().create());
        event.put(EntityInit.CAMPANILE.get(), Campanile.createAttributes().create());
        event.put(EntityInit.HERMIT.get(), Hermit.createAttributes());
        event.put(EntityInit.DRYOSAURUS.get(), Dryosaurus.createAttributes().create());
        event.put(EntityInit.WETHERELLUS.get(), Wetherellus.createAttributes().create());
        event.put(EntityInit.AEGIROCASSIS.get(), Aegirocassis.createAttributes().create());
        event.put(EntityInit.CAVEMAN.get(), Caveman.createAttributes());
        event.put(EntityInit.ANOMALOCARIS.get(), Anomalocaris.createAttributes().create());
        event.put(EntityInit.ACANTHODES.get(), Acanthodes.createAttributes().create());
        event.put(EntityInit.BELANTSEA.get(), Belantsea.createAttributes().create());
        event.put(EntityInit.MEGANEURA.get(), Meganeura.createAttributes().create());
        event.put(EntityInit.SQUALODON.get(), Squalodon.createAttributes().create());
        event.put(EntityInit.CERATOSAURUS.get(), Ceratosaurus.createAttributes().create());
    }

    @SubscribeEvent
    public static void armorRegistration(FMLCommonSetupEvent event){
        ArmorSlotItemApi.registerSaddle(Items.SADDLE);
        ArmorSlotItemApi.registerChest(Items.CHEST, 27);
        ArmorSlotItemApi.registerChest(Items.BARREL, 10);
    }
}
