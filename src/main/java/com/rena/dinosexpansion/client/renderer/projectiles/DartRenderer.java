package com.rena.dinosexpansion.client.renderer.projectiles;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class DartRenderer extends ArrowRenderer {

    private static final ResourceLocation TEXTURE = DinosExpansion.modLoc("textures/entity/projectile/dart.png");

    public DartRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }
}
