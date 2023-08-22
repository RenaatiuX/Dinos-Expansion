package com.rena.dinosexpansion.common.world.structure.structures.vulcano;

import com.rena.dinosexpansion.DinosExpansion;
import com.rena.dinosexpansion.common.world.gen.feature.vulcano.VulcanoConfig;
import com.rena.dinosexpansion.core.init.StructurePieceInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.structure.ScatteredStructurePiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VulcanoPiece extends ScatteredStructurePiece {

    protected Block vulcanoBlock, vulcanoFluid;
    protected int height, width, lavaLevel;
    protected double scale;
    protected final List<VulcanoConfig.VulcanoOreHolder> ores;

    public VulcanoPiece(int x, int z, Random rand, int height, double scale, Block vulcanoBlock, Block vulcanoFluid, List<VulcanoConfig.VulcanoOreHolder> ores) {
        super(StructurePieceInit.VULCANO_PIECE, rand, x, 64, z, 2 * calcWidth(height, scale), 64 + height, 2 * calcWidth(height, scale));
        this.ores = ores;
        this.width = calcWidth(height, scale);
        this.height = height;
        this.scale = scale;
        this.vulcanoBlock = vulcanoBlock;
        this.vulcanoFluid = vulcanoFluid;
        this.lavaLevel = (int) ((double) height * 1.3d);
    }

    public VulcanoPiece(TemplateManager manager, CompoundNBT nbt) {
        super(StructurePieceInit.VULCANO_PIECE, nbt);
        this.vulcanoBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(nbt.getString("vulcanoBlock")));
        this.vulcanoFluid = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(nbt.getString("vulcanoFluid")));
        this.height = nbt.getInt("height");
        this.scale = nbt.getDouble("scale");
        this.width = nbt.getInt("width");
        this.lavaLevel = nbt.getInt("lavaLevel");
        this.ores = new ArrayList<>();
        for (int i = 0; nbt.contains("ore_holder" + i); i++) {
            this.ores.add(fromNbt(nbt.getCompound("ore_holder" + i)));
        }


    }

    protected VulcanoConfig.VulcanoOreHolder fromNbt(CompoundNBT nbt) {
        int chance = nbt.getInt("chance");
        int minOres = nbt.getInt("min_ores");
        int maxOres = nbt.getInt("max_ores");
        int repetitions = nbt.getInt("repetitions");
        boolean canLookOutside = nbt.getBoolean("can_look_outside");
        Block vulcanoOres = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(nbt.getString("ore")));
        return new VulcanoConfig.VulcanoOreHolder(new SimpleBlockStateProvider(vulcanoOres.getDefaultState()), chance, maxOres, minOres, repetitions, canLookOutside);
    }

    protected CompoundNBT toNbt(CompoundNBT nbt, VulcanoConfig.VulcanoOreHolder holder) {
        nbt.putInt("chance", holder.getChance());
        nbt.putInt("min_ores", holder.getMinOres());
        nbt.putInt("max_ores", holder.getMaxOres());
        nbt.putBoolean("can_look_outside", holder.canLookOutside());
        nbt.putInt("repetitions", holder.getRepetitions());
        nbt.putString("ore", ForgeRegistries.BLOCKS.getKey(holder.getOre().getBlockState(new Random(), BlockPos.ZERO).getBlock()).toString());
        return nbt;
    }

    @Override
    protected void readAdditional(CompoundNBT nbt) {
        nbt.putString("vulcanoBlock", ForgeRegistries.BLOCKS.getKey(vulcanoBlock).toString());
        nbt.putString("vulcanoFluid", ForgeRegistries.BLOCKS.getKey(vulcanoFluid).toString());
        nbt.putInt("height", this.height);
        nbt.putDouble("scale", this.scale);
        nbt.putInt("width", this.width);
        nbt.putInt("lavaLevel", this.lavaLevel);

        for (int i = 0; i < this.ores.size(); i++) {
            nbt.put("ore_holder" + i, toNbt(new CompoundNBT(), this.ores.get(i)));
        }

    }

    //the actual generation of the vulcano
    @Override
    public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator, Random rand, MutableBoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
        int vulcanoMaxHeight = 0;
        if (width <= 0)
            return false;
        for (int x = 0; x <= 2 * width; x++) {
            for (int z = 0; z <= 2 * width; z++) {
                double vulcanoHeight = normalVulcanoFunction(0, 0, x - width, z - width, height, scale);
                if (((int) vulcanoHeight) > vulcanoMaxHeight) {
                    vulcanoMaxHeight = (int) vulcanoHeight;
                }
                //DinosExpansion.LOGGER.debug(String.format("vulcano height here is %s", vulcanoHeight));
                this.fillWithBlocksChanceFluid(world, boundingBox, rand, x, 0, z, x, (int) vulcanoHeight, z, this.vulcanoFluid.getDefaultState(), this.vulcanoBlock.getDefaultState());
                //this.setBlockState(world,this.vulcanoBlock.getDefaultState(), x, (int) vulcanoHeight, z, boundingBox);
                this.fillWithAir(world, boundingBox, x, (int) vulcanoHeight, z, x, height + 10, z);
            }
        }

        //calc inner circle radius
        double functionValue = normalVulcanoFunction(0, 0, 0, 0, height, scale);
        int innerCircleWidth = 0;
        for (int x = 0; Math.abs(functionValue) < vulcanoMaxHeight; x++) {
            functionValue = normalVulcanoFunction(0, 0, x, 0, height, scale);
            innerCircleWidth = Math.abs(x);
        }

        for (int x = -innerCircleWidth; x <= innerCircleWidth; x++) {
            for (int z = -innerCircleWidth; z <= innerCircleWidth; z++) {
                double vulcanoHeight = normalVulcanoFunction(0, 0, x, z, height, scale);
                if (vulcanoHeight < this.lavaLevel) {
                    this.fillWithBlocks(world, boundingBox, x + width, (int) vulcanoHeight, z + width, x + width, lavaLevel, z + width, this.vulcanoFluid.getDefaultState(), this.vulcanoFluid.getDefaultState(), false);
                }
            }
        }

        generateOres(world, boundingBox, rand);


        //DinosExpansion.LOGGER.debug(String.format("tried to add lava in the center at: (%s, %s), the vulcano height and the lava Level are: (%s, %s)", x, z, vulcanoHeight, lavaLevel));


        return true;
    }


    protected void fillWithBlocksChanceFluid(ISeedReader worldIn, MutableBoundingBox boundingboxIn, Random rand, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, BlockState fluid, BlockState insideBlockState) {
        for (int i = yMin; i <= yMax; ++i) {
            for (int j = xMin; j <= xMax; ++j) {
                for (int k = zMin; k <= zMax; ++k) {
                    if ((this.getBlockStateFromPos(worldIn, j + 1, i, k, boundingboxIn).isAir() ||
                            this.getBlockStateFromPos(worldIn, j - 1, i, k, boundingboxIn).isAir() ||
                            this.getBlockStateFromPos(worldIn, j, i, k + 1, boundingboxIn).isAir() ||
                            this.getBlockStateFromPos(worldIn, j, i, k - 1, boundingboxIn).isAir()) &&
                            rand.nextInt(100) == 0
                    ) {
                        this.setBlockState(worldIn, fluid, j, i, k, boundingboxIn);
                    } else {
                        this.setBlockState(worldIn, insideBlockState, j, i, k, boundingboxIn);
                    }
                }
            }
        }

    }

    protected void generateOres(ISeedReader world, MutableBoundingBox boundingBox, Random rand) {
        for (VulcanoConfig.VulcanoOreHolder holder : this.ores) {
            for (int i = 0; i < holder.getRepetitions(); i++) {
                if (rand.nextInt(holder.getChance()) != 0)
                    continue;
                //random x, z position in the vulcano
                BlockPos.Mutable basePos = new BlockPos.Mutable(0, 0, 0);
                //determine the height
                do {
                    basePos.setY(0);
                    basePos.setX(rand.nextInt(2 * width));
                    basePos.setZ(rand.nextInt(2 * width));
                    int vulcanoHeight = (int) Math.abs(normalVulcanoFunction(0, 0, basePos.getX() - width, basePos.getZ() - width, height, scale));
                    if (vulcanoHeight == 0)
                        continue;
                    basePos.setY(rand.nextInt(vulcanoHeight));
                } while ((hasAnywhereAir(basePos, world, boundingBox) && !holder.canLookOutside()) || ((int)normalVulcanoFunction(0, 0, basePos.getX() - width, basePos.getZ() - width, height, scale)) < 1);
                int count = 0;
                int amount = Math.min(holder.getMinOres(), holder.getMaxOres()) + rand.nextInt(Math.abs(holder.getMaxOres() - holder.getMinOres()));

                // yeah this will make veins
                List<BlockPos> doneBlockPos = new ArrayList<>();
                List<BlockPos> possibleNextPos = new ArrayList<>();
                possibleNextPos.add(new BlockPos(basePos));

                while (count < amount && possibleNextPos.size() > 0) {
                    BlockPos randomPos = possibleNextPos.get(rand.nextInt(possibleNextPos.size()));
                    count++;
                    doneBlockPos.add(randomPos);
                    possibleNextPos.remove(randomPos);
                    this.setBlockState(world, holder.getOre().getBlockState(rand, randomPos), randomPos.getX(), randomPos.getY(), randomPos.getZ(), boundingBox);
                    for (Direction dir : Direction.values()) {
                        BlockPos newPos = randomPos.offset(dir);
                        int vulcanoHeight = (int) normalVulcanoFunction(0, 0, newPos.getX() - width, newPos.getZ() - width, height, scale);
                        if (!doneBlockPos.contains(newPos) && (!hasAnywhereAir(basePos, world, boundingBox) || holder.canLookOutside()) && newPos.getY() < vulcanoHeight) {
                            possibleNextPos.add(newPos);
                        }
                    }
                }
            }


        }
    }

    protected int getSurfaceHeight(ChunkGenerator generator, int x, int z){
        return generator.getHeight(this.getXWithOffset(x, z), this.getZWithOffset(x, z), Heightmap.Type.WORLD_SURFACE_WG);
    }

    protected boolean hasAnywhereAir(BlockPos pos, ISeedReader world, MutableBoundingBox boundingBox) {
        for (Direction dir : Direction.values()) {
            pos = pos.offset(dir);
            if (this.getBlockStateFromPos(world, pos.getX(), pos.getY(), pos.getZ(), boundingBox).isAir()) {
                return true;
            }
        }
        return false;
    }

    public void placeFromUntil(int x, int z, int startY, int endY, Block block, ISeedReader world) {
        for (int y = startY; y < endY; y++) {
            BlockPos pos = new BlockPos(x, y, z);
            world.setBlockState(pos, block.getDefaultState(), 2);
        }
    }

    protected static int calcWidth(int height, double scale) {
        double functionValue = normalVulcanoFunction(0, 0, 0, 0, height, scale);
        int width = 0;
        for (int x = 0; Math.abs(functionValue) > 1d; x++) {
            functionValue = normalVulcanoFunction(0, 0, x, 0, height, scale);
            width = Math.abs(x);
        }
        return width;
    }

    public static double normalVulcanoFunction(double middleX, double middleY, double x, double z, double height, double scale) {
        return vulcanoFunction(middleX, middleY, x * scale, z * scale, 5.8d, height, -0.2);
    }


    public static double vulcanoFunction(double middleX, double middlez, double x, double z, double phi, double height, double k) {
        return vulcanoFunction(x - middleX, z - middlez, phi, height, k);
    }

    private static double vulcanoFunction(double x, double z, double phi, double height, double k) {
        double distanceSqr = Math.pow(x, 2) + Math.pow(z, 2);
        return height * Math.exp(-(distanceSqr / (2 * Math.pow(phi, 2)))) * (1 - k * Math.sqrt(distanceSqr));
    }
}
