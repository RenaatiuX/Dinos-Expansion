package com.rena.dinosexpansion.client.renderer.projectiles;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.projectile.DartEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DartRenderer extends ArrowRenderer<DartEntity> {

    private static final ResourceLocation TEXTURE = DinosExpansion.modLoc("textures/entity/projectile/dart.png");

    public DartRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(DartEntity entity) {
        return TEXTURE;
    }
}
