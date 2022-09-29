package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.EnchantmentInit;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class TieredBow extends BowItem {

    private final BowTier tier;

    public TieredBow(Properties properties, BowTier tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean flag = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = playerentity.findAmmo(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(i);
                f += tier.getSpeedAddition();
                if (!((double)f < 0.1D)) {
                    boolean flag1 = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
                    if (!worldIn.isRemote) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                        abstractarrowentity = customArrow(abstractarrowentity);
                        abstractarrowentity.setDirectionAndMovement(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F + tier.getSpeedAddition()) {
                            abstractarrowentity.setIsCritical(true);
                        }
                        if (f >= (1f + tier.getSpeedAddition()) * 0.9){
                            abstractarrowentity.setPierceLevel((byte) EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.BETTER_QUICK_CHARGE.get(), stack));
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        if (j > 0) {
                            abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double)j * 0.5D + 0.5D + tier.getDamageAddition());
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                        if (k > 0) {
                            abstractarrowentity.setKnockbackStrength(k);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            abstractarrowentity.setFire(100);
                        }
                        if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ELECTRIC_ENCHANTMENT.get(), stack) > 0){
                            CompoundNBT nbt = abstractarrowentity.getPersistentData();
                            nbt.putInt(DinosExpansion.MOD_ID + "." + EnchantmentInit.ELECTRIC_ENCHANTMENT.get().getRegistryName().getPath(), EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ELECTRIC_ENCHANTMENT.get(), stack));
                        }

                        stack.damageItem(1, playerentity, (player) -> {
                            player.sendBreakAnimation(playerentity.getActiveHand());
                        });
                        if (flag1 || playerentity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }

                        worldIn.addEntity(abstractarrowentity);
                    }

                    worldIn.playSound(null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !playerentity.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.deleteStack(itemstack);
                        }
                    }

                    playerentity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public BowTier getTier() {
        return tier;
    }

    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return tier.getAmmo();
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return super.getUseDuration(stack) - 15 * EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.BETTER_QUICK_CHARGE.get(), stack);
    }

    @Override
    public int getItemEnchantability() {
        return tier.getEnchantability();
    }

    public interface BowTier{

        double getDamageAddition();
        double getSpeedAddition();
        int getEnchantability();

        default Predicate<ItemStack> getAmmo(){
            return BowItem.ARROWS;
        }


    }
}