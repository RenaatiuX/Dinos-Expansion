package com.rena.dinosexpansion.common.entity.ia.helper;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;

public interface IFollower {

    boolean shouldFollow();

    default void followEntity(Dinosaur dinosaur, LivingEntity owner, double followSpeed){
        dinosaur.navigator.tryMoveToEntityLiving(owner, followSpeed);
    }

}
