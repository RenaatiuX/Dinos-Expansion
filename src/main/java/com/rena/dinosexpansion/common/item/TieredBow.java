package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.EnchantmentInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class TieredBow extends BowItem {

    //the bow enchants are already included
    public static final Set<Enchantment> ALLOWED_ENCHANTMENTS = Util.make(new HashSet<>(), set -> {
        set.add(Enchantments.PIERCING);
        set.add(Enchantments.QUICK_CHARGE);
    });

    private final BowTier tier;

    public TieredBow(Properties properties, BowTier tier) {
        super(properties.maxDamage(tier.getDurability()));
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
                    itemstack = new ItemStack(tier.creativeDefault());
                }

                float f = getArrowVelocity(i);
                f += tier.getSpeedAddition();
                if (!((double)f < 0.1D)) {
                    boolean flag1 = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
                    if (!worldIn.isRemote) {
                        if (!(itemstack.getItem() instanceof ArrowItem)){
                            DinosExpansion.LOGGER.warn("either the creativeDefault or the ammo Predicate contains items which r not ArrowItems");
                        }
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                        abstractarrowentity = customArrow(abstractarrowentity);
                        abstractarrowentity.setDirectionAndMovement(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F + tier.getSpeedAddition()) {
                            abstractarrowentity.setIsCritical(true);
                        }
                        if (f >= (1f + tier.getSpeedAddition()) * 0.9){
                            abstractarrowentity.setPierceLevel((byte) EnchantmentHelper.getEnchantmentLevel(Enchantments.PIERCING, stack));
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
                        if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ICE_ENCHANTMENT.get(), stack) > 0){
                            CompoundNBT nbt = abstractarrowentity.getPersistentData();
                            nbt.putInt(DinosExpansion.MOD_ID + "." + EnchantmentInit.ICE_ENCHANTMENT.get().getRegistryName().getPath(), EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ICE_ENCHANTMENT.get(), stack));
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
        return super.getUseDuration(stack) - 15 * EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || ALLOWED_ENCHANTMENTS.contains(enchantment);
    }

    @Override
    public int getItemEnchantability() {
        return tier.getEnchantability();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        double damage = getDamage(stack);
        double damageBonus = getDamage(stack) + tier.getDamageAddition();

        if(damage != 1){
            if(damageBonus != 0) tooltip.add(new TranslationTextComponent("tooltip.dinosexpansion.arrow.damage.both" + (isEnchantable(stack) ? ".modified" : ""), ItemStack.DECIMALFORMAT.format(damage), ItemStack.DECIMALFORMAT.format(damageBonus)));
            else tooltip.add(new TranslationTextComponent("tooltip.dinosexpansion.arrow.damage.mult" + (isEnchantable(stack) ? ".modified" : ""), ItemStack.DECIMALFORMAT.format(damage)));
        }
        else if (damageBonus != 0) tooltip.add(new TranslationTextComponent("tooltip.dinosexpansion.arrow.damage.flat" + (isEnchantable(stack) ? ".modified" : ""), ItemStack.DECIMALFORMAT.format(damageBonus)));
    }

    public interface BowTier{

        double getDamageAddition();
        double getSpeedAddition();
        int getEnchantability();
        int getDurability();

        /**
         * here will be determined which itemStack will be considered as Arrow for this Bow
         * @return
         */
        default Predicate<ItemStack> getAmmo(){
            return BowItem.ARROWS;
        }

        /**
         * this will be the arrow that will be shot if the creative player has no matching arrows in its inventory
         * @return - the arrowItem
         */
        default Item creativeDefault(){
            return Items.ARROW;
        }


    }
}
