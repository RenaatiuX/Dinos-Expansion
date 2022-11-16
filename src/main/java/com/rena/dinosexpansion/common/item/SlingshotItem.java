package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class SlingshotItem extends BowItem {

    public static final Predicate<ItemStack> TINY_ROCK = (p_220002_0_) ->
            p_220002_0_.getItem() == ItemInit.TINY_ROCK.get();

    public SlingshotItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    public static float getArrowVelocity(int charge) {
        float f = (float)charge / 10.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return TINY_ROCK;
    }

    @Override
    public Predicate<ItemStack> getAmmoPredicate() {
        return TINY_ROCK;
    }
}
