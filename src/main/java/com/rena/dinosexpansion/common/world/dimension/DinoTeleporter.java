package com.rena.dinosexpansion.common.world.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class DinoTeleporter implements ITeleporter {

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {

        BlockPos pos = entity.getPosition();

        Entity repositionedEntity = repositionEntity.apply(false);

        repositionedEntity.setPortalCooldown();

        int x = pos.getX();
        int z = pos.getZ();
        int surfaceY = destWorld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
        int y = surfaceY < 1 ? 70 : surfaceY;

        repositionedEntity.moveForced(x, y, z);

        return repositionedEntity;
    }
}
