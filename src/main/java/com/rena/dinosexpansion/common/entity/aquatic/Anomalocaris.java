package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.common.entity.ia.AnomalocarisGrabItemGoal;
import com.rena.dinosexpansion.common.entity.ia.DinosaurLookRandomGoal;
import com.rena.dinosexpansion.common.entity.ia.DinosaurSwimBottomGoal;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
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
        this.goalSelector.addGoal(3, new DinosaurLookRandomGoal(this));
        this.goalSelector.addGoal(4, new AnomalocarisGrabItemGoal(this));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0.1D);
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
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isGrabbing()) {
            this.dropHeldItem();
            this.setGrabbing(false);
        }
        return super.attackEntityFrom(source, amount);
    }

    private void dropHeldItem() {
        this.world.addEntity(new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), this.getHeldItem(Hand.MAIN_HAND)));
        this.setHeldItem(ItemStack.EMPTY);
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
                    double d = this.getPosX() + (this.getRNG().nextDouble() - 0.5D) * (double) this.getWidth();
                    double e = this.getPosY() + this.getRNG().nextDouble() * (double) this.getHeight();
                    double f = this.getPosZ() + (this.getRNG().nextDouble() - 0.5D) * (double) this.getWidth();
                    double g = (this.getRNG().nextDouble() - 0.5D) * 2.0D;
                    double h = -this.getRNG().nextDouble();
                    double k = (this.getRNG().nextDouble() - 0.5D) * 2.0D;
                    this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, itemstack), d, e, f, g, h, k);
                }
            }
        }
        super.livingTick();
    }

    private boolean canEatItem(ItemStack itemStackIn) {
        return itemStackIn.getItem().isFood() && this.getAttackTarget() == null && this.onGround || this.isInWater();
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
}
