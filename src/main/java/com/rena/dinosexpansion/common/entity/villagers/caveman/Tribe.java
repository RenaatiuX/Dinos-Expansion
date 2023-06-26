package com.rena.dinosexpansion.common.entity.villagers.caveman;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.config.DinosExpansionConfig;
import com.rena.dinosexpansion.core.init.ItemInit;
import com.rena.dinosexpansion.core.init.ModVillagerTrades;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.*;
import java.util.function.Supplier;

public class Tribe {

    public static final List<TribeType> TYPES = Util.make(new ArrayList<>(), list -> {
        list.add(new TribeType(ModVillagerTrades.WHITE_NORMAL_TRADES, ModVillagerTrades.WHITE_BOSS_TRADES, new TextFormatting[]{TextFormatting.RED}, AggresionLevel.HOSTILE, getCaveman("caveman_boss_white.png"), getCaveman("caveman_normal_white.png"), Lists.newArrayList(Pair.of(ItemInit.DIAMOND_SPEAR::get, 0.1f)), Lists.newArrayList()));
        list.add(new TribeType(ModVillagerTrades.ORANGE_NORMAL_TRADES, ModVillagerTrades.ORANGE_BOSS_TRADES, new TextFormatting[]{TextFormatting.GOLD}, AggresionLevel.PASSIVE, getCaveman("caveman_boss_orange.png"), getCaveman("caveman_normal_orange.png"), Lists.newArrayList(), Lists.newArrayList()));
    });

    private static ResourceLocation getCaveman(String textureName) {
        Pair<Supplier<IItemProvider>, Float> pair = Pair.of(() -> ItemInit.DIAMOND_SPEAR.get(), 0.25f);
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
        Tribe t = new Tribe(type, name, approvals);
        t.isBossFight = nbt.getBoolean("isBossfight");
        if (nbt.contains("bossfightX")) {
            double x = nbt.getDouble("bossfightX");
            double y = nbt.getDouble("bossfightY");
            double z = nbt.getDouble("bossfightZ");
            t.bossFightCenter = new BlockPos(x, y, z);
        }
        t.bossfightCounterCircle = nbt.getInt("bossfightCircle");
        t.startCounter = nbt.getInt("confirmBossfight");
        return t;
    }


    protected final TribeType type;
    protected final String name;
    protected Map<UUID, Integer> approvals;
    //stores their id
    protected final List<Caveman> cavemen = new ArrayList<>();
    protected Caveman boss;
    protected boolean isBossFight = false;
    protected BlockPos bossFightCenter = null;
    protected int bossfightCounterCircle = 0, startCounter = 1;


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

    public List<Caveman> getCavemen() {
        return cavemen;
    }

    public CompoundNBT write() {
        return write(new CompoundNBT());
    }

