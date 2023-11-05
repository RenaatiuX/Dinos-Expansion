package com.rena.dinosexpansion.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.dinosexpansion.client.model.entity.MeganeuraModel;
import com.rena.dinosexpansion.common.entity.terrestrial.ambient.Meganeura;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;
@OnlyIn(Dist.CLIENT)
public class MeganeuraRenderer extends GeoEntityRenderer<Meganeura> {
    public MeganeuraRenderer(EntityRendererManager renderManager) {
        super(renderManager, new MeganeuraModel());
    }

    @Override
    public RenderType getRenderType(Meganeura animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityCutoutNoCull(this.getTextureLocation(animatable));
    }

    @Override
    public ResourceLocation getEntityTexture(Meganeura entity) {
        return this.modelProvider.getTextureLocation(entity);
    }
}
