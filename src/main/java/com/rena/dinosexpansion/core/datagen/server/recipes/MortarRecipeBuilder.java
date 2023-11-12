package com.rena.dinosexpansion.core.datagen.server.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.common.recipe.MortarRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MortarRecipeBuilder {

    protected int workTime = 200;
    protected float xp = 0f;

    protected List<Pair<Ingredient, Integer>> inputs = new ArrayList<>();

    protected final Advancement.Builder advancementBuilder = Advancement.Builder.builder();

    protected final ItemStack output;

    public MortarRecipeBuilder(ItemStack output) {
        this.output = output;
    }

    public static MortarRecipeBuilder builder(IItemProvider item, int count){
        return new MortarRecipeBuilder(new ItemStack(item, count));
    }

    public static MortarRecipeBuilder builder(IItemProvider item){
        return new MortarRecipeBuilder(new ItemStack(item));
    }

    public MortarRecipeBuilder addCriterion(String name, ICriterionInstance criterionIn) {
        this.advancementBuilder.withCriterion(name, criterionIn);
        return this;
    }

    public MortarRecipeBuilder workTime(int workTime){
        if (workTime < 0){
            throw new IllegalArgumentException(String.format("worktime must be greater then 0 but was %s", workTime));
        }
        this.workTime = workTime;
        return this;
    }

    public MortarRecipeBuilder xp(float xp){
        if (xp < 0){
            throw new IllegalArgumentException(String.format("xp must be greater then 0 but was %s", xp));
        }
        this.xp = xp;
        return this;
    }

    public MortarRecipeBuilder addIngredient(Ingredient ing, int count){
        if (this.inputs.size() < 2){
            this.inputs.add(Pair.of(ing, count));
        }else {
            throw new IllegalArgumentException(String.format("there musnt be more then 2 inputs but someone tried to add a thrid one"));
        }
        return this;
    }

    public MortarRecipeBuilder addIngredient(Ingredient ing){
        return addIngredient(ing, 1);
    }

    public MortarRecipeBuilder addIngredient(IItemProvider item, int count){
        return addIngredient(Ingredient.fromItems(item), count);
    }

    public MortarRecipeBuilder addIngredient(IItemProvider item){
        return addIngredient(Ingredient.fromItems(item));
    }

    public MortarRecipeBuilder addIngredient(ITag<Item> item, int count){
        return addIngredient(Ingredient.fromTag(item), count);
    }

    public MortarRecipeBuilder addIngredient(ITag<Item> item){
        return addIngredient(Ingredient.fromTag(item));
    }

    public void build(Consumer<IFinishedRecipe> consumerIn) {
        this.build(consumerIn, Registry.ITEM.getKey(this.output.getItem()));
    }

    /**
     * Builds this recipe into an {@link IFinishedRecipe}. Use {@link #build(Consumer)} if save is the same as the ID for
     * the result.
     */
    public void build(Consumer<IFinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = Registry.ITEM.getKey(this.output.getItem());
        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Mortar Recipe " + save + " should remove its 'save' argument");
        } else {
            this.build(consumerIn, new ResourceLocation(save));
        }
    }

    /**
     * Builds this recipe into an {@link IFinishedRecipe}.
     */
    public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
        if (!this.advancementBuilder.getCriteria().isEmpty()){
            this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(id)).withRewards(AdvancementRewards.Builder.recipe(id)).withRequirementsStrategy(IRequirementsStrategy.OR);
            ResourceLocation advancementId = new ResourceLocation(id.getNamespace(), "recipes/mortar/" + id.getPath());
            consumerIn.accept(new Result(id, advancementId, workTime, xp, inputs, output, advancementBuilder));
        }else {
            consumerIn.accept(new Result(id, workTime, xp, inputs, output));
        }
    }

    private static class Result implements IFinishedRecipe{

        private final ResourceLocation id;
        private final ResourceLocation advancementId;
        private final int workTime;
        private final float xp;
        private final List<Pair<Ingredient, Integer>> inputs;
        private final ItemStack output;

        private final Advancement.Builder builder;

        public Result(ResourceLocation id, ResourceLocation advancementId, int workTime, float xp, List<Pair<Ingredient, Integer>> inputs, ItemStack output, Advancement.Builder builder) {
            this.id = id;
            this.advancementId = advancementId;
            this.workTime = workTime;
            this.xp = xp;
            this.inputs = inputs;
            this.output = output;
            this.builder = builder;
        }



        public Result(ResourceLocation id, int workTime, float xp, List<Pair<Ingredient, Integer>> inputs, ItemStack output) {
           this(id, null, workTime, xp, inputs, output, null);
        }

        @Override
        public void serialize(JsonObject json) {
            if (this.xp > 0)
                json.addProperty("experience", this.xp);
            if (this.workTime != 200){
                json.addProperty("workTime", this.workTime);
            }
            JsonArray ingredients = new JsonArray();
            for (Pair<Ingredient, Integer> input : this.inputs){
                JsonObject obj = new JsonObject();
                JsonElement ing = input.getFirst().serialize();
                obj.add("ingredient", ing);
                if (input.getSecond() > 1){
                    obj.addProperty("count", input.getSecond());
                }
                ingredients.add(obj);
            }
            json.add("ingredients", ingredients);
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.addProperty("item", ForgeRegistries.ITEMS.getKey(this.output.getItem()).toString());
            if (this.output.getCount() > 1) {
                jsonobject1.addProperty("count", this.output.getCount());
            }

            json.add("result", jsonobject1);
        }

        @Override
        public ResourceLocation getID() {
            return this.id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return MortarRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return this.builder == null ? null : builder.serialize();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return this.advancementId;
        }
    }
}
