package com.rena.dinosexpansion.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.dinosexpansion.client.model.entity.AnomalocarisModel;
import com.rena.dinosexpansion.client.renderer.layer.AnomalocarisHelItemLayer;
import com.rena.dinosexpansion.client.renderer.layer.AnomalocarisLayer;
import com.rena.dinosexpansion.common.entity.aquatic.Anomalocaris;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

import javax.annotation.Nullable;

public class AnomalocarisRenderer extends GeoEntityRenderer<Anomalocaris> {
    public AnomalocarisRenderer(EntityRendererManager renderManager) {
        super(renderManager, new AnomalocarisModel());
        this.addLayer(new AnomalocarisHelItemLayer(this));
        this.addLayer(new AnomalocarisLayer(this));
    }

    @Override
    public RenderType getRenderType(Anomalocaris animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public ResourceLocation getEntityTexture(Anomalocaris entity) {
        return this.modelProvider.getTextureLocation(entity);
    }
}
