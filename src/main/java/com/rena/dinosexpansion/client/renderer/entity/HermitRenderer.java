package com.rena.dinosexpansion.client.renderer.entity;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.model.entity.HermitModel;
import com.rena.dinosexpansion.common.entity.Hermit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.VillagerLevelPendantLayer;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

public class HermitRenderer extends MobRenderer<Hermit, HermitModel> {

    public static final ResourceLocation TEXTURE = DinosExpansion.modLoc("textures/entity/hermit.png");

    public HermitRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new HermitModel(), .5f);
        //TODO make custom overlay so it can display its level
        //addLayer(new VillagerLevelPendantLayer(this, (IReloadableResourceManager) Minecraft.getInstance().getResourceManager(), "villager"));
        addLayer(new CrossedArmsItemLayer<>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(Hermit entity) {
        return TEXTURE;
    }
}
