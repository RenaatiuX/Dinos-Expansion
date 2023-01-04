package com.rena.dinosexpansion.client.renderer.entity;

import com.rena.dinosexpansion.client.model.entity.DimorphodonModel;
import com.rena.dinosexpansion.client.renderer.layer.DimorphodonLayer;
import com.rena.dinosexpansion.common.entity.flying.Dimorphodon;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DimorphodonRenderer extends GeoEntityRenderer<Dimorphodon> {
    public DimorphodonRenderer(EntityRendererManager renderManager) {
        super(renderManager, new DimorphodonModel());
        this.addLayer(new DimorphodonLayer(this));
    }

    @Override
    public ResourceLocation getEntityTexture(Dimorphodon entity) {
        return this.modelProvider.getTextureLocation(entity);
    }
}
