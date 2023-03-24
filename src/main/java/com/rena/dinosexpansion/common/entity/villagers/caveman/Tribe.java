package com.rena.dinosexpansion.common.entity.villagers.caveman;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.config.DinosExpansionConfig;
import com.rena.dinosexpansion.core.init.ModVillagerTrades;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.*;

public class Tribe {

    public static final List<TribeType> TYPES = Util.make(new ArrayList<>(), list -> {
        list.add(new TribeType(ModVillagerTrades.WHITE_NORMAL_TRADES, ModVillagerTrades.WHITE_BOSS_TRADES, new TextFormatting[]{TextFormatting.RED}, AggresionLevel.HOSTILE, getCaveman("caveman_boss_white.png"), getCaveman("caveman_normal_white.png")));
    });

    private static ResourceLocation getCaveman(String textureName) {
        return DinosExpansion.modLoc("textures/entity/caveman/" + textureName);
    }

    public static int getIndex(TribeType type) {
        for (int i = 0; i < TYPES.size(); i++) {
            if (TYPES.get(i) == type)
                return i;
        }
        return -1;
    }

    public static Tribe fromNbt(CompoundNBT nbt) {
        String name = nbt.getString("name");
        TribeType type = TYPES.get(nbt.getInt("type"));
        Map<UUID, Integer> approvals = new HashMap<>();
        CompoundNBT approvalsByPlayer = nbt.getCompound("approvalByPlayer");
        for (int i = 0; approvalsByPlayer.contains("UUID" + i); i++) {
            approvals.put(approvalsByPlayer.getUniqueId("UUID" + i), approvalsByPlayer.getInt("approval" + i));
        }
        return new Tribe(type, name, approvals);
    }


    protected final TribeType type;
    protected final String name;
    protected Map<UUID, Integer> approvals;
    //stores their id
    protected final List<Caveman> cavemen = new ArrayList<>();
    protected Caveman boss;

    public Tribe(TribeType type, String name) {
        this(type, name, new HashMap<>());

    }

    protected Tribe(TribeType type, String name, Map<UUID, Integer> approvals) {
        this.type = type;
        this.name = name;
        this.approvals = approvals;
    }

    protected int getInitialApproval() {
        if (type.aggro == AggresionLevel.HOSTILE)
            return 0;
        return DinosExpansionConfig.MAX_TRIBE_APPROVAL.get();
    }

    public CompoundNBT write() {
        return write(new CompoundNBT());
    }

    public CompoundNBT write(CompoundNBT nbt) {
        nbt.putString("name", this.name);
        int index = getIndex(this.type);
        if (index < 0)
            throw new IllegalStateException("the type provided in tribe: " + name + " isnt registered to its final field");
        nbt.putInt("type", index);
        CompoundNBT approvalsByPlayer = new CompoundNBT();
        int i = 0;
        for (Map.Entry<UUID, Integer> entry : approvals.entrySet()) {
            approvalsByPlayer.putUniqueId("UUID" + i, entry.getKey());
            approvalsByPlayer.putInt("approval" + i, entry.getValue());
            i++;
        }
        nbt.put("approvalByPlayer", approvalsByPlayer);
        return nbt;
    }


    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("tribe." + DinosExpansion.MOD_ID + "." + this.name);
    }

    public String getName() {
        return name;
    }

    public TribeType getType() {
        return type;
    }

    public boolean canTrade(PlayerEntity player) {
        return getApproval(player) >= DinosExpansionConfig.TRIBE_TRADING_THRESHOLD.get();
    }

    public boolean isHostile(PlayerEntity player) {
        return getApproval(player) >= DinosExpansionConfig.TRIBE_HOSTILE_THRESHOLD.get();
    }

    public int getApproval(PlayerEntity player) {
        if (!this.approvals.containsKey(player.getUniqueID())) {
            this.approvals.put(player.getUniqueID(), getInitialApproval());
        }
        return this.approvals.get(player.getUniqueID());
    }

    public boolean containsCaveman(Caveman caveman) {
        return cavemen.contains(caveman);
    }

    public void addCaveman(Caveman c) {
        if (c.isBoss())
            this.boss = c;
        this.cavemen.add(c);
    }

    /**
     * make sure to check if u may have removed the boss
     */
    public void removeCaveman(Caveman c) {
        this.cavemen.remove(c);
    }

    public boolean hasBoss() {
        for (Caveman c : this.cavemen) {
            if (c.isBoss())
                return true;
        }
        return false;
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
