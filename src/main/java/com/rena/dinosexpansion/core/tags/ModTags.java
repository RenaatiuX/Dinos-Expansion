package com.rena.dinosexpansion.core.tags;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags {


    public static class Items{

        public static final ITag.INamedTag<Item> COMPOUND_ARROWS = mod("compound_arrows");

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

}
