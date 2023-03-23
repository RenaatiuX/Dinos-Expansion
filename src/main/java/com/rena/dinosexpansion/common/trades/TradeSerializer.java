package com.rena.dinosexpansion.common.trades;

import com.google.gson.JsonObject;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.util.ResourceLocation;

public abstract class TradeSerializer<T extends VillagerTrades.ITrade> {

    public abstract T fromJson(JsonObject obj, String filename);
    public abstract ResourceLocation getName();

    /**
     * dont modify it, because of teh set, except u really know what u are doing
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TradeSerializer){
            return this.getName().equals(((TradeSerializer<?>) obj).getName());
        }
        return super.equals(obj);
    }
}
