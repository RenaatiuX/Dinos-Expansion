package com.rena.dinosexpansion.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.rena.dinosexpansion.common.entity.misc.HatchetEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class TieredHatchet extends Item {
    private final Multimap<Attribute, AttributeModifier> hatchetAttributes;
    private final HatchetTier hatchetTier;

    public TieredHatchet(Properties properties, HatchetTier hatchetTier) {
        super(properties.maxDamage(hatchetTier.getDurability()));
        this.hatchetTier = hatchetTier;
        Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", 1.5D + hatchetTier.getDamageAddition(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -2.5F + hatchetTier.getSpeedAddition(), AttributeModifier.Operation.ADDITION));
        this.hatchetAttributes = builder.build();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (!(entityLiving instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) entityLiving;

        int useDuration = getUseDuration(stack) - timeLeft;
        if (useDuration < 10) {
            return;
        }

        if (!worldIn.isRemote()) {
            throwHatchet(worldIn, player, stack);
        }

        player.addStat(Stats.ITEM_USED.get(this));
    }

    protected void throwHatchet(final World world, final PlayerEntity thrower, final ItemStack stack) {
        stack.damageItem(1, thrower, e -> e.sendBreakAnimation(thrower.getActiveHand()));
        HatchetEntity hatchet = new HatchetEntity(thrower, world, stack);
        hatchet.setDirectionAndMovement(thrower, thrower.rotationPitch, thrower.rotationYaw, 0.0F, 1.75F, 1.0F);

        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
        if (j > 0) {
            hatchet.setDamage(hatchet.getDamage() + (double) j * 0.5D + 0.5D);
        }
        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
        if (k > 0) {
            hatchet.setKnockbackStrength(k);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
            hatchet.setFire(100);
        }
        // set pickup status and remove the itemStack
        if (thrower.abilities.isCreativeMode) {
            hatchet.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
        } else {
            thrower.inventory.deleteStack(stack);
        }
        world.addEntity(hatchet);
        world.playMovingSound(null, hatchet, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (stack.getDamage() >= stack.getMaxDamage() - 1) {
            return ActionResult.resultFail(stack);
        }
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume(stack);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (entity) -> {
            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot) {
        return slot == EquipmentSlotType.MAINHAND ? this.hatchetAttributes : super.getAttributeModifiers(slot);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == Enchantments.LOYALTY;
    }

    @Override
    public int getItemEnchantability() {
        return hatchetTier.getEnchantability();
    }

    public interface HatchetTier {

        double getDamageAddition();
        double getSpeedAddition();
        int getEnchantability();
        int getDurability();

    }
}
