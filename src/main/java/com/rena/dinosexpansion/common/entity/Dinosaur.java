package com.rena.dinosexpansion.common.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.BitUtils;
import com.rena.dinosexpansion.common.config.DinosExpansionConfig;
import com.rena.dinosexpansion.common.container.DinoInventoryContainer;
import com.rena.dinosexpansion.common.container.OrderContainer;
import com.rena.dinosexpansion.common.container.TamingContainer;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.common.entity.projectile.INarcoticProjectile;
import com.rena.dinosexpansion.common.item.armor.DinosaurArmorItem;
import com.rena.dinosexpansion.common.item.util.INarcotic;
import com.rena.dinosexpansion.common.util.NbtUtils;
import com.rena.dinosexpansion.common.util.enums.AttackOrder;
import com.rena.dinosexpansion.common.util.enums.MoveOrder;
import com.rena.dinosexpansion.core.init.CriteriaTriggerInit;
import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
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
    private long lastAttackTime = 0L;
    private long attackCooldown = 40L;

    public static ResourceLocation getLootTableForRarity(ResourceLocation entityId, Rarity rarity) {
        return DinosExpansion.entityLoot(entityId.getPath() + "_" + rarity.name().toLowerCase(Locale.ROOT));
    }

    /**
     * use this to generate a random level as it already contains all relevant config values
     *
     * @param minLevel the minimum Level the Dino should have when it spawns(inclusive)
     * @param maxLevel the maximum level the Dino can have when it spawns(inclusive)
     * @return the randomly distributed value between max and min
     */
    public static final int generateLevelWithinBounds(int minLevel, int maxLevel) {
        int max = Math.min(maxLevel + DinosExpansionConfig.LEVEL_OFFSET.get(), DinosExpansionConfig.MAX_LEVEL.get() + DinosExpansionConfig.LEVEL_OFFSET.get());
        int min = Math.max(minLevel + DinosExpansionConfig.LEVEL_OFFSET.get(), 0);
        int levl = MathHelper.nextInt(new Random(), min, max);
        return levl;
    }

    /**
     * this opens the taming gui for the given Dinosaur
     *
     * @param dino   the Dino which taming gui should be opened
     * @param player the player that opens this gui
     */
    public static final void openTamingGui(Dinosaur dino, ServerPlayerEntity player) {
        SimpleNamedContainerProvider provider = new SimpleNamedContainerProvider((id, inv, p) -> new TamingContainer(id, inv, dino), new TranslationTextComponent(DinosExpansion.MOD_ID + ".taming_gui.title", dino.getType().getName()));
        NetworkHooks.openGui(player, provider, buf -> buf.writeVarInt(dino.getEntityId()));
    }

    /**
     * opens the dino inventory with the set of armor slots, this is just the medium variant so only medium slots will be displayed
     * this will also support a chest with up to 27 slots
     */
    public static void openMediumDinoGui(Dinosaur dino, ServerPlayerEntity player) {
        SimpleNamedContainerProvider provider = new SimpleNamedContainerProvider((id, inv, p) -> new DinoInventoryContainer(id, inv, dino), new TranslationTextComponent(DinosExpansion.MOD_ID, ".inventory.title", dino.getType().getName()));
        NetworkHooks.openGui(player, provider, buf -> buf.writeVarInt(dino.getEntityId()));
    }

    public static final AttributeModifier RUNNING = new AttributeModifier(DinosExpansion.MOD_ID + ".run", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> XP = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> BOOLS = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> ARMOR = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> RARITY = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> GENDER = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> NARCOTIC_VALUE = EntityDataManager.createKey(Dinosaur.class, DataSerializers.VARINT);
    public static final DataParameter<Byte> TAMING_PROGRESS = EntityDataManager.createKey(Dinosaur.class, DataSerializers.BYTE);
    public static final DataParameter<Float> HUNGER_VALUE = EntityDataManager.createKey(Dinosaur.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> TAMING_EFFICIENCY = EntityDataManager.createKey(Dinosaur.class, DataSerializers.FLOAT);
    public static final DataParameter<Optional<UUID>> KNOCKED_OUT = EntityDataManager.createKey(Dinosaur.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    public static final DataParameter<Byte> MOVE_ORDER = EntityDataManager.createKey(Dinosaur.class, DataSerializers.BYTE);
    public static final DataParameter<Byte> ATTACK_ORDER = EntityDataManager.createKey(Dinosaur.class, DataSerializers.BYTE);


    protected int maxNarcotic, maxHunger;
    protected float feedHungerTaming = 0.0f;
    protected DinosaurInfo info;
    protected ItemStackHandler inventory = new ItemStackHandler(Math.min(this.getArmorPieces().length, DinosaurArmorSlotType.values().length)) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            Dinosaur.this.onContentsChanged(slot);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    public Dinosaur(EntityType<? extends TameableEntity> type, World world, DinosaurInfo info, int minLevel, int maxLevel) {
        this(type, world, info, generateLevelWithinBounds(minLevel, maxLevel));
    }

    public Dinosaur(EntityType<? extends TameableEntity> type, World world, DinosaurInfo info, int level) {
        super(type, world);
        this.maxNarcotic = (int) ((float) info.maxNarcotic * (float) DinosExpansionConfig.NARCOTIC_NEEDED_PERCENT.get() / 100f);
        this.maxHunger = info.maxHunger;
        this.dataManager.register(RARITY, getInitialRarity().ordinal());
        this.dataManager.register(GENDER, getInitialGender().ordinal());
        this.dataManager.register(LEVEL, level);
        setHungerValue(maxHunger);
        Rarity rarity = getRarity();
        this.info = info;
        //don't do that at home kids
        //normally goals should only be added in the registerGoals method
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
        this.dataManager.register(XP, 0);
        this.dataManager.register(BOOLS, 0);
        this.dataManager.register(KNOCKED_OUT, Optional.empty());
        this.dataManager.register(NARCOTIC_VALUE, 0);
        this.dataManager.register(HUNGER_VALUE, 0f);
        this.dataManager.register(TAMING_PROGRESS, (byte) 0);
        this.dataManager.register(MOVE_ORDER, (byte) MoveOrder.IDLE.ordinal());
        this.dataManager.register(ATTACK_ORDER, (byte) AttackOrder.NEUTRAL.ordinal());
        this.dataManager.register(TAMING_EFFICIENCY, 1f);
        this.dataManager.register(ARMOR, 0);
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        NbtUtils.setIfExists(nbt, "tamingEfficiency", CompoundNBT::getFloat, f -> this.dataManager.set(TAMING_EFFICIENCY, f));
        NbtUtils.setIfExists(nbt, "level", CompoundNBT::getInt, f -> this.dataManager.set(LEVEL, f));
        NbtUtils.setIfExists(nbt, "xp", CompoundNBT::getInt, f -> this.dataManager.set(XP, f));
        NbtUtils.setIfExists(nbt, "bools", CompoundNBT::getInt, f -> this.dataManager.set(BOOLS, f));
        NbtUtils.setIfExists(nbt, "rarity", CompoundNBT::getInt, f -> this.dataManager.set(RARITY, f));
        NbtUtils.setIfExists(nbt, "gender", CompoundNBT::getInt, f -> this.dataManager.set(GENDER, f));
        NbtUtils.setIfExists(nbt, "narcos", CompoundNBT::getInt, f -> this.dataManager.set(NARCOTIC_VALUE, f));
        NbtUtils.setIfExists(nbt, "hunger", CompoundNBT::getFloat, f -> this.dataManager.set(HUNGER_VALUE, f));
        NbtUtils.setIfExists(nbt, "move_order", CompoundNBT::getByte, f -> this.dataManager.set(MOVE_ORDER, f));
        NbtUtils.setIfExists(nbt, "attack_order", CompoundNBT::getByte, f -> this.dataManager.set(ATTACK_ORDER, f));
        NbtUtils.setIfExists(nbt, "taming_progress", CompoundNBT::getByte, f -> this.dataManager.set(TAMING_PROGRESS, f));
        NbtUtils.setIfExists(nbt, "knocked_out_player", CompoundNBT::getUniqueId, f -> this.dataManager.set(KNOCKED_OUT, Optional.of(f)));
        NbtUtils.setIfExists(nbt, "hasArmorBools", CompoundNBT::getInt, f -> this.dataManager.set(ARMOR, f));
        NbtUtils.setIfExists(nbt, "maxNarcotic", CompoundNBT::getInt, f -> this.maxNarcotic = f);
        NbtUtils.setIfExists(nbt, "maxHunger", CompoundNBT::getInt, f -> this.maxHunger = f);
        NbtUtils.setIfExists(nbt, "feedHungerTaming", CompoundNBT::getFloat, f -> this.feedHungerTaming = f);
        this.inventory.deserializeNBT(nbt.getCompound("inventory"));

    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putFloat("tamingEfficiency", getTamingEfficiency());
        nbt.putInt("level", this.dataManager.get(LEVEL));
        nbt.putInt("xp", this.dataManager.get(XP));
        nbt.putInt("bools", this.dataManager.get(BOOLS));
        nbt.putInt("rarity", this.dataManager.get(RARITY));
        nbt.putInt("gender", this.dataManager.get(GENDER));
        nbt.putFloat("hunger", this.getHungerValue());
        nbt.putByte("taming_progress", this.dataManager.get(TAMING_PROGRESS));
        nbt.putByte("move_order", this.dataManager.get(MOVE_ORDER));
        nbt.putByte("attack_order", this.dataManager.get(ATTACK_ORDER));
        nbt.putInt("narcos", this.getNarcoticValue());
        nbt.putInt("hasArmorBools", this.dataManager.get(ARMOR));
        nbt.putInt("maxNarcotic", this.maxNarcotic);
        nbt.putInt("maxHunger", this.maxHunger);
        nbt.put("inventory", this.inventory.serializeNBT());
        nbt.putFloat("feedHungerTaming", this.feedHungerTaming);
        this.dataManager.get(KNOCKED_OUT).ifPresent(uuid -> nbt.putUniqueId("knocked_out_player", uuid));
    }

    protected void increaseLevel() {
        this.dataManager.set(LEVEL, Math.min(this.dataManager.get(LEVEL) + 1, DinosExpansionConfig.MAX_LEVEL.get()));
        if (this.dataManager.get(LEVEL) < DinosExpansionConfig.MAX_LEVEL.get()) {
            double health = this.getAttribute(Attributes.MAX_HEALTH).getBaseValue();
            double attackDamage = this.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health + DinosExpansionConfig.HEALTH_PER_LEVEL.get());
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(attackDamage + DinosExpansionConfig.ATTACK_DAMAGE_PER_LEVEL.get());
            getAttribute(Attributes.ARMOR).setBaseValue(getAttribute(Attributes.ARMOR).getBaseValue() + DinosExpansionConfig.ARMOR_PER_LEVEL.get());
        }
    }

    public void increaseXp() {
        this.dataManager.set(XP, this.dataManager.get(XP) + 1);
        if (this.dataManager.get(XP) >= getMaxXp()) {
            this.dataManager.set(XP, 0);
            increaseLevel();
        }
    }

    /**
     * important:<b> just use every ArmorSlotType once, otherwise stuff will break</b>
     *
     * @return the Slot types this dino is supporting, normally it is just the Chest Piece
     */
    public DinosaurArmorSlotType[] getArmorPieces() {
        return new DinosaurArmorSlotType[]{DinosaurArmorSlotType.SADDLE};
    }

    public void setArmor(DinosaurArmorSlotType slot, ItemStack stack) {
        List<DinosaurArmorSlotType> slots = Arrays.asList(getArmorPieces());
        if (slots.contains(slot)) {
            int index = slots.indexOf(slot);
            this.inventory.setStackInSlot(index, stack);
        }
    }

    /**
     * @param slot checks whether this dino can hold this Armor slot
     * @return
     */
    public boolean supportsSlot(DinosaurArmorSlotType slot) {
        List<DinosaurArmorSlotType> slots = Arrays.asList(getArmorPieces());
        return slots.contains(slot);
    }

    /**
     * @param slot
     * @return the item which is corresponded with that slot, if {@link Dinosaur#supportsSlot(DinosaurArmorSlotType)} returns false this ,method will return {@link ItemStack#EMPTY}
     */
    public ItemStack getFromSlot(DinosaurArmorSlotType slot) {
        List<DinosaurArmorSlotType> slots = Arrays.asList(getArmorPieces());
        if (slots.contains(slot)) {
            int index = slots.indexOf(slot);
            return this.inventory.getStackInSlot(index);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        if (!world.isRemote() && hand == Hand.MAIN_HAND) {
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem() instanceof INarcotic && this.isKnockout()) {
                addNarcoticValue(((INarcotic) stack.getItem()).getNarcoticValue(), player);
                if (!player.isCreative())
                    stack.shrink(1);
                return ActionResultType.SUCCESS;
            }
            if ((DinosExpansionConfig.CAN_STEAL_DINOS.get() || !isTamed()) && canBeTamed() && isKnockedOutBy(player) && player.getHeldItem(hand).isEmpty()) {
                openTamingGui(this, (ServerPlayerEntity) player);
                return ActionResultType.SUCCESS;
            }
            if (player instanceof ServerPlayerEntity && isOwner(player) && player.getHeldItem(hand).getItem() == getOrderItem()) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                //sends the packet to the client where it will get executed and then opens the screen
                NetworkHooks.openGui(serverPlayer, new SimpleNamedContainerProvider((id, inv, p) -> new OrderContainer(id, inv, Dinosaur.this),
                        new TranslationTextComponent(DinosExpansion.MOD_ID + ".order_screen.title", this.getType().getName())), buf -> buf.writeVarInt(this.getEntityId()));
                return ActionResultType.SUCCESS;
            }
            if (canBeTamed() && (isOwner(player) || isKnockedOutBy(player)) && canEat(stack)) {
                this.addHungerValue(stack, player);
                if (!player.isCreative())
                    stack.shrink(1);
                return ActionResultType.SUCCESS;
            }
        }
        return super.applyPlayerInteraction(player, vec, hand);
    }

    public boolean canEat(ItemStack stack) {
        return this.isFood(stack);
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

    public void addMaxNarcotic(PlayerInventory inv) {
        //TODO
    }

    /**
     * searches threw the inventory of the player and decides greedy which food to feed
     */
    public void addMaxHunger(PlayerInventory inv, PlayerEntity player) {
        PriorityQueue<ItemStack> playerItems = new PriorityQueue<>(Comparator.comparing(stack -> stack.isFood() ? -1 : stack.getItem().getFood().getSaturation()));
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            playerItems.add(inv.getStackInSlot(i));
        }
        while (!playerItems.isEmpty()) {
            ItemStack currentStack = playerItems.poll();
            if (!currentStack.isFood() || (getHungerValue() + currentStack.getItem().getFood().getSaturation() > this.getMaxHunger()))
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
        recalculateSize();
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
            if (getNarcoticValue() <= this.info.narcoticThreshold && isKnockout())
                setKnockout(false);
            reduceHunger();
            if (getTamingProgress() >= 100) {
                onKnockoutTaming();
            }
        }
    }

    public void setTamingEfficiency(float efficeny) {
        this.dataManager.set(TAMING_EFFICIENCY, MathHelper.clamp(efficeny, 0, 1));
    }

    public void onKnockoutTaming() {
        if (this.dataManager.get(KNOCKED_OUT).isPresent()) {
            PlayerEntity player = this.world.getPlayerByUuid(this.dataManager.get(KNOCKED_OUT).get());
            setTamedBy(player);
            setKnockout(false);
            setTamingProgress((byte) 0);
            int additionalLevels = (int) (((float) this.getLevel()) / getTamingEfficiency());
            for (int i = 0; i < additionalLevels; i++) {
                this.increaseLevel();
            }
            this.dataManager.set(TAMING_EFFICIENCY, 1f);
            this.feedHungerTaming = 0;
        } else
            throw new IllegalStateException("dinosaur got knocked out by no Player, what went wrong here?");
    }

    public void setKnockedOutBy(LivingEntity player) {
        this.setKnockout(true);
        this.dataManager.set(KNOCKED_OUT, Optional.of(player.getUniqueID()));
        this.getPassengers().forEach(Entity::stopRiding);
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        return getPassengers().isEmpty() ? null : getPassengers().get(0);
    }

    public boolean isRunning() {
        return isBeingRidden() || this.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(RUNNING);
    }

    public void setKnockout(boolean knockout) {
        if (isKnockout() == knockout)
            return;
        int bools = this.dataManager.get(BOOLS);
        this.dataManager.set(BOOLS, BitUtils.setBit(1, bools, knockout));
        if (knockout) {
            this.navigator.clearPath();
            setAttackTarget(null);
        }
        recalculateSize();
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
        return hasArmor(DinosaurArmorSlotType.SADDLE);
    }

    /**
     * client synced
     */
    protected void setSaddle(boolean saddle) {
        if (saddle == hasSaddle())
            return;
        setArmor(DinosaurArmorSlotType.SADDLE, saddle);
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
        this.dataManager.set(HUNGER_VALUE, MathHelper.clamp(value, 0, maxHunger));
    }

    /**
     * client synced
     */
    public int getMaxHunger() {
        return maxHunger;
    }

    /**
     * client synced
     */
    public void addHungerValue(ItemStack food, LivingEntity feeder) {
        if (!food.isFood() || !isFood(food) || (!isOwner(feeder) && !isKnockedOutBy(feeder)))
            return;
        if (feeder instanceof ServerPlayerEntity && isOwner(feeder))
            CriteriaTriggerInit.FEED_DINOSAUR.trigger((ServerPlayerEntity) feeder, food, this);
        float saturation = food.getItem().getFood().getHealing();
        float maxSaturation = MathHelper.clamp(saturation, 0, maxHunger - getHungerValue());
        if (maxSaturation == saturation) {
            setHungerValue(getHungerValue() + maxSaturation);
            if (isKnockout()) {
                this.feedHungerTaming += saturation;
                int progress = (int) (saturation * 100f / (float) this.info.getMaxHunger());
                addTamingProgress((byte) MathHelper.clamp(progress, 0, 100));
            }
        }
    }

    /**
     * client synced
     */
    public void addHungerValue(float add) {
        this.dataManager.set(HUNGER_VALUE, Math.max(0, Math.min(getHungerValue() + add, maxHunger)));
    }

    /**
     * client synced
     */
    protected void addNarcoticValue(int add, LivingEntity cause) {
        this.dataManager.set(NARCOTIC_VALUE, Math.max(0, Math.min(getNarcoticValue() + add, this.maxNarcotic)));
        if (getNarcoticValue() >= this.maxNarcotic) {
            this.setKnockedOutBy(cause);
        } else if (getNarcoticValue() <= this.info.narcoticThreshold)
            this.setKnockout(false);
    }

    public boolean isKnockedOutBy(LivingEntity living) {
        if (!isKnockout())
            return false;
        Optional<UUID> knockOutBy = this.dataManager.get(KNOCKED_OUT);
        if (knockOutBy.isPresent()) {
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
                if (isKnockout()) {
                    this.dataManager.set(TAMING_EFFICIENCY, getTamingEfficiency() * .8f);
                }
                if (canBeKnockedOut() && source.getImmediateSource() instanceof INarcoticProjectile && source.getTrueSource() instanceof LivingEntity) {
                    this.addNarcoticValue(((INarcoticProjectile) source.getImmediateSource()).getNarcoticValue(), (LivingEntity) source.getTrueSource());
                }
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

    public boolean canBeKnockedOut() {
        return true;
    }

    /**
     * client synced
     *
     * @return the taming efficiency between 0.0 and 1.0
     */
    public float getTamingEfficiency() {
        return this.dataManager.get(TAMING_EFFICIENCY);
    }

    /**
     * this is the inventory where the armor and saddle will be in
     *
     * @return
     */
    public ItemStackHandler getInventory() {
        return inventory;
    }

    public int getSlotIndex(DinosaurArmorSlotType slotType) {
        List<DinosaurArmorSlotType> types = Arrays.asList(this.getArmorPieces());
        if (types.contains(slotType)) {
            return types.indexOf(slotType);
        }
        return -1;
    }

    /**
     * called when in the inventory of saddle and armor are changed
     *
     * @param slot - the slot where the change happened
     */
    protected void onContentsChanged(int slot) {
        if (!this.world.isRemote) {
            this.dataManager.set(ARMOR, BitUtils.setBit(slot, this.dataManager.get(ARMOR), !this.inventory.getStackInSlot(slot).isEmpty()));
        }
        for (DinosaurArmorSlotType slotType : this.getArmorPieces()) {
            ItemStack stack = this.getFromSlot(slotType);
            if (!stack.isEmpty() && stack.getItem() instanceof DinosaurArmorItem){
                DinosaurArmorItem item = (DinosaurArmorItem) stack.getItem();
                item.getDinoAttributes(slotType, stack).forEach((attribute, modifier) -> {
                    ModifiableAttributeInstance instance = getAttribute(attribute);
                    if (!hasArmor(slotType) && instance.hasModifier(modifier)){
                        instance.removeModifier(modifier);
                    }else if (hasArmor(slotType) && !instance.hasModifier(modifier)){
                        instance.applyNonPersistentModifier(modifier);
                    }
                });
            }
        }
    }

    public void setArmor(DinosaurArmorSlotType slot, boolean hasIt) {
        if (hasIt != hasArmor(slot)) {
            this.dataManager.set(ARMOR, BitUtils.setBit(getSlotIndex(slot), this.dataManager.get(ARMOR), hasIt));
        }
    }

    public boolean hasArmor(DinosaurArmorSlotType slot) {
        return BitUtils.getBit(getSlotIndex(slot), this.dataManager.get(ARMOR)) > 0;
    }


    public boolean canBeTamed() {
        return true;
    }

    /**
     * only use when knocked out and should be tamed
     * be careful this isnt checked so when u try to reduce it below 0 it will just clamp at 0
     * its also clamped to maxHunger in case u want to add Hunger threw this method
     *
     * @param reduce the amount u want it to reduce
     */
    public void reduceTamingFeed(float reduce) {
        this.feedHungerTaming = MathHelper.clamp(this.feedHungerTaming - reduce, 0, this.maxHunger);
    }

    /**
     * @param reduce the amount u want to reduce the hunger during taming
     * @return if u can reduce that amount of tamingFeed from the hunger
     */
    public boolean canReduceTamingFeed(float reduce) {
        return reduce <= feedHungerTaming;
    }

    /**
     * @return whether the dino is knocked out or sleeps
     */
    public boolean isMovementDisabled() {
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

    public boolean isFood(ItemStack stack) {
        return ModTags.Items.KIBBLE.contains(stack.getItem()) || getFood().contains(stack.getItem());
    }

    public abstract List<Item> getFood();

    /**
     * client synced
     * cant exceed the maxNarcotic
     */
    protected void setNarcoticValue(int value) {
        this.dataManager.set(NARCOTIC_VALUE, MathHelper.clamp(value, 0, maxNarcotic));
    }

    /**
     * this is called at the constructor to define the initial rarity
     *
     * @return
     */
    protected abstract Rarity getInitialRarity();

    /**
     * this is called at the constructor to define its initial gender
     */
    protected abstract Gender getInitialGender();

    /**
     * this is called every tick this entity is alive and the narcotic value is greater than 0
     * this is just called on the server side
     *
     * @param narcoticValue the narcotic value > 0
     * @return the new narcotic value, can not be smaller then 0
     */
    protected abstract int reduceNarcotic(int narcoticValue);

    public int getMaxNarcotic() {
        return maxNarcotic;
    }

    public void forceAttack(LivingEntity target) {
        long currentTime = this.world.getGameTime();
        if (currentTime - lastAttackTime >= attackCooldown) {
            if (target != null && target.isAlive()) {
                lastAttackTime = currentTime;
                this.swingArm(Hand.MAIN_HAND);
                this.attackEntityAsMob(target);
            }
        }
    }

    /**
     * just server side
     * in here hunger is reduced when running and moving, just a little reduced when doing basically nothing
     */
    protected void reduceHunger() {
        if (this.prevPosX != this.getPosX() || this.prevPosY != this.getPosY() || this.prevPosZ != this.getPosZ()) {
            if (getRNG().nextDouble() <= 0.1)
                this.addHungerValue(-1);
        }
        //TODO change this back to something more realistic
        if (this.isKnockout() && this.getRNG().nextDouble() <= 0.5) {
            this.addHungerValue(-1);
        }
        if (getRNG().nextDouble() <= 0.001)
            this.addHungerValue(-1);
    }

    public byte getTamingProgress() {
        return this.dataManager.get(TAMING_PROGRESS);
    }

    public void setTamingProgress(byte progress) {
        this.dataManager.set(TAMING_PROGRESS, (byte) Math.max(0, Math.min(100, progress)));
    }

    public void addTamingProgress(byte toAdd) {
        setTamingProgress((byte) (getTamingProgress() + MathHelper.clamp(toAdd, 0, 100)));
    }

    /**
     * client synced
     */
    public void setMoveOrder(MoveOrder order) {
        if (Arrays.stream(allowedMoveOrders()).anyMatch(o -> o == order))
            this.dataManager.set(MOVE_ORDER, (byte) order.ordinal());
        recalculateSize();
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if (BOOLS.equals(key) || MOVE_ORDER.equals(key) || ATTACK_ORDER.equals(key))
            recalculateSize();
        super.notifyDataManagerChange(key);
    }

    /**
     * client synced
     */
    public void setAttackOrder(AttackOrder order) {
        if (Arrays.stream(allowedAttackOrders()).anyMatch(o -> o == order))
            this.dataManager.set(ATTACK_ORDER, (byte) order.ordinal());
        recalculateSize();
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        if (getMoveOrder() == MoveOrder.SIT)
            return getSitSize(poseIn);
        if (this.isSleeping()) {
            return getSleepSize(poseIn);
        }
        if (this.isKnockout())
            return getKnockoutSize(poseIn);
        return super.getSize(poseIn);
    }

    protected EntitySize getSitSize(Pose pose) {
        return super.getSize(pose);
    }

    protected EntitySize getSleepSize(Pose pose) {
        return super.getSize(pose);
    }

    protected EntitySize getKnockoutSize(Pose pose) {
        return super.getSize(pose);
    }

    /**
     * client synced
     */
    public MoveOrder getMoveOrder() {
        return MoveOrder.values()[this.dataManager.get(MOVE_ORDER)];
    }

    /**
     * client synced
     */
    public AttackOrder getAttackOrder() {
        return AttackOrder.values()[this.dataManager.get(ATTACK_ORDER)];
    }

    /**
     * this determines which orders are allowed on this dinosaur
     *
     * @return an array of all allowed orders
     */
    public AttackOrder[] allowedAttackOrders() {
        return AttackOrder.values();
    }

    /**
     * this determines which orders are allowed on this dinosaur
     *
     * @return an array of all allowed orders
     */
    public MoveOrder[] allowedMoveOrders() {
        return new MoveOrder[]{MoveOrder.WANDER, MoveOrder.SIT, MoveOrder.FOLLOW, MoveOrder.IDLE};
    }

    /**
     * this item is the item the player has to right-click on the dino in order to change orders
     * make sure it isn´t food that the dino can potentially eat
     */
    public Item getOrderItem() {
        return Items.STICK;
    }


    public enum Rarity {
        COMMON(new TranslationTextComponent("rarity." + DinosExpansion.MOD_ID + ".common"), 0, 0, 0, 0),
        UNCOMMON(new TranslationTextComponent("rarity." + DinosExpansion.MOD_ID + ".uncommon"), 4f, 1f, 0, 2),
        RARE(new TranslationTextComponent("rarity." + DinosExpansion.MOD_ID + ".rare"), 8, 2, 0, 4),
        EPIC(new TranslationTextComponent("rarity." + DinosExpansion.MOD_ID + ".epic"), 16, 4, 0, 8),
        LEGENDARY(new TranslationTextComponent("rarity." + DinosExpansion.MOD_ID + ".legendary"), 32, 16, 0, 16);

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

        public ITextComponent getDisplayName() {
            return name;
        }
    }

    public enum Gender {
        MALE(new TranslationTextComponent("gender." + DinosExpansion.MOD_ID + ".male")),
        FEMALE(new TranslationTextComponent("gender." + DinosExpansion.MOD_ID + ".female"));

        private final ITextComponent name;

        Gender(ITextComponent name) {
            this.name = name;
        }

        public ITextComponent getDisplayName() {
            return name;
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

        public SleepRhythmGoal.SleepRhythm getRhythm() {
            return rhythm;
        }

        /**
         * just call it for Debugging as it will print everything relevant in the console
         */
        public void print() {
            DinosExpansion.LOGGER.debug("Level: " + this.level + " | Rarity: " + rarity.name() + " | Gender: " + gender.name());
        }
    }
}
