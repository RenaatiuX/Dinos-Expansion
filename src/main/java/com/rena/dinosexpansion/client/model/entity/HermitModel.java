package com.rena.dinosexpansion.client.model.entity;

import com.google.common.collect.Lists;
import com.rena.dinosexpansion.common.entity.Hermit;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class HermitModel extends SegmentedModel<Hermit> {

    protected ModelRenderer villagerHead;
    protected ModelRenderer hat;
    protected final ModelRenderer hatBrim;
    protected final ModelRenderer villagerBody;
    protected final ModelRenderer clothing;
    protected final ModelRenderer villagerArms;
    protected final ModelRenderer rightVillagerLeg;
    protected final ModelRenderer leftVillagerLeg;
    protected final ModelRenderer villagerNose;
    
    public HermitModel(){
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
    public void setRotationAngles(Hermit entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        
    }
}
