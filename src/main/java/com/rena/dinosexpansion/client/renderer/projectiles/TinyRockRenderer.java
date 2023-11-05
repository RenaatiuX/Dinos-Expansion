package com.rena.dinosexpansion.client.renderer.projectiles;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.model.misc.TinyRockModel;
import com.rena.dinosexpansion.common.entity.projectile.TinyRockEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TinyRockRenderer extends EntityRenderer<TinyRockEntity> {
    public static final ResourceLocation TEXTURE = DinosExpansion.modLoc("textures/entity/projectile/tiny_rock.png");
    private final TinyRockModel ROCK_MODEL = new TinyRockModel();

    public TinyRockRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(TinyRockEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch)));
        matrixStackIn.translate(-.2d, -1.4d, 0);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutout(this.getEntityTexture(entityIn)));
        ROCK_MODEL.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(TinyRockEntity entity) {
        return TEXTURE;
    }

}
