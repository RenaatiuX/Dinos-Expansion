package com.rena.dinosexpansion.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.container.DinoInventoryContainer;
import com.rena.dinosexpansion.common.entity.ChestedDinosaur;
import com.rena.dinosexpansion.common.entity.DinosaurArmorSlotType;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CJigsawBlockGeneratePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;

public class DinoInventoryScreen extends ContainerScreen<DinoInventoryContainer> {

    public static final ResourceLocation GUI = DinosExpansion.modLoc("textures/gui/medium_dino_gui.png");

    private float mousePosx;
    /** The mouse y-position recorded during the last renderered frame. */
    private float mousePosY;

    public DinoInventoryScreen(DinoInventoryContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.minecraft.textureManager.bindTexture(GUI);
        this.ySize = 230;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.blit(matrixStack, guiLeft, guiTop, 0, 0, 176, this.ySize);
        Arrays.stream(this.container.getDinosaur().getArmorPieces()).forEach(s -> this.drawFlorSlotType(matrixStack, s));
        if (this.container.getDinosaur() instanceof ChestedDinosaur && ((ChestedDinosaur) this.container.getDinosaur()).hasChest()) {
            this.drawChestSlots(matrixStack, (ChestedDinosaur) this.container.getDinosaur());
        }

        matrixStack.push();
        InventoryScreen.drawEntityOnScreen(this.guiLeft + 45, this.guiTop + 60,13,  this.guiLeft + 45 - this.mousePosx,this.guiTop + 60 - this.mousePosY,  this.container.getDinosaur());
        matrixStack.pop();

    }

    protected void drawChestSlots(MatrixStack stack, ChestedDinosaur dinosaur) {
        int chestSize = dinosaur.getChestSize();
        int rowCount = Math.floorDiv(chestSize - 1, 9) + 1;
        int currY = 84;
        for (int i = 0; i < rowCount; i++) {
            int colCount = Math.min(chestSize, 9);
            chestSize -= colCount;
            this.drawHorizontalSlots(stack, 7, currY, colCount);
            currY += 18;
        }
    }

    protected void drawHorizontalSlots(MatrixStack stack, int x, int y, int amount) {
        int slotSize = 18;
        for (int i = 0; i < amount; i++) {
            this.blit(stack, this.guiLeft + x + i * slotSize, this.guiTop + y, 176, 71, slotSize, slotSize);
        }
    }

    protected void drawFlorSlotType(MatrixStack stack, DinosaurArmorSlotType type) {
        Pair<Integer, Integer> position = DinoInventoryContainer.getPositionForSlot(type);
        switch (type) {
            case SADDLE:
                this.blit(stack, guiLeft + position.getFirst(), guiTop + position.getSecond(), 176, 0, 18, 18);
                break;
            case CHEST:
                this.blit(stack, guiLeft + position.getFirst(), guiTop + position.getSecond(), 194, 0, 18, 18);
                break;
            case HEAD:
                this.blit(stack, guiLeft + position.getFirst(), guiTop + position.getSecond(), 176, 18, 18, 18);
                break;
            case CHESTPLATE:
                this.blit(stack, guiLeft + position.getFirst(), guiTop + position.getSecond(), 194, 18, 18, 18);
                break;
            case LEG:
                this.blit(stack, guiLeft + position.getFirst(), guiTop + position.getSecond(), 176, 36, 18, 18);
                break;
            case FEET:
                this.blit(stack, guiLeft + position.getFirst(), guiTop + position.getSecond(), 194, 36, 18, 18);
                break;
        }
    }


    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.mousePosx = (float)mouseX;
        this.mousePosY = (float)mouseY;
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

}
