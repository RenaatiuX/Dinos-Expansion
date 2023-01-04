package com.rena.dinosexpansion.core.init;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.merchant.villager.VillagerTrades;

public class ModVillagerTrades {
    /**
     * if you want to add custom trades here, do it in the {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent}
     */
    public static Int2ObjectMap<VillagerTrades.ITrade[]> HERMIT_TRADES;


    public static Int2ObjectMap<VillagerTrades.ITrade[]> gatAsIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }
}
