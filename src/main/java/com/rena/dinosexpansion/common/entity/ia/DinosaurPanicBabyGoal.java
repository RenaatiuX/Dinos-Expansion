package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.ai.goal.PanicGoal;

public class DinosaurPanicBabyGoal extends PanicGoal {

    private Dinosaur dinosaur;

    public DinosaurPanicBabyGoal(Dinosaur dinosaur, double speedIn) {
        super(dinosaur, speedIn);
    }

    @Override
    public boolean shouldExecute() {
        return this.dinosaur.isChild() && super.shouldExecute();
    }
}
