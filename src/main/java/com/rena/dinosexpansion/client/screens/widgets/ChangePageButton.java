package com.rena.dinosexpansion.client.screens.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChangePageButton extends Button {

    private final boolean right;
    @SuppressWarnings("unused")
    private int page;
    private int color;
    private final boolean playTurnSound;

    public ChangePageButton(int x, int y, boolean right, int bookpage, int color, IPressable press, boolean playTurnSound) {
        super(x, y, 23, 10, new StringTextComponent(""), press);
        this.right = right;
        page = bookpage;
        this.color = color;
        this.playTurnSound = playTurnSound;
    }

    @Override
    public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partial) {
        if (this.active) {
            boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            Minecraft.getInstance().getTextureManager().bindTexture(DinosExpansion.modLoc("textures/gui/explorer_journal/widgets.png"));
            int i = 0;
            int j = 64;
            if (flag) {
                i += 23;
            }

            if (!this.right) {
                j += 13;
            }
            j += color * 23;

            this.blit(matrixStack, this.x, this.y, i, j, width, height);
        }
    }

    @Override
    public void playDownSound(SoundHandler handler) {
        if (this.playTurnSound) {
            handler.play(SimpleSound.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0F));
        }
    }
}
