package com.rena.dinosexpansion.common.entity;

import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class Hermit extends AbstractVillagerEntity {

    private int xp;
    private PlayerEntity previousCustomer;
    private boolean leveledUp;
    private int timeUntilReset;

    public static AttributeModifierMap createAttributes(){
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 40).create();
    }

    public static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(Hermit.class, DataSerializers.VARINT);

    private long lastRestock = 0;
    private int restocksToday;

    public Hermit(EntityType<? extends AbstractVillagerEntity> p_i50185_1_, World p_i50185_2_) {
        super(p_i50185_1_, p_i50185_2_);
        ((GroundPathNavigator)this.getNavigator()).setBreakDoors(true);
        this.getNavigator().setCanSwim(true);
        this.setCanPickUpLoot(true);
    }

    public Hermit(World world){
        this(EntityInit.HERMIT.get(), world);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(LEVEL, 1);
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        ItemStack itemstack = playerIn.getHeldItem(hand);
        if (itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.hasCustomer() && !this.isChild()) {
            if (hand == Hand.MAIN_HAND) {
                playerIn.addStat(Stats.TALKED_TO_VILLAGER);
            }
            if (!this.getOffers().isEmpty()) {
                if (!this.world.isRemote) {
                    this.setCustomer(playerIn);
                    this.openMerchantContainer(playerIn, this.getDisplayName(), 1);
                }

            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else {
            return super.getEntityInteractionResult(playerIn, hand);
        }
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

    private boolean canLevelUp() {
        int i = this.dataManager.get(LEVEL);
        return VillagerData.canLevelUp(i) && this.xp >= VillagerData.getExperienceNext(i);
    }

    private void levelUp() {
        this.dataManager.set(LEVEL, this.dataManager.get(LEVEL) + 1);
        this.populateTradeData();
    }

    @Override
    protected void populateTradeData() {

    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    @Override
    public boolean canDespawn(double p_213397_1_) {
        return false;
    }

    public void restock() {
        this.calculateDemandOfOffers();

        for(MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetUses();
        }

        this.lastRestock = this.world.getGameTime();
        ++this.restocksToday;
    }

    private void calculateDemandOfOffers() {
        for(MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.calculateDemand();
        }

    }

    public int getLevel(){
        return this.dataManager.get(LEVEL);
    }
}
