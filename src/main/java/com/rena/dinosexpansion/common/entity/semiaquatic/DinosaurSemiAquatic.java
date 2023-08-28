package com.rena.dinosexpansion.common.entity.semiaquatic;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.world.World;

public abstract class DinosaurSemiAquatic extends Dinosaur {

    public DinosaurSemiAquatic(EntityType<? extends TameableEntity> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.setPathPriority(PathNodeType.WATER_BORDER, 0.0F);
    }
}
