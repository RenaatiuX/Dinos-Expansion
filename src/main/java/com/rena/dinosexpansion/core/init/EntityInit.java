package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.aquatic.*;
import com.rena.dinosexpansion.common.entity.aquatic.fish.Acanthodes;
import com.rena.dinosexpansion.common.entity.aquatic.fish.Belantsea;
import com.rena.dinosexpansion.common.entity.aquatic.fish.Wetherellus;
import com.rena.dinosexpansion.common.entity.terrestrial.ambient.Meganeura;
import com.rena.dinosexpansion.common.entity.projectile.TinyRockEntity;
import com.rena.dinosexpansion.common.entity.terrestrial.Dryosaurus;
import com.rena.dinosexpansion.common.entity.villagers.Hermit;
import com.rena.dinosexpansion.common.entity.aquatic.fish.MegaPiranha;
import com.rena.dinosexpansion.common.entity.flying.Dimorphodon;
import com.rena.dinosexpansion.common.entity.misc.*;
import com.rena.dinosexpansion.common.entity.projectile.CustomArrow;
import com.rena.dinosexpansion.common.entity.projectile.DartEntity;
import com.rena.dinosexpansion.common.entity.semiaquatic.Astorgosuchus;
import com.rena.dinosexpansion.common.entity.terrestrial.ambient.Campanile;
import com.rena.dinosexpansion.common.entity.villagers.caveman.Caveman;
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
    public static final RegistryObject<EntityType<TinyRockEntity>> TINY_ROCK = ENTITY_TYPES.register("tiny_rock",
            () -> EntityType.Builder.<TinyRockEntity>create(TinyRockEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build(DinosExpansion.modLoc("tiny_rock").toString()));
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

    public static final RegistryObject<EntityType<Squalodon>> SQUALODON = register("squalodon",
            () -> EntityType.Builder.<Squalodon>create(Squalodon::new, EntityClassification.WATER_CREATURE).size(1.9f, 0.8f));
    public static final RegistryObject<EntityType<Parapuzosia>> PARAPUZOSIA = register("parapuzosia",
            () -> EntityType.Builder.<Parapuzosia>create(Parapuzosia::new, EntityClassification.WATER_CREATURE).size(2.4F, 2.0F));
    public static final RegistryObject<EntityType<Eosqualodon>> EOSQUALODON = register("eosqualodon",
            () -> EntityType.Builder.<Eosqualodon>create(Eosqualodon::new, EntityClassification.WATER_CREATURE).size(1.5F, 1.0F));
    public static final RegistryObject<EntityType<MegaPiranha>> MEGA_PIRANHA = register("megapiranha",
            () -> EntityType.Builder.<MegaPiranha>create(MegaPiranha::new, EntityClassification.WATER_CREATURE).size(0.5F, 0.75F));
    public static final RegistryObject<EntityType<Wetherellus>> WETHERELLUS = register("wetherellus",
            () -> EntityType.Builder.<Wetherellus>create(Wetherellus::new, EntityClassification.WATER_AMBIENT).size(0.5F, 0.5F));
    public static final RegistryObject<EntityType<Belantsea>> BELANTSEA = register("belantsea",
            () -> EntityType.Builder.<Belantsea>create(Belantsea::new, EntityClassification.WATER_AMBIENT).size(0.5F, 0.5F));
    public static final RegistryObject<EntityType<Acanthodes>> ACANTHODES = register("acanthodes",
            () -> EntityType.Builder.<Acanthodes>create(Acanthodes::new, EntityClassification.WATER_AMBIENT).size(0.5F, 0.5F));
    public static final RegistryObject<EntityType<Dimorphodon>> DIMORPHODON = register("dimorphodon",
            () -> EntityType.Builder.<Dimorphodon>create(Dimorphodon::new, EntityClassification.WATER_CREATURE).size(0.5F, 0.7F));
    public static final RegistryObject<EntityType<Astorgosuchus>> ASTORGOSUCHUS = register("astorgosuchus",
            () -> EntityType.Builder.<Astorgosuchus>create(Astorgosuchus::new, EntityClassification.CREATURE).size(1.0F, 2.0F));
    public static final RegistryObject<EntityType<Campanile>> CAMPANILE = register("campanile",
            () -> EntityType.Builder.<Campanile>create(Campanile::new, EntityClassification.AMBIENT).size(0.5F, 1.0F));
    public static final RegistryObject<EntityType<Dryosaurus>> DRYOSAURUS = register("dryosaurus",
            () -> EntityType.Builder.<Dryosaurus>create(Dryosaurus::new, EntityClassification.CREATURE).size(1.5F, 1.5F));
    public static final RegistryObject<EntityType<Aegirocassis>> AEGIROCASSIS = register("aegirocassis",
            () -> EntityType.Builder.<Aegirocassis>create(Aegirocassis::new, EntityClassification.WATER_CREATURE).size(0.5F, 0.5F));
    public static final RegistryObject<EntityType<Anomalocaris>> ANOMALOCARIS = register("anomalocaris",
            () -> EntityType.Builder.<Anomalocaris>create(Anomalocaris::new, EntityClassification.WATER_CREATURE).size(0.7F, 0.3F));
    public static final RegistryObject<EntityType<Meganeura>> MEGANEURA = register("meganeura",
            () -> EntityType.Builder.<Meganeura>create(Meganeura::new, EntityClassification.AMBIENT).size(0.5F, 0.5F));

    public static final RegistryObject<EntityType<Hermit>> HERMIT = register("hermit", () -> EntityType.Builder.create(Hermit::new, EntityClassification.CREATURE));
    public static final RegistryObject<EntityType<Caveman>> CAVEMAN = register("caveman", () -> EntityType.Builder.create(Caveman::new, EntityClassification.CREATURE));

    public static final RegistryObject<EntityType<CustomBoatEntity>> CUSTOM_BOAT = register("custom_boat", () -> EntityType.Builder.<CustomBoatEntity>create(CustomBoatEntity::new, EntityClassification.MISC).size(1.375F, 0.5625F).trackingRange(10));

    public static final <T extends Entity> RegistryObject<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder){
        return ENTITY_TYPES.register(name, () -> builder.get().build(DinosExpansion.modLoc(name).toString()));
    }

}
