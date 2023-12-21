package com.rena.dinosexpansion.client.renderer.layer.ceratosaurus;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.DinosaurArmorSlotType;
import com.rena.dinosexpansion.common.entity.terrestrial.Ceratosaurus;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class CeratosaurusArmorLayer extends GeoLayerRenderer<Ceratosaurus> {

    public static final ResourceLocation HEAD = DinosExpansion.entityTexture("layer/ceratosaurus_iron_armor_head.png");
    public static final ResourceLocation LEGS = DinosExpansion.entityTexture("layer/ceratosaurus_iron_armor_legs.png");
    public static final ResourceLocation TAIL = DinosExpansion.entityTexture("layer/ceratosaurus_iron_armor_tail.png");
    public static final ResourceLocation TORSO = DinosExpansion.entityTexture("layer/ceratosaurus_iron_armor_torso.png");



    public CeratosaurusArmorLayer(IGeoRenderer<Ceratosaurus> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, Ceratosaurus entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityLivingBaseIn.hasArmor(DinosaurArmorSlotType.HEAD)){
            renderLayer(HEAD, matrixStackIn, bufferIn, packedLightIn, entityLivingBaseIn, partialTicks);
        }
        if (entityLivingBaseIn.hasArmor(DinosaurArmorSlotType.LEG)){
            renderLayer(LEGS, matrixStackIn, bufferIn, packedLightIn, entityLivingBaseIn, partialTicks);
        }
        if (entityLivingBaseIn.hasArmor(DinosaurArmorSlotType.FEET)){
            renderLayer(TAIL, matrixStackIn, bufferIn, packedLightIn, entityLivingBaseIn, partialTicks);
        }
        if (entityLivingBaseIn.hasArmor(DinosaurArmorSlotType.CHESTPLATE)){
            renderLayer(TORSO, matrixStackIn, bufferIn, packedLightIn, entityLivingBaseIn, partialTicks);
        }
    }

    protected void renderLayer(ResourceLocation layer, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, Ceratosaurus entityLivingBaseIn, float partialTicks){
        RenderType type = RenderType.getEntityCutout(layer);
        matrixStackIn.push();
        this.getRenderer().render(getEntityModel().getModel(getEntityModel().getModelLocation(entityLivingBaseIn)), entityLivingBaseIn, partialTicks, type, matrixStackIn, bufferIn, bufferIn.getBuffer(type), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        matrixStackIn.pop();
    }
}
