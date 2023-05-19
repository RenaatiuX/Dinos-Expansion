package com.rena.dinosexpansion.client.renderer.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class DinosaurShoulderRidingLayer<T extends PlayerEntity> extends LayerRenderer<T, PlayerModel<T>> {
    public DinosaurShoulderRidingLayer(IEntityRenderer<T, PlayerModel<T>> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        renderShoulderEntity(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, netHeadYaw, headPitch, partialTicks, true);
        renderShoulderEntity(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, netHeadYaw, headPitch, partialTicks, false);
    }

    protected void renderShoulderEntity(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float netHeadYaw, float headPitch, float partialTicks, boolean leftShoulderIn) {
        CompoundNBT compoundnbt = leftShoulderIn ? entitylivingbaseIn.getLeftShoulderEntity() : entitylivingbaseIn.getRightShoulderEntity();
        EntityType.byKey(compoundnbt.getString("id")).filter(t -> t != EntityType.PARROT).ifPresent((entityType) -> {
            EntityRenderer<Entity> renderer = (EntityRenderer<Entity>) Minecraft.getInstance().getRenderManager().renderers.get(entityType);
            Entity e = entityType.create(Minecraft.getInstance().world);
            e.read(compoundnbt);
            matrixStackIn.push();
            matrixStackIn.translate(leftShoulderIn ? (double) 0.4F : (double) -0.4F, entitylivingbaseIn.isCrouching() ? (double) -1.3F : -1.5D, 0.0D);
            renderer.render(e, netHeadYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            matrixStackIn.pop();
        });
    }
}
