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
        map.put(ItemInit.COMPOUND_ARROW.get(),projectile("compound_arrow.png"));
        map.put(ItemInit.TRANQUILLIZER_ARROW.get(), projectile("tranquilizer_arrow.png"));
        map.put(ItemInit.BONE_ARROW.get(), projectile("bone_arrow.png"));
        map.put(ItemInit.DIAMOND_ARROW.get(), projectile("diamond_arrow.png"));
        map.put(ItemInit.EMERALD_ARROW.get(), projectile("emerald_arrow.png"));
        map.put(ItemInit.GOLD_ARROW.get(), projectile("golden_arrow.png"));
        map.put(ItemInit.IORN_ARROW.get(), projectile("iron_arrow.png"));
        map.put(ItemInit.STONE_ARROW.get(), projectile("stone_arrow.png"));
        map.put(ItemInit.WOODEN_ARROW.get(), projectile("wooden_arrow.png"));
        map.put(ItemInit.NETHERITE_ARROW.get(), projectile("netherite_arrow.png"));
    });

    public CustomArrowRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public ResourceLocation getEntityTexture(CustomArrow entity) {
        ResourceLocation texture = TEXTURE_REGISTRY.get().getOrDefault(entity.getArrowStack().getItem(), TippedArrowRenderer.RES_ARROW);
        return texture;
    }

    private static ResourceLocation projectile(String name){
        return DinosExpansion.modLoc("textures/entity/projectile/" + name);
    }
}
