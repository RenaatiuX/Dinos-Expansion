package com.rena.dinosexpansion.common.item.firearms;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public abstract class Firearms extends Item {
    public Firearms(Properties properties) {
        super(properties);
    }

    // Método para manejar el disparo del arma de fuego
    public abstract void shoot(World world, PlayerEntity shooter);

    // Método para calcular el daño del disparo
    public abstract float calculateDamage();

    // Método para manejar la recarga del arma de fuego
    public abstract void reload(PlayerEntity player, ItemStack firearm);

    // Método para obtener la capacidad máxima de munición
    public abstract int getMaxAmmo();

    // Método para obtener la munición actual
    public abstract int getCurrentAmmo(ItemStack firearm);

    // Método para reducir la munición después de disparar
    public abstract void consumeAmmo(ItemStack firearm);

    // Método para pintar el arma
    public void paint(ItemStack firearm, int color) {
        CompoundNBT tag = firearm.getOrCreateTag();
        tag.putInt("GunColor", color);
    }

    // Método para obtener el color del arma
    public int getGunColor(ItemStack firearm) {
        CompoundNBT tag = firearm.getTag();
        return (tag != null && tag.contains("GunColor")) ? tag.getInt("GunColor") : 0xFFFFFF; // Blanco predeterminado
    }

    // Método para gestionar el sonido al disparar
    public abstract SoundEvent getShootSound();

    // Método para gestionar el sonido al recargar
    public abstract SoundEvent getReloadSound();

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        aim(itemstack, worldIn, playerIn);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    // Método para apuntar con el clic derecho
    public void aim(ItemStack firearm, World world, PlayerEntity shooter) {

    }

    private boolean isAiming(ItemStack firearm, PlayerEntity player) {
        CompoundNBT tag = firearm.getOrCreateTag();
        return tag.getBoolean("Aiming");
    }

    private void setAiming(ItemStack firearm, PlayerEntity player, boolean aiming) {
        CompoundNBT tag = firearm.getOrCreateTag();
        tag.putBoolean("Aiming", aiming);
    }

    // Método para gestionar el sonido al apuntar
    public abstract SoundEvent getAimSound();
}
