package com.rena.dinosexpansion.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.rena.dinosexpansion.common.item.util.INarcotic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TieredClub extends Item implements INarcotic {

    private final ClubTier clubTier;
    private final float damage;
    private final int narcoticValue;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public TieredClub(Properties properties, ClubTier clubTier, int damage, int narcoticValue) {
        super(properties.maxDamage(clubTier.getDurability()));
        this.clubTier = clubTier;
        this.damage = damage + clubTier.getDamageAddition();
        this.narcoticValue = narcoticValue;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.damage, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public float getDamage() {
        return damage;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (entity) -> {
            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public int getNarcoticValue() {
        return this.narcoticValue;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }

    public interface ClubTier{

        int getDamageAddition();
        int getEnchantability();
        int getDurability();

    }
}
