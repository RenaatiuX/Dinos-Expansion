package com.rena.dinosexpansion.common.jei;

import com.google.common.collect.Lists;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.recipe.StrippingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StrippingRecipeCategory implements IRecipeCategory<StrippingRecipe> {

    public static final ResourceLocation UID = DinosExpansion.modLoc("stripping_category");

    protected final IDrawable icon;

    public StrippingRecipeCategory(IGuiHelper helper){
        this.icon = helper.createDrawableIngredient(new ItemStack(Items.NETHERITE_AXE));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends StrippingRecipe> getRecipeClass() {
        return StrippingRecipe.class;
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("category." + DinosExpansion.MOD_ID + ".stripping_category").getString();
    }

    @Override
    public IDrawable getBackground() {
        return new DrawableBlank(120, 70);
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(StrippingRecipe recipe, IIngredients ingredients) {
        List<ItemStack> axes = ForgeRegistries.ITEMS.getValues().stream().filter(i -> i.getToolTypes(new ItemStack(i)).contains(ToolType.AXE)).map(ItemStack::new).collect(Collectors.toList());
        List<List<ItemStack>> stacks = Lists.newArrayList(Arrays.asList(recipe.getIngredient().getMatchingStacks()), axes);
        ingredients.setInputLists(VanillaTypes.ITEM, stacks);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, StrippingRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();

        itemStackGroup.init(0, true, 10, 37);
        itemStackGroup.init(1, true, 52, 7);
        itemStackGroup.init(2, false, 90, 37);

        itemStackGroup.set(ingredients);
    }
}
