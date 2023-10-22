package com.rena.dinosexpansion.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.DinosaurArmorSlotType;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DinosaurArmorItem extends Item {
    public static final IDispenseItemBehavior DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {
        /**
         * Dispense the specified stack, play the dispense sound and spawn particles.
         */
        protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            return DinosaurArmorItem.func_226626_a_(source, stack) ? stack : super.dispenseStack(source, stack);
        }
    };
    protected final DinosaurArmorSlotType slot;
    private final int damageReduceAmount;
    private final float toughness;
    protected final float knockbackResistance;
    protected final DinosaurArmorMaterial material;
    private final Multimap<Attribute, AttributeModifier> field_234656_m_;

    public static boolean func_226626_a_(IBlockSource blockSource, ItemStack stack) {
        if (!(stack.getItem() instanceof DinosaurArmorItem))
            return false;
        BlockPos blockpos = blockSource.getBlockPos().offset(blockSource.getBlockState().get(DispenserBlock.FACING));
        List<LivingEntity> list = blockSource.getWorld().getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(blockpos), EntityPredicates.NOT_SPECTATING.and(new EntityPredicates.ArmoredMob(stack))).stream().filter(e -> e instanceof Dinosaur).collect(Collectors.toList());
        if (list.isEmpty()) {
            return false;
        } else {
            LivingEntity livingentity = list.get(0);
            ItemStack itemstack = stack.split(1);
            Dinosaur dino = (Dinosaur)livingentity;
            DinosaurArmorItem armor = (DinosaurArmorItem)stack.getItem();
            if (dino.supportsSlot(armor.getEquipmentSlot())) {
                dino.setArmor(armor.getEquipmentSlot(), itemstack);
                dino.enablePersistence();
            }else
                return false;



            return true;
        }
    }

    public DinosaurArmorItem(DinosaurArmorMaterial materialIn, DinosaurArmorSlotType slot, Item.Properties builderIn) {
        super(builderIn.defaultMaxDamage(materialIn.getDurability(slot)));
        this.material = materialIn;
        this.slot = slot;
        this.damageReduceAmount = materialIn.getDamageReductionAmount(slot);
        this.toughness = materialIn.getToughness();
        this.knockbackResistance = materialIn.getKnockbackResistance();
        DispenserBlock.registerDispenseBehavior(this, DISPENSER_BEHAVIOR);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = materialIn.getArmorModifierUUID(slot);
        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", (double) this.damageReduceAmount, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", (double) this.toughness, AttributeModifier.Operation.ADDITION));
        if (this.knockbackResistance > 0) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", (double) this.knockbackResistance, AttributeModifier.Operation.ADDITION));
        }

        this.field_234656_m_ = builder.build();
    }

    /**
     * Gets the equipment slot of this armor piece (formerly known as armor type)
     */
    public DinosaurArmorSlotType getEquipmentSlot() {
        return this.slot;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }

    public DinosaurArmorMaterial getArmorMaterial() {
        return this.material;
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return this.material.getRepairMaterial().test(repair) || super.getIsRepairable(toRepair, repair);
    }

   public Multimap<Attribute, AttributeModifier> getDinoAttributes(DinosaurArmorSlotType slot, ItemStack stack){
        return this.slot == slot ? this.field_234656_m_ : ImmutableMultimap.of();
   }

    public int getDamageReduceAmount() {
        return this.damageReduceAmount;
    }

    public float getToughness() {
        return this.toughness;
    }
}
