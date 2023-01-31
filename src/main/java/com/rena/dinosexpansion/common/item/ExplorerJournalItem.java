package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.client.events.ClientEvents;
import com.rena.dinosexpansion.common.item.util.JournalPages;
import com.rena.dinosexpansion.core.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ExplorerJournalItem extends Item {
    public ExplorerJournalItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        stack.setTag(new CompoundNBT());
        stack.getTag().putIntArray("Pages", new int[]{0});
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            items.add(new ItemStack(this));
            ItemStack stack = new ItemStack(ItemInit.EXPLORER_JOURNAL.get());
            stack.setTag(new CompoundNBT());
            int[] pages = new int[JournalPages.values().length];
            for (int i = 0; i < JournalPages.values().length; i++) {
                pages[i] = i;
            }
            stack.getTag().putIntArray("Pages", pages);
            items.add(stack);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemStackIn = playerIn.getHeldItem(handIn);
        if (worldIn.isRemote) {
            ClientEvents.openExplorerJournal(itemStackIn);
        }
        return new ActionResult<>(ActionResultType.PASS, itemStackIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (stack.getTag() == null) {
            stack.setTag(new CompoundNBT());
            stack.getTag().putIntArray("Pages", new int[]{JournalPages.INTRODUCTION.ordinal()});
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.getTag() != null) {
            if (ClientEvents.shouldSeeJournalContents()) {
                tooltip.add(new TranslationTextComponent("journal.contains").mergeStyle(TextFormatting.GRAY));
                List<JournalPages> pages = JournalPages.containedPages(JournalPages.toList(stack.getTag().getIntArray("Pages")));
                for (JournalPages page : pages) {
                    tooltip.add(new StringTextComponent(TextFormatting.WHITE + "-").appendSibling(new TranslationTextComponent("journal." + JournalPages.values()[page.ordinal()].toString().toLowerCase())).mergeStyle(TextFormatting.GRAY));
                }
            } else {
                tooltip.add(new TranslationTextComponent("journal.hold_shift").mergeStyle(TextFormatting.GRAY));
            }

        }
    }
}
