package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.common.util.enums.PoopSize;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

import static net.minecraftforge.common.util.FakePlayerFactory.getMinecraft;
import static net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal;

public class PoopItem extends Item {

    private static PoopSize poopEffect;
    public PoopItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        return super.onItemUse(context);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (worldIn.isRemote) {
            Minecraft mc = Minecraft.getInstance();
            mc.gameSettings.smoothCamera = !mc.gameSettings.smoothCamera;
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Deprecated //Forge: Use Player/Hand version
    public static boolean applyPoop(ItemStack stack, World worldIn, BlockPos pos) {
        if (worldIn instanceof ServerWorld)
            return applyPoop(stack, worldIn, pos, getMinecraft((ServerWorld)worldIn));
        return false;
    }

    public static boolean applyPoop(ItemStack stack, World worldIn, BlockPos pos, PlayerEntity player) {
        BlockState blockstate = worldIn.getBlockState(pos);
        int hook = onApplyBonemeal(player, worldIn, pos, blockstate, stack);
        if (hook != 0) return hook > 0;
        if (blockstate.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable)blockstate.getBlock();
            if (igrowable.canGrow(worldIn, pos, blockstate, worldIn.isRemote)) {
                if (worldIn instanceof ServerWorld) {
                    if (igrowable.canUseBonemeal(worldIn, worldIn.rand, pos, blockstate)) {
                        igrowable.grow((ServerWorld)worldIn, worldIn.rand, pos, blockstate);
                    }

                    stack.shrink(1);
                }

                return true;
            }
        }

        return false;
    }
}
