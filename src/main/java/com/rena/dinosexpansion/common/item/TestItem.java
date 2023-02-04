package com.rena.dinosexpansion.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TestItem extends Item {
    private static final int COOLDOWN = 20 * 10; // 20 ticks por segundo, 10 segundos
    private static final double DASH_LENGTH = 1.5D;

    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        // Lógica para el dash
        if (!playerIn.isSneaking()) {
            if (playerIn.getCooledAttackStrength(0.5F) == 1.0F) {
                playerIn.resetCooldown();
                playerIn.addExhaustion(0.3F);
                playerIn.setMotion(playerIn.getMotion().add(playerIn.getLookVec().scale(DASH_LENGTH)));
                playerIn.getCooldownTracker().setCooldown(this, COOLDOWN);
                playerIn.getHeldItem(handIn).damageItem(1, playerIn, e -> e.sendBreakAnimation(playerIn.getActiveHand()));
            }
        }
        // Devuelve el resultado
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
