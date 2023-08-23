package com.rena.dinosexpansion.common.tileentity;

import com.rena.dinosexpansion.core.init.BlockEntityInit;
import com.rena.dinosexpansion.core.init.SoundInit;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class CampanileShellTileEntity extends TileEntity implements ITickableTileEntity {

    protected int soundCooldownCounter = 0;
    protected boolean activated = true;

    public CampanileShellTileEntity() {
        super(BlockEntityInit.CAMPANILE_SHELL.get());
    }

    @Override
    public void tick() {
        if (!world.isRemote){
            if (activated) {
                if (soundCooldownCounter <= 0) {
                    BlockPos position = this.getPos();
                    this.world.playSound(null, position.getX(), position.getY(), position.getZ(), SoundInit.CAMPANILE_SHELL_OCEAN.get(), SoundCategory.BLOCKS, 0.1f, 1.0F);
                    soundCooldownCounter = 4 * 20;
                } else {
                    soundCooldownCounter--;
                }
            }else if (soundCooldownCounter > 0)
                soundCooldownCounter--;

        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.activated = nbt.getBoolean("activated");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound = super.write(compound);
        compound.putBoolean("activated", this.activated);
        return compound;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isActivated() {
        return activated;
    }
}
