package com.rena.dinosexpansion.common.block.crops;

import com.rena.dinosexpansion.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public  class StakedGrowCropBlock extends Block implements IGrowable, IPlantable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    protected static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(
            Block.makeCuboidShape(5.5, 14, 5.5, 10.5, 16, 10.5),
            Block.makeCuboidShape(6.5, 0, 6.5, 9.5, 14, 9.5), IBooleanFunction.OR);

    protected static final VoxelShape CROP_SHAPE = Stream.of(
            Block.makeCuboidShape(0, 0, 1, 16, 16, 1),
            Block.makeCuboidShape(15, 0, 0, 15, 16, 16),
            Block.makeCuboidShape(1, 0, 0, 1, 16, 16),
            Block.makeCuboidShape(0, 0, 15, 16, 16, 15)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    private Supplier<Item> seedItemSupplier;
    private Supplier<Item> cropItemSupplier;
    private int maxHeight;

    public StakedGrowCropBlock(Properties properties, Supplier<Item> seedItemSupplier, Supplier<Item> cropItemSupplier, int maxHeight) {
        super(properties);
        this.seedItemSupplier = seedItemSupplier;
        this.cropItemSupplier = cropItemSupplier;
        this.maxHeight = maxHeight;
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 3;
    }

    protected int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    public BlockState withAge(int age) {
        return this.getDefaultState().with(this.getAgeProperty(), age);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        this.checkAndDropBlock(worldIn, pos, state);
        if (worldIn.getLightSubtracted(pos.up(), 0) >= 9) {
            int i = this.getAge(state);
            if (i < getMaxAge()) {
                float f = getGrowthChance(this, worldIn, pos);
                if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                    worldIn.setBlockState(pos, this.withAge(i + 1), 2);
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            } else if (worldIn.getBlockState(pos.up()).getBlock() == BlockInit.CROP_STAKE.get() && worldIn.getBlockState(pos.down(getMaxHeight() - 1)).getBlock() != this) {
                float f = getGrowthChance(this, worldIn, pos);

                if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                    worldIn.setBlockState(pos.up(), this.withAge(0), 3);
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            }
        }
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }
        worldIn.setBlockState(pos, this.withAge(i), 2);
    }

    protected int getBonemealAgeIncrease(World worldIn) {
        return MathHelper.nextInt(worldIn.rand, 2, 5);
    }

    protected static float getGrowthChance(Block block, IWorldReader world, BlockPos pos) {
        final float BASE_GROWTH = 3.5F;
        final float GROWTH_FACTOR = 0.125F;
        final float LIGHT_SUBTRACTION = 11.0F;
        final float PLANT_SUSTAIN_MULTIPLIER = 1.5F;
        float growth = GROWTH_FACTOR * (world.getLightSubtracted(pos, 0) - LIGHT_SUBTRACTION);
        BlockState soil = world.getBlockState(pos.down());

        if (soil.canSustainPlant(world, pos.down(), Direction.UP, (IPlantable) block)) {
            growth *= PLANT_SUSTAIN_MULTIPLIER;
        }

        return BASE_GROWTH + growth;
    }


    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        worldIn.setBlockState(pos, BlockInit.CROP_STAKE.get().getDefaultState(), 3);
        spawnDrops(state, worldIn, pos);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> stacks = new ArrayList<>();
        Random rand = builder.getWorld().getRandom();
        stacks.add(new ItemStack(getSeed(), rand.nextInt(2) + 1));
        if (this.getAge(state) >= getMaxAge()) {
            stacks.add(new ItemStack(getCrop()));
        }
        return stacks;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        this.checkAndDropBlock(worldIn, pos, state);
    }

    protected void checkAndDropBlock(World worldIn, BlockPos pos, BlockState state) {
        if (!this.canBlockStay(worldIn, pos, state)) {
            spawnDrops(state, worldIn, pos);
            worldIn.setBlockState(pos, BlockInit.CROP_STAKE.get().getDefaultState(), 3);
        }
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return this.getAge(state) < getMaxAge();
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.PLAINS;
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return this.getDefaultState();
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, BlockState state) {
        BlockState soil = worldIn.getBlockState(pos.down());
        return (worldIn.getLightSubtracted(pos, 0) >= 8 || worldIn.canSeeSky(pos)) && (soil.getBlock() == this || soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), Direction.UP, this));
    }

    @Override
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(this.getSeed());
    }

    protected Item getSeed() {
        return this.getSeedItemSupplier().get();
    }

    protected Item getCrop() {
        return this.getCropItemSupplier().get();
    }

    public Supplier<Item> getSeedItemSupplier() {
        return seedItemSupplier;
    }

    public void setSeedItemSupplier(Supplier<Item> seedItemSupplier) {
        this.seedItemSupplier = seedItemSupplier;
    }

    public Supplier<Item> getCropItemSupplier() {
        return cropItemSupplier;
    }

    public void setCropItemSupplier(Supplier<Item> cropItemSupplier) {
        this.cropItemSupplier = cropItemSupplier;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        int currentAge = this.getAge(state);
        int maxAge = getMaxAge();
        if (currentAge >= maxAge) {
            world.setBlockState(pos, state.with(AGE, maxAge - 1), 2);
            Direction face = hit.getFace();
            ItemStack cropStack = new ItemStack(getCrop());
            Block.spawnAsEntity(world, pos.offset(face), cropStack);
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.FAIL;
    }


}
