package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.common.entity.misc.GlowStickEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GlowStickItem extends Item {
    public GlowStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote()) {
            GlowStickEntity glowStickEntity = new GlowStickEntity(worldIn, playerIn);
            glowStickEntity.setItem(itemStack);
            glowStickEntity.setDirectionAndMovement(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 0F);
            worldIn.addEntity(glowStickEntity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.isCreative()) {
            itemStack.shrink(1);
        }

        return ActionResult.resultSuccess(itemStack);
    }
}
