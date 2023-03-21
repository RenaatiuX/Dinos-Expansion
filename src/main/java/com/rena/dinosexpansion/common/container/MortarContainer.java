package com.rena.dinosexpansion.common.container;

import com.rena.dinosexpansion.common.container.util.BaseTileEntityContainer;
import com.rena.dinosexpansion.common.container.util.BetterReferenceHolder;
import com.rena.dinosexpansion.common.tileentity.MortarTileEntity;
import com.rena.dinosexpansion.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;

public class MortarContainer extends BaseTileEntityContainer<MortarTileEntity> {
    public MortarContainer(int id, PlayerInventory inv, MortarTileEntity tileEntity) {
        super(ContainerInit.MORTAR_CONTAINER.get(), id, inv, tileEntity);
    }

    public MortarContainer(int id, PlayerInventory inv, PacketBuffer buffer) {
        super(ContainerInit.MORTAR_CONTAINER.get(), id, inv, buffer);
    }

    @Override
    public void init() {
        //top
        addSlot(new Slot(this.tileEntity, 0, 56, 17));
        //bottom
        addSlot(new Slot(this.tileEntity, 1, 56, 53));
        //result
        addSlot(new MortarResultSlot(this.playerInventory.player, this.tileEntity, 2, 116, 35));


        addPlayerInventory(8, 84);

        trackCounterPercentage();
    }

    private void trackCounterPercentage(){
        trackInt(new BetterReferenceHolder() {
            @Override
            public int get() {
                return tileEntity.getCounterPercentage();
            }

            @Override
            public void set(int value) {
                tileEntity.setCounterPercentage(value);
            }
        });
    }

    public boolean isPowered(){
        return tileEntity.getWorld().getBlockState(tileEntity.getPos()).get(BlockStateProperties.POWERED);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();
            System.out.println(index);
            if (index == 2) {
                if (!this.mergeItemStack(stack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack1, stack);
            }
            if (index < tileEntity.getSizeInventory() && !this.mergeItemStack(stack1, tileEntity.getSizeInventory(), this.inventorySlots.size(), true))
                return ItemStack.EMPTY;
            if (!this.mergeItemStack(stack1, 0, tileEntity.getSizeInventory() - 1, false))
                return ItemStack.EMPTY;
            slot.onSlotChange(stack1, stack);
            if (stack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            slot.onTake(playerIn, stack1);
        }
        return stack;
    }

    public static class MortarResultSlot extends Slot{

        protected final PlayerEntity player;
        protected int amountRemoved;

        public MortarResultSlot(PlayerEntity player, IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
            this.player = player;
        }
        public boolean isItemValid(ItemStack stack) {
            return false;
        }

        /**
         * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new stack.
         */
        public ItemStack decrStackSize(int amount) {
            if (this.getHasStack()) {
                this.amountRemoved += Math.min(amount, this.getStack().getCount());
            }

            return super.decrStackSize(amount);
        }

        public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
            this.onCrafting(stack);
            super.onTake(thePlayer, stack);
            return stack;
        }

        /**
         * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
         * internal count then calls onCrafting(item).
         */
        protected void onCrafting(ItemStack stack, int amount) {
            this.amountRemoved += amount;
            this.onCrafting(stack);
        }

        /**
         * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
         */
        protected void onCrafting(ItemStack stack) {
            stack.onCrafting(this.player.world, this.player, this.amountRemoved);
            if (!this.player.world.isRemote && this.inventory instanceof MortarTileEntity) {
                ((MortarTileEntity)this.inventory).unlockRecipes(this.player);
            }

            this.amountRemoved = 0;
        }
    }
}
