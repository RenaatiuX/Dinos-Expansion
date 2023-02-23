package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.common.entity.ia.DinosaurSwimBottomGoal;
import com.rena.dinosexpansion.common.entity.ia.movecontroller.AquaticMoveController;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FindWaterGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
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

public class Wheterellus extends PrehistoricFish implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public Wheterellus(EntityType<? extends TameableEntity> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
        this.moveController = new AquaticMoveController(this, 1.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FindWaterGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1D));
        this.goalSelector.addGoal(3, new DinosaurSwimBottomGoal(this, 1F, 3));
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 1.0D, 7));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 3)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected ItemStack getFishBucket() {
        return null;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationEvent<Wheterellus> event) {
        if (this.isOnGround()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wetherellus.outofwater", ILoopType.EDefaultLoopTypes.LOOP));
        }
        if(isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wetherellus.swim", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }

    @Override
    public int tickTimer() {
        return 0;
    }
}
