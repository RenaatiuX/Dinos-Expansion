package com.rena.dinosexpansion.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.model.misc.SpearModel;
import com.rena.dinosexpansion.common.item.TieredSpear;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class DinosExpansionISTER extends ItemStackTileEntityRenderer {

    final SpearModel SPEAR_MODEL = new SpearModel();
    final ResourceLocation texture;

    public DinosExpansionISTER(final String regName) {
        texture = spear(regName + ".png");
    }

    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
            if (stack.getItem() == ItemInit.DIAMOND_SPEAR.get() || stack.getItem() == ItemInit.GOLD_SPEAR.get() || stack.getItem() == ItemInit.STONE_SPEAR.get() || stack.getItem() == ItemInit.IRON_SPEAR.get() || stack.getItem() == ItemInit.WOODEN_SPEAR.get() || stack.getItem() == ItemInit.NETHERITE_SPEAR.get() || stack.getItem() == ItemInit.EMERALD_SPEAR.get()) {
                matrixStack.translate(0.5F, 0.5F, 0.5F);
                if (p_239207_2_ == ItemCameraTransforms.TransformType.GUI || p_239207_2_ == ItemCameraTransforms.TransformType.FIXED || p_239207_2_ == ItemCameraTransforms.TransformType.NONE || p_239207_2_ == ItemCameraTransforms.TransformType.GROUND) {
                    ItemStack tridentInventory = null;
                    if (stack.getItem() == ItemInit.DIAMOND_SPEAR.get()) {
                        tridentInventory = new ItemStack(ItemInit.DIAMOND_SPEAR_INVENTORY.get());
                    } else if (stack.getItem() == ItemInit.GOLD_SPEAR.get()) {
                        tridentInventory = new ItemStack(ItemInit.GOLD_SPEAR_INVENTORY.get());
                    } else if (stack.getItem() == ItemInit.STONE_SPEAR.get()) {
                        tridentInventory = new ItemStack(ItemInit.STONE_SPEAR_INVENTORY.get());
                    } else if (stack.getItem() == ItemInit.IRON_SPEAR.get()) {
                        tridentInventory = new ItemStack(ItemInit.IRON_SPEAR_INVENTORY.get());
                    } else if (stack.getItem() == ItemInit.WOODEN_SPEAR.get()) {
                        tridentInventory = new ItemStack(ItemInit.WOODEN_SPEAR_INVENTORY.get());
                    } else if (stack.getItem() == ItemInit.NETHERITE_SPEAR.get()) {
                        tridentInventory = new ItemStack(ItemInit.NETHERITE_SPEAR_INVENTORY.get());
                    } else if (stack.getItem() == ItemInit.EMERALD_SPEAR.get()) {
                        tridentInventory = new ItemStack(ItemInit.EMERALD_SPEAR_INVENTORY.get());
                    }
                    if (stack.isEnchanted()) {
                        ListNBT enchantments = stack.getTag().getList("Enchantments", 10);
                        tridentInventory.setTagInfo("Enchantments", enchantments);
                    }
                    Minecraft.getInstance().getItemRenderer().renderItem(tridentInventory, p_239207_2_, p_239207_2_ == ItemCameraTransforms.TransformType.GROUND ? combinedLight : 240, combinedOverlay, matrixStack, buffer);
                } else {
                    matrixStack.push();
                    matrixStack.scale(-1.0F, -1.0F, 1.0F);
                    IVertexBuilder vertexBuilder = ItemRenderer.getEntityGlintVertexBuilder(buffer, RenderType.getEntityCutout(texture), false, stack.hasEffect());
                    SPEAR_MODEL.render(matrixStack, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                    matrixStack.pop();
                }
            }
    }



    private static ResourceLocation spear(String name){
        return DinosExpansion.modLoc("textures/entity/misc/" + name);
    }
}
