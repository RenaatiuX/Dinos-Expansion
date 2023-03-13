package com.rena.dinosexpansion.client.renderer.block;

import com.rena.dinosexpansion.client.model.block.MortarModel;
import com.rena.dinosexpansion.common.tileentity.MortarTileEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class MortarRenderer extends GeoBlockRenderer<MortarTileEntity> {

    public MortarRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new MortarModel());
    }
}
