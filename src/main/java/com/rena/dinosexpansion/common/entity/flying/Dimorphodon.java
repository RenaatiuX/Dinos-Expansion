package com.rena.dinosexpansion.common.entity.flying;

import com.rena.dinosexpansion.common.entity.aquatic.MegaPiranha;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
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

public class Dimorphodon extends DinosaurFlying implements IAnimatable, IAnimationTickable {

    public Dimorphodon(EntityType<Dimorphodon> type, World world) {
        super(type, world, new DinosaurInfo("dimorphodon", 50, 20, 10, SleepRhythmGoal.SleepRhythm.DIURNAL), generateLevelWithinBounds(10, 50));
    }
    public Dimorphodon(World world) {
        this(EntityInit.DIMORPHODON.get(), world);
    }
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10F)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public List<Item> getFood() {
        return null;
    }

    @Override
    protected Rarity getinitialRarity() {
        double rand = this.getRNG().nextDouble();
        if (rand <= 0.05)
            return Rarity.LEGENDARY;
        if (rand <= 0.1)
            return Rarity.EPIC;
        if (rand < 0.2)
            return Rarity.RARE;
        if (rand <= 0.5)
            return Rarity.UNCOMMON;
        return Rarity.COMMON;
    }

    @Override
    protected Gender getInitialGender() {
        return getRNG().nextDouble() <= 0.51 ? Gender.MALE : Gender.FEMALE;
    }

    @Override
    protected int reduceNarcotic(int narcoticValue) {
        if (getRNG().nextDouble() <= 0.05)
            return narcoticValue - 1;
        return narcoticValue;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    private PlayState predicate(AnimationEvent<Dimorphodon> event){
        if (event.isMoving() && this.isOnGround()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimorphodon.walk", ILoopType.EDefaultLoopTypes.LOOP));
        } else if (event.isMoving() && this.isFlying()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimorphodon.fly", ILoopType.EDefaultLoopTypes.LOOP));
        } else if (this.isSleeping()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimorphodon.sleep", ILoopType.EDefaultLoopTypes.LOOP));
        } else if (this.isKnockout()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimorphodon.knockout", ILoopType.EDefaultLoopTypes.LOOP));
        } else if (this.isQueuedToSit()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimorphodon.sit", ILoopType.EDefaultLoopTypes.LOOP));
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.dimorphodon.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 30, this::predicate));
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
