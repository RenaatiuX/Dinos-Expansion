package com.rena.dinosexpansion.common.entity.villagers.caveman;

import com.google.gson.*;
import com.mojang.realmsclient.util.JsonUtils;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.trades.TradeSerializer;
import com.rena.dinosexpansion.core.init.ModVillagerTrades;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class TribeTypeSerializer {

    private static final Gson GSON = new GsonBuilder().create();

    public static void serializeTribeTypes() {
        File directory = FMLPaths.CONFIGDIR.get().resolve("dinosexpansion/tribe_types/").toFile();
        if (!directory.exists() && directory.mkdirs())
            DinosExpansion.LOGGER.info("Created /config/dinosexpansion/tribe_types");
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null)
            return;
        for (File f : files) {
            JsonParser parser = new JsonParser();
            try {
                JsonObject json = parser.parse(new InputStreamReader(new FileInputStream(f))).getAsJsonObject();
                serializeFile(json, f.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    protected static void serializeFile(JsonObject obj, String name) {
        ResourceLocation bossTexture = new ResourceLocation(JSONUtils.getString(obj, "boss_texture"));
        ResourceLocation normalTexture = new ResourceLocation(JSONUtils.getString(obj, "normal_texture"));
        TextFormatting[] formats = serializeFormats(obj, name);
        Tribe.AggresionLevel level = Tribe.AggresionLevel.getBySerializableName(JSONUtils.getString(obj, "aggression"));
        if (level == null)
            throw new JsonParseException("the file: " + name + " doesnt have a valid AggressionLevel: " + JSONUtils.getString(obj, "aggression"));
        Int2ObjectMap<VillagerTrades.ITrade[]> normalTrades = serializeLevelTrades(obj, "normaltrades", name);
        Int2ObjectMap<VillagerTrades.ITrade[]> bossTrades = serializeLevelTrades(obj, "bosstrades", name);
        Tribe.TYPES.add(new Tribe.TribeType(normalTrades, bossTrades, formats, level, bossTexture, normalTexture));


    }

    protected static Int2ObjectMap<VillagerTrades.ITrade[]> serializeLevelTrades(JsonObject obj, String memberName, String filename) {
        if (obj.has(memberName) && obj.get(memberName).isJsonArray()) {
            JsonArray array = obj.getAsJsonArray(memberName);
            Int2ObjectMap<VillagerTrades.ITrade[]> trades = new Int2ObjectOpenHashMap<>();
            for (int i = 0; i < array.size(); i++) {
                JsonElement el = array.get(i);
                if (el.isJsonArray()) {
                    trades.put(i, serializeTrades(el.getAsJsonArray(), filename));
                } else
                    throw new JsonParseException("one of teh trades provided in the file: " + filename + " isnt a jsonArray");
            }
            return trades;
        }

        throw new JsonParseException("the file: " + filename + " doesnt have a JsonArray called: " + memberName);
    }

    protected static VillagerTrades.ITrade[] serializeTrades(JsonArray array, String filename) {
        VillagerTrades.ITrade[] trades = new VillagerTrades.ITrade[array.size()];
        for (int i = 0; i < array.size(); i++) {
            JsonElement el = array.get(i);
            if (el.isJsonObject()) {
                JsonObject trade = el.getAsJsonObject();
                String name = JSONUtils.getString(trade, "type");
                boolean serialized = false;
                for (TradeSerializer<?> serializer : ModVillagerTrades.SERIALIZER) {
                    if (serializer.getName().equals(name)) {
                        trades[i] = serializer.fromJson(trade, filename);
                        serialized = true;
                        break;
                    }
                }
                if (!serialized)
                    throw new JsonParseException("there wasnt a trade called: " + name);
            } else {
                throw new JsonParseException("an element of the trade array isnt a JsonObject, that happened in file: " + filename);
            }
        }
        return trades;
    }

    protected static TextFormatting[] serializeFormats(JsonObject obj, String name) {
        TextFormatting[] formatting;
        if (obj.has("formats") && obj.get("formats").isJsonArray()) {
            JsonArray array = obj.getAsJsonArray("formats");
            formatting = new TextFormatting[array.size()];
            for (int i = 0; i < array.size(); i++) {
                JsonElement el = array.get(i);
                if (el.isJsonPrimitive() && el.getAsJsonPrimitive().isString()) {
                    TextFormatting format = TextFormatting.getValueByName(el.getAsString());
                    if (format == null)
                        throw new JsonParseException("the format: " + el.getAsString() + " doesnt exist");
                    formatting[i] = format;
                }
            }
            return formatting;
        } else {
            DinosExpansion.LOGGER.warn("the field formats in json file: " + name + " isnt an array");
            throw new JsonParseException("was searching for a field formats as an json array in file: " + name + " but didnt find an array");
        }
    }
}
