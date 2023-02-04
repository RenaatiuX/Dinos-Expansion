package com.rena.dinosexpansion.common.item.util;

import com.rena.dinosexpansion.common.item.DinopediaItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;
import java.util.*;

public enum DinosaurDossier {

    INTRODUCTION(0),
    DIMORPHODON(1);

    public int pages;
    DinosaurDossier(int pages) {
        this.pages = pages;
    }

    //Adds the element to the list, and returns the list
    public static List<Integer> toList(int[] containedPages) {
        List<Integer> intList = new ArrayList<>();
        for (int containedPage : containedPages) {
            if (containedPage >= 0 && containedPage < DinosaurDossier.values().length) {
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
    public static List<DinosaurDossier> containedPages(List<Integer> pages) {
        Iterator<Integer> itr = pages.iterator();
        List<DinosaurDossier> list = new ArrayList<>();
        for (Integer page : pages) {
            if (page >= 0 && page < DinosaurDossier.values().length) {
                list.add(DinosaurDossier.values()[page]);
            }
        }
        return list;
    }

    public static boolean hasAllPages(ItemStack book) {
        List<DinosaurDossier> allPages = new ArrayList<>();
        Collections.addAll(allPages, DinosaurDossier.values());
        List<DinosaurDossier> pages = containedPages(DinosaurDossier.toList(book.getTag().getIntArray("Pages")));
        for (DinosaurDossier page : allPages) {
            return !pages.contains(page);
        }
        return false;
    }

    public static List<Integer> enumToInt(List<DinosaurDossier> pages) {
        Iterator<DinosaurDossier> itr = pages.iterator();
        List<Integer> list = new ArrayList<>();
        while (itr.hasNext()) {
            list.add(DinosaurDossier.values()[(itr.next()).ordinal()].ordinal());
        }
        return list;
    }

    public static DinosaurDossier getRand() {
        return DinosaurDossier.values()[new Random().nextInt(DinosaurDossier.values().length)];

    }

    public static void addRandomPage(ItemStack book) {
        if (book.getItem() instanceof DinopediaItem) {
            List<DinosaurDossier> list = DinosaurDossier.possiblePages(book);
            if (list != null && !list.isEmpty()) {
                addPage(list.get(new Random().nextInt(list.size())), book);
            }
        }
    }

    public static List<DinosaurDossier> possiblePages(ItemStack book) {
        if (book.getItem() instanceof DinopediaItem) {
            CompoundNBT tag = book.getTag();
            List<DinosaurDossier> allPages = new ArrayList<>();
            Collections.addAll(allPages, DinosaurDossier.values());
            List<DinosaurDossier> containedPages = containedPages(toList(tag.getIntArray("Pages")));
            List<DinosaurDossier> possiblePages = new ArrayList<>();
            for (DinosaurDossier page : allPages) {
                if (!containedPages.contains(page)) {
                    possiblePages.add(page);
                }
            }
            return possiblePages;
        }
        return null;
    }


    public static boolean addPage(DinosaurDossier page, ItemStack book) {
        boolean flag = false;
        if (book.getItem() instanceof DinopediaItem) {
            CompoundNBT tag = book.getTag();
            List<DinosaurDossier> enumlist = containedPages(toList(tag.getIntArray("Pages")));
            if (!enumlist.contains(page)) {
                enumlist.add(page);
                flag = true;
            }
            tag.putIntArray("Pages", fromList(enumToInt(enumlist)));
        }
        return flag;
    }


    @Nullable
    public static DinosaurDossier fromInt(int index) {
        if(index < 0){
            return null;
        }
        int length = values().length;
        return values()[index % length];
    }
}
