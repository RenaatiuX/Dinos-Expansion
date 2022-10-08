package com.rena.dinosexpansion.core.datagen.client;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelsProvider extends ItemModelProvider {

    public final ModelFile generated = getExistingFile(mcLoc("item/generated"));
    public final ModelFile spawnEgg = getExistingFile(mcLoc("item/template_spawn_egg"));

    public ModItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DinosExpansion.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simple(ItemInit.COMPOUND_ARROW.get());
        simple(ItemInit.TRANQUILLIZER_ARROW.get());
        spawnEgg(ItemInit.PARAPUZOSIA_SPAWN_EGG.get());
    }

    private void simple(Item... items) {
        for (Item item : items) {
            String name = item.getRegistryName().getPath();
            getBuilder(name).parent(generated).texture("layer0", "item/" + name);
        }
    }

    private void spawnEgg(Item... items) {
        for (Item item : items) {
            String name = item.getRegistryName().getPath();
            getBuilder(name).parent(spawnEgg);
        }
    }
}
