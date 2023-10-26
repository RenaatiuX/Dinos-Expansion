package com.rena.dinosexpansion.common.container;

import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.api.ArmorSlotItemApi;
import com.rena.dinosexpansion.common.container.util.DinosaurContainer;
import com.rena.dinosexpansion.common.container.util.DynamicItemHandlerSlot;
import com.rena.dinosexpansion.common.entity.ChestedDinosaur;
import com.rena.dinosexpansion.common.entity.Dinosaur;
import com.rena.dinosexpansion.common.entity.DinosaurArmorSlotType;
import com.rena.dinosexpansion.common.item.armor.DinosaurArmorItem;
import com.rena.dinosexpansion.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class DinoInventoryContainer extends DinosaurContainer<Dinosaur> {
    public DinoInventoryContainer(int id, PlayerInventory inv, Dinosaur dinosaur) {
        super(ContainerInit.DINO_INVENTORY_CONTAINER.get(), id, inv, dinosaur);
    }

    public DinoInventoryContainer(int id, PlayerInventory inv, PacketBuffer buffer) {
        super(ContainerInit.DINO_INVENTORY_CONTAINER.get(), id, inv, buffer);
    }

    @Override
    protected void init() {
        addSlotForType();
        if (this.dinosaur instanceof ChestedDinosaur) {
            int chestSize = ChestedDinosaur.MAX_INVENTORY_SIZE;
            int rowCount = Math.floorDiv(chestSize - 1, 9) + 1;
            int currY = 85;
            int startIndex = 0;
            for (int i = 0; i < rowCount; i++) {
                int colCount = Math.min(chestSize, 9);
                chestSize -= colCount;
                startIndex = this.addHorizontalSlots(startIndex, 8, currY, colCount, 18, (dino, index, x, y) -> new DynamicItemHandlerSlot((ChestedDinosaur) dino, index, x, y));
                currY += 18;
            }
        }
        addPlayerInventory(8, 147);
    }


    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack() && slot.isEnabled()) {
            ItemStack itemstack1 = slot.getStack();
            SlotRange playerInvRange = getPlayerInvRange();
            SlotRange chestInvRange = getChestRange();
            SlotRange armorInvRange = getArmorRange();

            stack = itemstack1.copy();
            if (isArmorInventory(slot) || isChestInventory(slot)) {
                if (!this.mergeItemStack(itemstack1, playerInvRange.start, playerInvRange.end, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (isPlayerInv(slot)) {
                boolean didSomething = false;
                if (!armorInvRange.isEmpty()){
                    didSomething |= mergeItemStack(itemstack1, armorInvRange.start, armorInvRange.end, false);
                }
                 if (!didSomething && !chestInvRange.isEmpty()) {
                     didSomething |= mergeItemStack(itemstack1, chestInvRange.start, chestInvRange.end, false);
                 }
                 if (!didSomething)
                     return ItemStack.EMPTY;
                 else
                     this.detectAndSendChanges();
            }
            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return stack;

    }


    protected boolean isChestInventory(Slot slot) {
        if (slot instanceof DynamicItemHandlerSlot && this.dinosaur instanceof ChestedDinosaur) {
            return ((DynamicItemHandlerSlot) slot).getItemHandler() == ((ChestedDinosaur) dinosaur).getChestInventory();
        }
        return false;

    }

    protected boolean isArmorInventory(Slot slot) {
        if (slot instanceof DinoArmorSlot) {
            return ((DinoArmorSlot) slot).getItemHandler() == dinosaur.getInventory();
        }
        return false;
    }

    protected boolean isPlayerInv(Slot slot) {
        return slot.inventory == this.playerInventory;
    }

    protected SlotRange getArmorRange() {
        int index = 0;
        int start = -1;
        int rangeCount = 0;
        for (Slot slot : this.inventorySlots) {
            if (isArmorInventory(slot)) {
                if (start == -1)
                    start = index;
                rangeCount++;
            }
            index++;
        }
        return new SlotRange(start, start + rangeCount);
    }

    protected SlotRange getChestRange() {
        int index = 0;
        int start = -1;
        int rangeCount = 0;
        for (Slot slot : this.inventorySlots) {
            if (isChestInventory(slot) && slot.isEnabled()) {
                if (start == -1)
                    start = index;
                rangeCount++;
            }
            index++;
        }
        return new SlotRange(start, start + rangeCount);
    }

    protected SlotRange getPlayerInvRange() {
        int index = 0;
        int start = -1;
        int rangeCOunt = 0;
        for (Slot slot : this.inventorySlots) {
            if (isPlayerInv(slot)) {
                if (start == -1)
                    start = index;
                rangeCOunt++;
            }
            index++;
        }
        return new SlotRange(start, start + rangeCOunt);
    }

    protected void addSlotForType() {
        for (DinosaurArmorSlotType slot : this.dinosaur.getArmorPieces()) {
            Pair<Integer, Integer> position = getPositionForSlot(slot);
            this.addSlot(new DinoArmorSlot(this.dinosaur, slot, position.getFirst() + 1, position.getSecond() + 1));
        }


    }

    public static Pair<Integer, Integer> getPositionForSlot(DinosaurArmorSlotType slot) {
        switch (slot) {
            case SADDLE:
                return Pair.of(103, 24);
            case CHEST:
                return Pair.of(103, 42);
            case HEAD:
                return Pair.of(152, 5);
            case CHESTPLATE:
                return Pair.of(152, 23);
            case LEG:
                return Pair.of(152, 41);
            case FEET:
                return Pair.of(152, 59);
        }
        return Pair.of(-1800, -1800);
    }


    public static class DinoArmorSlot extends SlotItemHandler {

        protected final Dinosaur dino;
        protected final DinosaurArmorSlotType slotType;

        public DinoArmorSlot(Dinosaur dino, DinosaurArmorSlotType slot, int xPosition, int yPosition) {
            super(dino.getInventory(), dino.getSlotIndex(slot), xPosition, yPosition);
            this.dino = dino;
            this.slotType = slot;
        }

        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return ArmorSlotItemApi.hasSlot(this.slotType, stack);
        }
    }

    public static class DinoInventorySLot extends SlotItemHandler {

        protected ChestedDinosaur dino;

        public DinoInventorySLot(ChestedDinosaur dino, int index, int xPosition, int yPosition) {
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
    }

    public static class SlotRange {
        protected final int start, end;

        /**
         * @param start inclusive
         * @param end   exclusive
         */
        public SlotRange(int start, int end) {
            this.start = Math.min(start, end);
            this.end = Math.max(start, end);
        }

        /**
         * @return end of the range exclusive
         */
        public int getEnd() {
            return end;
        }

        /**
         * @return start of the range inclusive
         */
        public int getStart() {
            return start;
        }

        public boolean isEmpty() {
            return start == end;
        }

        public void forEach(Consumer<Integer> loop) {
            for (int i = start; i < end; i++) {
                loop.accept(i);
            }
        }
    }
}
