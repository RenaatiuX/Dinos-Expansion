package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.entity.projectile.CustomArrow;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, DinosExpansion.MOD_ID);

    public static final RegistryObject<EntityType<CustomArrow>> CUSTOM_ARROW = ENTITY_TYPES.register("custom_arrow", () -> EntityType.Builder.<CustomArrow>create(CustomArrow::new, EntityClassification.MISC).size(0.5F, 0.5F).build(DinosExpansion.modLoc("custom_arrow").toString()));

}
