package com.rena.dinosexpansion.core.init;

import net.minecraft.item.Food;

public class FoodInit {

    //food
    public static final Food RAW_CARNOTAURUS_MEAT = new Food.Builder().hunger(3)
            .saturation(0.3F).meat().build();
    public static final Food COOKED_CARNOTAURUS_MEAT = new Food.Builder().hunger(8)
            .saturation(0.8F).meat().build();
    public static final Food RAW_ANKYLOSAURUS_MEAT = new Food.Builder().hunger(3)
            .saturation(0.3F).meat().build();
    public static final Food COOKED_ANKYLOSAURUS_MEAT = new Food.Builder().hunger(8)
            .saturation(0.8F).meat().build();
    public static final Food RAW_TRICERATOPS_MEAT = new Food.Builder().hunger(3)
            .saturation(0.3F).meat().build();
    public static final Food COOKED_TRICERATOPS_MEAT = new Food.Builder().hunger(8)
            .saturation(0.8F).meat().build();
    public static final Food RAW_GALLIMIMUS_MEAT = new Food.Builder().hunger(3)
            .saturation(0.3F).meat().build();
    public static final Food COOKED_GALLIMIMUS_MEAT = new Food.Builder().hunger(8)
            .saturation(0.8F).meat().build();
}
