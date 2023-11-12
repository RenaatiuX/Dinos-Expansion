package com.rena.dinosexpansion.core.datagen.server;

import com.rena.dinosexpansion.core.datagen.server.recipes.MortarRecipeBuilder;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        makeMortarRecipes(consumer);
    }

    protected void makeMortarRecipes(Consumer<IFinishedRecipe> consumer){
        MortarRecipeBuilder.builder(ItemInit.NARCOTICS.get()).xp(0.15f).workTime(400).addIngredient(ItemInit.NARCOTIC_BERRIES.get(), 3).addIngredient(Items.ROTTEN_FLESH).addCriterion("has_rotten", hasItem(ItemInit.NARCOTIC_BERRIES.get())).build(consumer);
    }
}
