package com.rena.dinosexpansion.common.entity.projectile;

import com.rena.dinosexpansion.core.init.EnchantmentInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class TinyRockEntity extends AbstractArrowEntity{

    public static final DataParameter<ItemStack> ROCK_STACK = EntityDataManager.createKey(TinyRockEntity.class, DataSerializers.ITEMSTACK);

    public TinyRockEntity(EntityType<TinyRockEntity> type, World world) {
        super(type, world);
    }

    public TinyRockEntity(EntityType<TinyRockEntity> type, World worldIn, double x, double y, double z) {
        this(type, worldIn);
        this.setPosition(x, y, z);
        setDamage(1.5D);
    }

    public TinyRockEntity(EntityType<TinyRockEntity> type, World world, LivingEntity shooter, ItemStack rock) {
        this(type, world, shooter.getPosX(),shooter.getPosY() + (double)shooter.getEyeHeight() - (double)0.1F, shooter.getPosZ());
        this.setShooter(shooter);
        ItemStack countRock = rock.copy();
        countRock.setCount(1);
        if (shooter instanceof PlayerEntity) {
            this.pickupStatus = AbstractArrowEntity.PickupStatus.ALLOWED;
        }
        this.dataManager.set(ROCK_STACK, rock);
    }

    @Override
    protected ItemStack getArrowStack() {
        return getRock().isEmpty() ? new ItemStack(ItemInit.TINY_ROCK.get()) : getRock();
    }

    @Override
    protected float getSpeedFactor() {
        if (EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.AQUATIC_ENCHANT.get(), getRock()) <= 0)
            return super.getSpeedFactor();
        return 1.0f;
    }

    @Override
    protected float getWaterDrag() {
        return EnchantmentHelper.getEnchantmentLevel(EnchantmentInit.AQUATIC_ENCHANT.get(), getRock()) <= 0 ? super.getWaterDrag() : 1.0f;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ROCK_STACK, ItemStack.EMPTY);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult raytraceResultIn) {
        if (raytraceResultIn.getType() == RayTraceResult.Type.ENTITY) {
            Entity hit = raytraceResultIn.getEntity();
            Entity shooter = getShooter();
            if (hit instanceof LivingEntity) {
                if (world.isRemote || (shooter == hit))
                    return;
            }
        }
        super.onEntityHit(raytraceResultIn);
    }

    @Override
    public void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.BLOCK && world.getBlockState(((BlockRayTraceResult) result).getPos()).getBlock() == Blocks.GLASS) {
            world.destroyBlock(((BlockRayTraceResult) result).getPos(), false);
        }
    }

    public ItemStack getRock(){
        return this.dataManager.get(ROCK_STACK);
    }
}
