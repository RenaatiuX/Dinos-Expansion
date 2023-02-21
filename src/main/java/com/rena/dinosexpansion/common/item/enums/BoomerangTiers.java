package com.rena.dinosexpansion.common.item.enums;

import com.rena.dinosexpansion.common.config.DinosExpansionConfig;
import com.rena.dinosexpansion.common.entity.misc.BoomerangEntity;
import com.rena.dinosexpansion.common.item.TieredBoomerang;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public enum BoomerangTiers implements TieredBoomerang.BoomerangTier {
    WOOD(DinosExpansionConfig.WOOD_BOOMERANG_DAMAGE.get(), 25, 100, DinosExpansionConfig.WOOD_BOOMERANG_RANGE.get()),
    IRON(DinosExpansionConfig.IRON_BOOMERANG_DAMAGE.get(), 12, 273, DinosExpansionConfig.IRON_BOOMERANG_RANGE.get()),
    DIAMOND(DinosExpansionConfig.DIAMOND_BOOMERANG_DAMAGE.get(), 5, 853, DinosExpansionConfig.DIAMOND_BOOMERANG_RANGE.get());

    private final double damageAddition;
    private final int enchantability, durability, range;

    BoomerangTiers(double damageAddition, int enchantability, int durability, int range) {
        this.damageAddition = damageAddition;
        this.enchantability = enchantability;
        this.durability = durability;
        this.range = range;
    }

    @Override
    public double getDamageAddition() {
        return this.damageAddition;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public int getRange() {
        return this.range;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }
}
