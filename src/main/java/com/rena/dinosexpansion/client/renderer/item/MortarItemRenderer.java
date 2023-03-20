package com.rena.dinosexpansion.client.renderer.item;

import com.rena.dinosexpansion.client.model.item.MortarModel;
import com.rena.dinosexpansion.common.block.AnimatedBlockItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class MortarItemRenderer extends GeoItemRenderer<AnimatedBlockItem> {

    public MortarItemRenderer() {
        super(new MortarModel());
    }
}
