package com.rena.dinosexpansion.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.screens.widgets.AttackOrderButton;
import com.rena.dinosexpansion.client.screens.widgets.MoveOrderButton;
import com.rena.dinosexpansion.client.screens.widgets.WidgetLabel;
import com.rena.dinosexpansion.common.container.util.DinosaurContainer;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.util.enums.AttackOrder;
import com.rena.dinosexpansion.common.util.enums.MoveOrder;
import com.rena.dinosexpansion.core.network.AttackOrderPacket;
import com.rena.dinosexpansion.core.network.MoveOrderPacket;
import com.rena.dinosexpansion.core.network.Network;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sun.nio.ch.Net;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class OrderScreen extends ContainerScreen<DinosaurContainer<Dinosaur>> {

    protected WidgetLabel attackOrderLabel, moveOrderLabel;

    public OrderScreen(DinosaurContainer<Dinosaur> screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init() {
        super.init();
        int margin = 10;
        Rectangle boundingBox = new Rectangle(guiLeft + margin, guiTop + margin, 176 - 2 * margin, 166 - 2 * margin);
        /**Attack Order*/
        Rectangle left = new Rectangle(boundingBox.x, boundingBox.y, boundingBox.width / 2, boundingBox.height);
        /**Move Order*/
        Rectangle right = new Rectangle(boundingBox.x + boundingBox.width / 2, boundingBox.y, boundingBox.width / 2, boundingBox.height);
        addAttackOrderButtons(this.container.getDinosaur().allowedAttackOrders(), left, 10, 10, 10);
        addMoveOrderButtons(this.container.getDinosaur().allowedMoveOrders(), right, 10, 10,10);

    }

    /**
     * this method changes the size of the buttons depending on how many to add.
     */
    protected void addAttackOrderButtons(AttackOrder[] orders, Rectangle boundingBox, int marginY, int paddingY, int paddingX) {
        // calculating the individual height of each widget(e.g. button, label) so it fills up the complete height and repsects margin and padding(i might mix up padding and margin)
        int individualHeight = (boundingBox.height - 2 * paddingY - (orders.length) * marginY - 10) / (orders.length);
        int currentY = boundingBox.y + paddingY;
        int currentX = boundingBox.x + paddingX;
        int width = boundingBox.width - 2 * paddingX;
        //we draw the current order
        this.attackOrderLabel = this.addButton(new WidgetLabel(currentX, currentY, width, 10, this.container.getDinosaur().getAttackOrder().getDisplayName()));
        //this just goes down the y coordinate in order to draw the next Widget
        currentY += 10 + marginY;
        for (AttackOrder order : orders){
            this.addButton(new AttackOrderButton(currentX, currentY, width, individualHeight, order, this::handleAttackOrder));
            currentY += individualHeight + marginY;
        }
    }

    /**
     * the same as the one for the attack Order just with Move Order
     */
    protected void addMoveOrderButtons(MoveOrder[] orders, Rectangle boundingBox, int marginY, int paddingY, int paddingX) {
        // calculating the individual height of each widget(e.g. button, label) so it fills up the complete height and repsects margin and padding(i might mix up padding and margin)
        int individualHeight = (boundingBox.height - 2 * paddingY - (orders.length) * marginY - 10) / (orders.length);
        int currentY = boundingBox.y + paddingY;
        int currentX = boundingBox.x + paddingX;
        int width = boundingBox.width - 2 * paddingX;
        this.moveOrderLabel = this.addButton(new WidgetLabel(currentX, currentY, width, 10, this.container.getDinosaur().getMoveOrder().getDisplayName()));
        currentY += 10 + marginY;
        for (MoveOrder order : orders){
            this.addButton(new MoveOrderButton(currentX, currentY, width, individualHeight, order, this::handleMoveOrder));
            currentY += individualHeight + marginY;
        }
    }

    protected void handleAttackOrder(Button b) {
        if (b instanceof AttackOrderButton){
            Network.CHANNEL1.sendToServer(new AttackOrderPacket(((AttackOrderButton) b).getOrder(), this.container.getDinosaur().getEntityId()));
        }
    }

    protected void handleMoveOrder(Button b) {
        if (b instanceof MoveOrderButton){
            Network.CHANNEL1.sendToServer(new MoveOrderPacket(((MoveOrderButton) b).getOrder(), this.container.getDinosaur().getEntityId()));
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.drawText(matrixStack, this.title, (float)this.titleX, (float)this.titleY, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        Minecraft.getInstance().getTextureManager().bindTexture(DinosExpansion.modLoc("textures/gui/blank_screen.png"));
        this.blit(matrixStack, guiLeft, guiTop, 0,0,176, 166);
        this.moveOrderLabel.setMessage(this.container.getDinosaur().getMoveOrder().getDisplayName());
        this.attackOrderLabel.setMessage(this.container.getDinosaur().getAttackOrder().getDisplayName());
    }
}
