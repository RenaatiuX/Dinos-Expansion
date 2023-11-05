package com.rena.dinosexpansion.client.renderer;

import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ClientISTERProvider {

    private static final Map<String, ItemStackTileEntityRenderer> spearMap = new HashMap<>();

    public static ItemStackTileEntityRenderer bakeSpearISTER(final String itemName) {
        if (!spearMap.containsKey(itemName)) {
            spearMap.put(itemName, new DinosExpansionISTER(itemName));
        }
        return spearMap.get(itemName);
    }

}
