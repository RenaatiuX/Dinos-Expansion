package com.rena.dinosexpansion.core.init;

import com.rena.dinosexpansion.DinosExpansion;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

import java.util.function.Supplier;

public class ModItemGroups extends ItemGroup {

    public static final ItemGroup BLOCKS = new ModItemGroups("blocks", () -> BlockInit.FUTURISTIC_BLOCK_ON1.get());


    private final Supplier<IItemProvider> icon;
    private ModItemGroups(String name, Supplier<IItemProvider> icon) {
        super(DinosExpansion.MOD_ID + "." + name);
        this.icon = icon;
    }

    @Override
    public ItemStack createIcon() {
        return this.icon.get().asItem().getDefaultInstance();
    }
}
