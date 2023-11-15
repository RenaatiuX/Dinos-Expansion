package com.rena.dinosexpansion.core.datagen.client;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ModItemModelsProvider extends ItemModelProvider {

    public final ModelFile generated = getExistingFile(mcLoc("item/generated"));
    public final ModelFile handheld = getExistingFile(mcLoc("item/handheld"));
    public final ModelFile spawnEgg = getExistingFile(mcLoc("item/template_spawn_egg"));
    public final ModelFile charkram = getExistingFile(modLoc("item/chakram"));

    public ModItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DinosExpansion.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        ItemInit.ITEMS.getEntries().stream().map(RegistryObject::get).filter(i -> i instanceof SpawnEggItem).forEach(this::spawnEgg);

        simple(ItemInit.COMPOUND_ARROW.get());
        simple(ItemInit.TRANQUILLIZER_ARROW.get(), ItemInit.WOOD_BOOMERANG.get());
        simple(ItemInit.GOLD_ARROW.get(), ItemInit.STONE_ARROW.get(), ItemInit.BONE_ARROW.get(), ItemInit.NARCOTICS.get());
        simple(ItemInit.DIAMOND_ARROW.get(), ItemInit.EMERALD_ARROW.get(), ItemInit.IRON_ARROW.get(), ItemInit.NETHERITE_ARROW.get(), ItemInit.WOODEN_ARROW.get());
        simple(ItemInit.KIBBLE_SIMPLE.get(), ItemInit.KIBBLE_BASIC.get(), ItemInit.KIBBLE_REGULAR.get(), ItemInit.KIBBLE_SUPERIOR.get(), ItemInit.KIBBLE_EXCEPTIONAL.get(), ItemInit.KIBBLE_EXTRAORDINARY.get(), ItemInit.CRATAEGUS_BOAT_ITEM.get());
        simple(BlockInit.CAMPANILE_SHELL_COMMON.get(), BlockInit.CAMPANILE_SHELL_UNCOMMON.get());
        simple(ItemInit.CAMPANILE_GOO.get());
        simple(ItemInit.COOKED_ANKYLOSAURUS_MEAT.get(), ItemInit.COOKED_ANOMALOCARIS_TAIL.get());
        simple(ItemInit.COOKED_ASTORGOSUCHUS_MEAT.get(), ItemInit.COOKED_CARNOTAURUS_MEAT.get(), ItemInit.COOKED_DIMORPHODON_MEAT.get(), ItemInit.COOKED_GALLIMIMUS_MEAT.get(), ItemInit.COOKED_PARAPUZOSIA_TENTACLE.get(), ItemInit.COOKED_TRICERATOPS_MEAT.get());
        simple(ItemInit.CORN.get(), ItemInit.CORN_SEED.get());
        simple(ItemInit.CUCUMBER.get());
        simple(ItemInit.DART.get(), ItemInit.DINOPEDIA.get(), ItemInit.EXPLORER_JOURNAL.get(), ItemInit.EXPLORER_JOURNAL_PAGE.get());
        handheld(ItemInit.DIAMOND_HATCHET.get(), ItemInit.DIAMOND_BOOMERANG.get());
        simple(ItemInit.EGGPLANT.get(), ItemInit.EGGPLANT_SEED.get());
        simple(ItemInit.ELECTRONICS_PARTS.get());
        handheld(ItemInit.EMERALD_HATCHET.get(), ItemInit.EMERALD_BOOMERANG.get());
        handheld(ItemInit.GOLD_HATCHET.get(), ItemInit.GOLDEN_BOOMERANG.get());
        handheld(ItemInit.IRON_HATCHET.get(), ItemInit.IRON_BOOMERANG.get());
        handheld(ItemInit.NETHERITE_BOOMERANG.get(), ItemInit.NETHERITE_HATCHET.get());
        simple(ItemInit.LETTUCE_SEED.get(), ItemInit.LETTUCE.get());
        simple(ItemInit.NARCOTIC_BERRIES.get());
        simple(ItemInit.OIL.get(), ItemInit.ONION.get(),ItemInit.ORANGE_BERRIES.get(),  ItemInit.QUICKSAND_BUCKET.get());
        simple(ItemInit.RAW_ANKYLOSAURUS_MEAT.get(), ItemInit.RAW_ANOMALOCARIS_TAIL.get(), ItemInit.RAW_ASTORGOSUCHUS_MEAT.get(), ItemInit.RAW_CARNOTAURUS_MEAT.get(), ItemInit.RAW_DIMORPHODON_MEAT.get(), ItemInit.RAW_DRYOSAURUS_MEAT.get(), ItemInit.RAW_PARAPUZOSIA_TENTACLE.get(), ItemInit.RAW_TRICERATOPS_MEAT.get());
        simple(ItemInit.RUBY.get(), ItemInit.RUBY_ARROW.get());
        handheld(ItemInit.RUBY_BOOMERANG.get(), ItemInit.RUBY_HATCHET.get());
        handheld(ItemInit.SAPPHIRE_BOOMERANG.get(), ItemInit.SAPPHIRE_HATCHET.get());
        simple(ItemInit.SAPPHIRE.get(), ItemInit.SAPPHIRE_ARROW.get());
        simple(ItemInit.METAL_SCRAP.get());
        simple(ItemInit.SPINACH.get(), ItemInit.BAMBOO_SPYGLASS.get());
        handheld(ItemInit.STONE_BOOMERANG.get(), ItemInit.STONE_HATCHET.get());
        simple(ItemInit.TELEPORT_ITEM.get(), ItemInit.TINY_ROCK.get(), ItemInit.TOMATO.get());
        handheld(ItemInit.WOODEN_HATCHET.get(), ItemInit.WOOD_BOOMERANG.get());
        simple(ItemInit.REDWOOD_STICK.get());
        handheld(ItemInit.RUBY_SWORD.get(), ItemInit.RUBY_PICKAXE.get(), ItemInit.RUBY_SHOVEL.get(), ItemInit.RUBY_HOE.get(), ItemInit.RUBY_AXE.get());
        handheld(ItemInit.SAPPHIRE_SWORD.get(), ItemInit.SAPPHIRE_PICKAXE.get(), ItemInit.SAPPHIRE_SHOVEL.get(), ItemInit.SAPPHIRE_HOE.get(), ItemInit.SAPPHIRE_AXE.get());
        simple(ItemInit.CRATAEGUS_SIGN.get(), ItemInit.CRATAEGUS_STICK.get());

        charkram(ItemInit.RUBY_CHAKRAM.get(), ItemInit.NETHERITE_CHAKRAM.get(), ItemInit.IRON_CHAKRAM.get(), ItemInit.STONE_CHAKRAM.get(), ItemInit.EMERALD_CHAKRAM.get(), ItemInit.DIAMOND_CHAKRAM.get(), ItemInit.SAPPHIRE_CHAKRAM.get(), ItemInit.WOODEN_CHAKRAM.get(), ItemInit.GOLD_CHAKRAM.get());

        makeSlingshot();

    }

    private void makeSlingshot(){
        String name = ItemInit.SLINGSHOT.getId().getPath();
        ResourceLocation overrideProperty = new ResourceLocation("pulling");
        String override0 = "_pulling_0";
        String override1 = "_pulling_1";
        getBuilder(name).parent(generated).texture("layer0", "item/" + name)
                .override().predicate(overrideProperty, 0).model(getBuilder(name + override0).parent(generated).texture("layer0", "item/" + name + override0)).end()
                .override().predicate(overrideProperty, 0).model(getBuilder(name + override1).parent(generated).texture("layer0", "item/" + name + override1)).end();
    }

    private void simple(Item... items) {
        for (Item item : items) {
            String name = item.getRegistryName().getPath();
            getBuilder(name).parent(generated).texture("layer0", "item/" + name);
        }
    }

    private void simple(IItemProvider... items) {
        for (IItemProvider itemProvider : items) {
            simple(itemProvider.asItem());
        }
    }

    private void charkram(Item... items) {
        for (Item item : items) {
            String name = item.getRegistryName().getPath();
            getBuilder(name).parent(charkram).texture("layer0", "item/" + name);
        }
    }

    private void charkram(IItemProvider... items) {
        for (IItemProvider itemProvider : items) {
            charkram(itemProvider.asItem());
        }
    }

    private void handheld(Item... items) {
        for (Item item : items) {
            String name = item.getRegistryName().getPath();
            getBuilder(name).parent(handheld).texture("layer0", "item/" + name);
        }
    }

    private void handheld(IItemProvider... items) {
        for (IItemProvider itemProvider : items) {
            handheld(itemProvider.asItem());
        }
    }

    private void spawnEgg(Item... items) {
        for (Item item : items) {
            String name = item.getRegistryName().getPath();
            getBuilder(name).parent(spawnEgg);
        }
    }
}
