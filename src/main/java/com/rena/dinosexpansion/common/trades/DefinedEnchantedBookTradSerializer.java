package com.rena.dinosexpansion.common.trades;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class DefinedEnchantedBookTradSerializer extends TradeSerializer<DefinedEnchantedBookTrade> {
    @Override
    public DefinedEnchantedBookTrade fromJson(JsonObject obj, String filename) {
        int xpValue = JSONUtils.getInt(obj, "xpValue");
        int baseCost = JSONUtils.getInt(obj, "xpValue");
        Enchantment[] enchs = deserializeEnchantments(obj, filename);
        int[] weights = deserializeWeights(obj, filename);
        return new DefinedEnchantedBookTrade(enchs, weights, xpValue, baseCost);
    }

    protected Enchantment[] deserializeEnchantments(JsonObject obj, String filename) {
        if (obj.has("enchantments") && obj.get("enchantments").isJsonArray()) {
            JsonArray array = obj.getAsJsonArray("enchantments");
            Enchantment[] enchs = new Enchantment[array.size()];
            for (int i = 0; i < array.size(); i++) {
                JsonElement el = array.get(i);
                if (el.isJsonPrimitive() && el.getAsJsonPrimitive().isString())
                    enchs[i] = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(el.getAsJsonPrimitive().getAsString()));
                else
                    throw new JsonParseException("the array from file: " + filename + " has an invalid array content in the array named enchantments: " + JSONUtils.toString(el));
            }
            return enchs;

        }
        throw new JsonParseException("there isnt an array named enchantments in file: " + filename);
    }

    protected int[] deserializeWeights(JsonObject obj, String filename) {
        if (obj.has("weights") && obj.get("weights").isJsonArray()) {
            JsonArray array = obj.getAsJsonArray("weights");
            int[] enchs = new int[array.size()];
            for (int i = 0; i < array.size(); i++) {
                JsonElement el = array.get(i);
                if (el.isJsonPrimitive() && el.getAsJsonPrimitive().isNumber())
                    enchs[i] = el.getAsInt();
                else
                    throw new JsonParseException("the array from file: " + filename + " has an invalid array content in the array named weights: " + JSONUtils.toString(el));
            }
            return enchs;

        }
        throw new JsonParseException("there isnt an array named weights in file: " + filename);
    }

    @Override
    public ResourceLocation getName() {
        return DinosExpansion.modLoc("defined_enchanted_book");
    }
}
