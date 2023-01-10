package com.rena.dinosexpansion.common.world.gen.trees;

import com.rena.dinosexpansion.common.world.gen.trees.util.ModTreeConfig;
import com.rena.dinosexpansion.common.world.gen.trees.util.TreeSpawner;
import com.rena.dinosexpansion.core.init.FeatureInit;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class ModTreeSpawners {

    public static final TreeSpawner REDWOOD = new TreeSpawner() {
        @Nullable
        public ConfiguredFeature<ModTreeConfig, ?> getTreeFeature(Random random) {
            return random.nextInt(2) == 0 ? FeatureInit.ConfiguredFeatures.REDWOOD_TREE1 : FeatureInit.ConfiguredFeatures.REDWOOD_TREE2;
        }
    };

}
