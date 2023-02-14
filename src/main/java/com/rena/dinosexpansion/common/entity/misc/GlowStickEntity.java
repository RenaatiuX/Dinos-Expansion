package com.rena.dinosexpansion.common.entity.misc;

import com.rena.dinosexpansion.common.block.GlowStickBlock;
import com.rena.dinosexpansion.core.init.BlockInit;
import com.rena.dinosexpansion.core.init.EntityInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class GlowStickEntity extends ProjectileItemEntity {
    public GlowStickEntity(EntityType<? extends GlowStickEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public GlowStickEntity(World worldIn, LivingEntity livingEntity) {
        super(EntityInit.GLOW_STICK.get(), livingEntity, worldIn);
    }

    public GlowStickEntity(World worldIn, double x, double y, double z) {
        super(EntityInit.GLOW_STICK.get(), x, y, z, worldIn);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemInit.GLOW_STICK.get();
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote()) {
            this.remove(true);
            BlockPos pos = new BlockPos(this.getPosX(), this.getPosY(), this.getPosZ());
            if (this.world.getBlockState(pos).isAir()) {
                this.world.setBlockState(pos, BlockInit.GLOW_STICK.get().getDefaultState()
                        .with(GlowStickBlock.FLIPPED, world.getRandom().nextBoolean()));
                this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_GLASS_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
            } else {
                ItemStack stack = new ItemStack(ItemInit.GLOW_STICK.get(), 1);
                ItemEntity entity = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), stack);
                this.world.addEntity(entity);
            }
        }
    }
}
