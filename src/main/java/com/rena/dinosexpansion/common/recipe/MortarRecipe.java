package com.rena.dinosexpansion.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rena.dinosexpansion.common.tileentity.MortarTileEntity;
import com.rena.dinosexpansion.core.init.RecipeInit;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Arrays;
import java.util.Map;

public class MortarRecipe implements IRecipe<IInventory> {

    public static final Serializer SERIALIZER = new Serializer();

    private final ItemStack output;
    private final ResourceLocation id;
    private final NonNullList<Ingredient> recipeItems;
    private final int workTime;
    private final float experience;
    private final int[] counts;

    public MortarRecipe(ItemStack output, ResourceLocation id, NonNullList<Ingredient> recipeItems, int[] counts, float experience, int workTime) {
        this.output = output;
        this.id = id;
        this.recipeItems = recipeItems;
        this.counts = counts;
        this.workTime = workTime;
        this.experience = experience;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        //also allow the user to make recipes with only one ingredient
        if (this.counts.length == 1) {
            return compareStacks(0, inv.getStackInSlot(0)) || compareStacks(0, inv.getStackInSlot(1));
        }
        if (compareStacks(0, inv.getStackInSlot(0))) {
            return compareStacks(1, inv.getStackInSlot(1));
        } else if (compareStacks(1, inv.getStackInSlot(0)))
            return compareStacks(0, inv.getStackInSlot(1));
        return false;
    }

    public boolean compareStacks(int ingredientIndex, ItemStack compare) {
        return this.recipeItems.get(ingredientIndex).test(compare) && this.counts[ingredientIndex] <= compare.getCount();
    }

    public int[] getCounts() {
        return counts;
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

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return output.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
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
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeInit.MORTAR_RECIPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MortarRecipe> {

        @Override
        public MortarRecipe read(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            JsonArray ingredients = JSONUtils.getJsonArray(json, "ingredients");
            if (ingredients.size() > 2) {
                throw new JsonParseException(String.format("htere are maximum 2 ingredients allowed for the mortar but found %s int recipe %s ", ingredients.size(), recipeId));
            }
            int[] counts = new int[ingredients.size()];
            Arrays.fill(counts, 1);
            float experience = JSONUtils.getFloat(json, "experience", 0.0F);
            int workTime = JSONUtils.getInt(json, "workTime", 200);
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                JsonObject obj = ingredients.get(i).getAsJsonObject();
                inputs.set(i, Ingredient.deserialize(obj.get("ingredient")));
                counts[i] = JSONUtils.getInt(obj, "count", 1);
            }

            return new MortarRecipe(output, recipeId, inputs, counts, experience, workTime);
        }

        @Override
        public MortarRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int size = buffer.readInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(size, Ingredient.EMPTY);
            int[] counts = new int[size];
            inputs.replaceAll(ignored -> Ingredient.read(buffer));
            for (int i = 0; i < size; i++) {
                counts[i] = buffer.readInt();
            }
            ItemStack output = buffer.readItemStack();
            float experience = buffer.readFloat();
            int workTime = buffer.readVarInt();

            return new MortarRecipe(output, recipeId, inputs, counts, experience, workTime);
        }

        @Override
        public void write(PacketBuffer buffer, MortarRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buffer);
            }
            for (int i : recipe.counts) {
                buffer.writeInt(i);
            }
            buffer.writeItemStack(recipe.output);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.workTime);
        }
    }
}
