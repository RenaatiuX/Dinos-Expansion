package com.rena.dinosexpansion.common.entity.villagers;

import com.rena.dinosexpansion.common.world.util.CompoundNbtUtil;
import com.rena.dinosexpansion.core.init.EntityInit;
import com.rena.dinosexpansion.core.init.ModVillagerTrades;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
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
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.SleepingLocationCheckEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;
import java.util.Arrays;

public class Hermit extends AbstractVillagerEntity implements IRangedAttackMob {


    public static AttributeModifierMap createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 40).create();
    }

    public static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(Hermit.class, DataSerializers.VARINT);
    public static final DataParameter<Byte> REPUTATION = EntityDataManager.createKey(Hermit.class, DataSerializers.BYTE);

    private long lastRestock = 0;
    private int restocksToday;
    private int xp;
    private PlayerEntity previousCustomer;
    private boolean leveledUp;
    private int timeUntilReset;
    protected BlockPos bedPos;

    public Hermit(EntityType<? extends AbstractVillagerEntity> p_i50185_1_, World p_i50185_2_) {
        super(p_i50185_1_, p_i50185_2_);
        ((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
        this.getNavigator().setCanSwim(true);
        this.setCanPickUpLoot(true);
    }

    public Hermit(World world) {
        this(EntityInit.HERMIT.get(), world);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FindBedGoal(20));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(0, new SleepGoal());
        this.goalSelector.addGoal(0, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(1, new RestockGoal());
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1, 100, 7));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 10));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 0.5));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(LEVEL, 1);
        this.dataManager.register(REPUTATION, (byte) 100);
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        ItemStack itemstack = playerIn.getHeldItem(hand);
        if (itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.hasCustomer() && !this.isChild()) {
            if (hand == Hand.MAIN_HAND) {
                playerIn.addStat(Stats.TALKED_TO_VILLAGER);
            }
            System.out.println(getReputation());
            if (!this.getOffers().isEmpty() && getReputation() >= 10) {
                if (!this.world.isRemote) {
                    this.setCustomer(playerIn);
                    this.openMerchantContainer(playerIn, this.getDisplayName(), getLevel());
                    System.out.println("should open");
                }

            } else if (getReputation() < 10) {
                shakeHead();
                rejectPlayer(playerIn);
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else {
            return super.getEntityInteractionResult(playerIn, hand);
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

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putLong("lastRestock", this.lastRestock);
        compound.putInt("restocksToday", this.restocksToday);
        compound.putInt("hermitxp", this.xp);
        compound.putBoolean("hasLeveledUp", this.leveledUp);
        compound.putInt("timeUntilReset", this.timeUntilReset);
        compound.putInt("hermitLevel", getLevel());
        compound.putByte("hermitReputation", this.dataManager.get(REPUTATION));
        if (bedPos != null)
            compound.put("bedPos", CompoundNbtUtil.saveBlockPos(bedPos));
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.lastRestock = compound.getLong("lastRestock");
        this.restocksToday = compound.getInt("restocksToday");
        this.xp = compound.getInt("hermitxp");
        this.leveledUp = compound.getBoolean("hasLeveledUp");
        this.timeUntilReset = compound.getInt("timeUntilReset");
        //this.dataManager.set(LEVEL, compound.getInt("hermitLevel"));
        //this.dataManager.set(REPUTATION, compound.getByte("hermitReputation"));
        if (compound.contains("bedPos")){
            this.bedPos = CompoundNbtUtil.readBlockPos(compound.getCompound("bedPos"));
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
        VillagerTrades.ITrade[] trades = ModVillagerTrades.HERMIT_TRADES.get(this.getLevel());
        System.out.println(Arrays.toString(trades));
        if (trades != null) {
            MerchantOffers merchantoffers = this.getOffers();
            this.addTrades(merchantoffers, trades, 2);
        }
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
    public void setRevengeTarget(@Nullable LivingEntity livingBase) {
        if (livingBase != null) {
            System.out.println("reduced");
            this.dataManager.set(REPUTATION, (byte) Math.max(0, getReputation() - 20));
        }
        super.setRevengeTarget(livingBase);
    }

    public int getLevel() {
        return this.dataManager.get(LEVEL);
    }

    /**
     * @return the reputation, 50 is normal 100 is good and below 10 means it cant trade
     */
    public byte getReputation() {
        return this.dataManager.get(REPUTATION);
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity player) {
        boolean flag = this.getCustomer() != null && player == null;
        super.setCustomer(player);
        if (flag) {
            this.resetCustomer();
        }

    }

    @Override
    protected void resetCustomer() {
        super.resetCustomer();
        this.resetAllSpecialPrices();
    }

    private void resetAllSpecialPrices() {
        for (MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetSpecialPrice();
        }

    }

    private void shakeHead() {
        this.setShakeHeadTicks(40);
        if (!this.world.isRemote()) {
            this.playSound(SoundEvents.ENTITY_VILLAGER_NO, this.getSoundVolume(), this.getSoundPitch());
        }

    }

    protected void rejectPlayer(PlayerEntity player) {
        double ratioX = this.getPosX() - player.getPosX();
        double ratioZ = this.getPosZ() - player.getPosZ();
        player.applyKnockback(1.5f, ratioX, ratioZ);
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        target.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 100, 1));
        target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 3));
        LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(this.world);
        lightningboltentity.moveForced(Vector3d.copyCenteredHorizontally(target.getPosition()));
        this.world.addEntity(lightningboltentity);
    }


    protected class RestockGoal extends Goal {

        @Override
        public boolean shouldExecute() {
            return (restocksToday < 4 && world.getGameTime() - lastRestock >= 3000 && world.isDaytime()) || (world.isNightTime() && restocksToday > 0);
        }

        @Override
        public void startExecuting() {
            if (world.isDaytime()) {
                restock();
            } else if (world.isNightTime()) {
                restock();
                restocksToday = 0;
            }


        }
    }

    protected class FindBedGoal extends Goal {

        private int radius;

        public FindBedGoal(int radius) {
            this.radius = radius;
        }

        @Override
        public boolean shouldExecute() {
            if (bedPos == null)
                return true;
            if (ForgeEventFactory.fireSleepingLocationCheck(Hermit.this, bedPos)) {
                return !world.getBlockState(bedPos).get(BedBlock.OCCUPIED);
            }
            return false;
        }

        @Override
        public void startExecuting() {
            for (int x = -radius; x < radius; x++) {
                for (int z = -radius; z < radius; z++) {
                    for (int y = -10; y < 10; y++) {
                        BlockPos potentialBed = getPosition().add(x, y, z);
                        if (world.isAirBlock(potentialBed))
                            continue;
                        if (fireSleepingLocationCheck(Hermit.this, potentialBed)) {
                            bedPos = potentialBed;
                        }
                    }
                }
            }
        }
    }


    protected class SleepGoal extends Goal {

        @Override
        public boolean shouldExecute() {

            return !isSleeping() && world.isNightTime() && bedPos != null;
        }

        @Override
        public void tick() {
            navigator.tryMoveToXYZ(bedPos.getX(), bedPos.getY(), bedPos.getZ(), 0.3);
            if (bedPos.distanceSq(getPosition()) < 4) {
                startSleeping(bedPos);
            }
        }
    }


    public static boolean fireSleepingLocationCheck(LivingEntity player, BlockPos sleepingLocation) {
        SleepingLocationCheckEvent evt = new SleepingLocationCheckEvent(player, sleepingLocation);
        MinecraftForge.EVENT_BUS.post(evt);

        Event.Result canContinueSleep = evt.getResult();
        if (canContinueSleep == Event.Result.DEFAULT) {
            BlockState state = player.world.getBlockState(sleepingLocation);
            return state.getBlock().isBed(state, player.world, sleepingLocation, player);
        } else
            return canContinueSleep == Event.Result.ALLOW;
    }

}
