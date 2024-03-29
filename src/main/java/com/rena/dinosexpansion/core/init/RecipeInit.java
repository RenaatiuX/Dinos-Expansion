package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.recipe.MortarRecipe;
import com.rena.dinosexpansion.common.recipe.StrippingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeInit {

    public static final IRecipeType<MortarRecipe> MORTAR_RECIPE = register("mortar");
    public static final IRecipeType<StrippingRecipe> STRIPPING = register("stripping_recipe");

    public static void registerRecipes(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        registerRecipe(event, MORTAR_RECIPE, MortarRecipe.SERIALIZER);
        registerRecipe(event, STRIPPING, StrippingRecipe.SERIALIZER);
    }

    private static void registerRecipe(RegistryEvent.Register<IRecipeSerializer<?>> event, IRecipeType<?> type, IRecipeSerializer<?> serializer) {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(type.toString()), type);
        event.getRegistry().register(serializer.setRegistryName(new ResourceLocation(type.toString())));
    }

    public static <T extends IRecipe<?>> IRecipeType<T> register(String name){
        return new IRecipeType<T>() {
            @Override
            public String toString() {
                return new ResourceLocation(DinosExpansion.MOD_ID, name).toString();
            }
        };
    }
}
