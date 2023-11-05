package com.rena.dinosexpansion.client.renderer.entity;

import com.rena.dinosexpansion.client.model.entity.AstorgosuchusModel;
import com.rena.dinosexpansion.common.entity.semiaquatic.Astorgosuchus;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
@OnlyIn(Dist.CLIENT)
public class AstorgosuchusRenderer extends GeoEntityRenderer<Astorgosuchus> {
    public AstorgosuchusRenderer(EntityRendererManager renderManager) {
        super(renderManager, new AstorgosuchusModel());
    }

    @Override
    public ResourceLocation getEntityTexture(Astorgosuchus entity) {
        return this.modelProvider.getTextureLocation(entity);
    }
}
