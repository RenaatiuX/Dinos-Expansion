package com.rena.dinosexpansion.common.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
            if(Minecraft.getInstance().gameSettings.getPointOfView() != PointOfView.FIRST_PERSON){
                Minecraft.getInstance().player.getPersistentData().putInt(DinosExpansion.MOD_ID + "view", Minecraft.getInstance().gameSettings.getPointOfView().ordinal());
                Minecraft.getInstance().gameSettings.setPointOfView(PointOfView.FIRST_PERSON);
            }
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
            if (Minecraft.getInstance().player.getPersistentData().contains(DinosExpansion.MOD_ID + "view")){
                Minecraft.getInstance().gameSettings.setPointOfView(PointOfView.values()[Minecraft.getInstance().player.getPersistentData().getInt(DinosExpansion.MOD_ID + "view")]);
                Minecraft.getInstance().player.getPersistentData().remove(DinosExpansion.MOD_ID + "view");
            }
            //TODO if u stand still and not moving ur mouse u basically will see not rendered Blocks
            //this will reload all renders
            //Minecraft.getInstance().worldRenderer.loadRenderers();
        }
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    public double getMaxZoom() {
        return maxZoom;
    }

    public double getZoomSPeed() {
        return zoomSPeed;
    }
}
