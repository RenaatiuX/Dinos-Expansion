package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.LookAtGoal;

public class DinosaurLookAtGoal extends LookAtGoal {

    protected Dinosaur dino;

    public DinosaurLookAtGoal(Dinosaur entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
        super(entityIn, watchTargetClass, maxDistance);
        this.dino = entityIn;
    }

    public DinosaurLookAtGoal(Dinosaur entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance, float chanceIn) {
        super(entityIn, watchTargetClass, maxDistance, chanceIn);
        this.dino = entityIn;
    }

    @Override
    public boolean shouldExecute() {
        return !dino.isMovementDisabled() && super.shouldExecute();
    }
}
