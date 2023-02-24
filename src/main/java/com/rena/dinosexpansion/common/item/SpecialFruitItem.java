package com.rena.dinosexpansion.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.UUID;

public class SpecialFruitItem extends Item {
    private static final double MAX_HEALTH = 40;
    private static final double HEALTH_BOOST = 1D;

    public SpecialFruitItem(Properties properties) {
        super(properties);
    }

    //TODO need review
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isRemote && entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;
            double maxHealth = player.getAttribute(Attributes.MAX_HEALTH).getValue();
            if (maxHealth + HEALTH_BOOST <= MAX_HEALTH) {
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth + HEALTH_BOOST);
                player.setHealth((float) (player.getHealth() + HEALTH_BOOST));
            }
        }
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
