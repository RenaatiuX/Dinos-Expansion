package com.rena.dinosexpansion.client.renderer.misc;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.misc.BoomerangEntity;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
@OnlyIn(Dist.CLIENT)
public class BoomerangRenderer extends EntityRenderer<BoomerangEntity> {

    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

    public BoomerangRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(BoomerangEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-entityYaw + 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch) + 90.0F));
        matrixStackIn.rotate(Vector3f.YN.rotationDegrees(90.0F));
        matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(entityIn.getBoomerangRotation()));
        this.itemRenderer.renderItem(this.getItemStackForRender(entityIn), ItemCameraTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    private ItemStack getItemStackForRender(BoomerangEntity entityIn) {
        return entityIn.getRenderedItemStack();
    }

    @Override
    public ResourceLocation getEntityTexture(BoomerangEntity entity) {
        return PlayerContainer.LOCATION_BLOCKS_TEXTURE;
    }
}
