package com.rena.dinosexpansion.common.recipe;

import com.google.gson.JsonObject;
import com.rena.dinosexpansion.core.init.RecipeInit;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class StrippingRecipe implements IRecipe<IInventory> {

    public static final Serializer SERIALIZER = new Serializer();

    protected final Ingredient ingredient;
    protected final Block result;
    protected final ResourceLocation id;
    protected final String group;

    public StrippingRecipe(ResourceLocation id, String group, Ingredient input, Block result) {
        this.id = id;
        this.ingredient = input;
        this.result = result;
        this.group = group;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.ingredient.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return null;
    }

    public Block getResult() {
        return result;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeInit.STRIPPING;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<StrippingRecipe> {
        @Override
        public StrippingRecipe read(ResourceLocation recipeId, JsonObject json) {
            String s = JSONUtils.getString(json, "group", "stripping");
            Ingredient ingredient;
            if (JSONUtils.isJsonArray(json, "ingredient")) {
                ingredient = Ingredient.deserialize(JSONUtils.getJsonArray(json, "ingredient"));
            } else {
                ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            }

            String s1 = JSONUtils.getString(json, "result");
            Block result = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(s1));
            return new StrippingRecipe(recipeId, s, ingredient, result);
        }

        @Nullable
        @Override
        public StrippingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            String s = buffer.readString(32767);
            Ingredient ingredient = Ingredient.read(buffer);
            Block result =  ForgeRegistries.BLOCKS.getValue(new ResourceLocation(buffer.readString()));
            return new StrippingRecipe(recipeId, s, ingredient, result);
        }

        @Override
        public void write(PacketBuffer buffer, StrippingRecipe recipe) {
            buffer.writeString(recipe.group);
            recipe.ingredient.write(buffer);
            buffer.writeString(recipe.result.getRegistryName().toString());
        }
    }
}
