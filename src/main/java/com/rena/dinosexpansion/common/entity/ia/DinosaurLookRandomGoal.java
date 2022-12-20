package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;

public class DinosaurLookRandomGoal extends LookRandomlyGoal {
    protected final Dinosaur dino;
    public DinosaurLookRandomGoal(Dinosaur dino) {
        super(dino);
        this.dino = dino;
    }

    @Override
    public boolean shouldExecute() {
        return !dino.isMovementDisabled() && super.shouldExecute();
    }
}
