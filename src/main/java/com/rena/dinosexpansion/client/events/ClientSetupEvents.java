package com.rena.dinosexpansion.client.events;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.renderer.entity.EosqualodonRenderer;
import com.rena.dinosexpansion.client.renderer.entity.MegaPiranhaRenderer;
import com.rena.dinosexpansion.client.renderer.entity.ParapuzosiaRenderer;
import com.rena.dinosexpansion.client.renderer.projectiles.CustomArrowRenderer;
import com.rena.dinosexpansion.common.entity.aquatic.Eosqualodon;
import com.rena.dinosexpansion.common.entity.aquatic.MegaPiranha;
import com.rena.dinosexpansion.common.entity.aquatic.Parapuzosia;
import com.rena.dinosexpansion.core.init.EntityInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;
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
    public static void clientSetup(FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CUSTOM_ARROW.get(), CustomArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.PARAPUZOSIA.get(), ParapuzosiaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.EOSQUALODON.get(), EosqualodonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.MEGA_PIRANHA.get(), MegaPiranhaRenderer::new);

        registerProperty(ItemInit.COMPOUND_BOW.get(), new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
            if (p_239429_2_ == null) {
                return 0.0F;
            } else {
                return p_239429_2_.getActiveItemStack() != p_239429_0_ ? 0.0F : (float)(p_239429_0_.getUseDuration() - p_239429_2_.getItemInUseCount()) / 20.0F;
            }
        });
        registerProperty(ItemInit.COMPOUND_BOW.get(), new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
    }

    @SubscribeEvent
    public static final void registerEntityAttributes(EntityAttributeCreationEvent event){
        event.put(EntityInit.PARAPUZOSIA.get(), Parapuzosia.createAttributes().create());
        event.put(EntityInit.EOSQUALODON.get(), Eosqualodon.createAttributes().create());
        event.put(EntityInit.MEGA_PIRANHA.get(), MegaPiranha.createAttributes().create());
    }
}
