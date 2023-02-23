package com.rena.dinosexpansion.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

public class ZoomItem extends Item {

    protected double zoomSPeed, maxZoom;

    /**
     *
     * @param properties
     * @param zoomSpeed the speed in which this will zoom to the maxZoom. the zoom speed is a factor, so when u have zoom speed 10 it will be 10 times as slow as zoomSpeed 1 same goes for 0.1
     * @param maxZoom the max Zoom this will zooming in its a fov factor so going from 10 to 20 will double the maxZoom
     */
    public ZoomItem(Item.Properties properties, double zoomSpeed, double maxZoom) {
        super(properties);
        if (zoomSpeed <= 0)
            this.zoomSPeed = 1;
        else
            this.zoomSPeed = zoomSpeed;
        if (maxZoom <= 0)
            this.maxZoom = 1;
        else
            this.maxZoom = maxZoom;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        playerIn.getPersistentData().putBoolean(DinosExpansion.MOD_ID + "zooming", true);
        if(worldIn.isRemote()){
            Minecraft.getInstance().gameSettings.smoothCamera = true;
        }
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        entityLiving.resetActiveHand();
        entityLiving.getPersistentData().remove(DinosExpansion.MOD_ID + "zooming");
        if (entityLiving instanceof PlayerEntity){
            double cooldown = 1d - (timeLeft / (double)getUseDuration(stack));
            DinosExpansion.LOGGER.debug(cooldown + "|" + (int) (cooldown * 20d));
            ((PlayerEntity)entityLiving).getCooldownTracker().setCooldown(this, (int) (cooldown * 20d));
        }
        if (worldIn.isRemote()){
            Minecraft.getInstance().gameSettings.smoothCamera = false;
            //TODO if u stand still and not moving ur mouse u basically will see not rendered Blocks
        }
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(modid = DinosExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class MyZoomEventHandler {

        @SubscribeEvent
       public static void changeFov(EntityViewRenderEvent.FOVModifier event){
           Minecraft mc = Minecraft.getInstance();
           if (mc.player == null || mc.currentScreen != null) {
               return;
           }
           boolean zooming = mc.player.getPersistentData().getBoolean(DinosExpansion.MOD_ID + "zooming");
           ItemStack zoomItem = mc.player.getActiveItemStack();
           if (zooming && zoomItem.getItem() instanceof ZoomItem) {
               ZoomItem zoomItem1 = (ZoomItem) zoomItem.getItem();
               double fov = event.getFOV();
               double useDuration = mc.player.getItemInUseCount();
               double maxUse = mc.player.getActiveItemStack().getUseDuration();
               double increasingDuration = maxUse - useDuration;

               event.setFOV(fov / Math.max(1, Math.min(increasingDuration / zoomItem1.zoomSPeed, zoomItem1.maxZoom)));
           }

       }


    }
}
