package com.rena.dinosexpansion.common.item.armor;

import com.rena.dinosexpansion.common.entity.DinosaurArmorSlotType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.UUID;

public interface DinosaurArmorMaterial {

    int getDurability(DinosaurArmorSlotType slotIn);

    int getDamageReductionAmount(DinosaurArmorSlotType slotIn);

    UUID getArmorModifierUUID(DinosaurArmorSlotType slot);

    int getEnchantability();

    default SoundEvent getSoundEvent(){
        return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
    }

    Ingredient getRepairMaterial();

    @OnlyIn(Dist.CLIENT)
    String getName();

    float getToughness();

    /**
     * Gets the percentage of knockback resistance provided by armor of the material.
     */
    float getKnockbackResistance();
    DinosaurArmorSlotType[] getArmorPieces();
}
