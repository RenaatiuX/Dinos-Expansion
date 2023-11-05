package com.rena.dinosexpansion.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.dinosexpansion.client.model.entity.ParapuzosiaModel;
import com.rena.dinosexpansion.common.entity.aquatic.Parapuzosia;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
@OnlyIn(Dist.CLIENT)
public class ParapuzosiaRenderer extends GeoEntityRenderer<Parapuzosia> {
    public ParapuzosiaRenderer(EntityRendererManager renderManager) {
        super(renderManager, new ParapuzosiaModel());
    }

    @Override
    public void render(Parapuzosia entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        stack.rotate(Vector3f.YP.rotationDegrees(180));
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(Parapuzosia entity) {
        return this.modelProvider.getTextureLocation(entity);
    }
}
