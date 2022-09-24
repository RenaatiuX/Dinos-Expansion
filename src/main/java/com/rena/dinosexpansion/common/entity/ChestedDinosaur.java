package com.rena.dinosexpansion.common.entity;

import com.rena.dinosexpansion.common.BitUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public abstract class ChestedDinosaur extends Dinosaur{

    public static final DataParameter<Boolean> HAS_CHEST = EntityDataManager.createKey(ChestedDinosaur.class, DataSerializers.BOOLEAN);

    public ChestedDinosaur(EntityType<? extends MonsterEntity> type, World world, int maxNarcotic, int maxHunger, int level) {
        super(type, world, maxNarcotic, maxHunger, level);
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
                this.dataManager.set(HAS_CHEST, chested);
        }
    }
}
