package com.rena.dinosexpansion.common.container.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;

import java.util.Objects;

public abstract class BaseTileEntityContainer<T extends TileEntity & IInventory> extends UtilContainer{

	protected final T tileEntity;
	protected final IWorldPosCallable canInteractWithCallable;

	/**
	 * normal constructor will be called by client and server, server will call it directly and the cleint via the other constructor
	 */
	public BaseTileEntityContainer(ContainerType<?> type, int id, PlayerInventory inv, T tileEntity) {
		super(type, id, inv);
		this.tileEntity = tileEntity;
		this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
		init();
	}

	/**
	 * @param type the type thiscontainer is associated to
	 * @param id the window id normally get provided from open {@link INamedContainerProvider#createMenu(int, PlayerInventory, PlayerEntity)}
	 * @param inv the player inventory which should be displayed in the gui
	 * @param buffer the buffer where the client values will be synced the pos of the TileEntity has be written here, can be done in {@link net.minecraftforge.fml.network.NetworkHooks#openGui(ServerPlayerEntity, INamedContainerProvider, BlockPos)}
	 */
	public BaseTileEntityContainer(ContainerType<?> type, int id, PlayerInventory inv, PacketBuffer buffer) {
		this(type, id, inv, getClientTileEntity(inv, buffer));
	}

	/**
	 * this will be called at the end of every constructor to do the slots
	 */
	public abstract void init();
	
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	protected static <X extends TileEntity> X getClientTileEntity(final PlayerInventory inventory,
			final PacketBuffer buffer) {
		Objects.requireNonNull(inventory, "the inventory must not be null");
		Objects.requireNonNull(buffer, "the buffer must not be null");
		final TileEntity tileEntity = inventory.player.world.getTileEntity(buffer.readBlockPos());
		return (X) tileEntity;
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(canInteractWithCallable, playerIn, getTileEntity().getBlockState().getBlock());
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			System.out.println(index);
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

	public T getTileEntity() {
		return tileEntity;
	}

	public static class FuelSlot extends Slot{

		public FuelSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}
		
		@Override
		public boolean isItemValid(ItemStack stack) {
			return ForgeHooks.getBurnTime(stack, null) > 0;
		}
		
	}

}
