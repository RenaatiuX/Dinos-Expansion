package com.rena.dinosexpansion.client.renderer.misc;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.model.misc.SpearModel;
import com.rena.dinosexpansion.common.entity.misc.SpearEntity;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Map;
import java.util.function.Supplier;

public class SpearRenderer extends EntityRenderer<SpearEntity> {

    //public static final ResourceLocation STEEL_SPEAR = DinosExpansion.modLoc("textures/entity/steel_spear.png");
    //private final SteelSpearModel steelSpearModel = new SteelSpearModel();
    public static final ResourceLocation SPEAR = spear("wooden_spear.png");
    private static final SpearModel SPEAR_MODEL = new SpearModel();

    public static final Supplier<Map<Item, ResourceLocation>> TEXTURE_REGISTRY = () -> Util.make(Maps.newHashMap(), map -> {

        map.put(ItemInit.WOODEN_SPEAR.get(), spear("wooden_spear.png"));

    });

    public SpearRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(SpearEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch) + 90.0F));
        IVertexBuilder ivertexbuilder = ItemRenderer.getEntityGlintVertexBuilder(bufferIn, RenderType.getEntityCutout(this.getEntityTexture(entityIn)), false, entityIn.isEnchanted());
        SPEAR_MODEL.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(SpearEntity entity) {
        return spear(entity.getArrowStack().getItem().getRegistryName().getPath() + ".png");
    }

    public static class SpearItemStackRenderer extends ItemStackTileEntityRenderer {

        protected final String regName;
        public SpearItemStackRenderer(final String regName) {
            this.regName = regName;
        }

        @Override
        public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
            if (this.regName.equals(stack.getItem().getRegistryName().toString())) {
                matrixStack.push();
                matrixStack.scale(-1.0F, -1.0F, 1.0F);
                IVertexBuilder vertexBuilder = ItemRenderer.getEntityGlintVertexBuilder(buffer, RenderType.getEntityCutout(spear(regName.substring(regName.indexOf(':') + 1))), false, stack.hasEffect());
                SPEAR_MODEL.render(matrixStack, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                matrixStack.pop();
            }
        }
    }

    private static ResourceLocation spear(String name){
        return DinosExpansion.modLoc("textures/entity/misc/" + name);
    }

}
