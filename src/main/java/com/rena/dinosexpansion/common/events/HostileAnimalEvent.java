package com.rena.dinosexpansion.common.events;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.Entity;
import net.minecraftforge.eventbus.api.Event;

public class HostileAnimalEvent extends Event {
    private final Dinosaur scaredEntity;
    private final Entity scaredFrom;
    private boolean isHostile;

    /**
     * only server side
     * @param scaredEntity the entity that may be seeing the other entity as hostile
     * @param scaredFrom the entity that might be hostile to the scaredEntity
     * @param isScared this value is the default value that the method already has determined
     */
    public HostileAnimalEvent(Dinosaur scaredEntity, Entity scaredFrom, boolean isScared) {
        this.scaredEntity = scaredEntity;
        this.scaredFrom = scaredFrom;
        this.isHostile = isScared;
    }

    @Override
    public boolean isCancelable() {
        return false;
    }

    public void setHostile(boolean hostile) {
        isHostile = hostile;
    }

    public boolean isHostile() {
        return isHostile;
    }

    /**
     *
     * @return the entity that might potentially sense a hostile Entity
     */
    public Dinosaur getScaredEntity() {
        return scaredEntity;
    }

    /**
     *
     * @return the entity that got sensed which should be determined Hostile or not
     */
    public Entity getScaredFrom() {
        return scaredFrom;
    }
}
