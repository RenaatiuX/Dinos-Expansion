package com.rena.dinosexpansion.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.rena.dinosexpansion.common.entity.misc.SpearEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.CreatureAttribute;
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
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class TieredSpear extends Item implements IVanishable {

    private final SpearTier spearTier;
    private final Multimap<Attribute, AttributeModifier> spearAttributes;

    public TieredSpear(Properties properties, SpearTier spearTier) {
        super(properties.maxDamage(spearTier.getDurability()));
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", 2.0D + spearTier.getDamageAddition(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -2.9F + spearTier.getAttackSpeed(), AttributeModifier.Operation.ADDITION));
        this.spearAttributes = builder.build();
        this.spearTier = spearTier;
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
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
            throwSpear(worldIn, player, stack);
        }

        player.addStat(Stats.ITEM_USED.get(this));
    }

    protected void throwSpear(final World world, final PlayerEntity thrower, final ItemStack stack) {
        stack.damageItem(1, thrower, e -> e.sendBreakAnimation(thrower.getActiveHand()));
        SpearEntity spear = new SpearEntity(world, thrower, stack);
        spear.setDirectionAndMovement(thrower, thrower.rotationPitch, thrower.rotationYaw, 0.0F, 2.25F, 1.0F);
        // set pickup status and remove the itemStack
        if (thrower.abilities.isCreativeMode) {
            spear.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
        } else {
            thrower.inventory.deleteStack(stack);
        }
        if (this.spearTier.canPierce())
            //u can maxpierce 10 enemies, I think that's enoughimum
            spear.setPierceLevel((byte)10);
        //sets the damage of the thrown spear to 50% of the actual damage, included playerAttribute and enchantments
        //note that this damage also increases the more i charge it so it get back to over 100%
        spear.setDamage(getTotalAttackDamageForPlayer(thrower) * 0.5d);
        spear.setKnockbackStrengthD(this.spearTier.getKnockback());
        world.addEntity(spear);
        world.playMovingSound(null, spear, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
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
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if ((double) state.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(2, entityLiving, (entity) -> {
                entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.spearAttributes : super.getAttributeModifiers(equipmentSlot);
    }

     public double getAttackDamage(){
        return this.spearAttributes.get(Attributes.ATTACK_DAMAGE).stream().mapToDouble(AttributeModifier::getAmount).sum();
     }

    /**
     *
     * @param player the player who throws the spear
     * @return the attack damage that was set in the tier + a base value + the players attack damage which takes stuff like strength into account
     */
     public double getTotalAttackDamageForPlayer(PlayerEntity player){
        return getAttackDamage() + player.getBaseAttributeValue(Attributes.ATTACK_DAMAGE);
     }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == Enchantments.LOYALTY || enchantment == Enchantments.SHARPNESS;
    }

    @Override
    public int getItemEnchantability() {
        return spearTier.getEnchantability();
    }

    public SpearTier getSpearTier() {
        return spearTier;
    }

    public interface SpearTier {

        double getDamageAddition();
        double getAttackSpeed();

        /**
         *
         * @return the knockback this spear should do not that 1 equals the knockback of Punch 1 and so on
         */
        double getKnockback();
        int getEnchantability();
        int getDurability();
        boolean canPierce();

    }
}
