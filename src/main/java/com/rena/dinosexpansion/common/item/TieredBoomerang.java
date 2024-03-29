package com.rena.dinosexpansion.common.item;

import com.rena.dinosexpansion.common.entity.misc.BoomerangEntity;
import com.rena.dinosexpansion.core.init.ItemInit;
import com.rena.dinosexpansion.core.init.SoundInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TieredBoomerang extends Item {

    private final BoomerangTier boomerangTier;

    public TieredBoomerang(Properties properties, BoomerangTier boomerangTier) {
        super(properties.maxDamage(boomerangTier.getDurability()));
        this.boomerangTier = boomerangTier;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        BoomerangEntity boom = new BoomerangEntity(worldIn, playerIn, playerIn.getHeldItem(handIn), handIn);
        boom.setDamage(boomerangTier.getDamageAddition());
        boom.setRange(boomerangTier.getRange());
        BlockPos currentPos = playerIn.getPosition();
        worldIn.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOOMERANG_THROW.get(), SoundCategory.PLAYERS, 0.6F, 1.0F);
        worldIn.addEntity(boom);
        playerIn.setHeldItem(handIn, ItemStack.EMPTY);
        stack.damageItem(1, playerIn, p -> p.sendBreakAnimation(handIn));
        showDurabilityBar(playerIn.getHeldItem(handIn));
        playerIn.setActiveHand(handIn);

        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    @Override
    public int getItemEnchantability() {
        return boomerangTier.getEnchantability();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        TieredBoomerang boomerang = getBoomerang();
        if (boomerang != null) {
            tooltip.add(StringTextComponent.EMPTY);
            tooltip.add((new TranslationTextComponent("dinosexpansion.modifiers.hand")).mergeStyle(TextFormatting.GRAY));
            tooltip.add((new StringTextComponent(" ")).appendSibling(new TranslationTextComponent("dinosexpansion.boomerang.damage", boomerangTier.getDamageAddition()).mergeStyle(TextFormatting.DARK_GREEN)));
            tooltip.add((new StringTextComponent(" ")).appendSibling(new TranslationTextComponent("dinosexpansion.boomerang.range", boomerangTier.getRange()).mergeStyle(TextFormatting.DARK_AQUA)));
        }
    }

    private TieredBoomerang getBoomerang() {
        TieredBoomerang[] boomerangs = {
                ItemInit.WOOD_BOOMERANG.get(),
                ItemInit.STONE_BOOMERANG.get(),
                ItemInit.IRON_BOOMERANG.get(),
                ItemInit.GOLDEN_BOOMERANG.get(),
                ItemInit.DIAMOND_BOOMERANG.get(),
                ItemInit.NETHERITE_BOOMERANG.get(),
                ItemInit.EMERALD_BOOMERANG.get(),
                ItemInit.RUBY_BOOMERANG.get(),
                ItemInit.SAPPHIRE_BOOMERANG.get()
        };

        for (TieredBoomerang boomerang : boomerangs) {
            if (this == boomerang) {
                return boomerang;
            }
        }
        return null;
    }

    public interface BoomerangTier {
        double getDamageAddition();

        int getEnchantability();

        /**
         * @return how long the boomerang will fly until it will return by itself 30 is normal
         */
        int getRange();

        int getDurability();
    }
}
