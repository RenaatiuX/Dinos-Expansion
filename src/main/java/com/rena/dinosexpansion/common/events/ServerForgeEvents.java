package com.rena.dinosexpansion.common.events;


import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.EffectInit;
import com.rena.dinosexpansion.core.init.EnchantmentInit;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DinosExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerForgeEvents {

    @SubscribeEvent
    public static final void playerTick(LivingEvent.LivingUpdateEvent event){
        if (!event.getEntityLiving().world.isRemote()){
            if (event.getEntityLiving().isPotionActive(EffectInit.PARLYSIS.get())) {
                KeyBinding.unPressAllKeys();
                event.getEntityLiving().getAttribute(Attributes.MOVEMENT_SPEED).applyNonPersistentModifier(new AttributeModifier(DinosExpansion.MOD_ID + ".place_keeper", -event.getEntityLiving().getAttributeValue(Attributes.MOVEMENT_SPEED), AttributeModifier.Operation.ADDITION));
            }else{
                event.getEntityLiving().getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(new AttributeModifier(DinosExpansion.MOD_ID + ".place_keeper", -event.getEntityLiving().getAttributeValue(Attributes.MOVEMENT_SPEED), AttributeModifier.Operation.ADDITION));
            }

        }
    }

    @SubscribeEvent
    public static final void onProjectileHit(ProjectileImpactEvent.Arrow event){
        if (!event.getArrow().world.isRemote()){
            AbstractArrowEntity arrow = event.getArrow();
            if (event.getRayTraceResult().getType() == RayTraceResult.Type.ENTITY) {
                EntityRayTraceResult hit = (EntityRayTraceResult) event.getRayTraceResult();
                if (arrow.getPersistentData().contains(DinosExpansion.MOD_ID + "." + EnchantmentInit.ELECTRIC_ENCHANTMENT.get().getRegistryName().getPath())) {
                    if (hit.getEntity() instanceof LivingEntity){
                        int amplifier = arrow.getPersistentData().getInt(DinosExpansion.MOD_ID + "." + EnchantmentInit.ELECTRIC_ENCHANTMENT.get().getRegistryName().getPath());
                        LivingEntity living = (LivingEntity) hit.getEntity();
                        living.addPotionEffect(new EffectInstance(EffectInit.PARLYSIS.get(), 20 * amplifier));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static final void onEntityHeal(LivingHealEvent event){
        if (!event.getEntityLiving().world.isRemote()){
            if (event.getEntityLiving().isPotionActive(EffectInit.FREEZE.get()))
                event.setAmount(0);
        }
    }
}
