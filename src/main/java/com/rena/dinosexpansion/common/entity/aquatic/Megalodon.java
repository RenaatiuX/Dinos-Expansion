package com.rena.dinosexpansion.common.entity.aquatic;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.List;

public class Megalodon extends DinosaurAquatic implements IAnimatable {

    public Megalodon(EntityType<? extends Megalodon> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
    }

    @Override
    public boolean canHuntMobsOnLand() {
        return false;
    }

    public float getTargetScale() {
        return 2.0F;
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
        return null;
    }

    @Override
    protected Gender getInitialGender() {
        return null;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return super.getLootTable();
    }

    @Override
    public double swimSpeed() {
        return 1D;
    }

    public boolean destroyBoats() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
    }

    @Override
    public void updateRidden() {
        super.updateRidden();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        return true;
    }

    @Override
    protected boolean canBeRidden(Entity entityIn) {
        return super.canBeRidden(entityIn);
    }

    @Override
    public boolean canBreathOnLand() {
        return false;
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }
}
