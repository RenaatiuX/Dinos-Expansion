package com.rena.dinosexpansion.common.entity.villagers.caveman;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.item.MerchantOffer;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class Caveman extends AbstractVillagerEntity {
    public Caveman(EntityType<? extends AbstractVillagerEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void onVillagerTrade(MerchantOffer offer) {

    }

    @Override
    protected void populateTradeData() {

    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }
}
