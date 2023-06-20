package com.rena.dinosexpansion.common.entity.villagers.caveman;

import net.minecraft.entity.ai.goal.Goal;

public class FightBossfightGoal extends Goal {
    protected final Caveman caveman;

    public FightBossfightGoal(Caveman caveman) {
        this.caveman = caveman;
    }

    @Override
    public boolean shouldExecute() {
        if (false) {
            System.out.println(caveman.tribe.getCavemen().stream().filter(c -> c.tookPlaceInBossfight).count() + "|" + caveman.tribe.getSize());
        }
        return caveman.tookPlaceInBossfight && caveman.tribe != null && caveman.tribe.getCavemen().stream().filter(c -> c.tookPlaceInBossfight).count() == caveman.tribe.getSize();
    }

    @Override
    public void startExecuting() {
        if (caveman.isBoss() && caveman.tribe != null)
            caveman.tribe.setStartCounter(Caveman.BOSSFIGHT_START_COOLDOWN);
    }

    @Override
    public void tick() {
        if (!caveman.world.isRemote() && caveman.tribe != null) {
            if (caveman.isFighterBossFight()) {
                if (caveman.tribe.getStartCounter() > 0) {
                    caveman.tribe.reduceStartCounter();
                } else {
                    caveman.setAttackTarget(caveman.rivalBoss);
                }
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting() && caveman.tribe.isBossFight() && caveman.tribe.countBosses() > 1;
    }

    @Override
    public void resetTask() {
        caveman.tookPlaceInBossfight = false;
    }
}
