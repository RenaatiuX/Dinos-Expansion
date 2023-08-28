package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.world.gen.feature.vulcano.VulcanoConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;

public class ConfiguredStructureInit {

    public static final StructureFeature<NoFeatureConfig, ?> HERMIT_HOUSE = register("hermit_house_configured", StructureInit.HERMIT_HOSE.get(), IFeatureConfig.NO_FEATURE_CONFIG);
    public static final StructureFeature<VulcanoConfig, ?> VULCANO = register("vulcano_configured",StructureInit.VULCANO.get(),  VulcanoConfig.VulcanoConfigBuilder.builder().withMaxHeight(40).withMinHeight(20).withScale(.3d).withVulcanoBlock(Blocks.BASALT).withVulcanoFluid(Blocks.LAVA).withOre(Blocks.DIAMOND_ORE, 10, 10, 20,50, true).withOre(Blocks.GOLD_ORE, 5, 10, 20, 50,true).build());



    public static <FC extends IFeatureConfig, F extends Structure<FC>> StructureFeature<FC, ? extends Structure<FC>> register(String name, F feature,  FC config){
        StructureFeature<FC, ? extends Structure<FC>> configFeature = Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, DinosExpansion.modLoc(name), feature.withConfiguration(config));
        FlatGenerationSettings.STRUCTURES.put(feature, configFeature);
        return configFeature;
    }
}
