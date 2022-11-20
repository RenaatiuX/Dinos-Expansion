package com.rena.dinosexpansion.client.renderer.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.model.misc.SpearModel;
import com.rena.dinosexpansion.client.model.misc.SteelSpearModel;
import com.rena.dinosexpansion.common.entity.misc.SpearEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class SpearRenderer extends EntityRenderer<SpearEntity> {

    //public static final ResourceLocation STEEL_SPEAR = DinosExpansion.modLoc("textures/entity/steel_spear.png");
    //private final SteelSpearModel steelSpearModel = new SteelSpearModel();
    public static final ResourceLocation SPEAR = spear("wooden_spear.png");
    private static final SpearModel spearModel = new SpearModel();

    public SpearRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(SpearEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch) + 90.0F));
        IVertexBuilder ivertexbuilder = ItemRenderer.getEntityGlintVertexBuilder(bufferIn, RenderType.getEntityCutout(this.getEntityTexture(entityIn)), false, entityIn.isEnchanted());
        spearModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(SpearEntity entity) {
        return SPEAR;
    }

    public static class SpearItemStackRenderer extends ItemStackTileEntityRenderer {

        public SpearItemStackRenderer(final String name) {
        }

        @Override
        public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
            matrixStack.push();
            matrixStack.scale(-1.0F, -1.0F, 1.0F);
            IVertexBuilder vertexBuilder = ItemRenderer.getEntityGlintVertexBuilder(buffer, RenderType.getEntityCutout(SPEAR), false, stack.hasEffect());
            spearModel.render(matrixStack, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStack.pop();
        }
    }

    private static ResourceLocation spear(String name){
        return DinosExpansion.modLoc("textures/entity/misc/" + name);
    }

}
