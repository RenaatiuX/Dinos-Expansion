package com.rena.dinosexpansion.client.renderer.entity;

import com.rena.dinosexpansion.client.model.entity.CampanileModel;
import com.rena.dinosexpansion.common.entity.terrestrial.ambient.Campanile;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
@OnlyIn(Dist.CLIENT)
public class CampanileRenderer extends GeoEntityRenderer<Campanile> {
    public CampanileRenderer(EntityRendererManager renderManager) {
        super(renderManager, new CampanileModel());
    }

    @Override
    public ResourceLocation getEntityTexture(Campanile entity) {
        return this.modelProvider.getTextureLocation(entity);
    }
}
