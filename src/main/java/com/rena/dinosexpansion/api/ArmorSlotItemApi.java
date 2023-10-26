package com.rena.dinosexpansion.api;

import com.mojang.datafixers.util.Pair;
import com.rena.dinosexpansion.common.entity.DinosaurArmorSlotType;
import com.rena.dinosexpansion.common.item.armor.DinosaurArmorItem;
import com.rena.dinosexpansion.common.item.armor.DinosaurArmorMaterial;
import com.rena.dinosexpansion.common.item.armor.IDinosaurSaddle;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArmorSlotItemApi {

    private static final Map<Item, Pair<List<DinosaurArmorSlotType>, DinosaurArmorMaterial>> REGISTRY = new HashMap<>();
    private static final Map<Item, Integer> CHEST_RERGISTRY = new HashMap<>();


    public static void registerSaddle(Item item){
        register(item, null, DinosaurArmorSlotType.SADDLE);
    }

    /**
     * register ur items here that will have slot types, and item can have many slot types
     * also u can override old ones, be careful
     * call this from the {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent}
     *
     * @param item  the item this will be registered to
     * @param slots the slots this item should be able to go in
     */
    public static void register(Item item,DinosaurArmorMaterial material, DinosaurArmorSlotType... slots) {
        List<DinosaurArmorSlotType> wantedSlotTypes = Arrays.asList(slots);
        if (wantedSlotTypes.contains(DinosaurArmorSlotType.CHEST)) {
            throw new IllegalArgumentException(String.format("this register method does not register chests, use the other register method to register chests"));
        }
        if (REGISTRY.containsKey(item)) {
            List<DinosaurArmorSlotType> list = REGISTRY.get(item).getFirst();
            wantedSlotTypes.forEach(s -> {
                if (!list.contains(s)) {
                    list.add(s);
                }
            });
            REGISTRY.replace(item, Pair.of(list, material));
        } else {
            REGISTRY.put(item,Pair.of(Arrays.asList(slots), material));
        }
    }

    /**
     * register items that will work as chest for dinos here
     * @param chest
     * @param size the size of the chest when the dino wars it, not that there will be a maximum what the ches can have
     */
    public static void registerChest(Item chest, int size) {
        if (CHEST_RERGISTRY.containsKey(chest))
            throw new IllegalArgumentException(String.format("the item: %s was already registered as chest, first remove it, then register it back again", chest.getRegistryName()));
        CHEST_RERGISTRY.put(chest, size);
    }

    public static void remove(Item item, DinosaurArmorSlotType... slots) {
        if (REGISTRY.containsKey(item)) {
            List<DinosaurArmorSlotType> list = REGISTRY.get(item).getFirst();
            Arrays.asList(slots).forEach(list::remove);
            if (list.isEmpty()) {
                REGISTRY.remove(item);
            }
        }
    }

    public static void removeChest(Item chest) {
        if (CHEST_RERGISTRY.containsKey(chest)) {
            CHEST_RERGISTRY.remove(chest);
        }
    }

    public static boolean hasSlot(DinosaurArmorSlotType slot, ItemStack stack){
        if (stack.getItem() instanceof DinosaurArmorItem){
            if (((DinosaurArmorItem)stack.getItem()).getEquipmentSlot() == slot)
                return true;
        }
        if (slot == DinosaurArmorSlotType.SADDLE && stack.getItem() instanceof IDinosaurSaddle)
            return true;
        if (!REGISTRY.containsKey(stack.getItem()) && !CHEST_RERGISTRY.containsKey(stack.getItem()))
            return false;
        if (REGISTRY.containsKey(stack.getItem()) && REGISTRY.get(stack.getItem()).getFirst().contains(slot))
            return true;
        if (slot == DinosaurArmorSlotType.CHEST)
            return CHEST_RERGISTRY.containsKey(stack.getItem());
        return false;
    }

    /**
     * will return -1 when this stack isnt a chest
     * u can check this with {@link ArmorSlotItemApi#hasSlot(DinosaurArmorSlotType, ItemStack)}
     * @param stack
     * @return
     */
    public static int getChestSize(ItemStack stack){
        if (hasSlot(DinosaurArmorSlotType.CHEST, stack)){
            return CHEST_RERGISTRY.get(stack.getItem());
        }
        return -1;
    }

    public static boolean isSaddle(ItemStack stack){
        if (stack.getItem() instanceof IDinosaurSaddle)
            return true;
        if (!REGISTRY.containsKey(stack.getItem()))
            return false;
        return REGISTRY.get(stack.getItem()).getFirst().contains(DinosaurArmorSlotType.SADDLE);
    }

    /**
     * be careful this is null when the item is a saddle
     * @param stack
     * @return
     */
    public static DinosaurArmorMaterial getArmor(ItemStack stack){
        if (stack.getItem() instanceof DinosaurArmorItem){
            return ((DinosaurArmorItem)stack.getItem()).getArmorMaterial();
        }
        if (!REGISTRY.containsKey(stack.getItem())){
            throw new IllegalArgumentException("this stack neither extends from DinosaurArmorItem nor is present in the registry");
        }
        return REGISTRY.get(stack.getItem()).getSecond();
    }
}
