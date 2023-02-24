package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.util.enums.MoveOrder;
import com.sun.org.apache.bcel.internal.generic.RET;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;

public class DinosaurWanderGoal extends RandomWalkingGoal {

    protected Dinosaur dino;

    public DinosaurWanderGoal(Dinosaur creatureIn, double speedIn) {
        super(creatureIn, speedIn);
        this.dino = creatureIn;
    }

    public DinosaurWanderGoal(Dinosaur creatureIn, double speedIn, int chance) {
        super(creatureIn, speedIn, chance);
        this.dino = creatureIn;
    }

    public DinosaurWanderGoal(Dinosaur creature, double speed, int chance, boolean stopWhenIdle) {
        super(creature, speed, chance, stopWhenIdle);
        this.dino = creature;
    }

    @Override
    public boolean shouldExecute() {
        if (dino.isTamed()){
            return !dino.isMovementDisabled() && dino.getMoveOrder() == MoveOrder.WANDER && super.shouldExecute();
        }
        return !dino.isMovementDisabled() && super.shouldExecute();
    }
}
