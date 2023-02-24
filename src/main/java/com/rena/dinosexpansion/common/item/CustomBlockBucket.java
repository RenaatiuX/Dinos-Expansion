package com.rena.dinosexpansion.common.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
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
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos pos = context.getPos();
        if (context.getWorld().isAirBlock(pos.up())){
            context.getWorld().setBlockState(pos.up(), blockSupplier.get().getDefaultState());
            context.getPlayer().setHeldItem(context.getHand(), new ItemStack(Items.BUCKET));
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }
}
