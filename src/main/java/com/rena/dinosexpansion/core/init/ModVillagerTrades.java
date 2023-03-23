package com.rena.dinosexpansion.core.init;

import com.google.common.collect.ImmutableMap;
import com.rena.dinosexpansion.common.trades.DefinedEnchantedBookTradSerializer;
import com.rena.dinosexpansion.common.trades.DefinedEnchantedBookTrade;
import com.rena.dinosexpansion.common.trades.EmeraldsForItemsTrade;
import com.rena.dinosexpansion.common.trades.TradeSerializer;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraftforge.common.BasicTrade;
import org.lwjgl.system.CallbackI;

import java.util.*;

public class ModVillagerTrades {
    /**
     * if you want to add custom trades here, do it in the {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent}
     */
    public static Int2ObjectMap<VillagerTrades.ITrade[]> HERMIT_TRADES = new Int2ObjectOpenHashMap<>();
    public static Int2ObjectMap<VillagerTrades.ITrade[]> WHITE_BOSS_TRADES = new Int2ObjectOpenHashMap<>();
    public static Int2ObjectMap<VillagerTrades.ITrade[]> WHITE_NORMAL_TRADES = new Int2ObjectOpenHashMap<>();

    /**
     * if you want to add custom trades here, do it <b>before</b> the {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent}
     */
    public static final Set<TradeSerializer<?>> SERIALIZER = Util.make(new HashSet<>(), set -> {
        set.add(new DefinedEnchantedBookTradSerializer());
    });


    public static Int2ObjectMap<VillagerTrades.ITrade[]> gatAsIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> p_221238_0_) {
        return new Int2ObjectOpenHashMap<>(p_221238_0_);
    }

    public static void registerTrades() {
        WHITE_BOSS_TRADES.put(1, new VillagerTrades.ITrade[]{new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.STONE_SPEAR.get(), 10, 2, 2), new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.STONE_CHAKRAM.get(), 10, 2, 2)});
        WHITE_BOSS_TRADES.put(2, new VillagerTrades.ITrade[]{new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.IRON_SPEAR.get(), 15, 2, 5), new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.IRON_CHAKRAM.get(), 15, 2, 5)});
        WHITE_BOSS_TRADES.put(3, new VillagerTrades.ITrade[]{new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.DIAMOND_CHAKRAM.get(), 30, 1, 20), new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.DIAMOND_CHAKRAM.get(), 30, 1, 20), new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.GOLD_SPEAR.get(), 20, 1, 17), new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.GOLD_CHAKRAM.get(), 20, 1, 17)});
        WHITE_BOSS_TRADES.put(4, new VillagerTrades.ITrade[]{new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.EMERALD_SPEAR.get(), 45, 1, 25), new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.EMERALD_CHAKRAM.get(), 45, 1, 25)});
        WHITE_BOSS_TRADES.put(5, new VillagerTrades.ITrade[]{new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.NETHERITE_SPEAR.get(), 55, 2, 30), new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.NETHERITE_CHAKRAM.get(), 55, 1, 30), new DefinedEnchantedBookTrade(new Enchantment[]{Enchantments.SHARPNESS, EnchantmentInit.AQUATIC_ENCHANT.get(), Enchantments.LOOTING, Enchantments.FIRE_ASPECT}, new int[]{3, 1, 1, 1}, 10, 35)});

        WHITE_NORMAL_TRADES.put(1, new VillagerTrades.ITrade[]{new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.WOODEN_SPEAR.get(), 5, 1, 4), new EmeraldsForItemsTrade(ItemInit.WOODEN_ARROW.get(),4, 3, 5, 2)});
        WHITE_NORMAL_TRADES.put(2, new VillagerTrades.ITrade[]{new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.WOODEN_CHAKRAM.get(), 5, 1, 6), new EmeraldsForItemsTrade(ItemInit.STONE_ARROW.get(), 4,5, 5, 3)});
        WHITE_NORMAL_TRADES.put(3, new VillagerTrades.ITrade[]{new VillagerTrades.EnchantedItemForEmeraldsTrade(ItemInit.WOOD_BOOMERANG.get(), 5, 1, 8), new EmeraldsForItemsTrade(ItemInit.IRON_ARROW.get(), 4,7, 5, 4)});
        WHITE_NORMAL_TRADES.put(4, new VillagerTrades.ITrade[]{new DefinedEnchantedBookTrade(new Enchantment[]{Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_PROTECTION, Enchantments.BLAST_PROTECTION, Enchantments.PROJECTILE_PROTECTION}, new int[]{4,1,1,1}, 10, 13), new EmeraldsForItemsTrade(ItemInit.DIAMOND_ARROW.get(), 4,10, 5, 5)});
        WHITE_NORMAL_TRADES.put(4, new VillagerTrades.ITrade[]{new DefinedEnchantedBookTrade(new Enchantment[]{Enchantments.SMITE, Enchantments.SHARPNESS, Enchantments.PROTECTION, Enchantments.UNBREAKING, Enchantments.EFFICIENCY}, new int[]{4,1,1,1, 1}, 12, 20), new EmeraldsForItemsTrade(ItemInit.NETHERITE_ARROW.get(), 4,20, 4, 6), new EmeraldsForItemsTrade(ItemInit.EMERALD_ARROW.get(), 4,17, 4, 6)});

        addHermitTrades();
    }

    public static void addHermitTrades() {
        HERMIT_TRADES.put(1, new VillagerTrades.ITrade[]{new BasicTrade(10, new ItemStack(ItemInit.DINOPEDIA.get()), 1, 5)});
        HERMIT_TRADES.put(2, new VillagerTrades.ITrade[]{new DefinedEnchantedBookTrade(new Enchantment[]{EnchantmentInit.ICE_ENCHANTMENT.get(), Enchantments.LOOTING, Enchantments.MENDING, Enchantments.UNBREAKING}, new int[]{10, 10, 5, 75}, 5, 30)});
    }
}
