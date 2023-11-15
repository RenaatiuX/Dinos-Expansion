package com.rena.dinosexpansion.common.entity;

import com.rena.dinosexpansion.api.ArmorSlotItemApi;
import com.rena.dinosexpansion.common.entity.util.DynamicItemHandler;
import com.rena.dinosexpansion.common.util.NbtUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class ChestedDinosaur extends Dinosaur {

    public static final int MAX_INVENTORY_SIZE = 27;

    private DynamicItemHandler chestInventory;

    public ChestedDinosaur(EntityType<? extends TameableEntity> type, World world, DinosaurInfo info, int level, int chestInventorySize) {
        super(type, world, info, level);
        this.chestInventory = new DynamicItemHandler(Math.min(MAX_INVENTORY_SIZE, chestInventorySize));
    }

    public ChestedDinosaur(EntityType<? extends TameableEntity> type, World world, DinosaurInfo info, int level) {
        this(type, world, info, level, 3);
    }

    public boolean hasChest() {
        return hasArmor(DinosaurArmorSlotType.CHEST);
    }

    public int getChestSize() {
        return this.chestInventory.getSlots();
    }

    public DinosaurArmorSlotType[] getArmorPieces() {
        return new DinosaurArmorSlotType[]{DinosaurArmorSlotType.CHEST, DinosaurArmorSlotType.SADDLE};
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        NbtUtils.setIfExists(nbt, "chestInventory", CompoundNBT::getCompound, this.chestInventory::deserializeNBT);
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.put("chestInventory", this.chestInventory.serializeNBT());
    }

    @Override
    protected void onContentsChanged(int slot) {
        boolean wasChested = this.hasChest();
        super.onContentsChanged(slot);
        if (wasChested && !this.hasChest()) {
            this.dropChestInventoryItems(100);
        }
        if (hasChest() || this.world.isRemote) {
            int chestSize = Math.max(0, ArmorSlotItemApi.getChestSize(this.getFromSlot(DinosaurArmorSlotType.CHEST)));
            if (chestSize != this.getChestSize()) {
                if (!this.world.isRemote)
                    this.chestInventory.setSize(chestSize, this::entityDropItem);
                else
                    this.chestInventory.setSize(chestSize, (s) -> {});
            }
        }

    }

    public DynamicItemHandler getChestInventory() {
        return chestInventory;
    }

    @Override
    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        dropChestInventoryItems(looting);
    }

    protected void dropChestInventoryItems(int looting) {
        //with 0 looting this should be around 70% drop chance for each item, with looting this can be increased up to 98% never exactly reaching 1
        double sigmoid = 1 / (1 + Math.exp(-looting - 1));
        for (int i = 0; i < this.chestInventory.getSlots(); i++) {
            if (this.rand.nextDouble() < sigmoid) {
                this.entityDropItem(this.chestInventory.getStackInSlot(i));
                chestInventory.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
    }
}
