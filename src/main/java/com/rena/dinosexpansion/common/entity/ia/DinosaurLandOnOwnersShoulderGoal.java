package com.rena.dinosexpansion.common.entity.ia;

import com.rena.dinosexpansion.common.entity.flying.Dimorphodon;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.ServerPlayerEntity;

public class DinosaurLandOnOwnersShoulderGoal extends Goal {
    private final Dimorphodon dimorphodon;
    private ServerPlayerEntity owner;
    private boolean isSittingOnShoulder;
    public DinosaurLandOnOwnersShoulderGoal(Dimorphodon riding) {
        this.dimorphodon = riding;
    }

    @Override
    public boolean shouldExecute() {
        ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)this.dimorphodon.getOwner();
        boolean flag = serverplayerentity != null && !serverplayerentity.isSpectator() && !serverplayerentity.abilities.isFlying && !serverplayerentity.isInWater();
        return !this.dimorphodon.isQueuedToSit() && flag && this.dimorphodon.canSitOnShoulder();
    }

    @Override
    public boolean isPreemptible() {
        return !this.isSittingOnShoulder;
    }

    public void startExecuting() {
        this.owner = (ServerPlayerEntity)this.dimorphodon.getOwner();
        this.isSittingOnShoulder = false;
    }

    public void tick() {
        if (!this.isSittingOnShoulder && !this.dimorphodon.isSleeping() && !this.dimorphodon.getLeashed()) {
            if (this.dimorphodon.getBoundingBox().intersects(this.owner.getBoundingBox())) {
                this.isSittingOnShoulder = this.dimorphodon.func_213439_d(this.owner);
            }
        }
    }
}
