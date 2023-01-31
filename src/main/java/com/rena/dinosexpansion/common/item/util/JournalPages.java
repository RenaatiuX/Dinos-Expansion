package com.rena.dinosexpansion.common.item.util;

import com.rena.dinosexpansion.common.item.ExplorerJournalItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;
import java.util.*;

public enum JournalPages {

    INTRODUCTION(0),
    //Ren
    NOTE_1(0),
    NOTE_2(0),
    NOTE_3(0),
    NOTE_4(0),
    NOTE_5(0),
    NOTE_6(0),
    NOTE_7(0),
    NOTE_8(0),
    NOTE_9(0),
    NOTE_10(0),
    NOTE_11(0),
    NOTE_12(0),
    NOTE_13(0),
    NOTE_14(0),
    NOTE_15(0),
    NOTE_16(0),
    NOTE_17(0),
    NOTE_18(0),
    NOTE_19(0),
    NOTE_20(0),
    NOTE_21(0),
    NOTE_22(0),
    NOTE_23(0),
    NOTE_24(0),
    NOTE_25(0),
    NOTE_26(0),
    NOTE_27(0),
    NOTE_28(0),
    NOTE_29(0),
    NOTE_30(0);
    public int pages;

    JournalPages(int pages) {
        this.pages = pages;
    }

    //Adds the element to the list, and returns the list
    public static List<Integer> toList(int[] containedPages) {
        List<Integer> intList = new ArrayList<>();
        for (int containedPage : containedPages) {
            if (containedPage >= 0 && containedPage < JournalPages.values().length) {
                intList.add(containedPage);
            }
        }
        return intList;
    }

    //copies each element and returns the array of elements
    public static int[] fromList(List<Integer> containedPages) {
        int[] pages = new int[containedPages.size()];
        for (int i = 0; i < pages.length; i++)
            pages[i] = containedPages.get(i);
        return pages;
    }

    @SuppressWarnings("unused")
    public static List<JournalPages> containedPages(List<Integer> pages) {
        Iterator<Integer> itr = pages.iterator();
        List<JournalPages> list = new ArrayList<>();
        for (Integer page : pages) {
            if (page >= 0 && page < JournalPages.values().length) {
                list.add(JournalPages.values()[page]);
            }
        }
        return list;
    }

    public static boolean hasAllPages(ItemStack book) {
        List<JournalPages> allPages = new ArrayList<>();
        Collections.addAll(allPages, JournalPages.values());
        List<JournalPages> pages = containedPages(JournalPages.toList(book.getTag().getIntArray("Pages")));
        for (JournalPages page : allPages) {
            return !pages.contains(page);
        }
        return false;
    }

    public static List<Integer> enumToInt(List<JournalPages> pages) {
        Iterator<JournalPages> itr = pages.iterator();
        List<Integer> list = new ArrayList<>();
        while (itr.hasNext()) {
            list.add(JournalPages.values()[(itr.next()).ordinal()].ordinal());
        }
        return list;
    }

    public static JournalPages getRand() {
        return JournalPages.values()[new Random().nextInt(JournalPages.values().length)];

    }

    public static void addRandomPage(ItemStack book) {
        if (book.getItem() instanceof ExplorerJournalItem) {
            List<JournalPages> list = JournalPages.possiblePages(book);
            if (list != null && !list.isEmpty()) {
                addPage(list.get(new Random().nextInt(list.size())), book);
            }
        }
    }

    public static List<JournalPages> possiblePages(ItemStack book) {
        if (book.getItem() instanceof ExplorerJournalItem) {
            CompoundNBT tag = book.getTag();
            List<JournalPages> allPages = new ArrayList<>();
            Collections.addAll(allPages, JournalPages.values());
            List<JournalPages> containedPages = containedPages(toList(tag.getIntArray("Pages")));
            List<JournalPages> possiblePages = new ArrayList<>();
            for (JournalPages page : allPages) {
                if (!containedPages.contains(page)) {
                    possiblePages.add(page);
                }
            }
            return possiblePages;
        }
        return null;
    }


    public static boolean addPage(JournalPages page, ItemStack book) {
        boolean flag = false;
        if (book.getItem() instanceof ExplorerJournalItem) {
            CompoundNBT tag = book.getTag();
            List<JournalPages> enumlist = containedPages(toList(tag.getIntArray("Pages")));
            if (!enumlist.contains(page)) {
                enumlist.add(page);
                flag = true;
            }
            tag.putIntArray("Pages", fromList(enumToInt(enumlist)));
        }
        return flag;
    }


    @Nullable
    public static JournalPages fromInt(int index) {
        if(index < 0){
            return null;
        }
        int length = values().length;
        return values()[index % length];
    }
}
