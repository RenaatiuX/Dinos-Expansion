package com.rena.dinosexpansion.common.events;

import com.rena.dinosexpansion.api.ArmorSlotItemApi;
import net.minecraft.item.Items;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class DinoArmorRegistration {

    public static void armorRegistration(FMLCommonSetupEvent event){
        ArmorSlotItemApi.registerSaddle(Items.SADDLE);
        ArmorSlotItemApi.registerChest(Items.CHEST, 27);
        ArmorSlotItemApi.registerChest(Items.BARREL, 10);
    }
}
