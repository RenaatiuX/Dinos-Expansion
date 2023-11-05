package com.rena.dinosexpansion.client.renderer.block;

import com.rena.dinosexpansion.client.model.block.MortarModel;
import com.rena.dinosexpansion.common.tileentity.MortarTileEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
@OnlyIn(Dist.CLIENT)
public class MortarRenderer extends GeoBlockRenderer<MortarTileEntity> {

    public MortarRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new MortarModel());
    }
}
