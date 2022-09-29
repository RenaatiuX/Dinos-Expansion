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
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class BoomerangRenderer extends EntityRenderer<BoomerangEntity> {

    public static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
            DinosExpansion.modLoc("textures/item/wood_boomerang.png"),
            DinosExpansion.modLoc("textures/item/iron_boomerang.png"),
            DinosExpansion.modLoc("textures/item/diamond_boomerang.png")};

    private final ItemRenderer itemRenderer;

    protected BoomerangRenderer(EntityRendererManager renderManager, ItemRenderer itemRenderer) {
        super(renderManager);
        this.itemRenderer = itemRenderer;
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.8F;
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
        Item renderItem = entity.getRenderedItemStack().getItem();
        System.out.println(renderItem.getRegistryName().toString());
        /*if(renderItem == ItemInit.WOOD_BOOMERANG.get())
            return TEXTURES[0];
        if(renderItem == ItemInit.IRON_BOOMERANG.get()) {
            return TEXTURES[1];
        }
        if(renderItem == ItemInit.DIAMOND_BOOMERANG.get()) {
            return TEXTURES[2];
        }*/
        return TEXTURES[1];
    }

    public static class Factory implements IRenderFactory<BoomerangEntity> {

        @Override
        public  EntityRenderer<? super BoomerangEntity> createRenderFor(EntityRendererManager manager) {
            return new BoomerangRenderer(manager, Minecraft.getInstance().getItemRenderer());
        }
    }
}
