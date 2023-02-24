package com.rena.dinosexpansion.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.dinosexpansion.client.model.entity.DryosaurusModel;
import com.rena.dinosexpansion.client.renderer.layer.DryosaurusLayer;
import com.rena.dinosexpansion.common.entity.terrestrial.Dryosaurus;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class DryosaurusRenderer extends GeoEntityRenderer<Dryosaurus> {
    public DryosaurusRenderer(EntityRendererManager renderManager) {
        super(renderManager, new DryosaurusModel());
        this.addLayer(new DryosaurusLayer(this));
    }

    @Override
    public RenderType getRenderType(Dryosaurus animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityCutoutNoCull(textureLocation);
    }

    @Override
    public ResourceLocation getEntityTexture(Dryosaurus entity) {
        return this.modelProvider.getTextureLocation(entity);
    }

    @Override
    public void renderEarly(Dryosaurus animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        if (animatable.isChild()) {
            stackIn.scale(0.5F, 0.5F, 0.5F);
        }
    }
}
