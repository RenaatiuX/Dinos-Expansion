package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.common.entity.misc.CustomEggEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class CustomEggItem extends Item {
    private final Supplier<Item> egg;
    public CustomEggItem(Properties properties, Supplier<Item> egg) {
        super(properties);
        this.egg = egg;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            CustomEggEntity customEgg = new CustomEggEntity(worldIn, playerIn, egg);
            customEgg.setItem(itemStack);
            customEgg.setDirectionAndMovement(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.addEntity(customEgg);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            itemStack.shrink(1);
        }

        return ActionResult.func_233538_a_(itemStack, worldIn.isRemote());
    }
}
