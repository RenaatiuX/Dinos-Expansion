package com.rena.dinosexpansion.common.trades;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;

import javax.annotation.Nullable;
import java.util.Random;

public class EmeraldsForItemsTrade implements VillagerTrades.ITrade {

    private final Item tradeItem;
    private final int count, emeraldCount;
    private final int maxUses;
    private final int xpValue;
    private final float priceMultiplier;

    public EmeraldsForItemsTrade(Item tradeItem, int count, int maxUses, int xpValue) {
        this(tradeItem, count, 1, maxUses, xpValue);
    }

    public EmeraldsForItemsTrade(Item tradeItem, int count, int emeraldCount, int maxUses, int xpValue) {
        this(tradeItem, count, emeraldCount, maxUses, xpValue, 0.05f);
    }

    public EmeraldsForItemsTrade(Item tradeItem, int count, int emeraldCount, int maxUses, int xpValue, float priceMultiplier) {
        this.tradeItem = tradeItem;
        this.count = count;
        this.emeraldCount = emeraldCount;
        this.maxUses = maxUses;
        this.xpValue = xpValue;
        this.priceMultiplier = priceMultiplier;
    }


    @Nullable
    @Override
    public MerchantOffer getOffer(Entity trader, Random rand) {
        ItemStack itemstack = new ItemStack(this.tradeItem, this.count);
        return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD, emeraldCount), this.maxUses, this.xpValue, this.priceMultiplier);
    }
}
