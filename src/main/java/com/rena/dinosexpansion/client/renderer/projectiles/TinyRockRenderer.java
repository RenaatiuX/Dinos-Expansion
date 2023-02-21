package com.rena.dinosexpansion.client.renderer.projectiles;

import com.rena.dinosexpansion.common.entity.projectile.TinyRockEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class TinyRockRenderer extends EntityRenderer<TinyRockEntity> {
    public TinyRockRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(TinyRockEntity entity) {
        return null;
    }

}
