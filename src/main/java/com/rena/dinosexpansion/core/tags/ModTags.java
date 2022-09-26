package com.rena.dinosexpansion.core.tags;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class ModTags {


    //Entities
    public static class EntityTypes{

        public static final ITag.INamedTag<EntityType<?>> NET_BLACKLISTED = createModTag("net_blacklisted");

        public static ITag.INamedTag<EntityType<?>> createModTag(String name){
            return EntityTypeTags.getTagById(new ResourceLocation(DinosExpansion.MOD_ID, name).toString());
        }

    }

}
