package com.rena.dinosexpansion.common.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.recipe.MortarRecipe;
import com.rena.dinosexpansion.core.init.BlockInit;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MortarRecipeCategory implements IRecipeCategory<MortarRecipe> {

    public static final ResourceLocation UID = DinosExpansion.modLoc("mortar_recipe_category");
    public static final ResourceLocation TEXTURE = DinosExpansion.modLoc("textures/gui/mortar_gui.png");

    private final IDrawable background, icon, mortar;
    private final LoadingCache<Integer, IDrawableAnimated> progressBar;

    public MortarRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 20, 9, 121, 74);
        this.icon = helper.createDrawableIngredient(new ItemStack(BlockInit.MORTAR.get()));
        this.mortar = helper.createDrawable(TEXTURE, 176, 0, 16, 14);

        this.progressBar = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<Integer, IDrawableAnimated>() {
            @Override
            public IDrawableAnimated load(Integer cookTime) throws Exception {
                return helper.drawableBuilder(TEXTURE, 176, 14, 24, 17).buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
            }
        });


    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends MortarRecipe> getRecipeClass() {
        return MortarRecipe.class;
    }

    protected IDrawableAnimated getCookTime(MortarRecipe recipe) {
        return this.progressBar.getUnchecked(Math.max(60, recipe.getWorkingTime()));
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("category." + DinosExpansion.MOD_ID + ".mortar_recipe").getString();
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(MortarRecipe recipe, IIngredients ingredients) {
        addIngredientsWithCount(ingredients, recipe.getIngredients(), recipe.getCounts());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, MortarRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();

        itemStackGroup.init(0, true, 35, 7);
        itemStackGroup.init(1, true, 35, 43);
        itemStackGroup.init(2, false, 95, 25);

        itemStackGroup.set(ingredients);
    }

    @Override
    public void draw(MortarRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        mortar.draw(matrixStack, 36, 28);

        IDrawableAnimated arrow = getCookTime(recipe);
        arrow.draw(matrixStack, 59, 26);

        drawCookTime(recipe, matrixStack, 56);
    }

    protected void drawCookTime(MortarRecipe recipe, MatrixStack matrixStack, int y) {
        float cookTime = recipe.getWorkingTime();
        if (cookTime > 0) {
            float cookTimeSeconds = cookTime / 20f;
            DecimalFormat df = new DecimalFormat("#.0");
            TranslationTextComponent timeString = new TranslationTextComponent("dinosexpansion.mortar.time.seconds", df.format(cookTimeSeconds));
            Minecraft minecraft = Minecraft.getInstance();
            FontRenderer fontRenderer = minecraft.fontRenderer;
            int stringWidth = fontRenderer.getStringPropertyWidth(timeString);
            fontRenderer.drawString(matrixStack, timeString.getString(), background.getWidth() - stringWidth, y, 0xFF808080);
        }
    }

    public static void addIngredientsWithCount(IIngredients ingredients, List<Ingredient> inputs, int[] counts) {
        if (inputs.size() != counts.length)
            throw new IllegalArgumentException("there has to be exactly as much counts as there are ingredients");
        List<List<ItemStack>> stacks = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            List<ItemStack> matchingSTacks = Arrays.stream(inputs.get(i).getMatchingStacks()).map(ItemStack::copy).collect(Collectors.toList());
            int finalI = i;
            matchingSTacks.forEach(stack -> stack.setCount(counts[finalI]));
            stacks.add(matchingSTacks);
        }
        ingredients.setInputLists(VanillaTypes.ITEM, stacks);

    }
}
