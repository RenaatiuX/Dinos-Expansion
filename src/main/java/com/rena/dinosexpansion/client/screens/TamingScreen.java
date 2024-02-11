package com.rena.dinosexpansion.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.container.TamingContainer;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;

@OnlyIn(Dist.CLIENT)
public class TamingScreen extends ContainerScreen<TamingContainer> {

    public static final ResourceLocation TEXTURE = DinosExpansion.modLoc("textures/gui/dinosaur_tamed_gui.png");

    private float mousePosx;
    private float mousePosY;

    public TamingScreen(TamingContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init() {
        this.xSize = 174;
        this.ySize = 200;
        super.init();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.drawText(matrixStack, this.title, (float) this.titleX, (float) this.titleY - 2, 4210752);
        this.font.drawText(matrixStack, this.playerInventory.getDisplayName(), (float) this.playerInventoryTitleX, (float) this.playerInventoryTitleY + 35, 4210752);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.mousePosx = (float)mouseX;
        this.mousePosY = (float)mouseY;
        this.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, mouseX, mouseY);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        Minecraft.getInstance().textureManager.bindTexture(TEXTURE);
        this.blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);
        Dinosaur dino = this.container.getDinosaur();
        //narcotic
        if (dino.getNarcoticValue() > 0) {
            double percent = (double) dino.getNarcoticValue() / (double) dino.getMaxNarcotic();
            int width = (int) (percent * 84d);
            this.blit(matrixStack, guiLeft + 7, guiTop + 73, 0, 202, width, 6);
        }
        //narcoticThreshold
        double thresholdPercent = (double) dino.getInfo().getNarcoticThreshold() / (double) dino.getMaxNarcotic();
        int thresholdX = (int) (thresholdPercent * 84d);
        this.blit(matrixStack, guiLeft + 6 + thresholdX, guiTop + 73, 0, 226, 1, 6);
        //hunger
        if (dino.getHungerValue() > 0) {
            double percent = (double) dino.getHungerValue() / (double) dino.getMaxHunger();
            int width = (int) (percent * 84d);
            this.blit(matrixStack, guiLeft + 7, guiTop + 86, 0, 210, width, 6);
        }
        //taming Progress
        if (dino.getTamingProgress() > 0) {
            int width = (int) ((float) dino.getTamingProgress() * 84f / 100f);
            this.blit(matrixStack, guiLeft + 7, guiTop + 99, 0, 218, width, 6);
        }
        float round = (float)Math.round(dino.getTamingEfficiency() * 1000f) / 10f;
        StringTextComponent comp = new StringTextComponent( round + "%");
        int width = this.font.getStringPropertyWidth(comp);
        this.font.drawText(matrixStack, comp, guiLeft + 7 + (float) (84 - width) / 2, guiTop + 99, 4210752);
        Dinosaur.DinosaurInfo info = dino.getInfo();
        int left = guiLeft + this.titleX + 88;
        this.font.drawText(matrixStack, new TranslationTextComponent(DinosExpansion.MOD_ID + ".gender", info.getGender().getDisplayName()), left, (float) guiTop + this.titleY - 2, 4210752);
        this.font.drawText(matrixStack, new TranslationTextComponent(DinosExpansion.MOD_ID + ".level", info.getLevel()), left, (float) guiTop + this.titleY + 10, 4210752);
        this.font.drawText(matrixStack, new TranslationTextComponent(DinosExpansion.MOD_ID + ".sleep_rhythm", info.getRhythm().getDisplayName()), left, (float) guiTop + this.titleY + 22, 4210752);
        InventoryScreen.drawEntityOnScreen(guiLeft + 55, guiTop + 50, 20, (guiLeft + 55) -this.mousePosx, (guiTop+ 75 - 50) - this.mousePosY, dino);
    }


    @Override
    protected void renderHoveredTooltip(MatrixStack matrixStack, int x, int y) {
        super.renderHoveredTooltip(matrixStack, x, y);
        Dinosaur dino = this.container.getDinosaur();
        if (x >= guiLeft + 7 && x <= guiLeft + 7 + 84 && y >= guiTop + 73 && y <= guiTop + 73 + 6) {
            this.renderTooltip(matrixStack, new TranslationTextComponent("taming_screen." + DinosExpansion.MOD_ID + ".narcotic", dino.getNarcoticValue(), dino.getInfo().getMaxNarcotic()), x, y);
        }
        if (x >= guiLeft + 7 && x <= guiLeft + 7 + 84 && y >= guiTop + 86 && y <= guiTop + 86 + 6) {
            this.renderTooltip(matrixStack, new TranslationTextComponent("taming_screen." + DinosExpansion.MOD_ID + ".hunger", dino.getHungerValue(), dino.getInfo().getMaxHunger()), x, y);
        }
        if (x >= guiLeft + 7 && x <= guiLeft + 7 + 84 && y >= guiTop + 99 && y <= guiTop + 99 + 6) {
            this.renderTooltip(matrixStack, new TranslationTextComponent("taming_screen." + DinosExpansion.MOD_ID + ".taming_progress", dino.getTamingProgress()), x, y);
        }
    }
}
