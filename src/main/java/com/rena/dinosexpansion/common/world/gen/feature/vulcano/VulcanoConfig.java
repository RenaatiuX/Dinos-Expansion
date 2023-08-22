package com.rena.dinosexpansion.common.world.gen.feature.vulcano;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.ArrayList;
import java.util.List;

public class VulcanoConfig implements IFeatureConfig {

    public static final Codec<VulcanoConfig> VOCANO_CODEC = RecordCodecBuilder.create(recoder -> recoder.group(
            BlockStateProvider.CODEC.fieldOf("vulcano_block").orElse(new SimpleBlockStateProvider(Blocks.STONE.getDefaultState())).forGetter(VulcanoConfig::getVulcanoBlock),
            BlockStateProvider.CODEC.fieldOf("vulcano_fluid").orElse(new SimpleBlockStateProvider(Blocks.LAVA.getDefaultState())).forGetter(VulcanoConfig::getVulcanoFluid),
            Codec.INT.fieldOf("min_height").orElse(50).forGetter(VulcanoConfig::getMinHeight),
            Codec.INT.fieldOf("max_height").orElse(100).forGetter(VulcanoConfig::getMaxHeight),
            Codec.DOUBLE.fieldOf("scale").orElse(1d).forGetter(VulcanoConfig::getScale),
            Codec.list(VulcanoOreHolder.ORE_HOLDER_CODEC).fieldOf("ores").forGetter(VulcanoConfig::getOres)
    ).apply(recoder, VulcanoConfig::new));

    private final BlockStateProvider vulcanoBlock, vulcanoFluid;
    private final int maxHeight, minHeight;
    private final double scale;

    private final List<VulcanoOreHolder> ores;

    public VulcanoConfig(BlockStateProvider vulcanoBlock, BlockStateProvider vulcanoFluid, int maxHeight, int minHeight, double scale, List<VulcanoOreHolder> ores) {
        this.vulcanoBlock = vulcanoBlock;
        this.vulcanoFluid = vulcanoFluid;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        this.scale = scale;
        this.ores = ores;
    }

    public BlockStateProvider getVulcanoBlock() {
        return vulcanoBlock;
    }

