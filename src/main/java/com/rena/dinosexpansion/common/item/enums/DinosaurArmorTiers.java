package com.rena.dinosexpansion.common.item.enums;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.common.entity.DinosaurArmorSlotType;
import com.rena.dinosexpansion.common.item.armor.DinosaurArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.*;
import java.util.function.Supplier;

public enum DinosaurArmorTiers implements DinosaurArmorMaterial {
    IRON_DINO_ARMOR(new DinosaurArmorSlotType[]{DinosaurArmorSlotType.HEAD, DinosaurArmorSlotType.CHEST, DinosaurArmorSlotType.FEET, DinosaurArmorSlotType.LEG}, new int[]{15*13*2, 15*15*2, 15*16*2, 15*11*2}, new double[]{2d, 6d, 5d, 2d}, 9, .1f, .1f, () -> Ingredient.fromItems(Items.IRON_INGOT), "iron_dino_armor");

    protected final int enchantability;
    protected final float toughness, knockbackResistence;

    protected final Map<DinosaurArmorSlotType, Pair<Integer, Double>> durabilityDamageReduction;


    private final Supplier<Ingredient> repairMaterial;

    protected final String name;

    DinosaurArmorTiers(DinosaurArmorSlotType[] supportedSlots, int[] durability, double[] damageReduction, int enchantability, float toughness, float knockbackResistence, Supplier<Ingredient> repairMaterial, String name) {
        this.enchantability = enchantability;
        this.toughness = toughness;
        this.knockbackResistence = knockbackResistence;
        this.repairMaterial = repairMaterial;
        this.name = name;
        this.durabilityDamageReduction = makeMap(supportedSlots, durability, damageReduction);
    }

    protected static Map<DinosaurArmorSlotType, Pair<Integer, Double>> makeMap(DinosaurArmorSlotType[] supportedSlots, int[] durability, double[] damageReduction) {
        if (supportedSlots.length != durability.length || durability.length != damageReduction.length)
            throw new IllegalArgumentException(String.format("supported slots with lenth %s and durability with length %s and damageReduction with length %s arent all equal", supportedSlots.length, durability.length, damageReduction.length));
        Map<DinosaurArmorSlotType, Pair<Integer, Double>> durabilityDamageReduction = new HashMap<>();
        for (int i = 0; i < supportedSlots.length; i++) {
            durabilityDamageReduction.put(supportedSlots[i], Pair.of(durability[i], damageReduction[i]));
        }
        return durabilityDamageReduction;
    }

    @Override
    public int getDurability(DinosaurArmorSlotType slotIn) {
        if (!this.durabilityDamageReduction.containsKey(slotIn))
            return 0;
        return this.durabilityDamageReduction.get(slotIn).getFirst();
    }

    @Override
    public double getDamageReductionAmount(DinosaurArmorSlotType slotIn) {
        if (!this.durabilityDamageReduction.containsKey(slotIn))
            return 0;
        return this.durabilityDamageReduction.get(slotIn).getSecond();
    }

    @Override
    public UUID getArmorModifierUUID(DinosaurArmorSlotType slot) {
        return slot.getModifierUUID();
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return repairMaterial == null ? null : repairMaterial.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistence;
    }
}
