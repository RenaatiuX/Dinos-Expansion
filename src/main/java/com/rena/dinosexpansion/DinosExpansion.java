package com.rena.dinosexpansion;

import com.rena.dinosexpansion.common.config.DinosExpansionConfig;
import com.rena.dinosexpansion.common.entity.villagers.caveman.Tribe;
import com.rena.dinosexpansion.common.entity.villagers.caveman.TribeTypeSerializer;
import com.rena.dinosexpansion.common.events.DinoArmorRegistration;
import com.rena.dinosexpansion.core.init.*;
import com.rena.dinosexpansion.core.network.Network;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.Comparator;

@Mod(DinosExpansion.MOD_ID)
public class DinosExpansion
{
    public static final String MOD_ID = "dinosexpansion";
    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean ENABLE_OVERWORLD_TREES = true;

    public DinosExpansion() {

        GeckoLib.initialize();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DinosExpansionConfig.BUILDER.build());
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addGenericListener(IRecipeSerializer.class, RecipeInit::registerRecipes);

        bus.addListener(this::setup);
        bus.addListener(DinoArmorRegistration::armorRegistration);
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        EntityInit.ENTITY_TYPES.register(bus);
        BlockEntityInit.TES.register(bus);
        BiomeInit.BIOMES.register(bus);
        EnchantmentInit.VANILLA.register(bus);
        EnchantmentInit.ENCHANTMENTS.register(bus);
        StructureInit.STRUCTURES.register(bus);
        EffectInit.EFFECTS.register(bus);
        SoundInit.SOUNDS.register(bus);
        ContainerInit.CONTAINERS.register(bus);
        FeatureInit.FEATURES.register(bus);
        SurfaceBuilderInit.SURFACE_BUILDERS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation modLoc(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public static ResourceLocation entityLoot(String name){
        return modLoc("entities/" + name);
    }

    public static ResourceLocation chestLoot(String name){
        return modLoc("chests/" + name);
    }

    public static ResourceLocation blockLoot(String name){
        return modLoc("blocks/" + name);
    }

    private void setup(final FMLCommonSetupEvent event) {
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
        });
    }
}
