package com.rena.dinosexpansion.common.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.BitUtils;
import com.rena.dinosexpansion.common.config.DinosExpansionConfig;
import com.rena.dinosexpansion.common.container.TamingContainer;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.common.entity.projectile.INarcoticProjectile;
import com.rena.dinosexpansion.common.item.util.INarcotic;
import com.rena.dinosexpansion.core.init.CriteriaTriggerInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.*;

public abstract class Dinosaur extends TameableEntity {

    /**
     * use this to generate a random level as it already contains all relevant config values
     * @param minLevel the minimum Level the Dino should have when it spawns(inclusive)
     * @param maxLevel the maximum level the Dino can have when it spawns(inclusive)
     * @return the randomly distributed value between max and min
     */
    public static final int generateLevelWithinBounds(int minLevel, int maxLevel){
        int max = Math.min(maxLevel + DinosExpansionConfig.LEVEL_OFFSET.get(), DinosExpansionConfig.MAX_LEVEL.get() + DinosExpansionConfig.LEVEL_OFFSET.get());
        int min = Math.max(minLevel + DinosExpansionConfig.LEVEL_OFFSET.get(), 0);
        return MathHelper.nextInt(new Random(), min, max);
    }

    public static final void openTamingGui(Dinosaur dino, ServerPlayerEntity player){
        SimpleNamedContainerProvider provider = new SimpleNamedContainerProvider((id, inv, p) -> new TamingContainer(id, inv, dino), new TranslationTextComponent(DinosExpansion.MOD_ID + ".taming_gui." + dino.getInfo().name));
        NetworkHooks.openGui(player, provider, buf -> buf.writeVarInt(dino.getEntityId()));
    }

    public static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> XP = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> BOOLS = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> RARITY = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> GENDER = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> NARCOTIC_VALUE = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Float> HUNGER_VALUE = EntityDataManager.createKey(Dinosaur.class, DataSerializers.FLOAT);
    public static final DataParameter<Optional<UUID>> KNOCKED_OUT = EntityDataManager.createKey(Dinosaur.class, DataSerializers.OPTIONAL_UNIQUE_ID);


