package com.rena.dinosexpansion.core.tags;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags {


    public static class Items{

        public static final ITag.INamedTag<Item> COMPOUND_ARROWS = mod("compound_arrows");
        public static final ITag.INamedTag <Item> AQUATIC_ENCHANT = mod("aquatic_enchant");
        public static final ITag.INamedTag<Item> KIBBLE = mod("kibble");
        public static final ITag.INamedTag<Item> DINO_STICKS = mod("dino_sticks");

        public static final ITag.INamedTag<Item> CAN_DESTROY_FUTURISTIC_BLOCKS = mod("can_destroy_futuristic_blocks");

        //Dinosaur Food
        public static final ITag.INamedTag<Item> EOSQUALODON_FOOD = mod("eosqualodon_food");
        public static final ITag.INamedTag<Item> DIMORPHODON_FOOD = mod("dimorphodon_food");
        public static final ITag.INamedTag<Item> CERATOSAURUS_FOOD = mod("ceratosaurus_food");
        public static final ITag.INamedTag<Item> TIME_MACHINE_FUEL = mod("time_machine_fuel");

        public static ITag.INamedTag<Item> mod(String name){
            return ItemTags.makeWrapperTag(DinosExpansion.modLoc(name).toString());
        }
    }

    //Entities
    public static class EntityTypes{

        public static final ITag.INamedTag<EntityType<?>> NET_BLACKLISTED = createModTag("net_blacklisted");

        public static ITag.INamedTag<EntityType<?>> createModTag(String name){
            return EntityTypeTags.getTagById(new ResourceLocation(DinosExpansion.MOD_ID, name).toString());
        }

    }


    public static class Fluids{

        public static final ITag.INamedTag<Fluid> CANNOT_FLY_FROM = createModTag("cannot_fly_from");

        public static ITag.INamedTag<Fluid> createModTag(String name){
            return FluidTags.makeWrapperTag(new ResourceLocation(DinosExpansion.MOD_ID, name).toString());
        }
    }

}
