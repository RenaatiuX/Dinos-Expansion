package com.rena.dinosexpansion.client.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.EffectInit;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

import java.util.Random;

import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.HEALTH;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = DinosExpansion.MOD_ID, value = Dist.CLIENT)
public class ClientForgeEvents {

    public static final ResourceLocation FREEZE_OVERLAY = DinosExpansion.modLoc("textures/overlay/ice_overlay.png");
    public static final ResourceLocation FROZEN_HEARTS = DinosExpansion.modLoc("textures/overlay/frozen_heart.png");
    public static final Random rand = new Random();


    @SubscribeEvent
    public static final void renderOverlay(RenderGameOverlayEvent.Post event){
        //this ensures this is renderd with the potions overlay cause it caused by a potion
        if (event.getType() == RenderGameOverlayEvent.ElementType.POTION_ICONS) {
            //check if the player has the potion effect
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isPotionActive(EffectInit.FREEZE.get()) && Minecraft.getInstance().gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
                //bin the texture
                Minecraft.getInstance().getTextureManager().bindTexture(FREEZE_OVERLAY);
                MainWindow res = event.getWindow();
                AbstractGui.blit(event.getMatrixStack(), 0, 0, 0, 0, res.getScaledWidth(), res.getScaledHeight(), res.getScaledWidth(), res.getScaledHeight());
            }
        }
    }

    @SubscribeEvent
    public static final void renderOverlayPre(RenderGameOverlayEvent.Pre event){
        //this is just there to cancel vanilla heart rendering so i can render our own hearts
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH){
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isPotionActive(EffectInit.FREEZE.get())) {
                event.setCanceled(true);
                MainWindow window = event.getWindow();
                renderHealth(window.getScaledWidth(), window.getScaledHeight(), event.getMatrixStack());
                Minecraft.getInstance().textureManager.bindTexture(ForgeIngameGui.GUI_ICONS_LOCATION);
            }
        }

    }

    //this is the copied vanilla code that draws the hearts
    public static void renderHealth(int width, int height, MatrixStack mStack)
    {
        Minecraft mc = Minecraft.getInstance();
        mc.textureManager.bindTexture(FROZEN_HEARTS);
        int ticks = mc.ingameGUI.getTicks();
        RenderSystem.enableBlend();

        PlayerEntity player = (PlayerEntity)mc.getRenderViewEntity();
        int health = MathHelper.ceil(player.getHealth());
        boolean highlight = mc.ingameGUI.healthUpdateCounter > (long)ticks && (mc.ingameGUI.healthUpdateCounter - (long)ticks) / 3L %2L == 1L;

        if (health <  mc.ingameGUI.playerHealth && player.hurtResistantTime > 0)
        {
            mc.ingameGUI.lastSystemTime = Util.milliTime();
            mc.ingameGUI.healthUpdateCounter = (long)(ticks + 20);
        }
        else if (health > mc.ingameGUI.playerHealth && player.hurtResistantTime > 0)
        {
            mc.ingameGUI.lastSystemTime = Util.milliTime();
            mc.ingameGUI.healthUpdateCounter = (long)(ticks + 10);
        }

        if (Util.milliTime() -  mc.ingameGUI.lastSystemTime > 1000L)
        {
            mc.ingameGUI.playerHealth = health;
            mc.ingameGUI.lastPlayerHealth = health;
            mc.ingameGUI.lastSystemTime = Util.milliTime();
        }

        mc.ingameGUI.playerHealth = health;
        int healthLast = mc.ingameGUI.lastPlayerHealth;

        ModifiableAttributeInstance attrMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        float healthMax = (float)attrMaxHealth.getValue();
        float absorb = MathHelper.ceil(player.getAbsorptionAmount());

        int healthRows = MathHelper.ceil((healthMax + absorb) / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);

        rand.setSeed((long)(ticks * 312871l));

        int left = width / 2 - 91;
        int top = height - ForgeIngameGui.left_height;
        ForgeIngameGui.left_height += (healthRows * rowHeight);
        if (rowHeight != 10) ForgeIngameGui.left_height += 10 - rowHeight;


        final int BACKGROUND = (highlight ? 43 : 34);
        float absorbRemaining = absorb;

        for (int i = MathHelper.ceil((healthMax + absorb) / 2.0F) - 1; i >= 0; --i)
        {
            //int b0 = (highlight ? 1 : 0);
            int row = MathHelper.ceil((float)(i + 1) / 10.0F) - 1;
            int x = left + i % 10 * 8;
            int y = top - row * rowHeight;

            if (health <= 4) y += rand.nextInt(2);

            mc.ingameGUI.blit(mStack, x, y, BACKGROUND, 0, 9, 9);

            if (highlight) {
                if (i * 2 + 1 < healthLast)
                    mc.ingameGUI.blit(mStack, x, y, 53, 0, 9, 9); //6
                else if (i * 2 + 1 == healthLast)
                    mc.ingameGUI.blit(mStack, x, y, 62, 0, 9, 9); //7
            }

            if (absorbRemaining > 0.0F) {
                if (absorbRemaining == absorb && absorb % 2.0F == 1.0F) {
                    mc.ingameGUI.blit(mStack, x , y, 170, 0, 9, 9); //17
                    absorbRemaining -= 1.0F;
                }
                else {
                    mc.ingameGUI.blit(mStack, x, y, 161, 0, 9, 9); //16
                    absorbRemaining -= 2.0F;
                }
            } else {
                if (i * 2 + 1 < health)
                    mc.ingameGUI.blit(mStack, x + 1, y, 53, 0, 9, 9); //4
                else if (i * 2 + 1 == health)
                    mc.ingameGUI.blit(mStack, x + 1, y, 62, 0, 9, 9); //5
            }
        }

        RenderSystem.disableBlend();
        mc.getProfiler().endSection();
    }

    private static void blit(MatrixStack matrixStack, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight){
        AbstractGui.blit(matrixStack, x, y, 1, (float)uOffset, (float)vOffset, uWidth, vHeight, 256, 256);
    }


}
