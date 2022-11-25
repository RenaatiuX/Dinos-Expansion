package com.rena.dinosexpansion.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.container.TamingContainer;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TamingScreen extends ContainerScreen<TamingContainer> {

    public static final ResourceLocation TEXTURE = DinosExpansion.modLoc("textures/gui/dinosaur_tamed_gui.png");
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
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, mouseX, mouseY);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f,1f,1f,1f);
        Minecraft.getInstance().textureManager.bindTexture(TEXTURE);
        this.blit(matrixStack, guiLeft, guiTop, 0,0,xSize, ySize);
        Dinosaur dino = this.container.getDinosaur();
        if (dino.getNarcoticValue() > 0){
            double percent = (double)dino.getNarcoticValue() / (double)dino.getInfo().getMaxNarcotic();
            int width = (int) (percent * 84d);
            this.blit(matrixStack, guiLeft + 7, guiTop + 73, 0, 202, width, 6);
        }
        if (dino.getHungerValue() > 0){
            double percent = (double) dino.getHungerValue() / (double) dino.getInfo().getMaxHunger();
            int width = (int) (percent * 84d);
            this.blit(matrixStack, guiLeft + 7, guiTop + 86, 0,210, width, 6);
        }
    }
}
