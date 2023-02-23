package com.rena.dinosexpansion.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.dinosexpansion.client.model.entity.WetherellusModel;
import com.rena.dinosexpansion.common.entity.aquatic.fish.Wetherellus;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class WetherellusRenderer extends GeoEntityRenderer<Wetherellus> {
    public WetherellusRenderer(EntityRendererManager renderManager) {
        super(renderManager, new WetherellusModel());
    }

    @Override
    public RenderType getRenderType(Wetherellus animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityCutoutNoCull(textureLocation);
    }

    @Override
    public ResourceLocation getEntityTexture(Wetherellus entity) {
        return this.modelProvider.getTextureLocation(entity);
    }
}
