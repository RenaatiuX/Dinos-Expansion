package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.common.entity.util.BoatType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class CustomBoatItem extends Item {
    private static final Predicate<Entity> field_219989_a = EntityPredicates.NOT_SPECTATING.and(Entity::canBeCollidedWith);
    private final BoatType type;
    public CustomBoatItem(Properties properties, BoatType type) {
        super(properties);
        this.type = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
