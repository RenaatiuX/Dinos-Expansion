package com.rena.dinosexpansion.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class CustomBlockBucket extends Item {

    private final Supplier<Block> blockSupplier;

    public CustomBlockBucket(Properties properties, Supplier<Block> blockSupplier) {
        super(properties);
        this.blockSupplier = blockSupplier;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BlockPos pos = playerIn.getPosition().offset(playerIn.getHorizontalFacing());
        if (worldIn.isAirBlock(pos)) {
            worldIn.setBlockState(pos, blockSupplier.get().getDefaultState());
            playerIn.setHeldItem(handIn, new ItemStack(Items.BUCKET));
            return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
        } else {
            return new ActionResult<>(ActionResultType.FAIL, playerIn.getHeldItem(handIn));
        }
    }
}