    protected int maxNarcotic, maxHunger;
    protected DinosaurInfo info;
    protected ItemStackHandler inventory = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            Dinosaur.this.onContentsChanged(slot);
        }
    };

    public Dinosaur(EntityType<? extends TameableEntity> type, World world, DinosaurInfo info, int minLevel, int maxLevel) {
        this(type, world, info, generateLevelWithinBounds(minLevel, maxLevel));
    }

    public Dinosaur(EntityType<? extends TameableEntity> type, World world, DinosaurInfo info, int level) {
        super(type, world);
        this.maxNarcotic = (int) ((float) info.maxNarcotic * (float) DinosExpansionConfig.NARCOTIC_NEEDED_PERCENT.get() / 100f);
        this.maxHunger = info.maxHunger;
        this.dataManager.set(RARITY, getinitialRarity().ordinal());
        this.dataManager.set(GENDER, getInitialGender().ordinal());
        this.dataManager.set(LEVEL, level);
        Rarity rarity = getRarity();
        this.info = info;
        //dont do that at home kids
        if (this.info.rhythm != SleepRhythmGoal.SleepRhythm.NONE)
            this.goalSelector.addGoal(3, new SleepRhythmGoal(this, info.rhythm));
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(getAttribute(Attributes.MAX_HEALTH).getValue() + rarity.healthBonus + DinosExpansionConfig.HEALTH_PER_LEVEL.get() * (double) level);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(getAttribute(Attributes.ATTACK_DAMAGE).getValue() + rarity.attackDamageBonus + DinosExpansionConfig.ATTACK_DAMAGE_PER_LEVEL.get() * (double) level);
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(getAttribute(Attributes.MOVEMENT_SPEED).getValue() + rarity.speedBonus);
        getAttribute(Attributes.ARMOR).setBaseValue(getAttribute(Attributes.ARMOR).getValue() + rarity.armorBonus + DinosExpansionConfig.ARMOR_PER_LEVEL.get() * (double) level);
        updateInfo();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(LEVEL, 0);
        this.dataManager.register(XP, 0);
        this.dataManager.register(BOOLS, 0);
        this.dataManager.register(RARITY, Rarity.COMMON.ordinal());
        this.dataManager.register(GENDER, Gender.MALE.ordinal());
        this.dataManager.register(KNOCKED_OUT, Optional.empty());
        this.dataManager.register(NARCOTIC_VALUE, 0);
        this.dataManager.register(HUNGER_VALUE, 0f);
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

    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        if (!world.isRemote() && hand == Hand.MAIN_HAND){
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem() instanceof INarcotic && this.isKnockout()) {
                addNarcoticValue(((INarcotic) stack.getItem()).getNarcoticValue(), player);
                stack.shrink(1);
                return ActionResultType.SUCCESS;
            }
            if ((isOwner(player) || isKnockedOutBy(player)) && canEat(stack)){
                this.addHungerValue(stack, player);
                stack.shrink(1);
                return ActionResultType.SUCCESS;
            }
        }
        return super.applyPlayerInteraction(player, vec, hand);
    }

    public boolean canEat(ItemStack stack) {
        for (Item item : this.getFood()) {
            if (item == stack.getItem())
                return true;
        }
        return false;
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

    public void addMaxNarcotic(PlayerInventory inv){

    }

    /**
     * searches threw the inventory of the player and decides greedy which food to feed
     * @param inv
     * @param player
     */
    public void addMaxHunger(PlayerInventory inv, PlayerEntity player){
        PriorityQueue<ItemStack> playerItems = new PriorityQueue<>(Comparator.comparing(stack -> stack.isFood() ? -1 : stack.getItem().getFood().getSaturation()));
        for (int i = 0;i < inv.getSizeInventory();i++){
            playerItems.add(inv.getStackInSlot(i));
        }
        while (!playerItems.isEmpty()){
            ItemStack currentStack = playerItems.poll();
            if (!currentStack.isFood() || (getHungerValue() + currentStack.getItem().getFood().getSaturation() > this.info.maxHunger))
                break;
            if (!isFood(currentStack))
                continue;
            this.addHungerValue(currentStack, player);
            currentStack.shrink(1);

        }

    }

    public void setSleeping(boolean sleeping) {
        if (isSleeping() == sleeping)
            return;
        if (sleeping) {
            setAttackTarget(null);
            this.navigator.clearPath();
        }
        this.dataManager.set(BOOLS, BitUtils.setBit(0, this.dataManager.get(BOOLS), sleeping));
    }

    public boolean isSleeping() {
        int value = BitUtils.getBit(0, this.dataManager.get(BOOLS));
        return value > 0;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote()) {
            if (this.getNarcoticValue() > 0)
                this.setNarcoticValue(Math.max(0, reduceNarcotic(getNarcoticValue())));
            if(getNarcoticValue() <= this.info.narcoticThreshold && isKnockout())
                setKnockout(false);
            reduceHunger();
        }
    }

    public void setKnockedOutBy(LivingEntity player) {
        this.setKnockout(true);
        this.dataManager.set(KNOCKED_OUT, Optional.of(player.getUniqueID()));
    }

    protected void setKnockout(boolean knockout) {
        if (isKnockout() == knockout)
            return;
        int bools = this.dataManager.get(BOOLS);
        this.dataManager.set(BOOLS, BitUtils.setBit(1, bools, knockout));
        if (knockout) {
            this.navigator.clearPath();
            setAttackTarget(null);
        }
    }

    /**
     * client synced
     */
    public boolean isKnockout() {
        return BitUtils.getBit(1, this.dataManager.get(BOOLS)) > 0;
    }
    /**
     * client synced
     */
    public boolean isTamed() {
        return BitUtils.getBit(2, this.dataManager.get(BOOLS)) > 0;
    }
    /**
     * client synced
     */
    public Rarity getRarity() {
        return Rarity.values()[this.dataManager.get(RARITY)];
    }
    /**
     * client synced
     */
    public Gender getGender() {
        return Gender.values()[this.dataManager.get(GENDER)];
    }
    /**
     * client synced
     */
    public boolean hasSaddle() {
        return BitUtils.getBit(3, this.dataManager.get(BOOLS)) > 0;
    }
    /**
     * client synced
     */
    protected void setSaddle(boolean saddle) {
        if (saddle == hasSaddle())
            return;
        int bools = this.dataManager.get(BOOLS);
        this.dataManager.set(BOOLS, BitUtils.setBit(3, bools, saddle));
    }
    /**
     * client synced
     */
    public boolean hasArmor() {
        return BitUtils.getBit(4, this.dataManager.get(BOOLS)) > 0;
    }
    /**
     * client synced
     */
    protected void setArmor(boolean armor) {
        if (armor == hasArmor())
            return;
        int bools = this.dataManager.get(BOOLS);
        this.dataManager.set(BOOLS, BitUtils.setBit(4, bools, armor));
    }

    /**
     * client synced
     */
    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.world.getPlayerByUuid(uuid);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }
    /**
     * client synced
     */
    public int getNarcoticValue() {
        return this.dataManager.get(NARCOTIC_VALUE);
    }
    /**
     * client synced
     */
    public float getHungerValue() {
        return this.dataManager.get(HUNGER_VALUE);
    }
    /**
     * client synced
     */
    protected void setHungerValue(float value) {
        this.dataManager.set(HUNGER_VALUE, Math.min(value, maxHunger));
    }
    /**
     * client synced
     */
    public void addHungerValue(ItemStack food, LivingEntity feeder) {
        if (!food.isFood() || !isFood(food) || !isOwner(feeder))
            return;
        if (feeder instanceof ServerPlayerEntity)
            CriteriaTriggerInit.FEED_DINOSAUR.trigger((ServerPlayerEntity) feeder, food, this);
        setHungerValue(getHungerValue() + food.getItem().getFood().getSaturation());
    }
    /**
     * client synced
     */
    protected void addHungerValue(int add) {
        this.dataManager.set(HUNGER_VALUE, Math.max(0, Math.min(getHungerValue() + add, maxHunger)));
    }
    /**
     * client synced
     */
    protected void addNarcoticValue(int add, LivingEntity cause) {
        this.dataManager.set(NARCOTIC_VALUE, Math.max(0, Math.min(getNarcoticValue() + add, this.maxNarcotic)));
        if (getNarcoticValue() >= this.maxNarcotic)
            this.setKnockedOutBy(cause);
        else if (getNarcoticValue() <= this.info.narcoticThreshold)
            this.setKnockout(false);
    }

    public boolean isKnockedOutBy(LivingEntity living){
        Optional<UUID> knockOutBy = this.dataManager.get(KNOCKED_OUT);
        if (knockOutBy.isPresent()){
            return living == this.world.getPlayerByUuid(knockOutBy.get());
        }
        return false;
    }

    @Override
    protected void damageEntity(DamageSource source, float damageAmount) {
        if (!this.isInvulnerableTo(source)) {
            damageAmount = net.minecraftforge.common.ForgeHooks.onLivingHurt(this, source, damageAmount);
            if (damageAmount <= 0) return;
            damageAmount = this.applyArmorCalculations(source, damageAmount);
            damageAmount = this.applyPotionDamageCalculations(source, damageAmount);
            float f2 = Math.max(damageAmount - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (damageAmount - f2));
            float f = damageAmount - f2;
            if (f > 0.0F && f < 3.4028235E37F && source.getTrueSource() instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) source.getTrueSource()).addStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(f * 10.0F));
            }

            f2 = net.minecraftforge.common.ForgeHooks.onLivingDamage(this, source, f2);
            if (f2 != 0.0F) {
                float f1 = this.getHealth();
                this.getCombatTracker().trackDamage(source, f1, f2);
                this.setHealth(f1 - f2); // Forge: moved to fix MC-121048
                this.setAbsorptionAmount(this.getAbsorptionAmount() - f2);
                if (source.getImmediateSource() instanceof INarcoticProjectile && source.getTrueSource() instanceof LivingEntity)
                    this.addNarcoticValue(((INarcoticProjectile) source.getImmediateSource()).getNarcoticValue(), (LivingEntity) source.getTrueSource());
            }
        }
    }
    /**
     * client synced
     */
    public DinosaurInfo getInfo() {
        this.updateInfo();
        return info;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    /**
     * called when in the inventory of saddle and armor are changed
     *
     * @param slot - the slot where the change happened
     */
    protected void onContentsChanged(int slot) {
        if (slot == 0) {
            boolean saddle = !inventory.getStackInSlot(slot).isEmpty();
            if (hasSaddle() != saddle)
                setSaddle(saddle);
        } else if (slot == 1) {
            boolean armor = !inventory.getStackInSlot(slot).isEmpty();
            if (hasArmor() == armor)
                setArmor(armor);
        }
    }

    public boolean isMovementDisabled(){
        return isSleeping() || isKnockout();
    }

    protected void updateInfo() {
        this.info.health = getAttributeValue(Attributes.MAX_HEALTH);
        this.info.armor = getAttributeValue(Attributes.ARMOR);
        this.info.movementSpeed = getAttributeValue(Attributes.MOVEMENT_SPEED);
        this.info.level = getLevel();
        this.info.rarity = this.getRarity();
        this.info.gender = this.getGender();
    }

    public boolean isFood(ItemStack stack){
        return getFood().contains(stack.getItem());
    }

    public abstract List<Item> getFood();
    /**
     * client synced
     * cant exceed the maxNarcotic
     */
    protected void setNarcoticValue(int value) {
        this.dataManager.set(NARCOTIC_VALUE, Math.min(value, maxNarcotic));
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

    /**
     * this is called every tick this entity is alive and the narcotic value is greater than 0
     * this is just called on the server side
     * @param narcoticValue the narcotic value > 0
     * @return the new narcotic value, can not be smaller then 0
     */
    protected abstract int reduceNarcotic(int narcoticValue);

    /**
     * just server side
     * in here hunger is reduced when running and moving, just a little reduced when doing basically nothing
     */
    protected void reduceHunger(){
        if(this.prevPosX != this.getPosX() || this.prevPosY != this.getPosY() || this.prevPosZ != this.getPosZ()){
            if (getRNG().nextDouble() <= 0.1)
                this.addHungerValue(-1);
        }
        if (getRNG().nextDouble() <= 0.001)
            this.addHungerValue(-1);
    }


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
         * @param attackDamageBonus will increase the attack Damage to the entity by that value
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

    public static class DinosaurInfo {
        private final int maxHunger, maxNarcotic, narcoticThreshold;
        private Gender gender;
        private Rarity rarity;
        private final SleepRhythmGoal.SleepRhythm rhythm;
        private int level;
        private double health, armor, movementSpeed;
        private final String name;

        public DinosaurInfo(String name, int maxHunger, int maxNarcotic, int narcoticThreshold, SleepRhythmGoal.SleepRhythm rhythm) {
            this.maxHunger = maxHunger;
            this.maxNarcotic = maxNarcotic;
            this.narcoticThreshold = narcoticThreshold;
            this.rhythm = rhythm;
            this.name = name;
        }

        public int getMaxHunger() {
            return maxHunger;
        }

        public Gender getGender() {
            return gender;
        }

        public int getMaxNarcotic() {
            return maxNarcotic;
        }

        public int getNarcoticThreshold() {
            return narcoticThreshold;
        }

        public Rarity getRarity() {
            return rarity;
        }

        public double getMaxHealth() {
            return health;
        }

        public double getArmor() {
            return armor;
        }

        public double getMovementSpeed() {
            return movementSpeed;
        }

        public int getLevel() {
            return level;
        }

        public String getName() {
            return name;
        }

        /**
         * just call it for Debugging as it will print everything relevant in the console
         */
        public void print(){
            DinosExpansion.LOGGER.debug("Level: " + this.level + " | Rarity: " + rarity.name() + " | Gender: " + gender.name());
        }
    }
}