    public Caveman getBoss() {
        return boss;
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
        nbt.putBoolean("isBossfight", this.isBossFight);
        if (bossFightCenter != null) {
            nbt.putDouble("bossfightX", bossFightCenter.getX());
            nbt.putDouble("bossfightY", bossFightCenter.getY());
            nbt.putDouble("bossfightZ", bossFightCenter.getZ());
        }
        nbt.putInt("bossfightCircle", this.bossfightCounterCircle);
        nbt.putInt("confirmBossfight", this.startCounter);
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

    public int getSize(){
        return this.cavemen.size();
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

    public boolean canStartBossfight(){
        return this.startCounter == 0;
    }

    /**
     * @param c the caveman we want to add
     * @return whether the caveman could be added or not
     */
    public boolean addCaveman(Caveman c) {
        if (c.isBoss() && boss == null) {
            this.boss = c;
            this.cavemen.add(c);
            return true;
        } else if (c.isBoss() && !isBossFight) {
            this.cavemen.add(c);
            startBossfight(c, boss);
            return true;
        }else {
            this.cavemen.add(c);
        }
        return false;
    }

    protected void startBossfight(Caveman boss1, Caveman boss2){
        this.isBossFight = true;
        boss1.setBoss(true);
        boss2.setBoss(true);
        boss1.startBossfight(boss2);
        boss2.startBossfight(boss1);
        Vector3d pos = null;
        while (pos == null) {
            pos = RandomPositionGenerator.findRandomTarget(boss1, 10, 7);
            if (pos == null)
                pos = RandomPositionGenerator.findRandomTarget(boss2, 10, 7);
        }
        this.bossFightCenter = new BlockPos(pos);
    }

    public BlockPos getBossFightCenter() {
        return bossFightCenter;
    }

    public boolean isBossFight() {
        return isBossFight;
    }

    /**
     * make sure to check if u may have removed the boss
     */
    public void removeCaveman(Caveman c) {
        this.cavemen.remove(c);
        if (!hasBoss() && this.cavemen.size() > 0) {
            Random rand = new Random();
            Caveman first = this.cavemen.get(rand.nextInt(this.cavemen.size()));
            Caveman second = this.cavemen.get(rand.nextInt(this.cavemen.size()));
            if (first == second)
                first.setBoss(true);
            else {
               startBossfight(first, second);
            }

        } else if (countBosses() == 1 && isBossFight) {
            endBossfight();
        }
        if (getSize() <= 0 && c.world instanceof ServerWorld){
            TribeSaveData.removeTribe(this, (ServerWorld) c.world);
        }
    }

    protected void endBossfight(){
        this.isBossFight = false;
        this.bossFightCenter = null;
        this.bossfightCounterCircle = 0;
        startCounter = 0;
        System.out.println("ended");
    }

    /**
     * this is to count the ticks until the bossfight starts
     * this sets the initial startCounter * 2 as there will be two bosses batteling each other and each of them reduces the counter by himself
     * @param startCounter
     */
    public void setStartCounter(int startCounter) {
        //two as there are two bosses in a bossfight
        this.startCounter = 2*startCounter;
    }

    public void reduceStartCounter(){
        if (startCounter > 0)
            startCounter--;
    }

    public int getStartCounter() {
        return startCounter;
    }

    public boolean hasBoss() {
        return countBosses() > 0;
    }

    public int countBosses() {
        int count = 0;
        for (Caveman c : this.cavemen) {
            if (c.isBoss())
                count++;
        }
        return count;
    }

    public void setBossfightCounterCircle(int bossfightCounterCircle) {
        this.bossfightCounterCircle = bossfightCounterCircle;
    }

    public int getBossfightCounterCircle() {
        return bossfightCounterCircle;
    }

    public static class TribeType {
        protected final Int2ObjectMap<VillagerTrades.ITrade[]> normalTrades, bossTrades;
        protected final TextFormatting[] formats;
        protected final AggresionLevel aggro;
        private final ResourceLocation bossTexture, normalTexture;
        protected final List<Pair<Supplier<IItemProvider>, Float>> bossItems, normalItems;

        public TribeType(Int2ObjectMap<VillagerTrades.ITrade[]> normalTrades, Int2ObjectMap<VillagerTrades.ITrade[]> bossTrades, TextFormatting[] formats, AggresionLevel aggro, ResourceLocation bossTexture, ResourceLocation normalTexture, List<Pair<Supplier<IItemProvider>, Float>> bossItems, List<Pair<Supplier<IItemProvider>, Float>> normalItems) {
            this.normalTrades = normalTrades;
            this.bossTrades = bossTrades;
            this.formats = formats;
            this.aggro = aggro;
            this.bossTexture = bossTexture;
            this.normalTexture = normalTexture;
            this.bossItems = bossItems;
            this.normalItems = normalItems;
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

        public List<Pair<Supplier<IItemProvider>, Float>> getBossItems() {
            return bossItems;
        }

        public List<Pair<Supplier<IItemProvider>, Float>> getNormalItems() {
            return normalItems;
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
