package com.rena.dinosexpansion.common.entity.projectile;

import com.rena.dinosexpansion.core.init.EntityInit;
import com.rena.dinosexpansion.core.init.ItemInit;
import com.rena.dinosexpansion.core.init.SoundInit;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class FireArrowEntity extends CustomArrow{

    float damage = 1.0F;

    public FireArrowEntity(EntityType<CustomArrow> type, World world) {
        super(type, world);
    }

    /*public FireArrowEntity(World worldIn, LivingEntity shooter)
    {
        super(EntityInit.FIRE_ARROW.get(), shooter, worldIn);
    }*/

    /*public FireArrowEntity(World worldIn, double x, double y, double z) {
        super(EntityInit.FIRE_ARROW.get(), x, y, z, worldIn);
    }*/

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        setDamage(4.0D);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        Entity entity = result.getEntity();

        if (entity.isAlive()) {
            entity.setFire(6);
        }

        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) entity;

            this.getDamage();
            if (!this.world.isRemote && this.getPierceLevel() <= 0) {
                livingentity.setArrowCountInEntity(livingentity.getArrowCountInEntity() - 1);
            }
        }

        super.onEntityHit(result);
    }

    @Override
    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);
        //playSound(SoundInit.ARROW_HIT_FIRE.get(), 1f, 1f);
    }

    @Override
    public void tick() {
        super.tick();
    }

    private void burnGroundOnImpact() {
        if (this.inGround)
        {
            if (!this.isWet())
            {
                if (world.isAirBlock(this.getPosition()))
                    world.setBlockState(this.getPosition(), Blocks.FIRE.getDefaultState(), 11);

                //playSoundAtBlockPosition(SoundInit.ARROW_HIT_FIRE.get());

                this.remove();

                setHorizontallyAdjacentBlocksAblaze();
            }
        }
    }

    private void addFireParticlesToFlightPath() {
        if (!this.isWet() && !this.inGround)
        {
            this.world.addParticle(ParticleTypes.FLAME, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    private void extinguishInWater() {
        // TODO shouldn't this include rain?
        if (this.isInWater())
        {
            playSoundAtBlockPosition(SoundEvents.BLOCK_FIRE_EXTINGUISH);
            this.remove();
        }
    }


    private void playSoundAtBlockPosition(SoundEvent soundEvent) {
        BlockPos currentPos = this.getPosition();
        this.world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), soundEvent, SoundCategory.PLAYERS, 1f,
                1f);
    }

    private void setHorizontallyAdjacentBlocksAblaze()
    {
        if (isEmptyBlock(Direction.WEST))
            world.setBlockState(this.getPosition().west(), Blocks.FIRE.getDefaultState(), 11);
        if (isEmptyBlock(Direction.EAST))
            world.setBlockState(this.getPosition().east(), Blocks.FIRE.getDefaultState(), 11);
        if (isEmptyBlock(Direction.NORTH))
            world.setBlockState(this.getPosition().north(), Blocks.FIRE.getDefaultState(), 11);
        if (isEmptyBlock(Direction.SOUTH))
            world.setBlockState(this.getPosition().south(), Blocks.FIRE.getDefaultState(), 11);
    }

    private boolean isEmptyBlock(Direction dir) {
        return world.isAirBlock(this.getPosition().offset(dir));
    }

}
