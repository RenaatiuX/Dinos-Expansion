package com.rena.dinosexpansion.common.entity.terrestrial;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.ia.SleepRhythmGoal;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.List;

public class Megalania extends Dinosaur implements IAnimatable, IAnimationTickable {
    public boolean fallRotation = rand.nextBoolean();
    public Megalania(EntityType<Megalania> type, World world) {
        super(type, world, new DinosaurInfo("megalania", 150, 100, 50, SleepRhythmGoal.SleepRhythm.DIURNAL), generateLevelWithinBounds(10, 100));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            this.setSitting(false);
            if (entity != null && this.isTamed() && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 3.0F;
            }
            return super.attackEntityFrom(source, amount);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (super.attackEntityAsMob(entityIn)) {
            if (entityIn instanceof LivingEntity) {
                int i = 5;
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    i = 10;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    i = 20;
                }
                ((LivingEntity)entityIn).addPotionEffect(new EffectInstance(Effects.POISON, i * 20, 0));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isPotionApplicable(EffectInstance potioneffectIn) {
        if (potioneffectIn.getPotion() == Effects.POISON) {
            return false;
        }
        return super.isPotionApplicable(potioneffectIn);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source == DamageSource.FALL || source == DamageSource.IN_WALL;
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
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }

    @Override
    public int tickTimer() {
        return this.ticksExisted;
    }
}
