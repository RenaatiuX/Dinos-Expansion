package com.rena.dinosexpansion.common.entity.util;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.api.BoatTypeRegistry;
import com.rena.dinosexpansion.common.entity.misc.CustomBoatEntity;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.function.Supplier;

public enum BoatType implements CustomBoatEntity.BoatTypeMaterial {
    CRATAEGUS("crataegus", ItemInit.CRATAEGUS_BOAT_ITEM::get,  BlockInit.CRATAEGUS_PLANKS::get, DinosExpansion.modLoc("textures/entity/misc/crataegus_boat_entity.png"));

    private final String name;
    private final Supplier<IItemProvider> boatItem, planks;
    private final ResourceLocation texture;
    private final EntityModel<?> model;

    BoatType(String name, Supplier<IItemProvider> boatItem, Supplier<IItemProvider> planks, ResourceLocation texture, EntityModel<?> model) {
        this.name = name;
        this.boatItem = boatItem;
        this.planks = planks;
        this.texture = texture;
        this.model = model;
    }

    BoatType(String name, Supplier<IItemProvider> boatItem, Supplier<IItemProvider> planks, ResourceLocation texture) {
        this(name, boatItem, planks, texture, null);
    }

    public String getName() {
        return name;
    }


    @Override
    public IItemProvider getBoatItem() {
        return this.boatItem.get();
    }

    @Override
    public IItemProvider getPlanks() {
        return this.planks.get();
    }

    @Override
    public ResourceLocation getBoatTexture() {
        return this.texture;
    }


    public static CustomBoatEntity.BoatTypeMaterial getTypeFromString(String nameIn) {
       for (CustomBoatEntity.BoatTypeMaterial material : BoatTypeRegistry.BOAT_TYPES){
            if (material.getName().equals(nameIn)) {
                return material;
            }
        }
        throw new IllegalArgumentException("there was no BoatType material named: " + nameIn);
    }
}
