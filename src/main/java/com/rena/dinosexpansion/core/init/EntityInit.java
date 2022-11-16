package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.aquatic.Eosqualodon;
import com.rena.dinosexpansion.common.entity.aquatic.MegaPiranha;
import com.rena.dinosexpansion.common.entity.aquatic.Parapuzosia;
import com.rena.dinosexpansion.common.entity.misc.ChakramEntity;
import com.rena.dinosexpansion.common.entity.misc.SpearEntity;
import com.rena.dinosexpansion.common.entity.projectile.CustomArrow;
import com.rena.dinosexpansion.common.entity.projectile.DartEntity;
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
    public static final RegistryObject<EntityType<DartEntity>> DART = ENTITY_TYPES.register("dart",
            () -> EntityType.Builder.<DartEntity>create(DartEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).build(DinosExpansion.modLoc("dart").toString()));

    public static final RegistryObject<EntityType<Parapuzosia>> PARAPUZOSIA = register("parapuzosia", () -> EntityType.Builder.<Parapuzosia>create(Parapuzosia::new, EntityClassification.AMBIENT).size(3f, 2.5f));
    public static final RegistryObject<EntityType<Eosqualodon>> EOSQUALODON = register("eosqualodon",
            () -> EntityType.Builder.<Eosqualodon>create(Eosqualodon::new, EntityClassification.WATER_CREATURE).size(3f, 2.0f));
    public static final RegistryObject<EntityType<MegaPiranha>> MEGA_PIRANHA = register("megapiranha",
            () -> EntityType.Builder.<MegaPiranha>create(MegaPiranha::new, EntityClassification.WATER_CREATURE).size(1F, 1F));

    public static final <T extends Entity> RegistryObject<EntityType<T>> register(String name, Supplier<EntityType.Builder<T>> builder){
        return ENTITY_TYPES.register(name, () -> builder.get().build(DinosExpansion.modLoc(name).toString()));
    }

}
