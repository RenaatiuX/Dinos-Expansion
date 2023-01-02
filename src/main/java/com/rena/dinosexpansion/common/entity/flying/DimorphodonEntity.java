package com.rena.dinosexpansion.common.entity.flying;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class DimorphodonEntity extends DinosaurFlying{
    private int rideCooldownCounter;
    public DimorphodonEntity(EntityType<DinosaurFlying> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
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
    protected int reduceNarcotic(int narcoticValue) {
        return 0;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }
}
