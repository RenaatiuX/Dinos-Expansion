package com.rena.dinosexpansion.common.entity;

import com.rena.dinosexpansion.common.BitUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public abstract class ChestedDinosaur extends Dinosaur{

   public  static final int MAX_INVENTORY_SIZE = 27;

    private ItemStackHandler chestInventory;

    public ChestedDinosaur(EntityType<? extends TameableEntity> type, World world, DinosaurInfo info, int level, int chestInventorySize) {
        this(type, world, info, level);
        this.chestInventory = new ItemStackHandler(Math.min(MAX_INVENTORY_SIZE, chestInventorySize));
    }

    public ChestedDinosaur(EntityType<? extends TameableEntity> type, World world, DinosaurInfo info, int level) {
        super(type, world, info, level);
        this.inventory = new ItemStackHandler(3){
            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                ChestedDinosaur.this.onContentsChanged(slot);
            }
        };
    }

    public boolean hasChest(){
        return BitUtils.getBit(5, this.dataManager.get(BOOLS)) > 0;
    }

    public int getChestSize(){
        return this.chestInventory.getSlots();
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        this.setChested(nbt.getBoolean("chested"));
        this.chestInventory.deserializeNBT(nbt.getCompound("chestInventory"));
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

    @Override
    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        //with 0 looting this should be around 70% drop chance for each item, with looting this can be increased up to 98% never exactly reaching 1
        double sigmoid = 1 / (1 + Math.exp(-looting - 1));
        for (int i = 0; i < this.chestInventory.getSlots(); i++) {
            if (this.rand.nextDouble() < sigmoid){
                this.entityDropItem(this.chestInventory.getStackInSlot(i));
            }
        }
    }
}
