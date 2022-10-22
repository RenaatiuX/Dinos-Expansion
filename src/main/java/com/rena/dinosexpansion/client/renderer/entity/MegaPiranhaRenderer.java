package com.rena.dinosexpansion.client.renderer.entity;

import com.rena.dinosexpansion.client.model.entity.MegaPiranhaModel;
import com.rena.dinosexpansion.common.entity.aquatic.MegaPiranha;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MegaPiranhaRenderer extends GeoEntityRenderer<MegaPiranha> {

    public MegaPiranhaRenderer(EntityRendererManager renderManager) {
        super(renderManager, new MegaPiranhaModel());
    }

    @Override
    public ResourceLocation getEntityTexture(MegaPiranha entity) {
        return this.modelProvider.getTextureLocation(entity);
    }
}
