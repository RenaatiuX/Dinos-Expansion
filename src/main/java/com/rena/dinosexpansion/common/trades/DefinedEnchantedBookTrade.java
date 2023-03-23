package com.rena.dinosexpansion.common.trades;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

public class DefinedEnchantedBookTrade  implements VillagerTrades.ITrade {

    private final Enchantment[] allowedEnchantments;
    private final int[] weights;
    private final int xpValue, baseCost;

    /**
     *
     * @param allowedEnchants a predicated that chooses from every enchantment the matching ones
     * @param weights the weights for each enchantment stored in  a function
     * @param xpValue the xpValue the player gains upon trading
     * @param baseCost the cost an enchanted book would have if it has a probability of 50%
     */
    public DefinedEnchantedBookTrade(Predicate<Enchantment> allowedEnchants, Function<Enchantment, Integer> weights, int xpValue, int baseCost) {
        this(calcAllowedEnchants(allowedEnchants), weights, xpValue, baseCost);
    }

    public DefinedEnchantedBookTrade(Enchantment[] allowedEnchantments, Function<Enchantment, Integer> weights, int xpValue, int baseCost) {
        this(allowedEnchantments, calcWeights(allowedEnchantments, weights), xpValue, baseCost);
    }

    public DefinedEnchantedBookTrade(Enchantment[] allowedEnchantments, int[] weights, int xpValue, int baseCost) {
        if (allowedEnchantments.length != weights.length) {
            throw new IllegalArgumentException("there has to be exactly as much weights as enchantments");
        }
        this.allowedEnchantments = allowedEnchantments;
        this.weights = weights;
        this.xpValue = xpValue;
        this.baseCost = baseCost;
    }

    @Nullable
    @Override
    public MerchantOffer getOffer(Entity trader, Random rand) {

        int enchId = getWeightedRandom(this.allowedEnchantments, this.weights, rand);
        Enchantment enchantment = allowedEnchantments[enchId];
        int level = MathHelper.nextInt(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
        ItemStack book = EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(enchantment, level));
        double sum = Arrays.stream(weights).sum();
        double percent = Math.floor(((double) weights[enchId]) * 200d / sum);
        int cost = (int) (((double)baseCost) * (percent - 50) + (double)rand.nextInt(5 + level * 10));
        cost = Math.min(64, cost);

        return new MerchantOffer(new ItemStack(Items.EMERALD, cost), new ItemStack(Items.BOOK), book, 2, this.xpValue, 0.2F);
    }

    private static int getWeightedRandom(Enchantment[] enchantments, int[] weights, Random rand) {
        int randWeight = rand.nextInt(Arrays.stream(weights).sum());
        for (int i = 0; i < enchantments.length; i++) {
            randWeight -= weights[i];
            if (randWeight < 0) {
                return i;
            }
        }
        return 0;
    }

    private static Enchantment[] calcAllowedEnchants(Predicate<Enchantment> allowedEnchants) {
        return (Enchantment[]) ForgeRegistries.ENCHANTMENTS.getValues().stream().filter(allowedEnchants).toArray();
    }

    private static int[] calcWeights(Enchantment[] allowedEnchants, Function<Enchantment, Integer> weightsFunc) {
        int[] weights = new int[allowedEnchants.length];
        for (int i = 0; i < allowedEnchants.length; i++) {
            weights[i] = weightsFunc.apply(allowedEnchants[i]);
        }
        return weights;
    }
}
