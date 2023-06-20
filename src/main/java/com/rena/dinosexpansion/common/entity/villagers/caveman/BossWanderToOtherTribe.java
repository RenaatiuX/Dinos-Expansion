package com.rena.dinosexpansion.common.entity.villagers.caveman;

import com.google.common.collect.Lists;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.server.ServerWorld;

import java.util.Collections;

public class BossWanderToOtherTribe extends Goal {

    protected final Caveman caveman;
    protected final double speed;

    public BossWanderToOtherTribe(Caveman caveman, double speed) {
        this.caveman = caveman;
        this.speed = speed;
    }

    @Override
    public boolean shouldExecute() {
        if (!caveman.world.isRemote() && caveman.isBoss() && caveman.tribe != null && caveman.tribe.getSize() == 1 && !caveman.tribe.isBossFight()) {
            for (Tribe t : TribeSaveData.getData((ServerWorld) caveman.world).getTribes())
                if (t != caveman.tribe && t.getType() == caveman.tribe.getType() && t.getSize() > 1)
                    caveman.wanderTribe = t;
            return caveman.wanderTribe != null;
        }
        return false;
    }

    @Override
    public void tick() {
        if (caveman.wanderTribe.boss != null)
            caveman.getNavigator().tryMoveToEntityLiving(caveman.wanderTribe.boss, speed);
        if (!caveman.wanderTribe.isBossFight() && caveman.wanderTribe.getCavemen().stream().filter(c -> c.getDistance(caveman) <= 1d).count() > 0){
            caveman.tribe.removeCaveman(caveman);
            caveman.tribe = caveman.wanderTribe;
            caveman.tribe.addCaveman(caveman);
        }
    }

    @Override
    public void resetTask() {
        caveman.wanderTribe = null;
    }
}
