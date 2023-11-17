package com.rena.dinosexpansion.common.jei;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.container.MortarContainer;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.rena.dinosexpansion.core.init.RecipeInit;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.stream.Collectors;

@JeiPlugin
public class DinosExpansionPlugin implements IModPlugin {

    public DinosExpansionPlugin(){

    }

    private static final ResourceLocation PLUGIN_ID = DinosExpansion.modLoc("dinosexpansion_plugin");
    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Minecraft.getInstance().world.getRecipeManager();

        registration.addRecipes(getRecipes(manager, RecipeInit.MORTAR_RECIPE), MortarRecipeCategory.UID);
        registration.addRecipes(getRecipes(manager, RecipeInit.STRIPPING), StrippingRecipeCategory.UID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new MortarRecipeCategory(helper));
        registration.addRecipeCategories(new StrippingRecipeCategory(helper));
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(MortarContainer.class, MortarRecipeCategory.UID, 0, 2, 3, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockInit.MORTAR.get()), MortarRecipeCategory.UID);
    }

    public static Collection<?> getRecipes(RecipeManager manager, IRecipeType<?> type){
        return manager.getRecipes().parallelStream().filter(recipe -> recipe.getType() == type).collect(Collectors.toList());
    }


}
