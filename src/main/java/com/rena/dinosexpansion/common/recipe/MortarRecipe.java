package com.rena.dinosexpansion.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rena.dinosexpansion.common.recipe.util.IMortarRecipe;
import com.rena.dinosexpansion.core.init.RecipeInit;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class MortarRecipe implements IMortarRecipe {
    private final ItemStack output;
    private final ResourceLocation id;
    private final NonNullList<Ingredient> recipeItems;
    private final int workTime;
    private final float experience;
    private final int count1;
    private final int count2;

    public MortarRecipe(ItemStack output, ResourceLocation id, NonNullList<Ingredient> recipeItems, int count1, int count2, float experience, int workTime) {
        this.output = output;
        this.id = id;
        this.recipeItems = recipeItems;
        this.count1 = count1;
        this.count2 = count2;
        this.workTime = workTime;
        this.experience = experience;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        if (this.recipeItems.get(0).test(inv.getStackInSlot(0))) {
            return recipeItems.get(1).test(inv.getStackInSlot(1));
        }
        return false;
    }

    public boolean testInput1(ItemStack stack) {
        return recipeItems.get(0).test(stack) && stack.getCount() >= count1;
    }

    public boolean testInput2(ItemStack stack) {
        return recipeItems.get(1).test(stack) && stack.getCount() >= count2;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    public float getExperience() {
        return this.experience;
    }

    public int getWorkingTime() {
        return workTime;
    }

    public int getCount1() {
        return count1;
    }

    public int getCount2() {
        return count2;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return output.copy();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return null;
    }

    public static class MortarRecipeTypes implements IRecipeType<MortarRecipe> {
        @Override
        public String toString() {
            return MortarRecipe.ID.toString();
        }
    }

    /*public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MortarRecipe> {

        @Override
        public MortarRecipe read(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            JsonArray ingredients = JSONUtils.getJsonArray(json, "ingredients");
            float experience = JSONUtils.getFloat(json, "experience", 0.0F);
            int workTime = JSONUtils.getInt(json, "workTime", 200);
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.deserialize(ingredients.get(i)));
            }

            return new MortarRecipe(output, recipeId, inputs, experience, workTime);
        }

        @Nullable
        @Override
        public MortarRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.read(buffer));
            ItemStack output = buffer.readItemStack();
            float experience = buffer.readFloat();
            int workTime = buffer.readVarInt();

            return new MortarRecipe(output, recipeId, inputs, experience, workTime);
        }

        @Override
        public void write(PacketBuffer buffer, MortarRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buffer);
            }
            buffer.writeItemStack(recipe.output);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.workTime);
        }
    }*/
}
