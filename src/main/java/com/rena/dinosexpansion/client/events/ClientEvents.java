package com.rena.dinosexpansion.client.events;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.screens.DinopediaScreen;
import com.rena.dinosexpansion.client.screens.JournalScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = DinosExpansion.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    public static boolean shouldSeeJournalContents() {
        return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 344);
    }

    @SuppressWarnings("resource")
    public static Object getFontRenderer() {
        return Minecraft.getInstance().fontRenderer;
    }

    public static void openExplorerJournal(ItemStack book) {
        Minecraft.getInstance().displayGuiScreen(new JournalScreen(book));
    }

    public static void openDinopedia(ItemStack book){
        Minecraft.getInstance().displayGuiScreen(new DinopediaScreen());
    }

}
