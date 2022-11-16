package com.rena.dinosexpansion.common.entity.aquatic;

import com.rena.dinosexpansion.common.BitUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public abstract class ChestedAquaticDinosaur extends DinosaurAquatic{

    private ItemStackHandler chestInventory = new ItemStackHandler(27);

    public ChestedAquaticDinosaur(EntityType<? extends ChestedAquaticDinosaur> type, World world, DinosaurInfo info, int level, int chestInventorySize) {
        super(type, world, info, level);
        this.chestInventory = new ItemStackHandler(chestInventorySize);
    }

    public ChestedAquaticDinosaur(EntityType<? extends ChestedAquaticDinosaur> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
        this.inventory = new ItemStackHandler(3){
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                ChestedAquaticDinosaur.this.onContentsChanged(slot);
            }
        };
    }

    public boolean hasChest(){
        return BitUtils.getBit(5, this.dataManager.get(BOOLS)) > 0;
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putBoolean("chested", hasChest());
        nbt.put("chestInventory", this.chestInventory.serializeNBT());
    }

    protected void setChested(boolean chested){
        if (chested == hasChest())
            return;
        this.dataManager.set(BOOLS, BitUtils.setBit(5, this.dataManager.get(BOOLS), chested));
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        if(slot == 2){
            boolean chested = !inventory.getStackInSlot(slot).isEmpty();
            if (hasChest() != chested)
                setChested(chested);
        }
    }

    public ItemStackHandler getChestInventory() {
        return chestInventory;
    }

}
