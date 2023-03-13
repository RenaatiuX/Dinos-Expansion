package com.rena.dinosexpansion.common.tileentity;

import com.rena.dinosexpansion.DinosExpansion;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public abstract class ContainerTileEntity extends TileEntity implements IRecipeHolder, IRecipeHelperPopulator, INamedContainerProvider, IInventory {

    protected final int slots;
    protected final LazyOptional<IItemHandlerModifiable> inventory;
    protected final Object2IntOpenHashMap<ResourceLocation> recipes = new Object2IntOpenHashMap<>();

    protected ContainerTileEntity(TileEntityType<?> typeIn, int slots) {
        super(typeIn);
        this.slots = slots;
        this.inventory = LazyOptional.of(() -> createInventory(slots));
    }

    @Override
    public boolean isEmpty() {
        IItemHandlerModifiable inv = getInventory();
        for (int i = 0; i < inv.getSlots(); i++) {
            if (!inv.getStackInSlot(i).isEmpty())
                return false;
        }
        return true;
    }

    public void forAllItems(Consumer<ItemStack> forEach){
        IItemHandlerModifiable inv = getInventory();
        for (int i = 0; i < inv.getSlots(); i++) {
            forEach.accept(inv.getStackInSlot(i));
        }
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return getInventory().getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return getInventory().extractItem(index, count, false);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return getInventory().extractItem(index,Integer.MAX_VALUE, false);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        getInventory().setStackInSlot(index, stack);
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        IItemHandlerModifiable inv = getInventory();
        for (int i = 0; i < inv.getSlots(); i++) {
            inv.setStackInSlot(i, ItemStack.EMPTY);
        }
    }


    public IItemHandlerModifiable getInventory() {
        return inventory.orElseThrow(() -> new IllegalStateException("inventory not initialized correctly"));
    }


    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("container." + DinosExpansion.MOD_ID + "." + setName());
    }

    @Override
    public int getSizeInventory() {
        return this.slots;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound = super.write(compound);
        compound = writeRecipesUsed(compound);
        return writeItems(compound);
    }

    protected void readRecipesUsed(CompoundNBT nbt){
        CompoundNBT compoundnbt = nbt.getCompound("RecipesUsed");

        for(String s : compoundnbt.keySet()) {
            this.recipes.put(new ResourceLocation(s), compoundnbt.getInt(s));
        }
    }

    protected CompoundNBT writeRecipesUsed(CompoundNBT nbt){
        CompoundNBT recipes = new CompoundNBT();
        this.recipes.forEach((recipeId, craftedAmount) -> {
            recipes.putInt(recipeId.toString(), craftedAmount);
        });
        nbt.put("RecipesUsed", recipes);
        return nbt;
    }

    protected CompoundNBT writeItems(CompoundNBT compound) {
        if (getInventory() instanceof INBTSerializable) {
            compound.put("inventory", ((INBTSerializable<INBT>) getInventory()).serializeNBT());
        }
        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        readRecipesUsed(nbt);
        readItems(nbt);
        this.markDirty();
    }

    protected void readItems(CompoundNBT nbt) {
        if (nbt.contains("inventory") && getInventory() instanceof INBTSerializable) {
            ((INBTSerializable<INBT>) getInventory()).deserializeNBT(nbt.get("inventory"));
        }
    }

    @Override
    public void fillStackedContents(RecipeItemHelper helper) {
        forAllItems(helper::accountStack);
    }

    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.getId();
            this.recipes.addTo(resourcelocation, 1);
        }

    }

    @Override
    @Nullable
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.removed && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return this.inventory.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        this.inventory.invalidate();
    }

    /**
     * @return the name of the container, will create a translation key like:
     * container.MODID.yourName
     */
    protected abstract String setName();

    /**
     * called by the constructor in order to create the inventory
     * u may put ur own inventory handler here but keep in mind that it will only be saved to the nbt when it implements {@link INBTSerializable}
     *
     * @param slots the amount of slots the inventory should have
     * @return the new inventory
     */
    protected IItemHandlerModifiable createInventory(int slots) {
        return new ItemStackHandler(slots);
    }
}
