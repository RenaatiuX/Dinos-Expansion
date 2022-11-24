package com.rena.dinosexpansion.common.container.util;

import com.rena.dinosexpansion.common.entity.Dinosaur;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

public abstract class DinosaurContainer<T extends Dinosaur> extends UtilContainer{
	
	protected final T dinosaur;

	public DinosaurContainer(ContainerType<?> type, int id,PlayerInventory inv, T dinosaur) {
		super(type, id, inv);
		this.dinosaur = dinosaur;
		init();
	}
	
	
	/**
	 * 
	 * @param buffer - don't forget to write the EntityId on the buffer before using this constructor
	 */
	public DinosaurContainer(ContainerType<?> type, int id,PlayerInventory inv, PacketBuffer buffer) {
		this(type, id ,inv, getClientDinosaur(inv, buffer));
	}
	
	
	
	public T getDinosaur() {
		return dinosaur;
	}
	
	protected int addHorizontalSlots(IInventory handler, int Index, int x, int y, int amount,
			int distanceBetweenSlots, IDinosaurInventorySlotProvider provider) {
		for (int i = 0; i < amount; i++) {
			addSlot(provider.createSlot(getDinosaur(), Index, x, y));
			Index++;
			x += distanceBetweenSlots;
		}
		return Index;
	}
	
	protected int addSlotField(IInventory handler, int StartIndex, int x, int y, int horizontalAmount,
			int horizontalDistance, int verticalAmount, int VerticalDistance, IDinosaurInventorySlotProvider provider) {
		for (int i = 0; i < verticalAmount; i++) {
			StartIndex = addHorizontalSlots(handler, StartIndex, x, y, horizontalAmount, horizontalDistance, provider);
			y += VerticalDistance;
		}
		return StartIndex;
	}


	protected abstract void init();
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}
	//save this for later
	/*
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			if (index < dinosaur.getDinosaurInventory().getSizeInventory()
					&& !this.mergeItemStack(stack1, dinosaur.getDinosaurInventory().getSizeInventory(), this.inventorySlots.size(), true))
				return ItemStack.EMPTY;
			if (!this.mergeItemStack(stack1, 0, dinosaur.getDinosaurInventory().getSizeInventory(), false))
				return ItemStack.EMPTY;
			if (stack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}
		return stack;
	}*/
	
	
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	protected static <X extends Dinosaur> X getClientDinosaur(final PlayerInventory inventory,
			final PacketBuffer buffer) {
		Objects.requireNonNull(inventory, "the inventory must not be null");
		Objects.requireNonNull(buffer, "the buffer must not be null");
		final Entity entity = inventory.player.world.getEntityByID(buffer.readVarInt());
		return (X) entity;
	}
	
	
	protected static class SaddleSlot extends Slot{

		private final Item saddle;
		
		public SaddleSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, Item saddle) {
			super(inventoryIn, index, xPosition, yPosition);
			this.saddle = saddle;
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return stack.getItem() == saddle;
		}
		
		@Override
		public int getItemStackLimit(ItemStack stack) {
			return 1;
		}
		
	}
	
	protected static class ChestSlot extends Slot{
		
		public ChestSlot(IInventory inv, int index, int xPosition, int yPosition) {
			super(inv, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return stack.getItem() == Blocks.CHEST.asItem() || stack.getItem() == Blocks.TRAPPED_CHEST.asItem();
		}
		
		@Override
		public int getItemStackLimit(ItemStack stack) {
			return 1;
		}
	}
	
	public static class BaseDinosaurInventorySlot extends Slot{
		
		public BaseDinosaurInventorySlot(IInventory inv, int index, int xPosition, int yPosition) {
			super(inv, index, xPosition, yPosition);
		}
	}

}
