package com.rena.dinosexpansion.client.renderer.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.aquatic.Anomalocaris;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class AnomalocarisHelItemLayer extends GeoLayerRenderer<Anomalocaris> {

    public AnomalocarisHelItemLayer(IGeoRenderer<Anomalocaris> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, Anomalocaris entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entityLivingBaseIn.isGrabbing() && !entityLivingBaseIn.hasHeldItem().isEmpty()){
            ItemStack heldItem = entityLivingBaseIn.hasHeldItem();
            RenderType renderType = RenderType.getCutout();
            matrixStackIn.push();
            //item render code in a minute with better comments and a detailed explanation
            //this basically gets u the model how it is saved in minecraft
            //this also takes in repsect of the model has overrides like the bow/crossbow or clock
            IBakedModel ibakedmodel = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(heldItem, entityLivingBaseIn.world, entityLivingBaseIn);
            //this actually renders the item then in the world
            //the itemstack here is the item stack that u want to render it must be the same as where u got the baked model from otherwise i dont know what will happen
            //CameraTransform defines that the item looks like when u throw it to the ground, well u can also render it like it is in the inventory(thats not usefule here but maybe for u in the future)
            Minecraft.getInstance().getItemRenderer().renderItem(heldItem, ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, ibakedmodel);
            matrixStackIn.pop();
        }
    }
}
