package com.rena.dinosexpansion.client.events;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.renderer.entity.*;
import com.rena.dinosexpansion.client.renderer.misc.ChakramRenderer;
import com.rena.dinosexpansion.client.renderer.misc.HatchetRenderer;
import com.rena.dinosexpansion.client.renderer.misc.SpearRenderer;
import com.rena.dinosexpansion.client.renderer.projectiles.CustomArrowRenderer;
import com.rena.dinosexpansion.client.renderer.projectiles.DartRenderer;
import com.rena.dinosexpansion.client.screens.OrderScreen;
import com.rena.dinosexpansion.client.screens.TamingScreen;
import com.rena.dinosexpansion.common.entity.Hermit;
import com.rena.dinosexpansion.common.entity.aquatic.Eosqualodon;
import com.rena.dinosexpansion.common.entity.aquatic.MegaPiranha;
import com.rena.dinosexpansion.common.entity.aquatic.Parapuzosia;
import com.rena.dinosexpansion.common.entity.flying.Dimorphodon;
import com.rena.dinosexpansion.common.entity.semiaquatic.Astorgosuchus;
import com.rena.dinosexpansion.common.entity.terrestrial.ambient.Campanile;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.rena.dinosexpansion.core.init.ContainerInit;
import com.rena.dinosexpansion.core.init.EntityInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraft.item.ItemModelsProperties.registerProperty;

@Mod.EventBusSubscriber(modid = DinosExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {

        registerPlantsRenderer();
        registerModelProperties();

        ScreenManager.registerFactory(ContainerInit.TAMING_CONTAINER.get(), TamingScreen::new);
        ScreenManager.registerFactory(ContainerInit.ORDER_CONTAINER.get(), OrderScreen::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CUSTOM_ARROW.get(), CustomArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.DART.get(), DartRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CHAKRAM.get(), ChakramRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.HATCHET.get(), HatchetRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.SPEAR.get(), SpearRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.PARAPUZOSIA.get(), ParapuzosiaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.EOSQUALODON.get(), EosqualodonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.MEGA_PIRANHA.get(), MegaPiranhaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.DIMORPHODON.get(), DimorphodonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.ASTORGOSUCHUS.get(), AstorgosuchusRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CAMPANILE.get(), CampanileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.HERMIT.get(), HermitRenderer::new);
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
    }

    public static void registerPlantsRenderer() {
        RenderTypeLookup.setRenderLayer(BlockInit.LAVENDER.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.LEMON_VERBENA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.ARCHAEOSIGILLARIA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.CEPHALOTAXUS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DILLHOFFIA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.EPHEDRA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.OSMUNDA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SARRACENIA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.VACCINIUM.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.ZAMITES.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.WELWITSCHIA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.PACHYPODA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.HORSETAIL.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.FOOZIA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DUISBERGIA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.BENNETTITALES.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.CRATAEGUS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.FLORISSANTIA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.AMORPHOPHALLUS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.TEMPSKYA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.PROTOTAXITES.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.PROTOTAXITES_2.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.PROTOTAXITES_3.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.PROTOTAXITES_4.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(BlockInit.REDWOOD_SAPLING.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.REDWOOD_LEAVES.get(), RenderType.getCutout());
    }

    public static void registerModelProperties() {
        registerProperty(ItemInit.COMPOUND_BOW.get(), new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
            if (p_239429_2_ == null) {
                return 0.0F;
            } else {
                return p_239429_2_.getActiveItemStack() != p_239429_0_ ? 0.0F : (float) (p_239429_0_.getUseDuration() - p_239429_2_.getItemInUseCount()) / 20.0F;
            }
        });
        registerProperty(ItemInit.COMPOUND_BOW.get(), new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);

        registerProperty(ItemInit.BLOWGUN.get(), new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);

        registerProperty(ItemInit.WOODEN_SPEAR.get(), new ResourceLocation("throwing"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
        registerProperty(ItemInit.STONE_SPEAR.get(), new ResourceLocation("throwing"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
        registerProperty(ItemInit.IRON_SPEAR.get(), new ResourceLocation("throwing"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
        registerProperty(ItemInit.GOLD_SPEAR.get(), new ResourceLocation("throwing"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
        registerProperty(ItemInit.DIAMOND_SPEAR.get(), new ResourceLocation("throwing"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
        registerProperty(ItemInit.NETHERITE_SPEAR.get(), new ResourceLocation("throwing"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
        registerProperty(ItemInit.EMERALD_SPEAR.get(), new ResourceLocation("throwing"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
    }
}
