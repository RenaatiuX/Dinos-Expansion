package com.rena.dinosexpansion.core.datagen.server.recipes;

import com.google.gson.JsonObject;
import com.rena.dinosexpansion.common.recipe.StrippingRecipe;
import net.minecraft.block.Block;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class StrippingRecipeBuilder {

    protected final Block stripped;
    protected Ingredient log;

    protected String group = "stripping";

    public StrippingRecipeBuilder(Block stripped) {
        this.stripped = stripped;
    }

    public static StrippingRecipeBuilder builder(Block stripped){
        return new StrippingRecipeBuilder(stripped);
    }

    public StrippingRecipeBuilder log(Ingredient log){
        this.log = log;
        return this;
    }

    public StrippingRecipeBuilder log(IItemProvider... logs){
        return log(Ingredient.fromItems(logs));
    }

    public StrippingRecipeBuilder log(ITag<Block> logs){
        Block[] possibleLogs = logs.getAllElements().toArray(new Block[0]);
        return log(possibleLogs);
    }

    public StrippingRecipeBuilder group(String group){
        if (!this.group.equals(group))
            this.group = group;
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumerIn) {
        this.build(consumerIn, this.stripped.getRegistryName());
    }

    /**
     * Builds this recipe into an {@link IFinishedRecipe}. Use {@link #build(Consumer)} if save is the same as the ID for
     * the result.
     */
    public void build(Consumer<IFinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = this.stripped.getRegistryName();
        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Stripping Recipe " + save + " should remove its 'save' argument");
        } else {
            this.build(consumerIn, new ResourceLocation(save));
        }
    }

    /**
     * Builds this recipe into an {@link IFinishedRecipe}.
     */
    public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new Result(id, this.stripped, this.log, this.group));
    }

    private static class Result implements IFinishedRecipe{

        private final ResourceLocation id;
        private final Block block;
        private final Ingredient log;

        private final String group;

        public Result(ResourceLocation id, Block block, Ingredient log, String group) {
            this.id = id;
            this.block = block;
            this.log = log;
            this.group = group;
        }

        @Override
        public void serialize(JsonObject json) {
            if (!group.equals("stripping"))
                json.addProperty("group", this.group);
            json.add("ingredient", this.log.serialize());
            json.addProperty("result", ForgeRegistries.BLOCKS.getKey(this.block).toString());
        }

        @Override
        public ResourceLocation getID() {
            return this.id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return StrippingRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }


}
