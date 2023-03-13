package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BretzelItem extends Item {
    public BretzelItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (worldIn.isRemote) {
            Minecraft mc = Minecraft.getInstance();
            mc.gameSettings.smoothCamera = !mc.gameSettings.smoothCamera;
            playerIn.getPersistentData().putFloat(DinosExpansion.MOD_ID + "prevFov", mc.gameSettings.fovScaleEffect);
            mc.gameSettings.fovScaleEffect = mc.gameSettings.fovScaleEffect + .5f;
            if (mc.gameSettings.fovScaleEffect > 110) {
                mc.gameSettings.fovScaleEffect = 110;
            }
        }
        //playerIn.setActiveHand(handIn);
        //CompoundNBT nbt = playerIn.getPersistentData();
        //nbt.putBoolean(DinosExpansion.MOD_ID + "use_spyglass", true);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 1200;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        //CompoundNBT nbt = entityLiving.getPersistentData();
        //if (nbt.contains(DinosExpansion.MOD_ID + "use_spyglass"))
            //nbt.putBoolean(DinosExpansion.MOD_ID + "use_spyglass", false);
        if (worldIn.isRemote) {
            Minecraft mc = Minecraft.getInstance();
            mc.gameSettings.smoothCamera = false;
            if (entityLiving.getPersistentData().contains(DinosExpansion.MOD_ID + "prevFov")){
                mc.gameSettings.fovScaleEffect = entityLiving.getPersistentData().getFloat(DinosExpansion.MOD_ID + "prevFov");
                entityLiving.getPersistentData().remove(DinosExpansion.MOD_ID + "prevFov");
            }
        }
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }
}
