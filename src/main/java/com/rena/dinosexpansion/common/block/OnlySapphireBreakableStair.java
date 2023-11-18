package com.rena.dinosexpansion.common.block;

import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import java.util.function.Supplier;

public class OnlySapphireBreakableStair extends StairsBlock {
    public OnlySapphireBreakableStair(Supplier<BlockState> p_i244789_1_, Properties p_i244789_2_) {
        super(p_i244789_1_, p_i244789_2_);
    }

    public OnlySapphireBreakableStair(Supplier<BlockState> stateSupplier) {
        this(stateSupplier, AbstractBlock.Properties.from(stateSupplier.get().getBlock()));
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
