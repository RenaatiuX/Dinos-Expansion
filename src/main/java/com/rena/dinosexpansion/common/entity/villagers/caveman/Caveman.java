package com.rena.dinosexpansion.common.entity.villagers.caveman;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.villagers.Hermit;
import com.rena.dinosexpansion.core.init.ModVillagerTrades;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.w3c.dom.Attr;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Caveman extends AbstractVillagerEntity {

    public static final DataParameter<Boolean> BOSS = EntityDataManager.createKey(Caveman.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(Hermit.class, DataSerializers.VARINT);

    public static AttributeModifierMap createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 40).createMutableAttribute(Attributes.ATTACK_DAMAGE, 11).createMutableAttribute(Attributes.ARMOR, 5).create();
    }

    protected long lastRestock = 0;
    protected int restocksToday;
    protected boolean leveledUp;
    protected int timeUntilReset;
    protected int xp;
    protected PlayerEntity previousCustomer;
    protected Tribe tribe;

    public Caveman(EntityType<? extends AbstractVillagerEntity> type, World worldIn) {
        super(type, worldIn);
        ((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
        this.getNavigator().setCanSwim(true);
        this.setCanPickUpLoot(true);
        searchForTribeBossOrBeOne();

        if (isBoss()){
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(80);
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(19);
            this.getAttribute(Attributes.ARMOR).setBaseValue(12);
        }
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


    protected void searchForTribeBossOrBeOne() {
        if (!this.world.isRemote()) {
            List<Entity> entities = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().grow(100), e -> e instanceof Caveman);
            for (Entity e : entities) {
                Caveman c = (Caveman) e;
                if (c.isBoss()) {
                    this.tribe = c.tribe;
                    this.tribe.addCaveman(this);
                    return;
                }
            }
            this.dataManager.set(BOSS, true);
            int index = Tribe.TYPES.size() > 0 ? this.rand.nextInt(Tribe.TYPES.size()) : -1;
            if (index > 0) {
                Tribe.TribeType type = Tribe.TYPES.get(index);
                String name = null;
                TribeSaveData data = TribeSaveData.getData((ServerWorld) this.world);
                for (int i = 0; i < 1000; i++) {
                    String testName = TribeNameGenerator.generateTribeName();
                    if (!data.hasTribe(testName)){
                        name = testName;
                        break;
                    }
                }
                if (name == null){
                    this.remove();
                    DinosExpansion.LOGGER.warn("didnt find a valid tribe name, all tribe names where occupied");
                }else {
                    this.tribe = new Tribe(type, name);
                    data.addTribe(this.tribe);
                    this.tribe.addCaveman(this);
                }
            } else
                throw new IllegalStateException("there arent any tribe types that can be used to create a tribe");
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(BOSS, false);
        this.dataManager.register(LEVEL, 1);
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

    public boolean isBoss() {
        return this.dataManager.get(BOSS);
    }
}
