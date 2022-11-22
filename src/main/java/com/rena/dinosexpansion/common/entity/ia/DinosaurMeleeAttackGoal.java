package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class DinosaurMeleeAttackGoal extends MeleeAttackGoal {
    protected final Dinosaur dino;
    public DinosaurMeleeAttackGoal(Dinosaur creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
        this.dino = creature;
    }

    @Override
    public boolean shouldExecute() {
        return !dino.isMovementDisabled() && super.shouldExecute();
    }
}
