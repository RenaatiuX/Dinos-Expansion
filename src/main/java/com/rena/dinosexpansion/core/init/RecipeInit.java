package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.recipe.MortarRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeInit {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DinosExpansion.MOD_ID);

    /*public static final RegistryObject<MortarRecipe.Serializer> MORTAR_SERIALIZER
            = RECIPE_SERIALIZER.register("mortar", MortarRecipe.Serializer::new);*/

    public static IRecipeType<MortarRecipe> MORTAR_RECIPE = new MortarRecipe.MortarRecipeTypes();
}
