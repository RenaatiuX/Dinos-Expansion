package com.rena.dinosexpansion.common.world.structure.structures.vulcano;

import com.rena.dinosexpansion.common.world.gen.feature.vulcano.VulcanoConfig;
import net.minecraft.block.BlockState;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class VulcanoStructure extends Structure<VulcanoConfig> {
    public VulcanoStructure() {
        super(VulcanoConfig.VOCANO_CODEC);
    }

    @Override
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public IStartFactory<VulcanoConfig> getStartFactory() {
        return VulcanoStructure.Start::new;
    }

    @Override
    protected boolean func_230363_a_(ChunkGenerator chunkGenerator, BiomeProvider biomeSource,
                                     long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ,
                                     Biome biome, ChunkPos chunkPos, VulcanoConfig featureConfig) {

        BlockPos centerOfChunk = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);
        int landHeight = chunkGenerator.getHeight(centerOfChunk.getX(), centerOfChunk.getZ(),
                Heightmap.Type.WORLD_SURFACE_WG);

        IBlockReader columnOfBlocks = chunkGenerator.func_230348_a_(centerOfChunk.getX(), centerOfChunk.getZ());
        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.up(landHeight));

        return topBlock.getFluidState().isEmpty();
    }

    public static class Start extends StructureStart<VulcanoConfig> {

        public Start(Structure<VulcanoConfig> structureIn, int chunkX, int chunkZ,
                     MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override // generatePieces
        public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator,
                                   TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn,
                                   VulcanoConfig config) {
            int chunkMiddleX = (chunkX << 4) + 7;
            int chunkMiddleZ = (chunkZ << 4) + 7;

            int minHeight = config.getMinHeight();
            int maxHeight = config.getMaxHeight();
            double scale = config.getScale();
            BlockStateProvider vulcanoFluid = config.getVulcanoFluid();
            BlockStateProvider vulcanoBlock = config.getVulcanoBlock();

            int height = Math.min(minHeight, maxHeight) + rand.nextInt(Math.abs(minHeight - maxHeight));

            List<VulcanoConfig.VulcanoOreHolder> ores = config.getOres();
            MinecraftForge.EVENT_BUS.post(new VulcanoGatherOresEvent(ores, dynamicRegistryManager, chunkGenerator, templateManagerIn, chunkX, chunkZ, biomeIn, config));

            this.components.add(new VulcanoPiece(chunkX * 16, chunkZ * 16, rand, height, scale, vulcanoBlock.getBlockState(rand, new BlockPos(chunkMiddleX, 0, chunkMiddleZ)).getBlock(), vulcanoFluid.getBlockState(rand, new BlockPos(chunkMiddleX, 0, chunkMiddleZ)).getBlock(), ores));
            //int landheight = chunkGenerator.getNoiseHeight(chunkMiddleX, chunkMiddleZ, Heightmap.Type.WORLD_SURFACE_WG);
            //this.components.get(0).offset(0, Math.max(0, landheight - 64), 0);
            this.recalculateStructureSize();


            LogManager.getLogger().log(Level.DEBUG, String.format("this got executed with values: scale: %s, height in range: [%s, %s]", scale, minHeight, maxHeight));

            LogManager.getLogger().log(Level.DEBUG, "Vulcano at " +
                    this.components.get(0).getBoundingBox().minX + " " +
                    this.components.get(0).getBoundingBox().minY + " " +
                    this.components.get(0).getBoundingBox().minZ);


        }

    }
}
