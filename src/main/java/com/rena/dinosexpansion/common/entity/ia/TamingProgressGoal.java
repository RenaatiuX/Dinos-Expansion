package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;

public class TamingProgressGoal extends Goal {

    private final Dinosaur dino;
    private final float eat, chance;
    private final int percentTamingProgressAdd;

    /**
     *
     * @param dino the dino this should work on
     * @param eat the hunger that the dino has to have and will be eaten
     * @param percentTamingProgressAdd the percentage that will be added to the taming progress goes from 0.0 to 100
     * @param chance the chance that the dino will eat and progress taming
     */
    public TamingProgressGoal(Dinosaur dino, float eat, byte percentTamingProgressAdd, float chance) {
        this.dino = dino;
        this.eat = MathHelper.clamp(eat, 0, dino.getMaxHunger());
        this.percentTamingProgressAdd = MathHelper.clamp(percentTamingProgressAdd, 0, 100);
        this.chance = MathHelper.clamp(chance, 0.0f, 1.0f);
    }

    @Override
    public boolean shouldExecute() {
        return dino.isKnockout() && dino.getHungerValue() > this.eat && dino.getTamingProgress() < 100 && dino.getRNG().nextFloat() < chance;
    }

    @Override
    public void startExecuting() {
        dino.addHungerValue(eat * -1f);
        dino.addTamingProgress((byte) percentTamingProgressAdd);
    }
}
