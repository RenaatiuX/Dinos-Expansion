package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.villagers.Hermit;
import com.rena.dinosexpansion.common.entity.aquatic.Eosqualodon;
import com.rena.dinosexpansion.common.entity.aquatic.MegaPiranha;
import com.rena.dinosexpansion.common.entity.aquatic.Parapuzosia;
import com.rena.dinosexpansion.common.entity.flying.Dimorphodon;
import com.rena.dinosexpansion.common.entity.misc.*;
import com.rena.dinosexpansion.common.entity.projectile.CustomArrow;
import com.rena.dinosexpansion.common.entity.projectile.DartEntity;
import com.rena.dinosexpansion.common.entity.semiaquatic.Astorgosuchus;
import com.rena.dinosexpansion.common.entity.terrestrial.ambient.Campanile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, DinosExpansion.MOD_ID);

    public static final RegistryObject<EntityType<CustomArrow>> CUSTOM_ARROW = ENTITY_TYPES.register("custom_arrow",
            () -> EntityType.Builder.<CustomArrow>create(CustomArrow::new, EntityClassification.MISC).size(0.5F, 0.5F).build(DinosExpansion.modLoc("custom_arrow").toString()));
    public static final RegistryObject<EntityType<SpearEntity>> SPEAR = ENTITY_TYPES.register("spear",
            () -> EntityType.Builder.<SpearEntity>create(SpearEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build(DinosExpansion.modLoc("spear").toString()));
    public static final RegistryObject<EntityType<ChakramEntity>> CHAKRAM = ENTITY_TYPES.register("chakram",
            () -> EntityType.Builder.<ChakramEntity>create(ChakramEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build(DinosExpansion.modLoc("chakram").toString()));
    public static final RegistryObject<EntityType<HatchetEntity>> HATCHET = ENTITY_TYPES.register("hatchet",
            () -> EntityType.Builder.<HatchetEntity>create(HatchetEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build(DinosExpansion.modLoc("hatchet").toString()));
    public static final RegistryObject<EntityType<DartEntity>> DART = ENTITY_TYPES.register("dart",
            () -> EntityType.Builder.<DartEntity>create(DartEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build(DinosExpansion.modLoc("dart").toString()));
    public static final RegistryObject<EntityType<GlowStickEntity>> GLOW_STICK = ENTITY_TYPES.register("glowstick",
            () -> EntityType.Builder.<GlowStickEntity>create(GlowStickEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build(DinosExpansion.modLoc("glowstick").toString()));
    public static final RegistryObject<EntityType<BoomerangEntity>> BOOMERANG = register("boomerang", () -> EntityType.Builder.<BoomerangEntity>create(BoomerangEntity::new, EntityClassification.MISC).size(.5f, .5f));

    public static final RegistryObject<EntityType<Parapuzosia>> PARAPUZOSIA = register("parapuzosia",
            () -> EntityType.Builder.<Parapuzosia>create(Parapuzosia::new, EntityClassification.WATER_CREATURE).size(1.5F, 1.7F));
    public static final RegistryObject<EntityType<Eosqualodon>> EOSQUALODON = register("eosqualodon",
            () -> EntityType.Builder.<Eosqualodon>create(Eosqualodon::new, EntityClassification.WATER_CREATURE).size(1.5F, 1.0F));
    public static final RegistryObject<EntityType<MegaPiranha>> MEGA_PIRANHA = register("megapiranha",
            () -> EntityType.Builder.<MegaPiranha>create(MegaPiranha::new, EntityClassification.WATER_CREATURE).size(0.5F, 0.75F));
    public static final RegistryObject<EntityType<Dimorphodon>> DIMORPHODON = register("dimorphodon",
            () -> EntityType.Builder.<Dimorphodon>create(Dimorphodon::new, EntityClassification.WATER_CREATURE).size(0.5F, 0.7F));
    public static final RegistryObject<EntityType<Astorgosuchus>> ASTORGOSUCHUS = register("astorgosuchus",
            () -> EntityType.Builder.<Astorgosuchus>create(Astorgosuchus::new, EntityClassification.CREATURE).size(1.0F, 2.0F));
    public static final RegistryObject<EntityType<Campanile>> CAMPANILE = register("campanile",
            () -> EntityType.Builder.<Campanile>create(Campanile::new, EntityClassification.AMBIENT).size(0.5F, 1.0F));
    public static final RegistryObject<EntityType<Hermit>> HERMIT = register("hermit", () -> EntityType.Builder.create(Hermit::new, EntityClassification.CREATURE));

    public static final <T extends Entity> RegistryObject<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder){
        return ENTITY_TYPES.register(name, () -> builder.get().build(DinosExpansion.modLoc(name).toString()));
    }

}
