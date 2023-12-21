package com.rena.dinosexpansion;

import com.rena.dinosexpansion.common.config.DinosExpansionConfig;
import com.rena.dinosexpansion.core.init.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

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

        //MinecraftForge.EVENT_BUS.register(this);
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

    public static ResourceLocation entityTexture(String name){
        return modLoc("textures/entity/" + name);
    }

    public static TranslationTextComponent translatable(String type, String key) {
        return new TranslationTextComponent(type + "." + MOD_ID + "." + key);
    }


}
