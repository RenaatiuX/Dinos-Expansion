package com.rena.dinosexpansion.client.renderer.entity;

import com.rena.dinosexpansion.client.model.entity.EosqualodonModel;
import com.rena.dinosexpansion.common.entity.aquatic.Eosqualodon;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EosqualodonRenderer extends GeoEntityRenderer<Eosqualodon> {

    public EosqualodonRenderer(EntityRendererManager renderManager) {
        super(renderManager, new EosqualodonModel());
    }

    @Override
    public ResourceLocation getEntityTexture(Eosqualodon entity) {
        return this.modelProvider.getTextureLocation(entity);
    }
}
