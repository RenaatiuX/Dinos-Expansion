package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.FollowBoatGoal;

public class DinosaurFollowBoatGoal extends FollowBoatGoal {
    protected final Dinosaur dino;
    public DinosaurFollowBoatGoal(Dinosaur swimmer) {
        super(swimmer);
        this.dino = swimmer;
    }

    @Override
    public boolean shouldExecute() {
        return !this.dino.isMovementDisabled() && super.shouldExecute();
    }
}
