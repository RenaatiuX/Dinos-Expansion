package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.util.enums.AttackOrder;
import com.rena.dinosexpansion.common.util.enums.MoveOrder;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;

public class DinosaurHurByTargetGoal extends HurtByTargetGoal {
    protected final Dinosaur dino;
    protected final boolean workNonTamed;

    public DinosaurHurByTargetGoal(Dinosaur creatureIn, Class<?>... excludeReinforcementTypes) {
        this(creatureIn, true, excludeReinforcementTypes);
    }
    public DinosaurHurByTargetGoal(Dinosaur creatureIn, boolean workNonTamed, Class<?>... excludeReinforcementTypes) {
        super(creatureIn, excludeReinforcementTypes);
        this.dino = creatureIn;
        this.workNonTamed = workNonTamed;
    }

    @Override
    public boolean shouldExecute() {
        if (dino.isTamed()) {
            return !dino.isMovementDisabled() && dino.getAttackOrder() != AttackOrder.PASSIVE && dino.getMoveOrder() != MoveOrder.SIT && super.shouldExecute();
        }
        return workNonTamed && !dino.isMovementDisabled() && super.shouldExecute();
    }
}
