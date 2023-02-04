package com.rena.dinosexpansion.common.events;


import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.item.util.JournalPages;
import com.rena.dinosexpansion.core.init.DamageSourceInit;
import com.rena.dinosexpansion.core.init.EffectInit;
import com.rena.dinosexpansion.core.init.EnchantmentInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.Random;

@Mod.EventBusSubscriber(modid = DinosExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerForgeEvents {

    @SubscribeEvent
    public static void potionAddEvent(PotionEvent.PotionAddedEvent event) {
        if (event.getPotionEffect().getPotion() == EffectInit.FREEZE.get())
            event.getEntityLiving().attackEntityFrom(DamageSourceInit.FREEZE, 4 << event.getPotionEffect().getAmplifier());
    }

    @SubscribeEvent
    public static void playerTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity living = event.getEntityLiving();
        if (living.isPotionActive(EffectInit.PARALYSIS.get())) {
            setStunned(living, event);
            living.baseTick();
        }
        if (living.isPotionActive(EffectInit.FREEZE.get()) && living.getActivePotionEffect(EffectInit.FREEZE.get()).getDuration() > 40) {
            setStunned(living, event);
            living.baseTick();
        }


    }

    private static void setStunned(LivingEntity entity, LivingEvent.LivingUpdateEvent event) {
        event.setCanceled(true);
        if (entity instanceof PlayerEntity) {
            KeyBinding.unPressAllKeys();
        }
    }

    @SubscribeEvent
    public static void onProjectileHit(ProjectileImpactEvent.Arrow event) {
        if (!event.getArrow().world.isRemote()) {
            AbstractArrowEntity arrow = event.getArrow();
            if (event.getRayTraceResult().getType() == RayTraceResult.Type.ENTITY) {
                EntityRayTraceResult hit = (EntityRayTraceResult) event.getRayTraceResult();
                if (arrow.getPersistentData().contains(DinosExpansion.MOD_ID + "." + EnchantmentInit.ELECTRIC_ENCHANTMENT.get().getRegistryName().getPath())) {
                    if (hit.getEntity() instanceof LivingEntity) {
                        int amplifier = arrow.getPersistentData().getInt(DinosExpansion.MOD_ID + "." + EnchantmentInit.ELECTRIC_ENCHANTMENT.get().getRegistryName().getPath());
                        LivingEntity living = (LivingEntity) hit.getEntity();
                        living.addPotionEffect(new EffectInstance(EffectInit.PARALYSIS.get(), 40 * amplifier));
                    }
                }
                if (arrow.getPersistentData().contains(DinosExpansion.MOD_ID + "." + EnchantmentInit.ICE_ENCHANTMENT.get().getRegistryName().getPath())) {
                    if (hit.getEntity() instanceof LivingEntity) {
                        int amplifier = arrow.getPersistentData().getInt(DinosExpansion.MOD_ID + "." + EnchantmentInit.ICE_ENCHANTMENT.get().getRegistryName().getPath());
                        LivingEntity living = (LivingEntity) hit.getEntity();
                        living.addPotionEffect(new EffectInstance(EffectInit.FREEZE.get(), 40 * amplifier));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHeal(LivingHealEvent event) {
        if (!event.getEntityLiving().world.isRemote()) {
            if (event.getEntityLiving().isPotionActive(EffectInit.FREEZE.get()))
                event.setAmount(0);
        }
    }

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        LivingEntity entity = event.player;
        if (entity.world.getGameTime() % 200L == 0L && entity.world.isDaytime() && !entity.world.isRemote) {
            // Repara armaduras y herramientas en el inventario
            PlayerEntity player = event.player;
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (!itemStack.isEmpty() && (itemStack.getItem() instanceof TieredItem || itemStack.getItem() instanceof ArmorItem)) {
                    int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.BLESSING_UNKNOWN.get(), itemStack);
                    if (level > 0) {
                        itemStack.setDamage(itemStack.getDamage() - 1);
                    }
                }
            }
        }
    }
}
