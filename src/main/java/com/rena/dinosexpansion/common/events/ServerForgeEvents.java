package com.rena.dinosexpansion.common.events;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.DamageSourceInit;
import com.rena.dinosexpansion.core.init.EffectInit;
import com.rena.dinosexpansion.core.init.EnchantmentInit;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

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
        PlayerEntity entity = event.player;
        Enchantment blessing = EnchantmentInit.BLESSING_UNKNOWN.get();
        if (!entity.world.isRemote && entity.world.isDaytime() && entity.world.canSeeSky(entity.getPosition())) {
            // Repara armaduras y herramientas en el inventario
            PlayerEntity player = event.player;
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack itemStack = player.inventory.getStackInSlot(i);
                if (!itemStack.isEmpty() && itemStack.isDamageable()) {
                    int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.BLESSING_UNKNOWN.get(), itemStack);
                    if (entity.world.getGameTime() % (100L * (blessing.getMaxLevel() + 1 - level)) == 0L) {
                        if (level > 0) {
                            itemStack.setDamage(itemStack.getDamage() - 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * If the condition is met, the arrow is not consumed when fired
     *
     * @param event
     */
    @SubscribeEvent
    public static void onArrowShot(ArrowLooseEvent event) {
        PlayerEntity player = event.getPlayer();
        ItemStack bow = event.getBow();
        boolean flag = player.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow) > 0;
        if (flag)
            return;
        ItemStack ammo = event.getPlayer().findAmmo(bow);
        if (ammo.isEmpty())
            return;
        int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.AMMO_RESERVATION.get(), bow);
        if (level > 0) {
            float chance = (1.0F - 0.1F * level);
            if (player.getRNG().nextFloat() > chance) {
                if (event.hasAmmo()) {
                    ammo.grow(1);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SURVIVAL_INSTINCT.get(), player.getItemStackFromSlot(EquipmentSlotType.FEET));

            if (enchantmentLevel > 0 && player.getHealth() < 4.0F) {
                player.addPotionEffect(new EffectInstance(Effects.SPEED, 200, enchantmentLevel - 1));
                player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 200, enchantmentLevel - 1));
            }
        }
    }
}
