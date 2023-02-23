package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.util.enums.AttackOrder;
import com.rena.dinosexpansion.common.util.enums.MoveOrder;
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
        if (dino.isTamed()){
            return !dino.isMovementDisabled() && dino.getMoveOrder() == MoveOrder.WANDER && (dino.getAttackOrder() != AttackOrder.PASSIVE) && super.shouldExecute();
        }
        return !dino.isMovementDisabled() && super.shouldExecute();
    }
}
