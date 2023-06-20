package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.common.config.DinosExpansionConfig;
import com.rena.dinosexpansion.common.entity.ia.AnomalocarisGrabItemGoal;
import com.rena.dinosexpansion.common.entity.ia.DinosaurLookRandomGoal;
import com.rena.dinosexpansion.common.entity.ia.DinosaurSwimBottomGoal;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FindWaterGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Anomalocaris extends DinosaurAquatic implements IAnimatable, IAnimationTickable {
    private static final DataParameter<Boolean> IS_GRABBING = EntityDataManager.createKey(Anomalocaris.class, DataSerializers.BOOLEAN);
    private static final DataParameter<ItemStack> HELD_ITEM = EntityDataManager.createKey(Anomalocaris.class, DataSerializers.ITEMSTACK);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final Predicate<ItemEntity> ITEMS = (obj) -> !obj.cannotPickup() && obj.isAlive();
    private int eatTimer;
    public Anomalocaris(EntityType<Anomalocaris> type, World world) {
        super(type, world, new DinosaurInfo("anomalocaris", 100, 50, 40, SleepRhythmGoal.SleepRhythm.NONE), generateLevelWithinBounds(1, 100));
        this.moveController = new AquaticMoveController(this, 1F);
        this.updateInfo();
        this.setCanPickUpLoot(true);
    }

    public Anomalocaris(World world) {
        this(EntityInit.ANOMALOCARIS.get(), world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FindWaterGoal(this));
        this.goalSelector.addGoal(2, new RandomSwimmingGoal(this, 0.8F, 1));
        this.goalSelector.addGoal(3, new DinosaurSwimBottomGoal(this, 0.8F, 9));
        this.goalSelector.addGoal(4, new AnomalocarisGrabItemGoal(this));
        this.goalSelector.addGoal(5, new DinosaurLookRandomGoal(this));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0.1D);
    }

    @Override
    public SoundEvent getEatSound(ItemStack itemStackIn) {
        return SoundEvents.ENTITY_GENERIC_EAT;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_GRABBING, false);
        this.dataManager.register(HELD_ITEM, ItemStack.EMPTY);
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putBoolean("grab", this.isGrabbing());
        nbt.put("item", this.hasHeldItem().write(new CompoundNBT()));
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.setGrabbing(nbt.getBoolean("grab"));
        this.setHeldItem(ItemStack.read(nbt));
    }

    public boolean isGrabbing() {
        return this.dataManager.get(IS_GRABBING);
    }

    public void setGrabbing(boolean isGrab) {
        this.dataManager.set(IS_GRABBING, isGrab);
    }

    public ItemStack hasHeldItem() {
        return this.dataManager.get(HELD_ITEM);
    }

    public void setHeldItem(ItemStack itemStack) {
        this.dataManager.set(HELD_ITEM, itemStack);
    }

    @Override
    public void livingTick() {
        if (!this.world.isRemote && this.isAlive() && this.isServerWorld()) {
            ++this.eatTimer;
            ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if (this.canEatItem(itemstack)) {
                if (this.eatTimer > 600) {
                    ItemStack itemstack1 = itemstack.onItemUseFinish(this.world, this);
                    if (!itemstack1.isEmpty()) {
                        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack1);
                    }

                    this.eatTimer = 0;
                } else if (this.eatTimer > 560 && this.rand.nextFloat() < 0.1F) {
                    this.playSound(this.getEatSound(itemstack), 1.0F, 1.0F);
                    this.world.setEntityState(this, (byte)45);
                }
            }
        }
        super.livingTick();
    }

    private boolean canEatItem(ItemStack itemStackIn) {
        return itemStackIn.getItem().isFood() && this.getAttackTarget() == null && this.isInWater();
    }

    @Override
    public boolean canPickUpItem(ItemStack itemstackIn) {
        EquipmentSlotType equipmentslottype = MobEntity.getSlotForItemStack(itemstackIn);
        if (!this.getItemStackFromSlot(equipmentslottype).isEmpty()) {
            return false;
        } else {
            return equipmentslottype == EquipmentSlotType.MAINHAND && super.canPickUpItem(itemstackIn);
        }
    }

    @Override
    public boolean canEquipItem(ItemStack stack) {
        Item item = stack.getItem();
        ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        return itemstack.isEmpty() || this.eatTimer > 0 && item.isFood() && !itemstack.getItem().isFood();
    }

    @Override
    protected void spawnDrops(DamageSource damageSourceIn) {
        ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        if (!itemstack.isEmpty()) {
            this.entityDropItem(itemstack);
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
        }

        super.spawnDrops(damageSourceIn);
    }

    @Override
    public List<Item> getFood() {
        return null;
    }

    @Override
    protected int reduceNarcotic(int narcoticValue) {
        return 0;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.DROWN || source == DamageSource.IN_WALL || source == DamageSource.FALLING_BLOCK || super.isInvulnerableTo(source);
    }

    private PlayState predicate(AnimationEvent<Anomalocaris> event) {
        if (this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("swim", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }

    private PlayState attackAndGrabPredicate(AnimationEvent<Anomalocaris> event) {
        if (this.isGrabbing()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("grab", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        } else if (!this.hasHeldItem().isEmpty()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("grabbed", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(10);
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
        data.addAnimationController(new AnimationController<>(this, "grab_controller", 0, this::attackAndGrabPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return this.ticksExisted;
    }

    public static boolean anomalocarisSpawn(EntityType<? extends DinosaurAquatic> type, IWorld worldIn, SpawnReason reason, BlockPos p_223363_3_, Random randomIn) {
        int level = worldIn.getSeaLevel();
        int pos = level - 13;
        return p_223363_3_.getY() >= pos && p_223363_3_.getY() <= level && worldIn.getFluidState(p_223363_3_.down()).isTagged(FluidTags.WATER) && worldIn.getBlockState(p_223363_3_.down()).matchesBlock(Blocks.WATER);
    }

}
