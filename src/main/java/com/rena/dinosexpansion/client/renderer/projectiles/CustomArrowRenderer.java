package com.rena.dinosexpansion.client.renderer.projectiles;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.projectile.CustomArrow;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TippedArrowRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.TippedArrowItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class CustomArrowRenderer extends ArrowRenderer<CustomArrow> {

    public static final Supplier<Map<Item, ResourceLocation>> TEXTURE_REGISTRY = () -> Util.make(Maps.newHashMap(), map -> {
        map.put(ItemInit.COMPOUND_ARROW.get(), DinosExpansion.modLoc("textures/entity/projectile/compound_arrow_model.png"));
        map.put(ItemInit.TRANQUILLIZER_ARROW.get(), DinosExpansion.modLoc("textures/entity/projectile/tranquilizer_arrow_model.png"));
    });

    public CustomArrowRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public ResourceLocation getEntityTexture(CustomArrow entity) {
        ResourceLocation texture = TEXTURE_REGISTRY.get().getOrDefault(entity.getArrowStack().getItem(), TippedArrowRenderer.RES_ARROW);
        return texture;
    }
}
