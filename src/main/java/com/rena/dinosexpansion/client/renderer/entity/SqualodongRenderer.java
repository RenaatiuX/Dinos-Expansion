package com.rena.dinosexpansion.client.renderer.entity;

import com.rena.dinosexpansion.client.model.entity.SquaolonModel;
import com.rena.dinosexpansion.common.entity.aquatic.Squalodon;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
@OnlyIn(Dist.CLIENT)
public class SqualodongRenderer extends GeoEntityRenderer<Squalodon> {


    public SqualodongRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SquaolonModel());
    }

    @Override
    public ResourceLocation getEntityTexture(Squalodon entity) {
        return this.modelProvider.getTextureLocation(entity);
    }
}
