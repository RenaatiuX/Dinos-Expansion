package com.rena.dinosexpansion.client.renderer.entity;

import com.rena.dinosexpansion.client.model.entity.CavemanModel;
import com.rena.dinosexpansion.common.entity.villagers.caveman.Caveman;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CavemanRenderer extends MobRenderer<Caveman, CavemanModel> {
    public CavemanRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CavemanModel(), .5f);
        //this.addLayer(new BipedArmorLayer<>(this, new CavemanModel(), new CavemanModel()));
    }

    @Override
    public ResourceLocation getEntityTexture(Caveman entity) {
        if (!entity.hasValidType())
            return null;
        return entity.isBoss() ? entity.getTribeType().getBossTexture() : entity.getTribeType().getNormalTexture();
    }
}
