package com.rena.dinosexpansion.common.entity.terrestrial;

import com.rena.dinosexpansion.common.entity.ChestedDinosaur;
import com.rena.dinosexpansion.common.entity.DinosaurArmorSlotType;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class Ankylosaurus extends ChestedDinosaur implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public Ankylosaurus(EntityType<Ankylosaurus> type, World world) {
        super(type, world, new DinosaurInfo("ankylosaurus", 200, 100, 50, SleepRhythmGoal.SleepRhythm.DIURNAL), generateLevelWithinBounds(10, 100));
        updateInfo();
        this.stepHeight = 1.0F;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 80.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 20)
                .createMutableAttribute(Attributes.ARMOR, 20.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 6.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 2.0F)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    public List<Item> getFood() {
        return null;
    }

    @Override
    protected Rarity getInitialRarity() {
        return null;
    }

    @Override
    protected Gender getInitialGender() {
        return getRNG().nextDouble() <= 0.51 ? Gender.MALE : Gender.FEMALE;
    }

    @Override
    protected int reduceNarcotic(int narcoticValue) {
        if (getRNG().nextDouble() <= 0.001)
            return narcoticValue - 1;
        return narcoticValue;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        return super.applyPlayerInteraction(player, vec, hand);
    }

    @Override
    public void travel(Vector3d travelVector) {
        super.travel(travelVector);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return this.ticksExisted;
    }

    @Override
    protected EntitySize getKnockoutSize(Pose pose) {
        return super.getKnockoutSize(pose);
    }

    @Override
    protected EntitySize getSleepSize(Pose pose) {
        return getKnockoutSize(pose);
    }
}
