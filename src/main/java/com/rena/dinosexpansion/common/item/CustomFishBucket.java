package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.common.entity.aquatic.fish.PrehistoricFish;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CustomFishBucket extends BucketItem {
    public CustomFishBucket(Supplier<EntityType<?>> fishTypeIn, Supplier<Fluid> fluid, Item.Properties properties) {
        super(fluid, properties.maxStackSize(1));
        this.fishTypeSupplier = fishTypeIn;
    }

    @Override
    public void onLiquidPlaced(World worldIn, ItemStack p_203792_2_, BlockPos pos) {
        if (worldIn instanceof ServerWorld) {
            this.placeFish((ServerWorld)worldIn, p_203792_2_, pos);
        }
    }

    @Override
    protected void playEmptySound(@Nullable PlayerEntity player, IWorld worldIn, BlockPos pos) {
        worldIn.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }

    private void placeFish(ServerWorld worldIn, ItemStack stack, BlockPos pos) {
        Entity entity = this.fishTypeSupplier.get().spawn(worldIn, stack, null, pos, SpawnReason.BUCKET, true, false);
        if (entity != null) {
            ((PrehistoricFish)entity).setFromBucket(true);
        }
    }

    private final java.util.function.Supplier<? extends EntityType<?>> fishTypeSupplier;
    protected EntityType<?> getFishType() {
        return fishTypeSupplier.get();
    }
}
