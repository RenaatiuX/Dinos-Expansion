package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.common.entity.misc.BoomerangEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TieredBoomerang extends Item {

    private final BoomerangSupplier supplier;

    public TieredBoomerang(Properties properties, BoomerangSupplier supplier) {
        super(properties);
        this.supplier = supplier;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        BoomerangEntity boom = supplier.createBoomerang(worldIn, playerIn, playerIn.getHeldItem(handIn), handIn);
        BlockPos currentPos = playerIn.getPosition();
        //worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOOMERANG_THROW.get(), SoundCategory.PLAYERS, 0.6F, 1.0F);
        worldIn.addEntity(boom);
        playerIn.setHeldItem(handIn, ItemStack.EMPTY);
        stack.damageItem(1, playerIn, p -> p.sendBreakAnimation(handIn));
        showDurabilityBar(playerIn.getHeldItem(handIn));
        playerIn.setActiveHand(handIn);

        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));    }

    public interface BoomerangSupplier{
        BoomerangEntity createBoomerang(World world, PlayerEntity player, ItemStack stack, Hand hand);
    }
}
