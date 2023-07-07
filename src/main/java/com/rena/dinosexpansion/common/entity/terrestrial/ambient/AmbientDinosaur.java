package com.rena.dinosexpansion.common.entity.terrestrial.ambient;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public abstract class AmbientDinosaur extends Dinosaur {

    public AmbientDinosaur(EntityType<? extends Dinosaur> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }

    @Override
    protected Rarity getInitialRarity() {
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
        //bring a bit of real life in the random distribution :-)
        return getRNG().nextDouble() <= 0.51 ? Gender.MALE : Gender.FEMALE;
    }

    @Override
    public boolean canBeKnockedOut() {
        return false;
    }

    @Override
    public boolean canBeTamed() {
        return false;
    }
}
