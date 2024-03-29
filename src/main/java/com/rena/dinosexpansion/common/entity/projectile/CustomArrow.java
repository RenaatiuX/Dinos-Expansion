package com.rena.dinosexpansion.common.entity.projectile;

import com.rena.dinosexpansion.core.init.EnchantmentInit;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class CustomArrow extends AbstractArrowEntity implements INarcoticProjectile{

    public static final DataParameter<ItemStack> ARROW_STACK = EntityDataManager.createKey(CustomArrow.class, DataSerializers.ITEMSTACK);

    private final int narcoticValue;
    private boolean hasAquaticEnchant = false;
    protected double damageScaling = .5d;

    public CustomArrow(EntityType<CustomArrow> type, World world) {
        super(type, world);
        narcoticValue = 0;
    }

    public CustomArrow( double x, double y, double z, World world, ItemStack arrow) {
        this(x, y, z, world, arrow, 0);
    }

    public CustomArrow( double x, double y, double z, World world, ItemStack arrow, double damage) {
      this(x, y, z, world, arrow, damage, 0);
    }
    public CustomArrow(double x, double y, double z, World world, ItemStack arrow, double damage, int narcoticValue) {
        super(EntityInit.CUSTOM_ARROW.get(), x, y, z, world);
        ItemStack countArrow = arrow.copy();
        countArrow.setCount(1);
        setDamage(damage);
        this.dataManager.set(ARROW_STACK, countArrow);
        this.narcoticValue = narcoticValue;
        this.hasAquaticEnchant = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.AQUATIC_ENCHANT.get(), this.getArrowStack()) > 0;
    }

    public CustomArrow(LivingEntity shooter, World world, ItemStack arrow) {
        this(shooter, world, arrow, 2.0, 0);
    }

    public CustomArrow(LivingEntity shooter, World world, ItemStack arrow, double damage) {
        this(shooter, world, arrow, damage, 0);
    }

    public CustomArrow(LivingEntity shooter, World world, ItemStack arrow, double damage, int narcoticValue) {
        super(EntityInit.CUSTOM_ARROW.get(), shooter, world);
        ItemStack countArrow = arrow.copy();
        countArrow.setCount(1);
        setDamage(damage);
        this.dataManager.set(ARROW_STACK, countArrow);
        this.narcoticValue = narcoticValue;
        this.hasAquaticEnchant = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.AQUATIC_ENCHANT.get(), this.getArrowStack()) > 0;
    }

    /**
     * this defines how the damage is scaled before added to the narcotic value, this may be negative but note that the arrow then will remove narcotic value from the dinosaur
     * @param damageScaling
     */
    public void setDamageScaling(double damageScaling) {
        this.damageScaling = damageScaling;
    }

    @Override
    protected float getSpeedFactor() {
        if (!hasAquaticEnchant)
            return super.getSpeedFactor();
        return 1.0f;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ARROW_STACK, ItemStack.EMPTY);
    }

    @Override
    public ItemStack getArrowStack() {
        return this.dataManager.get(ARROW_STACK);
    }

    @Override
    public int getNarcoticValue() {
        return this.narcoticValue + (int)(this.getDamage() * this.damageScaling);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
