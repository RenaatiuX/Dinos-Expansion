package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.world.dimension.DinoTeleporter;
import com.rena.dinosexpansion.core.init.DimensionInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.function.Predicate;

public class TeleportedItem extends ShootableItem {


    public static final Predicate<ItemStack> CONSUMABLE = (stack) ->
            stack.getItem().isIn(ModTags.Items.TIME_MACHINE_FUEL);

    public TeleportedItem(Properties builder) {
        super(builder);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entity, int timeLeft) {
        if(entity instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entity;
            boolean flag = playerentity.abilities.isCreativeMode;
            ItemStack itemstack = playerentity.findAmmo(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            if(i < 0) return;

            if(!itemstack.isEmpty() || flag) {
                if(itemstack.isEmpty()) {
                    itemstack = new ItemStack(ModTags.Items.TIME_MACHINE_FUEL.getRandomElement(world.rand));
                }

                float f = getPowerForTime(i);
                if(!((double)f < 0.1D)) {
                    boolean flag1 = playerentity.abilities.isCreativeMode;
                    if(!world.isRemote) {
                        if(!entity.isPassenger() && !entity.isBeingRidden() && entity.canChangeDimension()) {
                            if(entity.world instanceof ServerWorld) {
                                if(entity.world.getDimensionKey() == World.THE_NETHER || entity.world.getDimensionKey() == World.THE_END) {
                                    entity.sendMessage(new TranslationTextComponent(DinosExpansion.MOD_ID+ ".time_machine.not_works", entity.world.getDimensionKey().getRegistryName()), entity.getUniqueID());
                                }

                                if(entity.world instanceof ServerWorld) {
                                    ServerWorld serverworld = (ServerWorld)entity.world;
                                    MinecraftServer minecraftserver = serverworld.getServer();
                                    RegistryKey<World> registrykey = entity.world.getDimensionKey() == DimensionInit.DINO_DIMENSION ? World.OVERWORLD : DimensionInit.DINO_DIMENSION;
                                    ServerWorld serverworld1 = minecraftserver.getWorld(registrykey);
                                    if(serverworld1 != null && !entity.isPassenger())
                                    {
                                        playerentity.changeDimension(serverworld1, new DinoTeleporter());
                                        if(registrykey.equals(DimensionInit.DINO_DIMENSION)) {
                                            entity.sendMessage(DinosExpansion.translatable("timeMachine", "transport_to_dinoWorld"), entity.getUniqueID());
                                        }
                                        else
                                        {
                                            entity.sendMessage(DinosExpansion.translatable("timeMachine", "transport_to_overworld"), entity.getUniqueID());
                                        }
                                    }
                                }
                            }
                        }
                    }

                    world.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.BLOCK_PORTAL_TRAVEL, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if(!flag1 && !playerentity.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }
                    playerentity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        ItemStack ammo = playerIn.findAmmo(itemstack);
        if (!playerIn.abilities.isCreativeMode && ammo.isEmpty()) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
    }

    public static float getPowerForTime(int time) {
        float f = (float) time / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return CONSUMABLE;
    }

    @Override
    public int func_230305_d_() {
        return 15;
    }
}
