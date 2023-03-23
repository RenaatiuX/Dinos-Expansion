package com.rena.dinosexpansion.common.entity.villagers.caveman;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.ModVillagerTrades;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class Tribe {

    public static final List<TribeType> TYPES = Util.make(new ArrayList<>(), list -> {
        list.add(new TribeType(ModVillagerTrades.WHITE_NORMAL_TRADES, ModVillagerTrades.WHITE_BOSS_TRADES, new TextFormatting[]{TextFormatting.RED}, AggresionLevel.HOSTILE, getCaveman("caveman_boss_white.png"), getCaveman("caveman_normal_white.png")));
    });

    private static ResourceLocation getCaveman(String textureName) {
        return DinosExpansion.modLoc("textures/entity/caveman/" + textureName);
    }


    protected final TribeType type;
    protected final String name;
    protected final List<Caveman> caveman = new ArrayList<>();

    public Tribe(TribeType type, String name) {
        this.type = type;
        this.name = name;
    }


    public static class TribeType {
        protected final Int2ObjectMap<VillagerTrades.ITrade[]> normalTrades, bossTrades;
        protected final TextFormatting[] formats;
        protected final AggresionLevel aggro;
        private final ResourceLocation bossTexture, normalTexture;

        public TribeType(Int2ObjectMap<VillagerTrades.ITrade[]> normalTrades, Int2ObjectMap<VillagerTrades.ITrade[]> bossTrades, TextFormatting[] formats, AggresionLevel aggro, ResourceLocation bossTexture, ResourceLocation normalTexture) {
            this.normalTrades = normalTrades;
            this.bossTrades = bossTrades;
            this.formats = formats;
            this.aggro = aggro;
            this.bossTexture = bossTexture;
            this.normalTexture = normalTexture;
        }

        public Int2ObjectMap<VillagerTrades.ITrade[]> getNormalTrades() {
            return normalTrades;
        }

        public Int2ObjectMap<VillagerTrades.ITrade[]> getBossTrades() {
            return bossTrades;
        }

        public TextFormatting[] getFormats() {
            return formats;
        }

        public AggresionLevel getAggro() {
            return aggro;
        }

        public ResourceLocation getBossTexture() {
            return bossTexture;
        }

        public ResourceLocation getNormalTexture() {
            return normalTexture;
        }
    }

    public enum AggresionLevel implements IStringSerializable {
        PASSIVE("passive"),
        NEUTRAL("neutral"),
        HOSTILE("hostile");

        private final String name;

        AggresionLevel(String name) {
            this.name = name;
        }

        @Override
        public String getString() {
            return this.name;
        }

        public static AggresionLevel getBySerializableName(String name) {
            for (AggresionLevel level : AggresionLevel.values()) {
                if (level.getString().equalsIgnoreCase(name))
                    return level;
            }
            return null;
        }
    }

}
