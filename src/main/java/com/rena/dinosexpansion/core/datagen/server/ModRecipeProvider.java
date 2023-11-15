package com.rena.dinosexpansion.core.datagen.server;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.datagen.server.recipes.MortarRecipeBuilder;
import com.rena.dinosexpansion.core.datagen.server.recipes.StrippingRecipeBuilder;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.CommandSuggestionHelper;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.Objects;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator p_i48262_1_) {
        super(p_i48262_1_);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        makeMortarRecipes(consumer);
        makeObfuscatedCraftingRecipes(consumer);
        chakrams(consumer);
        makeHatchets(consumer);
        makeWoodRecipes(consumer);
        makeToolRecipes(consumer);
    }

    protected void makeToolRecipes(Consumer<IFinishedRecipe> consumer){
        makeSurroundingRecipe(consumer, ItemInit.RUBY.get(), Items.NETHERITE_SWORD, ItemInit.RUBY_SWORD.get());
        makeSurroundingRecipe(consumer, ItemInit.RUBY.get(), Items.NETHERITE_PICKAXE, ItemInit.RUBY_PICKAXE.get());
        makeSurroundingRecipe(consumer, ItemInit.RUBY.get(), Items.NETHERITE_AXE, ItemInit.RUBY_AXE.get());
        makeSurroundingRecipe(consumer, ItemInit.RUBY.get(), Items.NETHERITE_HOE, ItemInit.RUBY_HOE.get());
        makeSurroundingRecipe(consumer, ItemInit.RUBY.get(), Items.NETHERITE_SHOVEL, ItemInit.RUBY_SHOVEL.get());

        makeSurroundingRecipe(consumer, ItemInit.SAPPHIRE.get(), ItemInit.RUBY_SWORD.get(), ItemInit.SAPPHIRE_SWORD.get());
        makeSurroundingRecipe(consumer, ItemInit.SAPPHIRE.get(), ItemInit.RUBY_PICKAXE.get(), ItemInit.SAPPHIRE_PICKAXE.get());
        makeSurroundingRecipe(consumer, ItemInit.SAPPHIRE.get(), ItemInit.RUBY_AXE.get(), ItemInit.SAPPHIRE_AXE.get());
        makeSurroundingRecipe(consumer, ItemInit.SAPPHIRE.get(), ItemInit.RUBY_HOE.get(), ItemInit.SAPPHIRE_HOE.get());
        makeSurroundingRecipe(consumer, ItemInit.SAPPHIRE.get(), ItemInit.RUBY_SHOVEL.get(), ItemInit.SAPPHIRE_SHOVEL.get());
    }

    protected void makeWoodRecipes(Consumer<IFinishedRecipe> consumer){
        makeWoodRecipes(consumer, BlockInit.CRATAEGUS_LOG.get(), BlockInit.CRATAEGUS_PLANKS.get(), ItemInit.CRATAEGUS_STICK.get(), Items.CHARCOAL, BlockInit.STRIPPED_CRATAEGUS_LOG.get());
    }

    protected void makeObfuscatedCraftingRecipes(Consumer<IFinishedRecipe> consumer){
        ShapedRecipeBuilder.shapedRecipe(ItemInit.SLINGSHOT.get())
                .key('s', ModTags.Items.DINO_STICKS)
                .key('l', Items.LEAD)
                .patternLine("sls")
                .patternLine(" s ")
                .addCriterion("has_item", hasItem(ModTags.Items.DINO_STICKS))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(BlockInit.MORTAR.get())
                .key('s', Blocks.SMOOTH_STONE)
                .key('w', ModTags.Items.DINO_STICKS)
                .patternLine("  w")
                .patternLine("sws")
                .patternLine("sss")
                .addCriterion("has_item", hasItem(ModTags.Items.DINO_STICKS));


    }

    protected void chakrams(Consumer<IFinishedRecipe> consumer){
        makeChakramRecipe(consumer, ItemInit.WOODEN_CHAKRAM.get(), ItemTags.PLANKS, ModTags.Items.DINO_STICKS);
        makeChakramRecipe(consumer, ItemInit.STONE_CHAKRAM.get(), Blocks.SMOOTH_STONE, ItemInit.WOODEN_CHAKRAM.get());
        makeChakramRecipe(consumer, ItemInit.GOLD_CHAKRAM.get(), Items.GOLD_INGOT, ItemInit.WOODEN_CHAKRAM.get());
        makeChakramRecipe(consumer, ItemInit.IRON_CHAKRAM.get(), Items.IRON_INGOT, ItemInit.STONE_CHAKRAM.get());
        makeChakramRecipe(consumer, ItemInit.DIAMOND_CHAKRAM.get(), Items.DIAMOND, ItemInit.IRON_CHAKRAM.get());
        makeChakramRecipe(consumer, ItemInit.EMERALD_CHAKRAM.get(), Items.EMERALD, ItemInit.DIAMOND_CHAKRAM.get());
        makeChakramRecipe(consumer, ItemInit.RUBY_CHAKRAM.get(), ItemInit.RUBY.get(), ItemInit.NETHERITE_CHAKRAM.get());
        makeChakramRecipe(consumer, ItemInit.SAPPHIRE_CHAKRAM.get(), ItemInit.SAPPHIRE.get(), ItemInit.RUBY_CHAKRAM.get());
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(ItemInit.EMERALD_CHAKRAM.get()), Ingredient.fromItems(Items.NETHERITE_INGOT), ItemInit.NETHERITE_CHAKRAM.get()).addCriterion("has_item", hasItem(Items.NETHERITE_INGOT)).build(consumer, DinosExpansion.modLoc("netherite_chakram"));
    }

    protected void makeHatchets(Consumer<IFinishedRecipe> consumer){
        makeHatchetRecipe(consumer, Ingredient.fromItems(Items.WOODEN_AXE), Ingredient.fromTag(ModTags.Items.DINO_STICKS), ItemInit.WOODEN_HATCHET.get());
        makeHatchetRecipe(consumer, Ingredient.fromItems(ItemInit.WOODEN_HATCHET.get()), Ingredient.fromItems(Items.SMOOTH_STONE), ItemInit.STONE_HATCHET.get());
        makeHatchetRecipe(consumer, Ingredient.fromItems(ItemInit.WOODEN_HATCHET.get()), Ingredient.fromItems(Items.GOLD_INGOT), ItemInit.GOLD_HATCHET.get());
        makeHatchetRecipe(consumer, Ingredient.fromItems(ItemInit.STONE_HATCHET.get()), Ingredient.fromItems(Items.IRON_INGOT), ItemInit.IRON_HATCHET.get());
        makeHatchetRecipe(consumer, Ingredient.fromItems(ItemInit.IRON_HATCHET.get()), Ingredient.fromItems(Items.DIAMOND), ItemInit.DIAMOND_HATCHET.get());
        makeHatchetRecipe(consumer, Ingredient.fromItems(ItemInit.DIAMOND_HATCHET.get()), Ingredient.fromItems(Items.NETHERITE_INGOT), ItemInit.NETHERITE_HATCHET.get());
        makeHatchetRecipe(consumer, Ingredient.fromItems(ItemInit.NETHERITE_HATCHET.get()), Ingredient.fromItems(ItemInit.RUBY.get()), ItemInit.RUBY_HATCHET.get());
        makeHatchetRecipe(consumer, Ingredient.fromItems(ItemInit.RUBY_HATCHET.get()), Ingredient.fromItems(ItemInit.SAPPHIRE.get()), ItemInit.SAPPHIRE_HATCHET.get());

    }

    protected void makeMortarRecipes(Consumer<IFinishedRecipe> consumer){
        MortarRecipeBuilder.builder(ItemInit.NARCOTICS.get()).xp(0.15f).workTime(400).addIngredient(ItemInit.NARCOTIC_BERRIES.get(), 3).addIngredient(Items.ROTTEN_FLESH).addCriterion("has_rotten", hasItem(ItemInit.NARCOTIC_BERRIES.get())).build(consumer);
    }


    protected static void makeHatchetRecipe(Consumer<IFinishedRecipe> consumer, Ingredient hatchet, Ingredient material, IItemProvider result){
        SmithingRecipeBuilder.smithingRecipe(hatchet, material, result.asItem()).addCriterion("has_item", hasItem(result.asItem())).build(consumer, Objects.requireNonNull(result.asItem().getRegistryName()));
    }

    protected static void makeChakramRecipe(Consumer<IFinishedRecipe> consumer, IItemProvider chakram, IItemProvider material, IItemProvider middle){
        ShapedRecipeBuilder.shapedRecipe(chakram)
                .key('m', material)
                .key('s', middle)
                .patternLine(" m ")
                .patternLine("msm")
                .patternLine(" m ")
                .addCriterion("has_item", hasItem(material))
                .build(consumer);
    }

    protected static void makeChakramRecipe(Consumer<IFinishedRecipe> consumer, IItemProvider chakram, ITag<Item> material, ITag<Item> middle){
        ShapedRecipeBuilder.shapedRecipe(chakram)
                .key('m', material)
                .key('s', middle)
                .patternLine(" m ")
                .patternLine("msm")
                .patternLine(" m ")
                .addCriterion("has_item", hasItem(material))
                .build(consumer);
    }

    /**
     * this will make all the wood related recipes, this will also make smelting recipes, so wood can be smelted to the provided coal
     * @param consumer
     * @param log
     * @param planks
     * @param sticks
     */

    protected static void makeWoodRecipes(Consumer<IFinishedRecipe> consumer, IItemProvider log, IItemProvider planks, IItemProvider sticks, IItemProvider coal, Block stripped){
        if (coal.asItem() != Items.CHARCOAL)
            CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(log), coal, 0.15F, 200).addCriterion("has_log", hasItem(log)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(planks, 4).addIngredient(Ingredient.fromItems(log)).addCriterion("has_log", hasItem(log)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(sticks).key('p', planks)
                .patternLine("pp")
                .patternLine("pp").addCriterion("has_planks", hasItem(planks)).build(consumer, extend(sticks.asItem().getRegistryName(), "_from_planks"));
        ShapedRecipeBuilder.shapedRecipe(sticks).key('l', log)
                .patternLine("l")
                .patternLine("l").addCriterion("has_log", hasItem(log)).build(consumer, extend(sticks.asItem().getRegistryName(), "_from_logs"));
        if (stripped != null){
            ShapedRecipeBuilder.shapedRecipe(stripped, 3).key('l', log)
                    .patternLine("ll")
                    .patternLine("ll").addCriterion("has_log", hasItem(log)).build(consumer,extend(stripped.asItem().getRegistryName(), "_from_logs"));
            StrippingRecipeBuilder.builder(stripped).log(log).build(consumer, extend(stripped.getRegistryName(), "_from_axe"));
        }
    }

    protected static void makeWoodRecipes(Consumer<IFinishedRecipe> consumer, IItemProvider log, IItemProvider planks, IItemProvider sticks, IItemProvider coal){
        makeWoodRecipes(consumer, log, planks, sticks, coal, null);
    }

    protected static void makeWoodRecipes(Consumer<IFinishedRecipe> consumer, IItemProvider log, IItemProvider planks, IItemProvider sticks){
        makeWoodRecipes(consumer, log, planks, sticks, Items.CHARCOAL);
    }

    protected static void makeSurroundingRecipe(Consumer<IFinishedRecipe> consumer, IItemProvider surroundingMaterial, IItemProvider middle, IItemProvider output){
        makeSurroundingRecipe(consumer, surroundingMaterial, Ingredient.fromItems(middle), output);
    }

    protected static void makeSurroundingRecipe(Consumer<IFinishedRecipe> consumer, IItemProvider surroundingMaterial, Ingredient middle, IItemProvider output){
        makeSurroundingRecipe(consumer, surroundingMaterial, middle, output, 1);
    }

    protected static void makeSurroundingRecipe(Consumer<IFinishedRecipe> consumer, IItemProvider surroundingMaterial, Ingredient middle, IItemProvider output, int count){
        makeSurroundingRecipe(consumer, Ingredient.fromItems(surroundingMaterial), middle, new ItemStack(output, count));
    }

    /**
     * this will make a recipe like:
     * "sss"
     * "sms"
     * "sss"
     * with s as the surrounding and m as the middle
     */
    protected static void makeSurroundingRecipe(Consumer<IFinishedRecipe> consumer, Ingredient surroundingMaterial, Ingredient middle, ItemStack output){
        ShapedRecipeBuilder.shapedRecipe(output.getItem(), output.getCount())
                .key('s', surroundingMaterial)
                .key('m', middle)
                .patternLine("sss")
                .patternLine("sms")
                .patternLine("sss")
                .addCriterion("has_item", hasItem(middle.getMatchingStacks()[0].getItem()))
                .build(consumer);
    }

    protected static ResourceLocation extend(ResourceLocation original, String extension){
        return new ResourceLocation(original.getNamespace(), original.getPath() + extension);
    }


}
