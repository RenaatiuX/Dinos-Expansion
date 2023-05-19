package com.rena.dinosexpansion.client.model.entity;

import com.google.common.collect.Lists;
import com.rena.dinosexpansion.common.entity.villagers.caveman.Caveman;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.util.math.MathHelper;

public class CavemanModel extends SegmentedModel<Caveman> {

    protected ModelRenderer villagerHead;
    protected ModelRenderer hat;
    protected final ModelRenderer hatBrim;
    protected final ModelRenderer villagerBody;
    protected final ModelRenderer clothing;
    protected final ModelRenderer villagerArms;
    protected final ModelRenderer rightVillagerLeg;
    protected final ModelRenderer leftVillagerLeg;
    protected final ModelRenderer villagerNose;

    public CavemanModel(){
        float f = 0.5F;
        this.villagerHead = (new ModelRenderer(this)).setTextureSize(64, 64);
        this.villagerHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0);
        this.hat = (new ModelRenderer(this)).setTextureSize(64, 64);
        this.hat.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hat.setTextureOffset(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0 + 0.5F);
        this.villagerHead.addChild(this.hat);
        this.hatBrim = (new ModelRenderer(this)).setTextureSize(64, 64);
        this.hatBrim.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hatBrim.setTextureOffset(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F, 0);
        this.hatBrim.rotateAngleX = (-(float)Math.PI / 2F);
        this.hat.addChild(this.hatBrim);
        this.villagerNose = (new ModelRenderer(this)).setTextureSize(64, 64);
        this.villagerNose.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.villagerNose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, 0);
        this.villagerHead.addChild(this.villagerNose);
        this.villagerBody = (new ModelRenderer(this)).setTextureSize(64, 64);
        this.villagerBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.villagerBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, 0);
        this.clothing = (new ModelRenderer(this)).setTextureSize(64, 64);
        this.clothing.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.clothing.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, 0 + 0.5F);
        this.villagerBody.addChild(this.clothing);
        this.villagerArms = (new ModelRenderer(this)).setTextureSize(64, 64);
        this.villagerArms.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.villagerArms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0);
        this.villagerArms.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0, true);
        this.villagerArms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F, 0);
        this.rightVillagerLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(64, 64);
        this.rightVillagerLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.rightVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0);
        this.leftVillagerLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(64, 64);
        this.leftVillagerLeg.mirror = true;
        this.leftVillagerLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.leftVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0);
    }
    @Override
    public Iterable<ModelRenderer> getParts() {
        return Lists.newArrayList(this.villagerHead, this.villagerBody, this.rightVillagerLeg, this.leftVillagerLeg, this.villagerArms);
    }

    @Override
    public void setRotationAngles(Caveman entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {;
        boolean flag = entityIn.getShakeHeadTicks() > 0;

        this.villagerHead.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.villagerHead.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        if (flag) {
            this.villagerHead.rotateAngleZ = 0.3F * MathHelper.sin(0.45F * ageInTicks);
            this.villagerHead.rotateAngleX = 0.4F;
        } else {
            this.villagerHead.rotateAngleZ = 0.0F;
        }

        this.villagerArms.rotationPointY = 3.0F;
        this.villagerArms.rotationPointZ = -1.0F;
        this.villagerArms.rotateAngleX = -0.75F;
        this.rightVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.leftVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.rightVillagerLeg.rotateAngleY = 0.0F;
        this.leftVillagerLeg.rotateAngleY = 0.0F;
    }
}
