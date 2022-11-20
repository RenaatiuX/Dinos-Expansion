package com.rena.dinosexpansion.client.renderer.screen;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class EncyclopediaScreen extends Screen {

    public static final ResourceLocation GUI = DinosExpansion.modLoc("textures/gui/encyclopedia.png");

    protected EncyclopediaScreen() {
        super(StringTextComponent.EMPTY);
    }

    @Override
    protected void init() {
        super.init();
    }
}
