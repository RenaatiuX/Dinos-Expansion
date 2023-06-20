package com.rena.dinosexpansion.common.entity.villagers.caveman;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class Caveman extends AbstractVillagerEntity {

    public static final double RADIUS_BOSSFIGHT = 10d;
    public static final int BOSSFIGHT_START_COOLDOWN = 100;

    public static final DataParameter<Boolean> BOSS = EntityDataManager.createKey(Caveman.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(Caveman.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> TYPE = EntityDataManager.createKey(Caveman.class, DataSerializers.VARINT);

    public static AttributeModifierMap createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 40).createMutableAttribute(Attributes.ATTACK_DAMAGE, 11).createMutableAttribute(Attributes.ARMOR, 5).create();
    }

    protected long lastRestock = 0;
    protected int restocksToday;
    protected boolean leveledUp;
    protected int timeUntilReset;
    protected int xp;
    protected PlayerEntity previousCustomer;
    protected Tribe tribe, wanderTribe;
    protected Caveman rivalBoss;
    protected boolean tookPlaceInBossfight = false, takingPlaceInBossfight = false;
    protected String tribeName;

    public Caveman(EntityType<? extends AbstractVillagerEntity> type, World worldIn) {
        super(type, worldIn);
        ((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
        this.getNavigator().setCanSwim(true);
        this.setCanPickUpLoot(true);
    }

    public Caveman(World world) {
        this(EntityInit.CAVEMAN.get(), world);
    }

    public void tick() {
        super.tick();
        if (!world.isRemote()) {
            if (timeUntilReset > 0) {
                timeUntilReset--;
                if (timeUntilReset <= 0) {
                    if (leveledUp) {
                        levelUp();
                        leveledUp = false;
                    }
                    restock();
                }
            }
        }
        if (this.getShakeHeadTicks() > 0) {
            this.setShakeHeadTicks(this.getShakeHeadTicks() - 1);
        }
    }


    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        ItemStack itemstack = playerIn.getHeldItem(hand);
        if (!this.world.isRemote() && itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.hasCustomer() && !this.isChild()) {
            if (hand == Hand.MAIN_HAND) {
                playerIn.addStat(Stats.TALKED_TO_VILLAGER);
            }
            System.out.println(isBoss() + "| name: " + this.tribe.getName());
            if (!this.getOffers().isEmpty() && this.tribe.canTrade(playerIn)) {
                if (!this.world.isRemote) {
                    this.setCustomer(playerIn);
                    this.openMerchantContainer(playerIn, this.getDisplayName(), getLevel());
                    System.out.println("should open");
                }

            } else if (!this.tribe.canTrade(playerIn)) {
                shakeHead();
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else {
            return super.getEntityInteractionResult(playerIn, hand);
        }
    }


    protected void searchForTribeBossOrBeOne() {
        if (!this.world.isRemote()) {
            List<Entity> entities = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().grow(1000), e -> e instanceof Caveman);
            for (Entity e : entities) {
                Caveman c = (Caveman) e;
                if (c.isBoss() && c.tribe != null) {
                    this.tribe = c.tribe;
                    this.dataManager.set(TYPE, c.dataManager.get(TYPE));
                    c.tribe.addCaveman(this);
                    return;
                }
            }
            setBoss(true);
            int index = Tribe.TYPES.size() > 0 ? this.rand.nextInt(Tribe.TYPES.size()) : -1;
            if (index >= 0) {
                this.dataManager.set(TYPE, index);
                Tribe.TribeType type = Tribe.TYPES.get(index);
                String name = null;
                TribeSaveData data = TribeSaveData.getData((ServerWorld) this.world);
                for (int i = 0; i < 1000; i++) {
                    String testName = TribeNameGenerator.generateTribeName();
                    if (!data.hasTribe(testName)) {
                        name = testName;
                        break;
                    }
                }
                if (name == null) {
                    this.remove();
                    DinosExpansion.LOGGER.warn("didnt find a valid tribe name, all tribe names where occupied");
                } else {
                    this.tribe = new Tribe(type, name);
                    TribeSaveData.addTribe(this.tribe, (ServerWorld) world);
                    this.tribe.addCaveman(this);
                }
            } else {
                this.remove();
                throw new IllegalStateException("there arent any tribe types that can be used to create a tribe");
            }
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0f, true));
        this.goalSelector.addGoal(2, new StartBossfightGoal(this, RADIUS_BOSSFIGHT));
        this.goalSelector.addGoal(2, new FightBossfightGoal(this));
        this.goalSelector.addGoal(3, new BossWanderToOtherTribe(this, .7d));
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, PlayerEntity.class, 5, true, false, e -> e instanceof PlayerEntity && tribe.isHostile((PlayerEntity) e)){
            @Override
            public boolean shouldExecute() {
                return !isInBossfight() &&super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(3, new NearestAttackableTargetGoal(this, Caveman.class, 10, true, false, e -> e instanceof Caveman && ((Caveman) e).tribe != this.tribe){
            @Override
            public boolean shouldExecute() {
                return !isInBossfight() && super.shouldExecute();
            }
        });

        this.goalSelector.addGoal(4, new CavemanWalkToBossGoal(this, 50d, .5d));
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        if (nbt.contains("boss"))
            this.dataManager.set(BOSS, nbt.getBoolean("boss"));
        if (nbt.contains("level"))
            this.dataManager.set(LEVEL, nbt.getInt("level"));
        if (nbt.contains("type"))
            this.dataManager.set(TYPE, nbt.getInt("type"));
        if (nbt.contains("tribe")) {
            loadTribe(nbt.getString("tribe"));
        }
        if (nbt.contains("wanderTribe")) {
            loadTribe(nbt.getString("wanderTribe"));
        }
        this.tookPlaceInBossfight = nbt.getBoolean("tookPlaceInBossfight");
        this.takingPlaceInBossfight = nbt.getBoolean("takingPlaceInBossfight");
        this.lastRestock = nbt.getLong("lastRestock");
        this.restocksToday = nbt.getInt("restocksToday");
        this.xp = nbt.getInt("cavemanxp");
        this.leveledUp = nbt.getBoolean("hasLeveledUp");
        this.timeUntilReset = nbt.getInt("timeUntilReset");
        if (!world.isRemote() && this.tribe == null) {
            searchForTribeBossOrBeOne();
        }
    }

    /**
     * only on the server
     */
    protected void loadTribe(String name) {
        if (!this.world.isRemote) {
            TribeSaveData data = TribeSaveData.getData((ServerWorld) this.world);
            System.out.println(data.getSize());
            Tribe t = data.getTribeWithName(name);
            if (t != null) {
                t.addCaveman(this);
                this.tribe = t;
            } else {
                DinosExpansion.LOGGER.warn("cant find a tribe with name: " + name + " when loading entity: " + this.toString());
            }
        }
    }


    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putBoolean("boss", isBoss());
        nbt.putInt("level", getLevel());
        nbt.putInt("type", this.dataManager.get(TYPE));
        if (this.tribe != null && !this.world.isRemote)
            nbt.putString("tribe", this.tribe.getName());
        if (this.wanderTribe != null && !this.world.isRemote)
        nbt.putString("wanderTribe", this.wanderTribe.getName());
        nbt.putLong("lastRestock", this.lastRestock);
        nbt.putInt("restocksToday", this.restocksToday);
        nbt.putInt("cavemanxp", this.xp);
        nbt.putBoolean("hasLeveledUp", this.leveledUp);
        nbt.putInt("timeUntilReset", this.timeUntilReset);
        nbt.putBoolean("takingPlaceInBossfight", this.takingPlaceInBossfight);
        nbt.putBoolean("tookPlaceInBossfight", this.tookPlaceInBossfight);

    }


    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(BOSS, false);
        this.dataManager.register(LEVEL, 1);
        this.dataManager.register(TYPE, -1);
    }

    /**
     * only valid on the server side, on the client side will return null
     */
    public Tribe getTribe() {
        return tribe;
    }

    /**
     * this only works when this is a boss, so it fights against the other boss
     */
    public void startBossfight(Caveman otherBoss) {
        if (this.isBoss() && otherBoss != this) {
            this.rivalBoss = otherBoss;
        }
    }

    /**
     * only works on client side
     *
     * @return
     */
    public boolean isInBossfight() {
        return getTribe() != null && getTribe().isBossFight();
    }

    /**
     * @return the rival boss, will return null if there isnt a rival Boss or on client side
     */
    public Caveman getRivalBoss() {
        return rivalBoss;
    }

    public boolean isViewerBossFight() {
        if (isInBossfight()) {
            return !this.isBoss();
        }
        return false;
    }

    public boolean isFighterBossFight() {
        if (isInBossfight()) {
            return this.isBoss();
        }
        return false;
    }

    private boolean canLevelUp() {
        int i = this.dataManager.get(LEVEL);
        return VillagerData.canLevelUp(i) && this.xp >= VillagerData.getExperienceNext(i);
    }

    private void levelUp() {
        this.dataManager.set(LEVEL, this.dataManager.get(LEVEL) + 1);
        this.populateTradeData();
    }

    @Override
    protected void onVillagerTrade(MerchantOffer offer) {
        int i = 3 + this.rand.nextInt(4);
        this.xp += offer.getGivenExp();
        this.previousCustomer = this.getCustomer();
        if (this.canLevelUp()) {
            this.timeUntilReset = 40;
            this.leveledUp = true;
            i += 5;
        }

        if (offer.getDoesRewardExp()) {
            this.world.addEntity(new ExperienceOrbEntity(this.world, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), i));
        }
    }

    protected void shakeHead() {
        this.setShakeHeadTicks(40);
        if (!this.world.isRemote()) {
            this.playSound(SoundEvents.ENTITY_VILLAGER_NO, this.getSoundVolume(), this.getSoundPitch());
        }

    }

    public void restock() {
        this.calculateDemandOfOffers();

        for (MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetUses();
        }

        this.lastRestock = this.world.getGameTime();
        ++this.restocksToday;
    }

    private void calculateDemandOfOffers() {
        for (MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.calculateDemand();
        }

    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();
        if (tribe != null) {
            this.tribe.removeCaveman(this);
        }
    }

    /**
     * client synced
     */
    public boolean hasValidType() {
        return this.dataManager.get(TYPE) >= 0 && this.dataManager.get(TYPE) < Tribe.TYPES.size();
    }

    /**
     * client synced
     */
    public Tribe.TribeType getTribeType() {
        if (!hasValidType())
            return null;
        return Tribe.TYPES.get(this.dataManager.get(TYPE));
    }

    @Override
    protected void populateTradeData() {
        VillagerTrades.ITrade[] trades = isBoss() ? this.tribe.getType().getBossTrades().get(getLevel()) : this.tribe.getType().getNormalTrades().get(getLevel());
        if (trades != null) {
            MerchantOffers merchantoffers = this.getOffers();
            this.addTrades(merchantoffers, trades, 1);
        }
    }

    public int getLevel() {
        return this.dataManager.get(LEVEL);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    /**
     * only call from tribes, if u call this from somewhere else this can mess things up really badly
     */
    public void setBoss(boolean boss) {
        if (!isBoss() && boss) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(80);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(19);
            this.getAttribute(Attributes.ARMOR).setBaseValue(12);
            this.setHealth(80);
        }else if (isBoss() && !boss){
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(11);
            this.getAttribute(Attributes.ARMOR).setBaseValue(5);
            this.setHealth(40);
        }
        this.dataManager.set(BOSS, boss);
    }

    public boolean isBoss() {
        return this.dataManager.get(BOSS);
    }

}
