package com.rena.dinosexpansion.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.dinosexpansion.client.model.entity.DimorphodonModel;
import com.rena.dinosexpansion.client.renderer.layer.DimorphodonLayer;
import com.rena.dinosexpansion.common.entity.flying.Dimorphodon;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class DimorphodonRenderer extends GeoEntityRenderer<Dimorphodon> {
    public DimorphodonRenderer(EntityRendererManager renderManager) {
        super(renderManager, new DimorphodonModel());
        this.addLayer(new DimorphodonLayer(this));
    }

    @Override
    public void render(Dimorphodon entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        stack.push();
        float scale = entity.getShoulderScaling();
        stack.scale(scale, scale, scale);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        stack.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(Dimorphodon entity) {
        return this.modelProvider.getTextureLocation(entity);
    }

    @Override
    public RenderType getRenderType(Dimorphodon animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityCutout(textureLocation);
    }
}
