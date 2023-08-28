package com.rena.dinosexpansion.client.renderer.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.aquatic.Anomalocaris;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class AnomalocarisLayer extends GeoLayerRenderer<Anomalocaris> {

    private static final ResourceLocation LAYER = new ResourceLocation(DinosExpansion.MOD_ID, "textures/entity/anomalocaris/layer/anomalocaris_legendary_layer.png");
    private static final ResourceLocation MODEL = new ResourceLocation(DinosExpansion.MOD_ID, "geo/anomalocaris.geo.json");

    public AnomalocarisLayer(IGeoRenderer<Anomalocaris> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, Anomalocaris entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityLivingBaseIn.getRarity() == Dinosaur.Rarity.LEGENDARY) {
            RenderType renderType = RenderType.getEyes(LAYER);
            matrixStackIn.push();
            this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, renderType, matrixStackIn, bufferIn, bufferIn.getBuffer(renderType), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
            matrixStackIn.pop();
        }
    }
}
