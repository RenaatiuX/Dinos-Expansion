package com.rena.dinosexpansion.common.recipe.util;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface IMortarRecipe extends IRecipe<IInventory> {

    ResourceLocation ID = DinosExpansion.modLoc("mortar");

    @Override
    default IRecipeType<?> getType(){
        return Registry.RECIPE_TYPE.getOptional(ID).get();
    }

    @Override
    default boolean canFit(int width, int height){
        return true;
    }

    @Override
    default boolean isDynamic() {
        return true;
    }
}
