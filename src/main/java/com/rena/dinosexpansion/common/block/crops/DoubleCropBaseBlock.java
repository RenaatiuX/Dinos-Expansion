package com.rena.dinosexpansion.common.block.crops;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.Constants;

import java.util.Random;
import java.util.function.Supplier;

import static com.rena.dinosexpansion.client.events.ClientForgeEvents.rand;

public class DoubleCropBaseBlock extends CropBaseBlock {

    public static final BooleanProperty UPPER = BooleanProperty.create("upper");
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };
    public DoubleCropBaseBlock(Properties builder, Supplier<Item> seedItemSupplier) {
        super(builder, seedItemSupplier);
        setDefaultState(stateContainer.getBaseState().with(getAgeProperty(), 0).with(UPPER, false));
    }

    public BooleanProperty getUpperProperty() {
        return UPPER;
    }

    public int getGrowUpperAge() {
        return 4;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE_BY_AGE[state.get(this.getAgeProperty())];
    }

    @Override
    public BlockState withAge(int age) {
        return super.withAge(age);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isAreaLoaded(pos, 1))
            return;

        if (worldIn.getLightSubtracted(pos, 0) >= 9) {
            int age = getAge(state);
            if (age < getMaxAge()) {
                float f = getGrowthChance(this, worldIn, pos);
                if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                    if (age == getMaxAge() - 1) {
                        if (worldIn.getBlockState(pos.up()) == Blocks.AIR.getDefaultState() && worldIn.getBlockState(pos.down()).getBlock() instanceof FarmlandBlock) {
                            worldIn.setBlockState(pos.up(), this.withAge(age + 1).with(this.getUpperProperty(), state.get(this.getUpperProperty())), Constants.BlockFlags.BLOCK_UPDATE);
                            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                        }
                    }
                }

            if(state.get(this.getUpperProperty()))
                    return;
                if (age >= this.getGrowUpperAge()) {
                    if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0)) {
                        if (this.getDefaultState().isValidPosition(worldIn, pos.up()) && worldIn.isAirBlock(pos.up())) {
                            worldIn.setBlockState(pos.up(), this.getDefaultState().with(this.getUpperProperty(), true));
                            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos downPos = pos.down();
        if(worldIn.getBlockState(downPos).matchesBlock(this))
            return  !worldIn.getBlockState(downPos).get(this.getUpperProperty())
                    && (worldIn.getLightSubtracted(pos, 0) >= 8 || worldIn.canSeeSky(pos))
                    && this.getAge(worldIn.getBlockState(downPos))>= this.getGrowUpperAge();
        return super.isValidPosition(state, worldIn, pos);
    }


    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, UPPER);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && isMaxAge(state)) {
            entityIn.setMotionMultiplier(state, new Vector3d(0.8D, 0.75D, 0.8D));
        }
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        BlockState upperState = worldIn.getBlockState(pos.up());
        if (upperState.matchesBlock(this)) {
            return !(this.isMaxAge(upperState));
        }
        if (state.get(this.getUpperProperty())) {
            return !(this.isMaxAge(state));
        }
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        int ageGrowth = Math.min(this.getAge(state) + this.getBonemealAgeIncrease(worldIn), 15);
        if (ageGrowth <= this.getMaxAge()) {
            worldIn.setBlockState(pos, state.with(AGE, ageGrowth));
        } else {
            worldIn.setBlockState(pos, state.with(AGE, this.getMaxAge()));
            if (state.get(this.getUpperProperty())) {
                return;
            }
            BlockState top = worldIn.getBlockState(pos.up());
            if (top.matchesBlock(this)) {
                IGrowable growable = (IGrowable) worldIn.getBlockState(pos.up()).getBlock();
                if (growable.canGrow(worldIn, pos.up(), top, false)) {
                    growable.grow(worldIn, worldIn.rand, pos.up(), top);
                }
            } else {
                int remainingGrowth = ageGrowth - this.getMaxAge() - 1;
                if (this.getDefaultState().isValidPosition(worldIn, pos.up()) && worldIn.isAirBlock(pos.up())) {
                    worldIn.setBlockState(pos.up(), this.getDefaultState()
                            .with(this.getUpperProperty(), true)
                            .with(this.getAgeProperty(), remainingGrowth), 3);
                }
            }
        }
    }
}
