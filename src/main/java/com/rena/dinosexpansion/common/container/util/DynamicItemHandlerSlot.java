package com.rena.dinosexpansion.common.container.util;

import com.rena.dinosexpansion.common.entity.ChestedDinosaur;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class DynamicItemHandlerSlot extends SlotItemHandler {

    protected ChestedDinosaur dino;

    public DynamicItemHandlerSlot(ChestedDinosaur dino, int index, int xPosition, int yPosition) {
        super(dino.getChestInventory(), index, xPosition, yPosition);
        this.dino = dino;
    }

    @Override
    public boolean isEnabled() {
        return this.dino.hasChest() && this.getSlotIndex() < this.dino.getChestSize();
    }

    @Nonnull
    @Override
    public ItemStack getStack() {
        if (this.getSlotIndex() >= this.dino.getChestSize())
            return ItemStack.EMPTY;
        return super.getStack();
    }

    @Override
    public void putStack(@Nonnull ItemStack stack) {
        if (this.getSlotIndex() < this.dino.getChestSize())
            super.putStack(stack);
    }

    @Override
    public int getSlotStackLimit() {
        if (this.getSlotIndex() < this.dino.getChestSize())
            return super.getSlotStackLimit();
        return 1;
    }

    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack) {
        if (this.getSlotIndex() < this.dino.getChestSize()) {
            return super.getItemStackLimit(stack);
        }
        return 0;
    }

    @Override
    public boolean canTakeStack(PlayerEntity playerIn) {
        if (this.getSlotIndex() < this.dino.getChestSize()) {
            return super.canTakeStack(playerIn);
        }
        return false;
    }

    @Nonnull
    @Override
    public ItemStack decrStackSize(int amount) {
        if (this.getSlotIndex() < this.dino.getChestSize()) {
            return super.decrStackSize(amount);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if (this.getSlotIndex() < this.dino.getChestSize()) {
            return super.isItemValid(stack);
        }
        return false;
    }
}
