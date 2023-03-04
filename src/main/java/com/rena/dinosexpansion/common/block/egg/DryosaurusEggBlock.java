package com.rena.dinosexpansion.common.block.egg;

import com.rena.dinosexpansion.common.entity.terrestrial.Dryosaurus;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.rena.dinosexpansion.core.init.EntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent;

public class DryosaurusEggBlock extends Block {
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH_0_2;
    public static final IntegerProperty EGGS = BlockStateProperties.EGGS_1_4;
    public DryosaurusEggBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HATCH, 0).with(EGGS, 1));
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        this.tryTrample(worldIn, pos, entityIn, 100);
        super.onEntityWalk(worldIn, pos, entityIn);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (!(entityIn instanceof ZombieEntity)) {
            this.tryTrample(worldIn, pos, entityIn, 3);
        }
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
    }

    private void tryTrample(World worldIn, BlockPos pos, Entity trampler, int chances) {
        if (this.canTrample(worldIn, trampler)) {
            if (!worldIn.isRemote && worldIn.rand.nextInt(chances) == 0) {
                BlockState blockstate = worldIn.getBlockState(pos);
                /*if (blockstate.matchesBlock(BlockInit.DRYOSAURUS_EGG)) {
                    this.removeOneEgg(worldIn, pos, blockstate);
                }*/
            }
        }
    }

    private void removeOneEgg(World worldIn, BlockPos pos, BlockState state) {
        worldIn.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + worldIn.rand.nextFloat() * 0.2F);
        int i = state.get(EGGS);
        if (i <= 1) {
            worldIn.destroyBlock(pos, false);
        } else {
            worldIn.setBlockState(pos, state.with(EGGS, i - 1), 2);
            worldIn.playEvent(2001, pos, Block.getStateId(state));
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (this.canGrow(worldIn) && hasProperHabitat(worldIn, pos)) {
            int i = state.get(HATCH);
            if (i < 2) {
                worldIn.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                worldIn.setBlockState(pos, state.with(HATCH, i + 1), 2);
            } else {
                worldIn.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                worldIn.removeBlock(pos, false);

                for(int j = 0; j < state.get(EGGS); ++j) {
                    worldIn.playEvent(2001, pos, Block.getStateId(state));
                    Dryosaurus dryosaurus = EntityInit.DRYOSAURUS.get().create(worldIn);
                    dryosaurus.setGrowingAge(-24000);
                    dryosaurus.setLocationAndAngles((double)pos.getX() + 0.3D + (double)j * 0.2D, pos.getY(), (double)pos.getZ() + 0.3D, 0.0F, 0.0F);
                    worldIn.addEntity(dryosaurus);
                }
            }
        }
    }

    public static boolean hasProperHabitat(IBlockReader reader, BlockPos blockReader) {
        return isProperHabitat(reader, blockReader.down());
    }

    public static boolean isProperHabitat(IBlockReader reader, BlockPos pos) {
        return reader.getBlockState(pos).matchesBlock(Blocks.DIRT);
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (hasProperHabitat(worldIn, pos) && !worldIn.isRemote) {
            worldIn.playEvent(2005, pos, 0);
        }
    }

    private boolean canGrow(World worldIn) {
        float f = worldIn.func_242415_f(1.0F);
        if ((double)f < 0.69D && (double)f > 0.65D) {
            return true;
        } else {
            return worldIn.rand.nextInt(500) == 0;
        }
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        this.removeOneEgg(worldIn, pos, state);
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return useContext.getItem().getItem() == this.asItem() && state.get(EGGS) < 4 || super.isReplaceable(state, useContext);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());
        return blockstate.matchesBlock(this) ? blockstate.with(EGGS, Math.min(4, blockstate.get(EGGS) + 1)) : super.getStateForPlacement(context);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return super.getShape(state, worldIn, pos, context);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HATCH, EGGS);
    }

    private boolean canTrample(World worldIn, Entity trampler) {
        if (!(trampler instanceof Dryosaurus) && !(trampler instanceof BatEntity)) {
            if (!(trampler instanceof LivingEntity)) {
                return false;
            } else {
                return trampler instanceof PlayerEntity || getMobGriefingEvent(worldIn, trampler);
            }
        } else {
            return false;
        }
    }
}
