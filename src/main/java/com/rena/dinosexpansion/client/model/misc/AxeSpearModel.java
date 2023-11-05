package com.rena.dinosexpansion.client.model.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AxeSpearModel extends Model {
    private final ModelRenderer root;
    private final ModelRenderer cube_r1;
    public AxeSpearModel() {
        super(RenderType::getEntitySolid);
        textureWidth = 128;
        textureHeight = 128;

        root = new ModelRenderer(this);
        root.setRotationPoint(0.0F, 24.0F, 0.0F);


        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(0.0F, -18.0F, -1.0F);
        root.addChild(cube_r1);
        setRotationAngle(cube_r1, -1.5708F, 0.0F, 0.0F);
        cube_r1.setTextureOffset(47, 1).addBox(-1.0F, -1.5F, -17.0F, 4.0F, 1.0F, 10.0F, 0.0F, false);
        cube_r1.setTextureOffset(13, 0).addBox(-3.0F, -1.0F, -22.0F, 9.0F, 0.0F, 17.0F, 0.0F, false);
        cube_r1.setTextureOffset(0, 0).addBox(-1.0F, -1.5F, -10.0F, 1.0F, 1.0F, 28.0F, 0.01F, false);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        root.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
