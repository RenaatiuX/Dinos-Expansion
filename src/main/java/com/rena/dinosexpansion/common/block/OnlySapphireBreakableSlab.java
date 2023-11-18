package com.rena.dinosexpansion.common.block;

import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class OnlySapphireBreakableSlab extends SlabBlock {
    public OnlySapphireBreakableSlab(Properties p_i48331_1_) {
        super(p_i48331_1_);
    }

    @Override
    public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
        float f = state.getBlockHardness(worldIn, pos);
        ItemStack tool = player.inventory.getCurrentItem();
        if (f == -1.0F || !ModTags.Items.CAN_DESTROY_FUTURISTIC_BLOCKS.contains(tool.getItem())) {
            return 0.0F;
        } else {
            int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100;
            return player.getDigSpeed(state, pos) / f / (float)i;
        }
    }
}
