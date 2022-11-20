package com.rena.dinosexpansion.client.renderer.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.dinosexpansion.common.entity.misc.HatchetEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;

public class HatchetRenderer extends EntityRenderer<HatchetEntity> {

    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

    protected HatchetRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(HatchetEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(HatchetEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}
