package com.rena.dinosexpansion.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.rena.dinosexpansion.common.entity.misc.SpearEntity;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

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
        // set pickup status and remove the itemstack
        if (thrower.abilities.isCreativeMode) {
            spear.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
        } else {
            thrower.inventory.deleteStack(stack);
        }
        if (this.spearTier.canPierce())
            //u can maximum pierce 10 enemies, i think thats enough
            spear.setPierceLevel((byte)10);
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

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == Enchantments.LOYALTY;
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
        int getEnchantability();
        int getDurability();
        boolean canPierce();

    }
}
