package com.rena.dinosexpansion.common.events;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.core.init.DamageSourceInit;
import com.rena.dinosexpansion.core.init.EffectInit;
import com.rena.dinosexpansion.core.init.EnchantmentInit;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = DinosExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerForgeEvents {

    @SubscribeEvent
    public static void onTargetSet(LivingSetAttackTargetEvent event) {
        if (event.getTarget() != null && event.getEntityLiving() instanceof CreatureEntity && event.getEntityLiving().isPotionActive(EffectInit.FEAR.get())) {
            ((CreatureEntity) event.getEntityLiving()).setAttackTarget(null);
        }
    }

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


    public static final String COOLDOWN_NAME = "survival_instinct_cooldown";

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.SURVIVAL_INSTINCT.get(), player.getItemStackFromSlot(EquipmentSlotType.FEET));
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                if (player.inventory.getStackInSlot(i).hasTag())
                    if (hasCooldown(player.inventory.getStackInSlot(i))) {
                        decreaseCooldown(player.inventory.getStackInSlot(i));
                    }
            }


            if (enchantmentLevel > 0 && player.getHealth() < 4.0F && !hasCooldown(player.getItemStackFromSlot(EquipmentSlotType.FEET))) {
                player.addPotionEffect(new EffectInstance(Effects.SPEED, 200, enchantmentLevel - 1));
                player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 200, enchantmentLevel - 1));
                setCooldown(player.getItemStackFromSlot(EquipmentSlotType.FEET), 1200);
            }
        }
        int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.PRIMAL_FRENZY.get(), event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.MAINHAND));
        if (level > 0) {
            List<CreatureEntity> nearbyEntities = event.getEntityLiving().world.getEntitiesWithinAABB(CreatureEntity.class, event.getEntityLiving().getBoundingBox().grow(16, 8, 16));

            if (nearbyEntities.stream().filter(e -> !(e instanceof TameableEntity) || !((TameableEntity) e).isOwner(event.getEntityLiving())).count() >= 4) {
                event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.STRENGTH, 100, level - 1));
                event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.SPEED, 100, Math.max(0, level - 2)));
                event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.SATURATION, 100, level - 1));
            }
        }
    }

    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.hasTag() && stack.getTag().contains(COOLDOWN_NAME)) {
            event.getToolTip().add(new TranslationTextComponent(DinosExpansion.MOD_ID + ".survival_instinct_cooldown", Math.floorDiv(stack.getTag().getInt(COOLDOWN_NAME), 20)));
        }
    }

    private static void decreaseCooldown(ItemStack stack) {
        if (!stack.getOrCreateTag().contains(COOLDOWN_NAME))
            return;
        int cooldown = stack.getOrCreateTag().getInt(COOLDOWN_NAME);
        if (cooldown > 0)
            stack.getOrCreateTag().putInt(COOLDOWN_NAME, cooldown - 1);

    }

    private static void setCooldown(ItemStack stack, int cooldown) {
        stack.getOrCreateTag().putInt(COOLDOWN_NAME, cooldown);
    }

    private static boolean hasCooldown(ItemStack stack) {
        if (!stack.hasTag())
            return false;
        if (stack.getTag().contains(COOLDOWN_NAME)) {
            return stack.getTag().getInt(COOLDOWN_NAME) > 0;
        }
        return false;
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ANCIENT_RESURRECTION.get(), event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.CHEST)) > 0) {
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.ANCIENT_RESURRECTION.get(), event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.CHEST));
            if (Math.random() < 0.05 * level) {
                event.setCanceled(true);
                LivingEntity living = event.getEntityLiving();
                living.setHealth(2f);
                List<EffectInstance> instances = living.getActivePotionEffects().stream().filter(e -> e.getPotion().getEffectType() != EffectType.HARMFUL).collect(Collectors.toList());
                living.clearActivePotions();
                instances.forEach(living::addPotionEffect);
                living.addPotionEffect(new EffectInstance(Effects.LEVITATION, 30));
                living.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100, 1));
                living.addPotionEffect(new EffectInstance(Effects.SPEED, 100, 2));
                living.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 100, 2));
            }
        }
    }

    @SubscribeEvent
    public static void onArmorEquipped(LivingEquipmentChangeEvent event) {
        if (event.getSlot() == EquipmentSlotType.CHEST || event.getSlot() == EquipmentSlotType.HEAD || event.getSlot() == EquipmentSlotType.LEGS || event.getSlot() == EquipmentSlotType.FEET) {
            LivingEntity livingEntity = event.getEntityLiving();
            ItemStack stack = event.getTo();

            if (stack.getItem() instanceof ArmorItem && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.PREHISTORIC_WEIGHT.get(), stack) > 0) {
                livingEntity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, Integer.MAX_VALUE, 1, false, false, false));
                livingEntity.addPotionEffect(new EffectInstance(Effects.HUNGER, Integer.MAX_VALUE, 0, false, false, false));
            } else {
                livingEntity.removePotionEffect(Effects.SLOWNESS);
                livingEntity.removePotionEffect(Effects.HUNGER);
            }
        }
    }

    @SubscribeEvent
    public static void onEquipmentTick(LivingEquipmentChangeEvent event) {
        ItemStack stack = event.getTo();

        if (stack.getItem() instanceof ArmorItem && EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.PREHISTORIC_WEAR.get(), stack) > 0) {
            // Apply the equipment wear effect to the player
            int damageRate = 2; // Adjust this value based on how much faster the equipment should wear out
            if (event.getSlot() == EquipmentSlotType.MAINHAND || event.getSlot() == EquipmentSlotType.OFFHAND) {
                damageRate *= 2; // Optionally, increase the wear rate for main hand and offhand items
            }
            stack.setDamage(stack.getDamage() + damageRate);
        }
    }
}
