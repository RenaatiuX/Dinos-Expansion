package com.rena.dinosexpansion.common.entity.terrestrial;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class CarnotaurusEntity extends Dinosaur implements IAnimatable {
    public CarnotaurusEntity(EntityType<CarnotaurusEntity> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
    }

    @Override
    public Iterable<Item> getFood() {
        return null;
    }

    @Override
    protected Rarity getinitialRarity() {
        return null;
    }

    @Override
    protected Gender getInitialGender() {
        return null;
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
}
