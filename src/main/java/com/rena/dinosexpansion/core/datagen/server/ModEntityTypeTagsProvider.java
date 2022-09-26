package com.rena.dinosexpansion.core.datagen.server;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.tags.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public ModEntityTypeTagsProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, DinosExpansion.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        addBlacklisted();
    }

    /**
     * adds Entity types that cant be cough with the ball
     */
    private void addBlacklisted() {
        getOrCreateBuilder(ModTags.EntityTypes.NET_BLACKLISTED).add(EntityType.WITHER, EntityType.ENDER_DRAGON, EntityType.END_CRYSTAL).addTag(EntityTypeTags.ARROWS);
    }
}
