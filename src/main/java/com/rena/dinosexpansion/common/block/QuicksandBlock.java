package com.rena.dinosexpansion.common.block;

import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class QuicksandBlock extends Block {
    public QuicksandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos pos, Entity entity) {
        entity.setMotionMultiplier(blockState, new Vector3d(0.25D, 0.075D, 0.25D));
        if (entity instanceof LivingEntity && entity.isAlive() && entity.getPosYEye() < (double)(pos.getY() + 1)) {
            entity.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }
    }

    @Override
    public boolean shouldDisplayFluidOverlay(BlockState state, IBlockDisplayReader world, BlockPos pos, FluidState fluidState) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemStack = player.getHeldItem(handIn);

        if (itemStack.getItem() == Items.BUCKET) {
            // Atrapar el bloque en una cubeta
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
            itemStack.shrink(1);
            player.addItemStackToInventory(new ItemStack(ItemInit.QUICKSAND_BUCKET.get()));
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }
}
