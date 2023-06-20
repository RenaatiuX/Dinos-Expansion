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
        simple(ItemInit.TRANQUILLIZER_ARROW.get(), ItemInit.WOOD_BOOMERANG.get());
        spawnEgg(ItemInit.PARAPUZOSIA_SPAWN_EGG.get(), ItemInit.EOSQUALODON_SPAWN_EGG.get(), ItemInit.MEGA_PIRANHA_SPAWN_EGG.get(), ItemInit.DIMORPHODON_SPAWN_EGG.get(), ItemInit.ASTORGOSUCHUS_SPAWN_EGG.get(), ItemInit.CAMPANILE_SPAEN_EGG.get(), ItemInit.ANOMALOCARIS_SPAWN_EGG.get());
        simple(ItemInit.GOLD_ARROW.get(), ItemInit.STONE_ARROW.get(), ItemInit.BONE_ARROW.get(), ItemInit.NARCOTICS.get());
        simple(ItemInit.DIAMOND_ARROW.get(), ItemInit.EMERALD_ARROW.get(), ItemInit.IRON_ARROW.get(), ItemInit.NETHERITE_ARROW.get(), ItemInit.WOODEN_ARROW.get());
        simple(ItemInit.KIBBLE_SIMPLE.get(), ItemInit.KIBBLE_BASIC.get(), ItemInit.KIBBLE_REGULAR.get(), ItemInit.KIBBLE_SUPERIOR.get(), ItemInit.KIBBLE_EXCEPTIONAL.get(), ItemInit.KIBBLE_EXTRAORDINARY.get());
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
