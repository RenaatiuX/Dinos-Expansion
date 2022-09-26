package com.rena.dinosexpansion.common.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.BitUtils;
import com.rena.dinosexpansion.common.config.DinosExpansionConfig;
import com.rena.dinosexpansion.core.init.CriteriaTriggerInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Optional;
import java.util.UUID;

public abstract class Dinosaur extends MonsterEntity {

    public static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> XP = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> BOOLS = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> RARITY = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> GENDER = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> NARCOTIC_VALUE = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> HUNGER_VALUE = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Optional<UUID>> OWNER = EntityDataManager.createKey(Dinosaur.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    public static final DataParameter<Optional<UUID>> KNOCKED_OUT = EntityDataManager.createKey(Dinosaur.class, DataSerializers.OPTIONAL_UNIQUE_ID);


    protected int maxNarcotic, maxHunger;
    protected ItemStackHandler inventory = new ItemStackHandler(2){
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            Dinosaur.this.onContentsChanged(slot);
        }
    };

    public Dinosaur(EntityType<? extends MonsterEntity> type, World world, int maxNarcotic, int maxHunger, int level) {
        super(type, world);
        this.maxNarcotic = (int) ((float) maxNarcotic * (float) DinosExpansionConfig.NARCOTIC_NEEDED_PERCENT.get() / 100f);
        this.maxHunger = maxHunger;
        this.dataManager.set(RARITY, getinitialRarity().ordinal());
        this.dataManager.set(GENDER, getInitialGender().ordinal());
        this.dataManager.set(LEVEL, level);
        Rarity rarity = getRarity();
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(getAttribute(Attributes.MAX_HEALTH).getValue() + rarity.healthBonus + DinosExpansionConfig.HEALTH_PER_LEVEL.get() * (double) level);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(getAttribute(Attributes.ATTACK_DAMAGE).getValue() + rarity.attackDamageBonus + DinosExpansionConfig.ATTACK_DAMAGE_PER_LEVEL.get() * (double) level);
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(getAttribute(Attributes.MOVEMENT_SPEED).getValue() + rarity.speedBonus);
        getAttribute(Attributes.ARMOR).setBaseValue(getAttribute(Attributes.ARMOR).getValue() + rarity.armorBonus + DinosExpansionConfig.ARMOR_PER_LEVEL.get() * (double) level);
    }


    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.set(LEVEL, 0);
        this.dataManager.set(XP, 0);
        this.dataManager.set(BOOLS, 0);
        this.dataManager.set(RARITY, Rarity.COMMON.ordinal());
        this.dataManager.set(GENDER, Gender.MALE.ordinal());
        this.dataManager.set(OWNER, Optional.empty());
        this.dataManager.set(KNOCKED_OUT, Optional.empty());
        this.dataManager.set(NARCOTIC_VALUE, 0);
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.dataManager.set(LEVEL, nbt.getInt("level"));
        this.dataManager.set(XP, nbt.getInt("xp"));
        this.dataManager.set(BOOLS, nbt.getInt("bools"));
        this.dataManager.set(RARITY, nbt.getInt("rarity"));
        this.dataManager.set(GENDER, nbt.getInt("gender"));
        this.maxNarcotic = nbt.getInt("maxNarcotic");
        this.maxHunger = nbt.getInt("maxHunger");
        this.inventory.deserializeNBT(nbt.getCompound("inventory"));
        if (nbt.contains("owner"))
            this.dataManager.set(OWNER, Optional.of(nbt.getUniqueId("owner")));
        if (nbt.contains("knocked_out_player"))
            this.dataManager.set(KNOCKED_OUT, Optional.of(nbt.getUniqueId("knocked_out_player")));
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putInt("level", this.dataManager.get(LEVEL));
        nbt.putInt("xp", this.dataManager.get(XP));
        nbt.putInt("bools", this.dataManager.get(BOOLS));
        nbt.putInt("rarity", this.dataManager.get(RARITY));
        nbt.putInt("gender", this.dataManager.get(GENDER));
        nbt.putInt("maxNarcotic", this.maxNarcotic);
        nbt.putInt("maxHunger", this.maxHunger);
        nbt.put("inventory", this.inventory.serializeNBT());
        this.dataManager.get(OWNER).ifPresent(uuid -> nbt.putUniqueId("owner", uuid));
        this.dataManager.get(KNOCKED_OUT).ifPresent(uuid -> nbt.putUniqueId("knocked_out_player", uuid));
    }

    protected void increaseLevel() {
        this.dataManager.set(LEVEL, Math.min(this.dataManager.get(LEVEL) + 1, DinosExpansionConfig.MAX_LEVEL.get()));
        double health = this.getAttribute(Attributes.MAX_HEALTH).getValue();
        double attackDamage = this.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health + DinosExpansionConfig.HEALTH_PER_LEVEL.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(attackDamage + DinosExpansionConfig.ATTACK_DAMAGE_PER_LEVEL.get());
        getAttribute(Attributes.ARMOR).setBaseValue(getAttribute(Attributes.ARMOR).getValue() + DinosExpansionConfig.ARMOR_PER_LEVEL.get());
    }

    public void increaseXp() {
        this.dataManager.set(XP, this.dataManager.get(XP) + 1);
        if (this.dataManager.get(XP) >= getMaxXp()) {
            this.dataManager.set(XP, 0);
            increaseLevel();
        }
    }

    /**
     * client synced
     *
     * @return the max xp needed to level up
     */
    public int getMaxXp() {
        return DinosExpansionConfig.MAX_XP.get() + getLevel() * DinosExpansionConfig.XP_INCREASE.get();
    }

    public int getLevel() {
        return this.dataManager.get(LEVEL);
    }

    public void setSleeping(boolean sleeping) {
        if (isSleeping() == sleeping)
            return;
        this.dataManager.set(BOOLS, BitUtils.setBit(0, this.dataManager.get(BOOLS), sleeping));
    }

    public boolean isSleeping() {
        int value = BitUtils.getBit(0, this.dataManager.get(BOOLS));
        return value > 0;
    }

    public void setKnockedOutBy(PlayerEntity player) {
        this.setKnockout(true);
        this.dataManager.set(KNOCKED_OUT, Optional.of(player.getUniqueID()));
    }

    public void setTamedBy(PlayerEntity player) {
        this.setTamed(true);
        if (player instanceof ServerPlayerEntity){
            CriteriaTriggerInit.TAME_DINOSAUR.trigger((ServerPlayerEntity) player, this);
        }
        this.dataManager.set(OWNER, Optional.of(player.getUniqueID()));
    }

    protected void setKnockout(boolean knockout) {
        if (isKnockout() == knockout)
            return;
        int bools = this.dataManager.get(BOOLS);
        this.dataManager.set(BOOLS, BitUtils.setBit(1, bools, knockout));
    }

    public boolean isKnockout() {
        return BitUtils.getBit(1, this.dataManager.get(BOOLS)) > 0;
    }

    protected void setTamed(boolean tamed) {
        if (tamed == isTamed())
            return;
        int bools = this.dataManager.get(BOOLS);
        this.dataManager.set(BOOLS, BitUtils.setBit(2, bools, tamed));
    }

    public boolean isTamed() {
        return BitUtils.getBit(2, this.dataManager.get(BOOLS)) > 0;
    }

    public Rarity getRarity() {
        return Rarity.values()[this.dataManager.get(RARITY)];
    }

    public Gender getGender() {
        return Gender.values()[this.dataManager.get(GENDER)];
    }

    public boolean hasSaddle() {
        return BitUtils.getBit(3, this.dataManager.get(BOOLS)) > 0;
    }

    protected void setSaddle(boolean saddle) {
        if (saddle == hasSaddle())
            return;
        int bools = this.dataManager.get(BOOLS);
        this.dataManager.set(BOOLS, BitUtils.setBit(3, bools, saddle));
    }

    public boolean hasArmor() {
        return BitUtils.getBit(4, this.dataManager.get(BOOLS)) > 0;
    }

    protected void setArmor(boolean armor) {
        if (armor == hasArmor())
            return;
        int bools = this.dataManager.get(BOOLS);
        this.dataManager.set(BOOLS, BitUtils.setBit(4, bools, armor));
    }

    public int getNarcoticValue() {
        return this.dataManager.get(NARCOTIC_VALUE);
    }

    public int getHungerValue() {
        return this.dataManager.get(HUNGER_VALUE);
    }

    protected void setHungerValue(int value) {
        this.dataManager.set(HUNGER_VALUE, Math.min(value, maxHunger));
    }

    protected void addHungerValue(int add) {
        this.dataManager.set(HUNGER_VALUE, Math.min(getHungerValue() + add, maxHunger));
    }

    protected void addNarcoticValue(int add) {
        this.dataManager.set(NARCOTIC_VALUE, Math.min(getNarcoticValue() + add, this.maxNarcotic));
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    /**
     * called when in the inventory of saddle and armor are changed
     * @param slot - the slot where the change happened
     */
    protected void onContentsChanged(int slot){
        if (slot == 0){
            boolean saddle = !inventory.getStackInSlot(slot).isEmpty();
            if (hasSaddle() != saddle)
                setSaddle(saddle);
        }else if (slot == 1){
            boolean armor = !inventory.getStackInSlot(slot).isEmpty();
            if (hasArmor() == armor)
                setArmor(armor);
        }
    }

    protected void setNarcoticValue(int value) {
        this.dataManager.set(NARCOTIC_VALUE, Math.min(value, maxNarcotic));
    }

    public boolean isOwner(PlayerEntity player){
        if (this.dataManager.get(OWNER).isPresent())
            return this.dataManager.get(OWNER).get().equals(player.getUniqueID());
        return false;
    }

    /**
     * this is called at the constructor to define the initial rarity
     *
     * @return
     */
    protected abstract Rarity getinitialRarity();

    /**
     * this is called at the constructor to define its initial gender
     */
    protected abstract Gender getInitialGender();


    public enum Rarity {
        COMMON(new TranslationTextComponent("rarity." + DinosExpansion.MOD_ID + ".common"), 0, 0, 0, 0),
        UNCOMMON(new TranslationTextComponent("rarity." + DinosExpansion.MOD_ID + ".uncommon"), 4f, 1f, 0, 2),
        RARE(new TranslationTextComponent("rarity." + DinosExpansion.MOD_ID + ".rare"), 8, 2, .3f, 4),
        EPIC(new TranslationTextComponent("rarity." + DinosExpansion.MOD_ID + ".epic"), 16, 4, .3f, 8),
        LEGENDARY(new TranslationTextComponent("rarity." + DinosExpansion.MOD_ID + ".legendary"), 32, 16, .5f, 16);

        private final float healthBonus, attackDamageBonus, speedBonus, armorBonus;
        private final ITextComponent name;

        /**
         * @param name              the display name that will be displayed
         * @param healthBonus       increase max Health by that value
         * @param attackDamageBonus will increase the attack Damage of the entity by that value
         * @param speedBonus        will just be faster when attacking a target
         */
        Rarity(ITextComponent name, float healthBonus, float attackDamageBonus, float speedBonus, float armorBonus) {
            this.healthBonus = healthBonus;
            this.attackDamageBonus = attackDamageBonus;
            this.speedBonus = speedBonus;
            this.name = name;
            this.armorBonus = armorBonus;
        }
    }

    public enum Gender {
        MALE(new TranslationTextComponent("gender." + DinosExpansion.MOD_ID + ".male")),
        FEMALE(new TranslationTextComponent("gender." + DinosExpansion.MOD_ID + ".female"));

        private final ITextComponent name;

        Gender(ITextComponent name) {
            this.name = name;
        }
    }
}
