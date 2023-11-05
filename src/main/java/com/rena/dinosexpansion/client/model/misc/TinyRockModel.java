package com.rena.dinosexpansion.client.model.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TinyRockModel extends Model {

    private final ModelRenderer bone;

    public TinyRockModel() {
        super(RenderType::getEntitySolid);
        textureWidth = 16;
        textureHeight = 16;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(8.0F, 24.0F, -8.0F);
        bone.setTextureOffset(0, 4).addBox(-9.0F, -2.0F, 7.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        bone.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
