package com.rena.dinosexpansion.common.entity.terrestrial;

import com.rena.dinosexpansion.common.entity.ChestedDinosaur;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.List;

public class Gallimimus extends ChestedDinosaur implements IAnimatable, IAnimationTickable {
    public Gallimimus(EntityType<Gallimimus> type, World world, DinosaurInfo info, int minLevel, int maxLevel) {
        super(type, world, info, minLevel, maxLevel);
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
