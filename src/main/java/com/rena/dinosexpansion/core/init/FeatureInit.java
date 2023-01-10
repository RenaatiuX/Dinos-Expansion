package com.rena.dinosexpansion.core.init;

import com.google.common.collect.ImmutableList;
import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.world.gen.trees.ModTreeSpawners;
import com.rena.dinosexpansion.common.world.gen.trees.redwood.RedWoodTree2;
import com.rena.dinosexpansion.common.world.gen.trees.redwood.RedwoodTree1;
import com.rena.dinosexpansion.common.world.gen.trees.util.ModTreeConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureInit {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, DinosExpansion.MOD_ID);

    public static final RegistryObject<Feature<ModTreeConfig>> REDWOOD_TREE1 = FEATURES.register("redwood_tree1",
            () -> new RedwoodTree1(ModTreeConfig.CODEC.stable()));
    public static final RegistryObject<Feature<ModTreeConfig>> REDWOOD_TREE2 = FEATURES.register("redwood_tree2",
            () -> new RedWoodTree2(ModTreeConfig.CODEC.stable()));

    public static final class ConfiguredFeatures {
        //Trees
        public static final ConfiguredFeature<ModTreeConfig, ?> REDWOOD_TREE1 = FeatureInit.REDWOOD_TREE1.get().withConfiguration(
                new ModTreeConfig.Builder().setTrunkBlock(BlockInit.REDWOOD_LOG.get()).setLeavesBlock(BlockInit.REDWOOD_LEAVES.get()).setMaxHeight(37).setMinHeight(36).build());
        public static final ConfiguredFeature<ModTreeConfig, ?> REDWOOD_TREE2 = FeatureInit.REDWOOD_TREE2.get().withConfiguration(
                new ModTreeConfig.Builder().setTrunkBlock(BlockInit.REDWOOD_LOG.get()).setLeavesBlock(BlockInit.REDWOOD_LEAVES.get()).setMaxHeight(34).setMinHeight(31).build());
        public static final ConfiguredFeature<?, ?> RANDOM_REDWOOD_TREE = Feature.RANDOM_SELECTOR.withConfiguration(
                new MultipleRandomFeatureConfig(ImmutableList.of(
                        REDWOOD_TREE1.withChance(0.55F),
                        REDWOOD_TREE2.withChance(0.1F)),
                        REDWOOD_TREE2));
    }

    public static void registerConfiguredFeatures() {
        register("redwood_trees", ConfiguredFeatures.RANDOM_REDWOOD_TREE.withPlacement(
                Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(
                        new AtSurfaceWithExtraConfig(1, 0.3F, 2))));
    }

    private static <FC extends IFeatureConfig> void register(String name, ConfiguredFeature<FC, ?> feature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(DinosExpansion.MOD_ID, name), feature);
    }
}
