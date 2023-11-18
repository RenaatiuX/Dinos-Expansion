package com.rena.dinosexpansion.common.tileentity;

import com.rena.dinosexpansion.core.init.BlockEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.server.ServerWorld;

public class UnknownLockTileEntity extends TileEntity implements ITickableTileEntity {

    protected int tickCounter = 0;
    protected int shouldDestroy = -1;

    public UnknownLockTileEntity() {
        super(BlockEntityInit.UNKNOWN_TE.get());
    }

    @Override
    public void tick() {
        if (!this.world.isRemote) {
            if (shouldDestroy > 0) {
                if (tickCounter >= shouldDestroy) {
                    this.world.destroyBlock(this.getPos(), false);
                    if (this.world instanceof ServerWorld){
                        Vector3d center = Vector3d.copyCentered(this.getPos());
                        for (int i = 0; i < 10; i++) {
                            float dx = this.world.rand.nextFloat();
                            float dy = this.world.rand.nextFloat();
                            float dz = this.world.rand.nextFloat();
                            ((ServerWorld)this.world).spawnParticle(ParticleTypes.ASH, center.x, center.y, center.z, 3, dx, dy, dz, .4f);
                        }

                    }
                    tickCounter = 0;
                    shouldDestroy = -1;
                } else
                    tickCounter++;
            }
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt.putInt("ticks", this.tickCounter);
        nbt.putInt("delay", this.shouldDestroy);
        return super.write(nbt);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.tickCounter = nbt.getInt("ticks");
        this.shouldDestroy = nbt.getInt("delay");
    }

    public void setShouldDestroy(int shouldDestroy) {
        this.shouldDestroy = shouldDestroy;
    }
}
