package com.rena.dinosexpansion.client.model.misc;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SteelSpearModel extends Model {

    private final ModelRenderer root;
    private final ModelRenderer add;

    public SteelSpearModel() {
        super(RenderType::getEntitySolid);
        textureWidth = 32;
        textureHeight = 32;

        root = new ModelRenderer(this);
        root.setRotationPoint(0.0F, 0.0F, 0.0F);
        root.setTextureOffset(0, 0).addBox(-0.5F, 6.0F, -0.5F, 1.0F, 17.0F, 1.0F, 0.0F, false);

        add = new ModelRenderer(this);
        add.setRotationPoint(0.0F, 0.0F, 0.0F);
        root.addChild(add);
        add.setTextureOffset(12, 8).addBox(-1.0F, 5.8F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);
        add.setTextureOffset(4, 14).addBox(-1.0F, 22.6F, -1.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
        add.setTextureOffset(4, 8).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 6.0F, 0.0F, 0.0F, false);
        add.setTextureOffset(11, 13).addBox(-2.0F, 24.6F, -0.5F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        add.setTextureOffset(4, 0).addBox(-3.0F, 24.0F, 0.0F, 6.0F, 8.0F, 0.0F, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        root.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
