package com.rena.dinosexpansion.client.renderer;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.client.renderer.misc.SpearRenderer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ClientISTERProvider {

    private static final Map<String, ItemStackTileEntityRenderer> spearMap = new HashMap<>();

    public static ItemStackTileEntityRenderer bakeSpearISTER(final ResourceLocation itemName) {
        if (!spearMap.containsKey(itemName.toString())) {
            spearMap.put(itemName.toString(), new SpearRenderer.SpearItemStackRenderer(itemName.toString()));
        }
        return spearMap.get(itemName);
    }

    public static ItemStackTileEntityRenderer bakeSpearISTER(final String itemName) {
        return bakeSpearISTER(DinosExpansion.modLoc(itemName));
    }
}
