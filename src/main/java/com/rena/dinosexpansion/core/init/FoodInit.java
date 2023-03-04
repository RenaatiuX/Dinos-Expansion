package com.rena.dinosexpansion.core.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

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
    public static final Food RAW_DIMORPHODON_MEAT = new Food.Builder().hunger(3)
            .saturation(0.3F).meat().build();
    public static final Food COOKED_DIMORPHODON_MEAT = new Food.Builder().hunger(5)
            .saturation(0.5F).meat().build();
    public static final Food RAW_ASTORGOSUCHUS_MEAT = new Food.Builder().hunger(3)
            .saturation(0.3F).meat().build();
    public static final Food COOKED_ASTORGOSUCHUS_MEAT = new Food.Builder().hunger(8)
            .saturation(0.8F).meat().build();
    public static final Food RAW_PARAPUZOSIA_TENTACLE = new Food.Builder().hunger(3)
            .saturation(0.3F).meat().build();
    public static final Food COOKED_PARAPUZOSIA_TENTACLE = new Food.Builder().hunger(8)
            .saturation(0.8F).meat().build();
    public static final Food RAW_DRYOSAURUS_MEAT = new Food.Builder().hunger(3)
            .saturation(0.3F).meat().build();
    public static final Food COOKED_DRYOSAURUS_MEAT = new Food.Builder().hunger(8)
            .saturation(0.8F).meat().build();

    public static final Food SPECIAL_FRUIT = new Food.Builder().hunger(0)
            .saturation(0.0F).setAlwaysEdible().build();
    public static final Food NARCOTIC_BERRIES = new Food.Builder().hunger(2)
            .saturation(0.1F)
            .effect(() -> new EffectInstance(Effects.HUNGER, 10*20, 2), 1)
            .effect(() -> new EffectInstance(Effects.NAUSEA, 10*10, 2), 1).build();
    public static final Food EGGPLANT = new Food.Builder().hunger(3)
            .saturation(0.6F).build();
    public static final Food ONION = new Food.Builder().hunger(3)
            .saturation(0.6F).build();
    public static final Food SPINACH = new Food.Builder().hunger(3)
            .saturation(0.6F).build();
    public static final Food TOMATO = new Food.Builder().hunger(3)
            .saturation(0.6F).build();
    public static final Food CORN = new Food.Builder().hunger(3)
            .saturation(0.6F).build();
    public static final Food CUCUMBER = new Food.Builder().hunger(3)
            .saturation(0.6F).build();
    public static final Food LETTUCE = new Food.Builder().hunger(3)
            .saturation(0.6F).build();
    public static final Food KIBBLE_BASIC = kibbleEffects(new Food.Builder().hunger(10).saturation(0)).build();
    public static final Food KIBBLE_SIMPLE = kibbleEffects(new Food.Builder().hunger(15).saturation(0)).build();
    public static final Food KIBBLE_REGULAR = kibbleEffects(new Food.Builder().hunger(20).saturation(0)).build();
    public static final Food KIBBLE_EXCEPTIONAL = kibbleEffects(new Food.Builder().hunger(25).saturation(0)).build();
    public static final Food KIBBLE_SUPERIOR = kibbleEffects(new Food.Builder().hunger(30).saturation(0)).build();
    public static final Food KIBBLE_EXTRAORDINARY = kibbleEffects(new Food.Builder().hunger(40).saturation(0)).build();

    public static Food.Builder kibbleEffects(Food.Builder builder){
        return builder.effect(() -> new EffectInstance(Effects.BLINDNESS, 10*20, 4), 1)
                .effect(() -> new EffectInstance(Effects.SLOWNESS, 10*20, 4), 1)
                .effect(() -> new EffectInstance(Effects.HUNGER, 40*20, 7), 1)
                .effect(() -> new EffectInstance(Effects.NAUSEA, 10*20, 1), 1);
    }
}
