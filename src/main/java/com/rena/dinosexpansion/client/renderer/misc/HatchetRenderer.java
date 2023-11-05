package com.rena.dinosexpansion.client.renderer.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.dinosexpansion.common.entity.misc.HatchetEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HatchetRenderer extends EntityRenderer<HatchetEntity> {

    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

    public HatchetRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(HatchetEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.scale(1.7F, 1.7F, 1.7F);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) + 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch + entityIn.getPrevRotation(), entityIn.rotationPitch + entityIn.getRotation()) - 45.0F));
        ItemStack itemStack = entityIn.getArrowStack();
        IBakedModel bakedModel = this.itemRenderer.getItemModelWithOverrides(itemStack, entityIn.world, null);
        this.itemRenderer.renderItem(itemStack, ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, bakedModel);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(HatchetEntity entity) {
        return PlayerContainer.LOCATION_BLOCKS_TEXTURE;
    }
}
