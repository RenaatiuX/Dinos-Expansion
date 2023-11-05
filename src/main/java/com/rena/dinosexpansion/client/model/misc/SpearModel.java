package com.rena.dinosexpansion.client.model.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpearModel extends Model {

    private final ModelRenderer root;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;
    private final ModelRenderer cube_r3;

    public SpearModel() {
        super(RenderType::getEntitySolid);
        textureWidth = 64;
        textureHeight = 64;

        root = new ModelRenderer(this);
        root.setRotationPoint(0.0F, 8.0F, 0.0F);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(0.0F, -1.0F, -1.5F);
        root.addChild(cube_r1);
        setRotationAngle(cube_r1, -1.5708F, 0.0F, 0.0F);
        cube_r1.setTextureOffset(0, 35).addBox(-0.5F, -2.0F, -5.0F, 1.0F, 1.0F, 28.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(0.0F, -10.0F, 0.0F);
        root.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, -1.5708F, -1.5708F);
        cube_r2.setTextureOffset(0, 6).addBox(0.0F, -4.5F, -5.0F, 0.0F, 9.0F, 10.0F, 0.0F, false);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setRotationPoint(0.0F, -10.0F, 0.0F);
        root.addChild(cube_r3);
        setRotationAngle(cube_r3, 1.5708F, 0.0F, 3.1416F);
        cube_r3.setTextureOffset(0, 6).addBox(0.0F, -4.5F, -5.0F, 0.0F, 9.0F, 10.0F, 0.0F, false);
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
