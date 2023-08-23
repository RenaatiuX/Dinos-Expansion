package com.rena.dinosexpansion.client.renderer.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.dinosexpansion.common.entity.aquatic.Anomalocaris;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class AnomalocarisHelItemLayer extends GeoLayerRenderer<Anomalocaris> {

    public AnomalocarisHelItemLayer(IGeoRenderer<Anomalocaris> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, Anomalocaris entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack stack = entityLivingBaseIn.getHeldItem();
        if (!stack.isEmpty()) {
            matrixStackIn.push();
            matrixStackIn.translate(-0.1, -.1, -.5);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F)); // Rotación horizontal en el eje Y
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90.0F)); // Rotación adicional de 180 grados para ajustar la orientación
            Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
    }
}
