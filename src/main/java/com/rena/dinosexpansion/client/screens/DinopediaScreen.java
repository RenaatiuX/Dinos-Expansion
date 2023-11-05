package com.rena.dinosexpansion.client.screens;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DinopediaScreen extends Screen {

    protected static final int X = 390;
    protected static final int Y = 245;
    public static final ResourceLocation TEXTURE = DinosExpansion.modLoc("textures/gui/dinopedia/encyclopedia.png");

    public DinopediaScreen() {
        super(StringTextComponent.EMPTY);
    }

    @Override
    protected void init() {
        super.init();
    }
}
