package com.rena.dinosexpansion.client.renderer.entity;

import com.rena.dinosexpansion.client.model.entity.CeratorsaurusModel;
import com.rena.dinosexpansion.client.renderer.layer.ceratosaurus.CeratosaurusArmorLayer;
import com.rena.dinosexpansion.client.renderer.layer.ceratosaurus.CeratosaurusSaddleLayer;
import com.rena.dinosexpansion.common.entity.terrestrial.Ceratosaurus;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
@OnlyIn(Dist.CLIENT)
public class CeratosaurusRenderer extends GeoEntityRenderer<Ceratosaurus> {
    public CeratosaurusRenderer(EntityRendererManager renderManager) {
        super(renderManager, new CeratorsaurusModel());
        this.addLayer(new CeratosaurusArmorLayer(this));
        this.addLayer(new CeratosaurusSaddleLayer(this));

    }

    @Override
    public ResourceLocation getEntityTexture(Ceratosaurus entity) {
        return this.modelProvider.getTextureLocation(entity);
    }
}
