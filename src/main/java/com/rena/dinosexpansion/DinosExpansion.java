package com.rena.dinosexpansion;

import com.rena.dinosexpansion.common.config.DinosExpansionConfig;
import com.rena.dinosexpansion.core.init.*;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DinosExpansion.MOD_ID)
public class DinosExpansion
{
    public static final String MOD_ID = "dinosexpansion";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public DinosExpansion() {

        GeckoLib.initialize();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DinosExpansionConfig.BUILDER.build());
        // Register the setup method for modloading
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        EntityInit.ENTITY_TYPES.register(bus);
        BiomeInit.BIOMES.register(bus);
        EnchantmentInit.VANILLA.register(bus);
        EnchantmentInit.ENCHANTMENTS.register(bus);
        EffectInit.EFFECTS.register(bus);
        SoundInit.SOUNDS.register(bus);
        ContainerInit.CONTAINERS.register(bus);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation modLoc(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() ->  {
            CriteriaTriggerInit.REGISTRY.forEach(CriteriaTriggers::register);
        });
    }
}
