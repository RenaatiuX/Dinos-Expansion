package com.rena.dinosexpansion.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.container.MortarContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MortarScreen extends ContainerScreen<MortarContainer> {

    protected static final ResourceLocation GUI = DinosExpansion.modLoc("textures/gui/mortar_gui.png");

    public MortarScreen(MortarContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.minecraft.textureManager.bindTexture(GUI);
        int middleX = (this.width - this.xSize) / 2;
        int middleY = (this.height - this.ySize) / 2;
        this.blit(matrixStack, middleX, middleY, 0, 0, 176, 166);
        if (container.isPowered()) {
            //icon
            this.blit(matrixStack, middleX + 56, middleY + 37, 176, 0, 16, 14);
            //arrow
            this.blit(matrixStack, middleX + 79, middleY + 35, 176, 14,
                    (int) (((double)container.getTileEntity().getCounterPercentage()) * 0.25), 17);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }
}