    public BlockStateProvider getVulcanoFluid() {
        return vulcanoFluid;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public double getScale() {
        return scale;
    }

    public List<VulcanoOreHolder> getOres() {
        return ores;
    }

    public boolean hasOres() {
        return this.ores.size() > 0;
    }

    public static class VulcanoConfigBuilder {
        public static VulcanoConfigBuilder builder() {
            return new VulcanoConfigBuilder();
        }

        private BlockStateProvider vulcanoBlock;
        private BlockStateProvider vulcanoFluid;
        private int maxHeight;
        private int minHeight;
        private double scale;

        private List<VulcanoOreHolder> ores;

        public VulcanoConfigBuilder() {
            // Set default values if needed
            this.maxHeight = 100; // Example default value
            this.minHeight = 50;  // Example default value
            this.scale = 1.0;     // Example default value
            this.ores = new ArrayList<>();
        }

        /**
         * @param vulcanoBlock the block the vulcano will be made of
         */
        public VulcanoConfigBuilder withVulcanoBlock(Block vulcanoBlock) {
            this.vulcanoBlock = new SimpleBlockStateProvider(vulcanoBlock.getDefaultState());
            return this;
        }

        /**
         * @param vulcanoFluid the fluid that will spwan around the vulcano and inside
         */

        public VulcanoConfigBuilder withVulcanoFluid(Block vulcanoFluid) {
            this.vulcanoFluid = new SimpleBlockStateProvider(vulcanoFluid.getDefaultState());
            return this;
        }

        /**
         * sets the maxHeight elevation the vulcano can reach together with the minHeight the vulcano can have a range of [minHeight, maxHeight]
         */
        public VulcanoConfigBuilder withMaxHeight(int maxHeight) {
            this.maxHeight = maxHeight;
            return this;
        }

        /**
         * together with the minHeight the vulcano can have a range of [minHeight, maxHeight]
         *
         * @param minHeight the min Height the vulcanoe can get
         * @return
         */
        public VulcanoConfigBuilder withMinHeight(int minHeight) {
            this.minHeight = minHeight;
            return this;
        }

        /**
         * @param scale scales the vulcanoe, just makes it wider, normally the vulcano will be around 50x50 up to 80x80
         * @return
         */
        public VulcanoConfigBuilder withScale(double scale) {
            this.scale = scale;
            return this;
        }

        /**
         * @param ore            the ore u want to add to this vulcano
         * @param chance         the chance 1 / chance
         * @param minOre         the min amount of ores that should spawn
         * @param maxOre         the max amount of ores that should spawn
         * @param repetitions    how often the ore should try to generate in the vulcano
         * @param canLookOutside this defines if the ore can be seen from outside
         */
        public VulcanoConfigBuilder withOre(BlockStateProvider ore, int chance, int minOre, int maxOre, int repetitions, boolean canLookOutside) {
            if (minOre > maxOre)
                throw new IllegalArgumentException(String.format("min ore must be less or equal to maxOre not [%s, %s]", minOre, maxOre));
            this.ores.add(new VulcanoOreHolder(ore, chance, maxOre, minOre, repetitions, canLookOutside));
            return this;
        }

        public VulcanoConfigBuilder withOre(Block ore, int chance, int minOre, int maxOre, int repetitions) {
            return withOre(ore, chance, minOre, maxOre, repetitions, false);
        }

        public VulcanoConfigBuilder withOre(Block ore, int chance, int minOre, int maxOre, int repetitions, boolean canLookOutside) {
            return withOre(new SimpleBlockStateProvider(ore.getDefaultState()), chance, minOre, maxOre, repetitions, canLookOutside);
        }

        public VulcanoConfigBuilder withOre(BlockStateProvider ore, int chance, int minOre, int maxOre, int repetitions) {
            return withOre(ore, chance, minOre, maxOre, repetitions, false);
        }

        public VulcanoConfig build() {
            return new VulcanoConfig(vulcanoBlock, vulcanoFluid, maxHeight, minHeight, scale, ores);
        }
    }

    public static class VulcanoOreHolder {

        public static final Codec<VulcanoOreHolder> ORE_HOLDER_CODEC = RecordCodecBuilder.create(r -> r.group(
                BlockStateProvider.CODEC.fieldOf("ore").forGetter(VulcanoOreHolder::getOre),
                Codec.INT.fieldOf("chance").fieldOf("chance").forGetter(VulcanoOreHolder::getChance),
                Codec.INT.fieldOf("min_ores").forGetter(VulcanoOreHolder::getMinOres),
                Codec.INT.fieldOf("max_ores").forGetter(VulcanoOreHolder::getMaxOres),
                Codec.INT.fieldOf("repetitions").forGetter(VulcanoOreHolder::getRepetitions),
                Codec.BOOL.fieldOf("can_look_outside").forGetter(VulcanoOreHolder::canLookOutside)
        ).apply(r, VulcanoOreHolder::new));
        protected final BlockStateProvider ore;
        protected final int chance;
        protected final int maxOres, minOres, repetitions;
        protected final boolean canLookOutside;

        public VulcanoOreHolder(BlockStateProvider ore, int chance, int maxOres, int minOres, int repetitions, boolean canLookOutside) {
            this.ore = ore;
            this.chance = chance;
            this.maxOres = maxOres;
            this.minOres = minOres;
            this.repetitions = repetitions;
            this.canLookOutside = canLookOutside;
        }

        public BlockStateProvider getOre() {
            return ore;
        }

        public int getChance() {
            return chance;
        }

        public int getRepetitions() {
            return repetitions;
        }

        public int getMaxOres() {
            return maxOres;
        }

        public int getMinOres() {
            return minOres;
        }

        public boolean canLookOutside() {
            return canLookOutside;
        }
    }
}
